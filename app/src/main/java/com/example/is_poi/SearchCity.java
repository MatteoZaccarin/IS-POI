package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.example.is_poi.databinding.ActivitySearchCityBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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
        binding.leftMenu.name.setText("Benvenuto "+FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);
        viewmodel=MainActivity.viewModel;
        getAlberghi(this);
        getAgriturismo(this);
        getSentieri(this);
        getPisteScii(this);
        getPisteCiclabili(this);
        getEventi(this);
        Intent intent = getIntent();
        if(intent != null) {
            String valoreRecuperato = intent.getStringExtra("comune");
            setAscoltatori(this, valoreRecuperato);
            binding.toolbar.setTitle("Comune di "+valoreRecuperato);
            setLeftMenu(this);
        }
    }

    private void setLeftMenu(Context context){
        TextView eventi=findViewById(R.id.eventi);
        TextView profilo=findViewById(R.id.profilo);
        TextView logout=findViewById(R.id.logout);

        eventi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventiActivity.class);
                startActivity(intent);
            }
        });
        profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfiloActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    public  void setAscoltatori(Context context, String comune){
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventiAggiungiActivity.class);
                intent.putExtra("comune", comune);
                startActivity(intent);
            }
        });
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
                int cont=0;
                for(Alberghi a: alberghis){
                    if(a.TIPOLOGIA.compareTo("ALBERGO")==0){
                        if(areStringsEqualIgnoreCase(a.COMUNE,valoreRecuperato)){
                            Log.d("ciao",a.COMUNE);
                            a.icon=R.drawable.hotel;
                            alberghi.add(a);
                            cont++;
                        }

                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerAlberghi));
                /*if(cont>=3){
                    int heightInPx = (int) (500 * 2 + 0.5f);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) RW.getLayoutParams();
                    params.height = heightInPx; // Imposta l'altezza desiderata in pixel, ad esempio 100px
                    RW.setLayoutParams(params);
                }*/
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
                        a.image=R.drawable.sentiero;
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
    private void getPisteScii(Activity a) {
        viewmodel = MainActivity.viewModel;
        viewmodel.fetchPisteDaSci();
        viewmodel.getMyEsperienze_PisteSci().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> sentieris) {
                ArrayList<Esperienze> sentieri=new ArrayList<>();
                Intent intent = getIntent();
                String valoreRecuperato = intent.getStringExtra("comune");

                for(Esperienze a: sentieris){
                    if((a.PuntoDiPartenza.contains(valoreRecuperato)) || a.Titolo.toUpperCase().contains(valoreRecuperato.toUpperCase())){
                        Log.d("ciao",a.PuntoDiPartenza);
                        a.image=R.drawable.sci;
                        sentieri.add(a);
                    }else{
                        Log.d("xxx",a.Titolo.toUpperCase());
                        Log.d("bbb",valoreRecuperato.toUpperCase());
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerPisteScii));
                RW.setLayoutManager(new LinearLayoutManager(SearchCity.this));
                RW.setAdapter(new SciAdapter(a,sentieri ));
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });

    }
    private void getPisteCiclabili(Activity a) {
        viewmodel = MainActivity.viewModel;
        viewmodel.fetchOnlyRoadBike();
        viewmodel.getMyRoadBike().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> sentieris) {
                ArrayList<Esperienze> sentieri=new ArrayList<>();
                Intent intent = getIntent();
                String valoreRecuperato = intent.getStringExtra("comune");

                for(Esperienze a: sentieris){
                    if((a.PuntoDiPartenza.contains(valoreRecuperato)) || a.PuntoDiPartenza.toUpperCase().contains(valoreRecuperato.toUpperCase())){
                        Log.d("ciao",a.PuntoDiPartenza);
                        a.image=R.drawable.bici;
                        sentieri.add(a);
                    }else{
                        Log.d("xxx",a.Titolo.toUpperCase());
                        Log.d("bbb",valoreRecuperato.toUpperCase());
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerPisteCiclabili));
                RW.setLayoutManager(new LinearLayoutManager(SearchCity.this));
                RW.setAdapter(new CiclabiliMountainAdapter(a,sentieri ));
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });

    }


    private void getEventi(Activity a) {
        viewmodel = MainActivity.viewModel;
        viewmodel.fetchAllEventi();
        viewmodel.getAllEventi().observe(this, new Observer<ArrayList<Evento>>() {
            @Override
            public void onChanged(ArrayList<Evento> sentieris) {
                ArrayList<Evento> sentieri=new ArrayList<>();
                Intent intent = getIntent();
                String valoreRecuperato = intent.getStringExtra("comune");

                for(Evento a: sentieris){
                    if((a.Comune.contains(valoreRecuperato)) || a.Comune.toUpperCase().contains(valoreRecuperato.toUpperCase())){
                        sentieri.add(a);
                    }else{
                        Log.d("bbb",valoreRecuperato.toUpperCase());
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerEVENTI));
                RW.setLayoutManager(new LinearLayoutManager(SearchCity.this));
                RW.setAdapter(new EventiAdapter(a,sentieri ));
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });

    }

}