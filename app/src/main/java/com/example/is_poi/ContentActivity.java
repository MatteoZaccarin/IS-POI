package com.example.is_poi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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
                break;
            case "Piste ciclabili":
                Log.d("a","3");
                break;
            case "Alberghi":
                Log.d("a","4");
                break;
            case "Sentieri enogastronomici":
                Log.d("a","5");
                break;
        }
    }

    private void getPisteDaSci(){
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.fetchPisteDaSci();
    }

    private MainActivityViewModel viewModel;
    interface RequestPisteSci{
        @GET("export/json/Percorsi-Sci-di-Fondo-nel-Veneto.json")
        Call<ArrayList<Esperienze>> getPisteSci();
    }
}
