package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

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
    private DashboardViewModel viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewmodel=new ViewModelProvider(this).get(DashboardViewModel.class);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawer.openDrawer(GravityCompat.START);
            }
        });
        binding.leftMenu.textView2.setText("ciao pippo");

        viewmodel.fetchdata();
        viewmodel.nomiComuni().observe(this, list -> {
            if(list!=null){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(binding.getRoot().getContext(), android.R.layout.simple_dropdown_item_1line,list);
                binding.autoCompleteTextView.setAdapter(adapter);
            }
        });

        //butta dentro
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
}