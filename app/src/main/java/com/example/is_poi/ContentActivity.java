package com.example.is_poi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;



public class ContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_poi);

        Intent intent = getIntent();
        String poi_type = intent.getStringExtra("poi_type");
        Log.d("Arrivato", poi_type);


        setTypeOfPOI(poi_type);



        TextView nuovoTesto = new TextView(this);
        nuovoTesto.setText("WOOOOWWWW");

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_content_poi);
        layout.addView(nuovoTesto);

    }

    private void setTypeOfPOI(String value){
        switch (value){
            case "Piste da sci":
                Log.d("a","1");
                getPisteDaSci();
                break;
            case "Sentieri":
                Log.d("a","2");
                getSentieriStorici();
                break;
            case "Piste ciclabili":
                Log.d("a","3");
                break;
            case "Alberghi":
                Log.d("a","4");
                getListAlberghi();
                break;
            case "Sentieri enogastronomici":
                Log.d("a","5");
                break;
        }
    }

    private void getPisteDaSci(){
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.fetchPisteDaSci();
        viewModel.getMyEsperienze_PisteSci().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                for(Esperienze esp : esperienzes ){
                    Log.d("SCI", esp.Titolo);
                }
            }
        });
    }

    private void getSentieriStorici(){
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.fetchSentieri();

        viewModel.getMySentieriPanoramici_SentieriStorici().observe(this, new Observer<ArrayList<SentieriPanoramici>>() {
            @Override
            public void onChanged(ArrayList<SentieriPanoramici> sentieriPanoramicis) {
                for(SentieriPanoramici sp : sentieriPanoramicis ){
                    Log.d("sentieri", sp.Titolo);
                }
            }
        });
    }

    private void getListAlberghi(){
        viewModel =new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.fetchAlberghi();

        viewModel.getMyAlberghi().observe(this, new Observer<ArrayList<Alberghi>>() {
            @Override
            public void onChanged(ArrayList<Alberghi> alberghis) {
                for(Alberghi a: alberghis){
                    Log.d("alberghi",a.EMAIL);
                }
            }
        });
    }

    private MainActivityViewModel viewModel;
    interface RequestPisteSci{
        @GET("export/json/Percorsi-Sci-di-Fondo-nel-Veneto.json")
        Call<ArrayList<Esperienze>> getPisteSci();
    }
    interface  RequestSentieri{
        @GET("export/json/Sentieri-Storico-Culturali-e-Panoramici-nel-Veneto.json")
        Call<ArrayList<SentieriPanoramici>> getSentieri();
    }
    interface  RequestAlberghi{
        @GET("/export/json/Elenco-delle-Strutture-Ricettive-Turistiche-della-Regione-Veneto.json")
        Call<ArrayList<Alberghi>> getAlberghi();
    }
}
