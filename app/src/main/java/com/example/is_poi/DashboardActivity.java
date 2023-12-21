package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.is_poi.databinding.ActivityDashboardBinding;
import com.example.is_poi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawer.openDrawer(GravityCompat.START);
            }
        });
        binding.leftMenu.textView2.setText("ciao pippo");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit= new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        MainActivity.RequestComuni ru= retrofit.create(MainActivity.RequestComuni.class);

        ru.getComuni().enqueue(new retrofit2.Callback<ArrayList<Comuni>>() {
            @Override
            public void onResponse(Call<ArrayList<Comuni>> call, Response<ArrayList<Comuni>> response) {
                String nomiComuni[]=new String[response.body().size()];
                for(int i=0;i<response.body().size();i++) {
                    nomiComuni[i] = response.body().get(i).Comune;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(binding.getRoot().getContext(), android.R.layout.simple_dropdown_item_1line,nomiComuni);
                binding.autoCompleteTextView.setAdapter(adapter);

                binding.CercaComune.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean find=false;
                        for (String comune:nomiComuni) {
                            if (comune.equals(binding.autoCompleteTextView.getText().toString())){
                                find = true;
                                break;
                            } else
                                Log.d(comune, binding.autoCompleteTextView.getText().toString());
                        }
                        if(find){
                            //replaceFragment(new City_risultati());
                        }
                        else{
                            Toast errorToast = Toast.makeText(binding.getRoot().getContext(), "Inserisci un comune presente in Veneto, selezionalo dai consigli automatici!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }

                    }
                });
            }
            @Override
            public void onFailure(Call<ArrayList<Comuni>> call, Throwable t) {
                return;
            }
        });

    }
}