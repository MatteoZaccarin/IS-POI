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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<DashboardUiState> uiState =
        new MutableLiveData<>(new DashboardUiState(null));
    public LiveData<DashboardUiState> getUiState() {
        return uiState;
    }

    public MutableLiveData<ArrayList<Esperienze>> myEsperienze_PisteSci;
    public MutableLiveData<ArrayList<SentieriPanoramici>>  mySentieriPanoramici_SentieriStorici;
    public MutableLiveData<ArrayList<Alberghi>> myAlberghi;

    public MutableLiveData<ArrayList<SentieriPanoramici>> getMySentieriPanoramici_SentieriStorici() {
        if(mySentieriPanoramici_SentieriStorici==null){
            mySentieriPanoramici_SentieriStorici=new MutableLiveData<ArrayList<SentieriPanoramici>>();
        }
        return mySentieriPanoramici_SentieriStorici;
    }

    public MutableLiveData<ArrayList<Esperienze>> getMyEsperienze_PisteSci(){
        if(myEsperienze_PisteSci==null){
            myEsperienze_PisteSci=new MutableLiveData<ArrayList<Esperienze>>();
        }

        return myEsperienze_PisteSci;
    }

    public MutableLiveData<ArrayList<Alberghi>> getMyAlberghi(){
        if(myAlberghi==null){
            myAlberghi=new MutableLiveData<ArrayList<Alberghi>>();
        }
        return myAlberghi;
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
                MainActivityViewModel.this.myEsperienze_PisteSci.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Esperienze>> call, Throwable throwable) {
                Log.d("error", "chiamata API Piste da scii");
            }
        });
    }

    public void fetchSentieri(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        ContentActivity.RequestSentieri request = retrofit.create(ContentActivity.RequestSentieri.class);

        request.getSentieri().enqueue(new Callback<ArrayList<SentieriPanoramici>>() {
            @Override
            public void onResponse(Call<ArrayList<SentieriPanoramici>> call, Response<ArrayList<SentieriPanoramici>> response) {
                MainActivityViewModel.this.mySentieriPanoramici_SentieriStorici.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<SentieriPanoramici>> call, Throwable t) {
                Log.d("error", "chiamata API sentieri storici");
            }
        });
    }

    public void fetchAlberghi(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        ContentActivity.RequestAlberghi request = retrofit.create(ContentActivity.RequestAlberghi.class);

        request.getAlberghi().enqueue(new Callback<ArrayList<Alberghi>>() {
            @Override
            public void onResponse(Call<ArrayList<Alberghi>> call, Response<ArrayList<Alberghi>> response) {
                MainActivityViewModel.this.myAlberghi.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Alberghi>> call, Throwable t) {
                Log.d("error", "chiamata API alberghi");
            }
        });
    }
}
