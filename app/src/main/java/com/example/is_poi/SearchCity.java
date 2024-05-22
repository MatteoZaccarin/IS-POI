package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.example.is_poi.databinding.ActivitySearchCityBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchCity extends AppCompatActivity {

    public static boolean areStringsEqualIgnoreCase(String str1, String str2) {
        // Converti entrambe le stringhe in minuscolo
        String normalizedStr1 = str1.toLowerCase();
        String normalizedStr2 = str2.toLowerCase();

        // Confronta le stringhe risultanti
        return normalizedStr1.equals(normalizedStr2);
    }

    private ActivitySearchCityBinding binding;
    private MainActivityViewModel viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(view ->
                binding.drawer.openDrawer(GravityCompat.START)
        );
        viewmodel=MainActivity.viewModel;
        getAlberghi(this);
        getAgriturismo(this);
        getSentieri(this);
        Intent intent = getIntent();
        if(intent != null) {
            String valoreRecuperato = intent.getStringExtra("comune");
        }

    }
    private void getAlberghi(Activity a) {
        viewmodel = MainActivity.viewModel;
        viewmodel.fetchAlberghi();
        viewmodel.getMyStrutture().observe(this, new Observer<ArrayList<Alberghi>>() {
            @Override
            public void onChanged(ArrayList<Alberghi> alberghis) {
                ArrayList<Alberghi> alberghi=new ArrayList<>();
                Intent intent = getIntent();
                String valoreRecuperato = intent.getStringExtra("comune");

                for(Alberghi a: alberghis){
                    if(a.TIPOLOGIA.compareTo("ALBERGO")==0){
                        if(areStringsEqualIgnoreCase(a.COMUNE,valoreRecuperato)){
                            Log.d("ciao",a.COMUNE);
                            a.icon=R.drawable.hotel;
                            alberghi.add(a);
                        }

                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerAlberghi));
                RW.setLayoutManager(new LinearLayoutManager(SearchCity.this));
                RW.setAdapter(new AlberghiAdapter(a, alberghi));
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });

    }
    private void getAgriturismo(Activity a) {
        viewmodel = MainActivity.viewModel;
        viewmodel.fetchAlberghi();
        viewmodel.getMyStrutture().observe(this, new Observer<ArrayList<Alberghi>>() {
            @Override
            public void onChanged(ArrayList<Alberghi> alberghis) {
                ArrayList<Alberghi> alberghi=new ArrayList<>();
                Intent intent = getIntent();
                String valoreRecuperato = intent.getStringExtra("comune");

                for(Alberghi a: alberghis){
                    if(a.TIPOLOGIA.compareTo("AGRITURISMO")==0){
                        if(areStringsEqualIgnoreCase(a.COMUNE,valoreRecuperato)){
                            Log.d("ciao",a.COMUNE);
                            a.icon=R.drawable.agri;
                            alberghi.add(a);
                        }

                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerAgriturismo));
                RW.setLayoutManager(new LinearLayoutManager(SearchCity.this));
                RW.setAdapter(new AlberghiAdapter(a, alberghi));
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });

    }
    private void getSentieri(Activity a) {
        viewmodel = MainActivity.viewModel;
        viewmodel.fetchSentieri();
        viewmodel.getMySentieriPanoramici().observe(this, new Observer<ArrayList<SentieriPanoramici>>() {
            @Override
            public void onChanged(ArrayList<SentieriPanoramici> sentieris) {
                ArrayList<SentieriPanoramici> sentieri=new ArrayList<>();
                Intent intent = getIntent();
                String valoreRecuperato = intent.getStringExtra("comune");

                for(SentieriPanoramici a: sentieris){
                    if(areStringsEqualIgnoreCase(a.PuntoDiPartenza,valoreRecuperato)){
                        Log.d("ciao",a.PuntoDiPartenza);
                        sentieri.add(a);
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerSentieri));
                RW.setLayoutManager(new LinearLayoutManager(SearchCity.this));
                RW.setAdapter(new SentieriAdapter(a,sentieri ));
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });

    }
}