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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                getPisteCiclabili();
                break;
            case "Alberghi":
                Log.d("a","4");
                getListAlberghi();
                break;
            case "Sentieri enogastronomici":
                Log.d("a","5");
                getSentieriEnogastronomici();
                break;
        }
    }

    private void getPisteDaSci(){
        viewModel = MainActivity.viewModel;
        viewModel.fetchPisteDaSci();
        viewModel.getMyEsperienze_PisteSci().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                for(Esperienze esp : esperienzes ){
                    esp.image=R.drawable.sci;
                    Log.d("link", esp.consigli);
                }

                RecyclerView RW =findViewById((R.id.recyclerview));
                RW.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                RW.setAdapter(new SciAdapter(getApplicationContext(), esperienzes));

            }
        });
    }
    private void getPisteCiclabili(){
        viewModel = MainActivity.viewModel;
        viewModel.fetchPisteCiclabili();

        viewModel.getMyRoadBike().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                for(Esperienze esp : esperienzes){
                    Log.d("roadbike", esp.Titolo);
                }
            }
        });

        viewModel.getMyMountainBike().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                for(Esperienze esp: esperienzes){
                    Log.d("mountainbike", esp.Titolo);
                }
            }
        });
        viewModel.getMyPisteCiclabili().observe(this, new Observer<ArrayList<SentieriPanoramici>>() {
            @Override
            public void onChanged(ArrayList<SentieriPanoramici> sentieriPanoramicis) {
                for (SentieriPanoramici sp: sentieriPanoramicis){
                    Log.d("piste ciclabili", sp.Titolo);
                }
            }
        });
    }
    private void getSentieriStorici(){
        viewModel = MainActivity.viewModel;
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
        viewModel =MainActivity.viewModel;
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

    private void getSentieriEnogastronomici(){
        viewModel=MainActivity.viewModel;
        viewModel.fetchSentieriEnogastronomici();

        viewModel.getMySentieriEnogastronomici().observe(this, new Observer<ArrayList<SentieriPanoramici>>() {
            @Override
            public void onChanged(ArrayList<SentieriPanoramici> sentieriPanoramicis) {
                for(SentieriPanoramici sp : sentieriPanoramicis){
                    Log.d("sentieri enogastronomici", sp.Titolo);
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
    interface RequestRoadBike{
        @GET("export/json/Percorsi-Road-Bike-nel-Veneto.json")
        Call<ArrayList<Esperienze>> getRoadBike();
    }
    interface  RequestMountainBike{
        @GET("export/json/Percorsi-Mountain-Bike-nel-Veneto.json")
        Call<ArrayList<Esperienze>> getMountainBike();
    }
    interface RequestSentieriEnogastronomici{
        @GET("export/json/Sentieri-Enogastronomici-nel-Veneto.json")
        Call<ArrayList<SentieriPanoramici>> getSentieriEnogastronomici();
    }
    interface  RequestPisteCiclabili{
        @GET("export/json/Percorsi-Ciclabili-Panoramici-nel-Veneto.json")
        Call<ArrayList<SentieriPanoramici>> getPisteCiclabili();
    }
}
