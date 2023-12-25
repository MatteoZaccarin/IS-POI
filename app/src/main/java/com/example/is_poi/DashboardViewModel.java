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
    private final MutableLiveData<DashboardUiState> uiState =
            new MutableLiveData<>(new DashboardUiState(null));
    public LiveData<DashboardUiState> getUiState() {
        return uiState;
    }

    public void fetchMunicipallyData() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        MainActivity.RequestAlberghi request = retrofit.create(MainActivity.RequestAlberghi.class);

        request.getAlberghi().enqueue(new retrofit2.Callback<ArrayList<Alberghi>>() {
            @Override
            public void onResponse(
                    @NonNull Call<ArrayList<Alberghi>> call,
                    @NonNull Response<ArrayList<Alberghi>> response
            ) {
                List<String> mapList = new ArrayList<>();
                if (response.body() != null) {
                    for (Alberghi alberghi : response.body()) {
                        if (!mapList.contains(alberghi.COMUNE)) {
                            mapList.add(alberghi.COMUNE);
                        }
                    }
                }
                uiState.postValue(new DashboardUiState(mapList));
            }
            @Override
            public void onFailure(
                    @NonNull Call<ArrayList<Alberghi>> call,
                    @NonNull Throwable throwable
            ) {
                return;
            }
        });
    }
}
