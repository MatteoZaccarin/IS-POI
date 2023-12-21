package com.example.is_poi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search_city extends Fragment {
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line,nomiComuni);
                AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
                textView.setAdapter(adapter);

                Button b=view.findViewById(R.id.Cerca_comune);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean find=false;
                        for (String comune:nomiComuni) {
                            if (comune.equals(textView.getText().toString())){
                                find = true;
                                break;
                            } else
                                Log.d(comune, textView.getText().toString());
                        }
                        if(find)
                            replaceFragment(new City_risultati());
                        else{
                            Toast errorToast = Toast.makeText(getActivity(), "Inserisci un comune presente in Veneto, selezionalo dai consigli automatici!", Toast.LENGTH_SHORT);
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