package com.example.is_poi;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<String>> _nomiComuni=new MutableLiveData<>(null);
    public LiveData<List<String>> nomiComuni(){
        return _nomiComuni;
    }
    public void fetchdata(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit= new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        MainActivity.RequestComuni ru= retrofit.create(MainActivity.RequestComuni.class);

        ru.getComuni().enqueue(new retrofit2.Callback<ArrayList<Comuni>>() {
            @Override
            public void onResponse(Call<ArrayList<Comuni>> call, Response<ArrayList<Comuni>> response) {
                ArrayList<String> nomiComuni=new ArrayList<>;
                for(int i=0;i<response.body().size();i++) {
                    nomiComuni[i] = response.body().get(i).Comune;
                }
                _nomiComuni.setValue(List.of(nomiComuni));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(binding.getRoot().getContext(), android.R.layout.simple_dropdown_item_1line,nomiComuni);
                binding.autoCompleteTextView.setAdapter(adapter);


            }
            @Override
            public void onFailure(Call<ArrayList<Comuni>> call, Throwable t) {
                return;
            }
        });
    }
}
