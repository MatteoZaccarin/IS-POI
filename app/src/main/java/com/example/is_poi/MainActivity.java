package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.is_poi.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    interface RequestAlberghi{
        @GET("/export/json/Elenco-delle-Strutture-Ricettive-Turistiche-della-Regione-Veneto.json")
        Call<ArrayList<Alberghi>> getAlberghi();
    }
    interface RequestComuni{
        @GET("/export/json/SUPERFICIE-TERRITORIALE-IN-KMQ-COMUNI-DEL-VENETO.json")
        Call<ArrayList<Comuni>> getComuni();
    }
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setContentView(binding.getRoot());

        viewModel.fetchMunicipallyData();

        manageViews();
        initObservers();
    }

    private void initObservers() {
        viewModel.getUiState().observe(this, uiState -> {
            if (uiState.municipalities != null) {
                // Update adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    binding.getRoot().getContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    uiState.municipalities
                );
                binding.autoCompleteTextView.setAdapter(adapter);
            }
        });
    }

    private void manageViews() {
        binding.toolbar.setNavigationOnClickListener(view ->
            binding.drawer.openDrawer(GravityCompat.START)
        );
        binding.autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selected = (String) adapterView.getItemAtPosition(i);
            Intent intent=new Intent(getApplicationContext(), SearchCity.class);
            startActivity(intent);
            intent.putExtra("comune",selected);
            Bundle b=new Bundle();
            //intent.putExtra("alberghi",viewModel.getUiState());
            finish();
        });

        /*messo la lista dei POI nel menù a tendina*/
        String[] s=getResources().getStringArray(R.array.poi_type);
        ArrayAdapter<String> arrayApp=new ArrayAdapter<String>(binding.getRoot().getContext(), R.layout.dropdown_item, s);
        binding.autoCompleteTextView2.setAdapter(arrayApp);

        /*prendo l'input selezionato*/
        binding.autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prova=parent.getItemAtPosition(position).toString();
                Log.d("occhio!!! -> ",prova);
                /*mi basta capire come passare sto valore dall'altra parte e sopratutto se devo metterlo dentro a queste view*/
            }
        });

    }
}