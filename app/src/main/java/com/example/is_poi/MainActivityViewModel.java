package com.example.is_poi;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<DashboardUiState> uiState =
        new MutableLiveData<DashboardUiState>(new DashboardUiState(null, null));
    public LiveData<DashboardUiState> getUiState() {
        return uiState;
    }

    public MutableLiveData<ArrayList<Esperienze>> myEsperienze_PisteSci;
    public MutableLiveData<ArrayList<SentieriPanoramici>>  mySentieriPanoramici;
    public MutableLiveData<ArrayList<Alberghi>> myStrutture;
    public MutableLiveData<ArrayList<Esperienze>> myRoadBike;
    public MutableLiveData<ArrayList<Esperienze>> myMountainBike;
    public MutableLiveData<ArrayList<Esperienze>> myPisteCiclabili;
    public MutableLiveData<ArrayList<Evento>> myEventi;
    public MutableLiveData<ArrayList<Evento>> AllEventi;
    public MutableLiveData<ArrayList<Evento>> getMyEventi(){
        if(myEventi==null){
            myEventi=new MutableLiveData<ArrayList<Evento>>();
        }
        return myEventi;
    }

    public MutableLiveData<ArrayList<Evento>> getAllEventi(){
        if(AllEventi==null){
            AllEventi=new MutableLiveData<ArrayList<Evento>>();
        }
        return AllEventi;
    }

    public MutableLiveData<ArrayList<Alberghi>> getMyStrutture(){
        if(myStrutture==null){
            myStrutture=new MutableLiveData<ArrayList<Alberghi>>();
        }
        return myStrutture;
    }

    public MutableLiveData<ArrayList<Esperienze>> getMyPisteCiclabili(){
        if(myPisteCiclabili==null){
            myPisteCiclabili=new MutableLiveData<ArrayList<Esperienze>>();
        }
        return myPisteCiclabili;
    }
    public MutableLiveData<ArrayList<Esperienze>> getMyMountainBike(){
        if(myMountainBike==null){
            myMountainBike=new MutableLiveData<ArrayList<Esperienze>>();
        }
        return myMountainBike;
    }

    public MutableLiveData<ArrayList<Esperienze>> getMyRoadBike(){
        if(myRoadBike==null){
            myRoadBike=new MutableLiveData<ArrayList<Esperienze>>();
        }
        return myRoadBike;
    }

    public MutableLiveData<ArrayList<SentieriPanoramici>> getMySentieriPanoramici() {
        if(mySentieriPanoramici==null){
            mySentieriPanoramici=new MutableLiveData<ArrayList<SentieriPanoramici>>();
        }
        return mySentieriPanoramici;
    }

    public MutableLiveData<ArrayList<Esperienze>> getMyEsperienze_PisteSci(){
        if(myEsperienze_PisteSci==null){
            myEsperienze_PisteSci=new MutableLiveData<ArrayList<Esperienze>>();
        }

        return myEsperienze_PisteSci;
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
                List<Comuni> mapList = new ArrayList<>();
                if (response.body() != null) {
                    for (Comuni temp : response.body()) {
                        if (!mapList.contains(temp)) {
                            mapList.add(temp);
                        }
                    }
                }
                uiState.postValue(new DashboardUiState(mapList,null));
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
        if(myEsperienze_PisteSci==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
            ContentActivity.RequestPisteSci request = retrofit.create(ContentActivity.RequestPisteSci.class);

            request.getPisteSci().enqueue(new retrofit2.Callback<ArrayList<Esperienze>>() {
                @Override
                public void onResponse(Call<ArrayList<Esperienze>> call, Response<ArrayList<Esperienze>> response) {
                    MainActivityViewModel.this.myEsperienze_PisteSci.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Esperienze>> call, Throwable throwable) {
                    Log.e("error", "chiamata API Piste da scii");
                }
            });
        }
    }

    public void fetchSentieri(){
        ArrayList<SentieriPanoramici> array=new ArrayList<>();
        if(mySentieriPanoramici==null){
            OkHttpClient.Builder httpClient1 = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });
            Retrofit retrofit1 = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient1.build()).build();
            ContentActivity.RequestSentieriEnogastronomici request1 = retrofit1.create(ContentActivity.RequestSentieriEnogastronomici.class);

            request1.getSentieriEnogastronomici().enqueue(new Callback<ArrayList<SentieriPanoramici>>() {
                @Override
                public void onResponse(Call<ArrayList<SentieriPanoramici>> call, Response<ArrayList<SentieriPanoramici>> response) {
                    array.addAll(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<SentieriPanoramici>> call, Throwable t) {
                    Log.e("error", "chiamata API Sentieri Enogastronomici");
                }
            });

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
            ContentActivity.RequestSentieri request = retrofit.create(ContentActivity.RequestSentieri.class);

            request.getSentieri().enqueue(new Callback<ArrayList<SentieriPanoramici>>() {
                @Override
                public void onResponse(Call<ArrayList<SentieriPanoramici>> call, Response<ArrayList<SentieriPanoramici>> response) {
                    array.addAll(response.body());
                    MainActivityViewModel.this.mySentieriPanoramici.setValue(array);
                }
                @Override
                public void onFailure(Call<ArrayList<SentieriPanoramici>> call, Throwable t) {
                    Log.e("error", "chiamata API sentieri storici");
                }
            });
        }
    }

    public void fetchAlberghi(){
        if(myStrutture==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });

            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
            ContentActivity.RequestAlberghi request = retrofit.create(ContentActivity.RequestAlberghi.class);
            Log.d("send","request API alberghi");
            request.getAlberghi().enqueue(new Callback<ArrayList<Alberghi>>() {
                @Override
                public void onResponse(Call<ArrayList<Alberghi>> call, Response<ArrayList<Alberghi>> response) {
                    MainActivityViewModel.this.myStrutture.postValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Alberghi>> call, Throwable t) {
                    MainActivityViewModel.this.myStrutture.setValue(null);
                    Log.e("error", "chiamata API alberghi");
                }
            });
        }

    }
    public void fetchPisteCiclabili(){
        if(myRoadBike==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
            ContentActivity.RequestRoadBike request = retrofit.create(ContentActivity.RequestRoadBike.class);

            request.getRoadBike().enqueue(new Callback<ArrayList<Esperienze>>() {
                @Override
                public void onResponse(Call<ArrayList<Esperienze>> call, Response<ArrayList<Esperienze>> response) {
                    MainActivityViewModel.this.myRoadBike.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Esperienze>> call, Throwable t) {
                    Log.e("error","chiamata API roadbike");
                }
            });
        }

        if(myMountainBike==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
            ContentActivity.RequestMountainBike request1 = retrofit.create(ContentActivity.RequestMountainBike.class);
            request1.getMountainBike().enqueue(new Callback<ArrayList<Esperienze>>() {
                @Override
                public void onResponse(Call<ArrayList<Esperienze>> call, Response<ArrayList<Esperienze>> response) {
                    MainActivityViewModel.this.myMountainBike.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Esperienze>> call, Throwable t) {
                    Log.e("error", "chiamata API mountainbike");
                }
            });
        }

        if(myPisteCiclabili==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Response originalResponse = chain.proceed(chain.request());
                            MediaType mediaType = MediaType.parse("application/json; charset=ISO-8859-1");
                            ResponseBody modifiedBody = ResponseBody.create(mediaType, originalResponse.body().bytes());
                            return originalResponse.newBuilder().body(modifiedBody).build();
                        }
                    });
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
            ContentActivity.RequestPisteCiclabili request1 = retrofit.create(ContentActivity.RequestPisteCiclabili.class);
            request1.getPisteCiclabili().enqueue(new Callback<ArrayList<Esperienze>>() {
                @Override
                public void onResponse(Call<ArrayList<Esperienze>> call, Response<ArrayList<Esperienze>> response) {
                    for(Esperienze esp: response.body()){
                        esp.Categoria="Piste Ciclabili";
                    }
                    MainActivityViewModel.this.myPisteCiclabili.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Esperienze>> call, Throwable t) {
                    Log.e("error", "chiamata API mountainbike");
                }
            });
        }
    }
    public void fetchEventi(){
        Query q  = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app").getReference("eventi").orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Evento> eventi=new ArrayList<>();
                // Itera su ogni risultato della query
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Evento e=childSnapshot.getValue(Evento.class);
                    e.id=childSnapshot.getKey();
                    if(e.Creatore.compareTo(FirebaseAuth.getInstance().getCurrentUser().getEmail())==0){
                        eventi.add(e);
                    }
                }
                myEventi.setValue(eventi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("errore interrogazione eventi firebase", error.getDetails());
            }
        });
    }
    public void fetchAllEventi() {
        Query q = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app").getReference("eventi").orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Evento> eventi = new ArrayList<>();
                // Itera su ogni risultato della query
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Evento e = childSnapshot.getValue(Evento.class);
                    e.id = childSnapshot.getKey();
                    eventi.add(e);
                }
                AllEventi.setValue(eventi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("errore interrogazione eventi firebase", error.getDetails());
            }
        });
    }
}
