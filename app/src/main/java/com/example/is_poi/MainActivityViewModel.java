package com.example.is_poi;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<DashboardUiState> uiState =
        new MutableLiveData<>(new DashboardUiState(null));
    public LiveData<DashboardUiState> getUiState() {
        return uiState;
    }

    public void fetchMunicipallyData() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        MainActivity.RequestComuni request = retrofit.create(MainActivity.RequestComuni.class);

        request.getComuni().enqueue(new retrofit2.Callback<ArrayList<Comuni>>() {
            @Override
            public void onResponse(
                @NonNull Call<ArrayList<Comuni>> call,
                @NonNull Response<ArrayList<Comuni>> response
            ) {
                List<String> mapList = new ArrayList<>();
                if (response.body() != null) {
                    for (Comuni temp : response.body()) {
                        if (!mapList.contains(temp.Comune)) {
                            mapList.add(temp.Comune);
                        }
                    }
                }
                uiState.postValue(new DashboardUiState(mapList));
            }
            @Override
            public void onFailure(
                @NonNull Call<ArrayList<Comuni>> call,
                @NonNull Throwable throwable
            ) {
                return;
            }
        });
    }

    public void fetchPisteDaSci(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        ContentActivity.RequestPisteSci request = retrofit.create(ContentActivity.RequestPisteSci.class);

        request.getPisteSci().enqueue(new retrofit2.Callback<ArrayList<Esperienze>>() {
            @Override
            public void onResponse(Call<ArrayList<Esperienze>> call, Response<ArrayList<Esperienze>> response) {
                for (Esperienze esp : response.body()) {
                    Log.d("esp", esp.Titolo);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Esperienze>> call, Throwable throwable) {
                Log.d("error", "chiamata API Piste da scii");
            }
        });
    }
}
