package com.example.is_poi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.is_poi.databinding.ActivityContentPoiBinding;
import com.example.is_poi.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;



public class ContentActivity extends AppCompatActivity {
    private ActivityContentPoiBinding binding;
    String poi_type;
    private SwipeRefreshLayout swipeRefreshLayout;
    Activity myActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        poi_type = intent.getStringExtra("poi_type");

        myActivity=this;
        binding = ActivityContentPoiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(view ->
                binding.drawerLayout.openDrawer(GravityCompat.START)
        );
        binding.toolbar.setTitle(poi_type);
        binding.leftMenu.name.setText("Benvenuto "+FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        setTypeOfPOI(poi_type, this);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        setLeftMenu(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setTypeOfPOI(poi_type, myActivity);
            }
        });
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

    private void setTypeOfPOI(String value, Activity a){
        switch (value){
            case "Piste da sci":
                Log.d("a","1");
                getPisteDaSci(a);
                break;
            case "Sentieri":
                Log.d("a","2");
                getSentieriStorici(a);
                break;
            case "Piste ciclabili":
                Log.d("a","3");
                getPisteCiclabili(a);
                break;
            case "Alberghi":
                Log.d("a","4");
                getListAlberghi(a);
                break;
            case "Agriturismo":
                Log.d("a","5");
                getListAgriturismo(a);
                break;
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getListAgriturismo(Activity a) {
        viewModel = MainActivity.viewModel;
        viewModel.fetchAlberghi();
        viewModel.getMyStrutture().observe(this, new Observer<ArrayList<Alberghi>>() {
            @Override
            public void onChanged(ArrayList<Alberghi> alberghis) {
                ArrayList<Alberghi> soloAgriturismi=new ArrayList<>();
                for(Alberghi a: alberghis){
                    if(a.TIPOLOGIA.compareTo("AGRITURISMO")==0){
                        a.icon=R.drawable.agri;
                        soloAgriturismi.add(a);
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerview));
                RW.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                RW.setAdapter(new AlberghiAdapter(a, soloAgriturismi));
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
    }

    private void getPisteDaSci(Activity a){
        viewModel = MainActivity.viewModel;
        viewModel.fetchPisteDaSci();
        viewModel.getMyEsperienze_PisteSci().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                // spostare sta roba nel view model
                for(Esperienze esp : esperienzes ){
                    esp.image=R.drawable.sci;
                    Log.d("link", esp.consigli);
                }

                RecyclerView RW =findViewById((R.id.recyclerview));
                RW.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                RW.setAdapter(new SciAdapter(a, esperienzes));
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
    }
    private void getPisteCiclabili(Activity a){
        viewModel = MainActivity.viewModel;
        viewModel.fetchPisteCiclabili();
        RecyclerView RW =findViewById((R.id.recyclerview));
        RW.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
        ArrayList<Esperienze> espContainer=new ArrayList<Esperienze>();
        viewModel.getMyRoadBike().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                // spostare sta roba nel view model
                for(Esperienze esp : esperienzes){
                    Log.d("roadbike", esp.Titolo);
                    esp.image=R.drawable.bici;
                }
                espContainer.addAll(esperienzes);
            }
        });

        viewModel.getMyMountainBike().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                // spostare sta roba nel view model
                for(Esperienze esp: esperienzes){
                    Log.d("mountainbike", esp.Titolo);
                    esp.image=R.drawable.mbike;
                }
                espContainer.addAll(esperienzes);
            }
        });
        viewModel.getMyPisteCiclabili().observe(this, new Observer<ArrayList<Esperienze>>() {
            @Override
            public void onChanged(ArrayList<Esperienze> esperienzes) {
                // spostare sta roba nel view model
                for (Esperienze sp: esperienzes){
                    Log.d("piste ciclabili", sp.Titolo);
                    sp.image=R.drawable.bici;
                }
                espContainer.addAll(esperienzes);
                RW.setAdapter(new CiclabiliMountainAdapter(a,espContainer));
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });


    }
    private void getSentieriStorici(Activity a){
        viewModel = MainActivity.viewModel;
        viewModel.fetchSentieri();

        viewModel.getMySentieriPanoramici().observe(this, new Observer<ArrayList<SentieriPanoramici>>() {
            @Override
            public void onChanged(ArrayList<SentieriPanoramici> sentieriPanoramicis) {
                // spostare sta roba nel view model
                for(SentieriPanoramici sp : sentieriPanoramicis ){
                    Log.d("sentieri", sp.Titolo);
                    if(sp.Categoria.compareTo("Storico-Culturale")==0){
                        sp.image=R.drawable.sentiero;
                    }else{
                        if(sp.Categoria.compareTo("Panoramico")==0){
                            sp.image=R.drawable.panorama;
                        }else{
                            //enogastronomico
                            sp.image=R.drawable.eno;
                        }
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerview));
                RW.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                RW.setAdapter(new SentieriAdapter(a, sentieriPanoramicis));
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
    }
    private void getListAlberghi(Activity a){
        viewModel =MainActivity.viewModel;
        viewModel.fetchAlberghi();
        viewModel.getMyStrutture().observe(this, new Observer<ArrayList<Alberghi>>() {
            @Override
            public void onChanged(ArrayList<Alberghi> alberghis) {
                ArrayList<Alberghi> soloAlberghi=new ArrayList<>();
                for(Alberghi a: alberghis){
                    if(a.TIPOLOGIA.compareTo("ALBERGO")==0){
                        a.icon=R.drawable.hotel;
                        soloAlberghi.add(a);
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerview));
                RW.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                RW.setAdapter(new AlberghiAdapter(a, soloAlberghi));
                findViewById(R.id.progressBar).setVisibility(View.GONE);
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
        Call<ArrayList<Esperienze>> getPisteCiclabili();
    }



}
