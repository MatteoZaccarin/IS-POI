package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.is_poi.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.io.UnsupportedEncodingException;
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
    public static MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setContentView(binding.getRoot());
        viewModel.fetchMunicipallyData();
        manageViews(this);
        setLeftMenu(this);
        initObservers();
    }

    private void initObservers() {
        viewModel.getUiState().observe(this, uiState -> {
            if (uiState.comuni != null) {
                // Update adapter
                java.util.List<String> listaComuni=new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    binding.getRoot().getContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    uiState.ritornaStringheComuni()
                );
                binding.autoCompleteTextView.setAdapter(adapter);
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

    private void manageViews(Context context) {
        binding.toolbar.setNavigationOnClickListener(view ->
            binding.drawer.openDrawer(GravityCompat.START)
        );
        binding.leftMenu.name.setText("Benvenuto "+FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);
        binding.autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selected = (String) adapterView.getItemAtPosition(i);
            Intent intent=new Intent(getApplicationContext(), SearchCity.class);
            intent.putExtra("comune",selected);
            startActivity(intent);
        });

        /*messo la lista dei POI nel menù a tendina*/
        Log.d("encoding",System.getProperty("file.encoding"));

        String[] s=getResources().getStringArray(R.array.poi_type);
        ArrayAdapter<String> arrayApp=new ArrayAdapter<String>(binding.getRoot().getContext(), R.layout.dropdown_item, s);
        binding.autoCompleteTextView2.setAdapter(arrayApp);

        /*prendo l'input selezionato*/
        binding.autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String poi=parent.getItemAtPosition(position).toString();
                Log.d("occhio!!! -> ",poi);
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("poi_type", poi);
                startActivity(intent);
            }
        });

    }
}