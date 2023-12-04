package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class MainActivity extends AppCompatActivity {
    interface RequestUser{
        @GET("/export/json/Elenco-delle-Strutture-Ricettive-Turistiche-della-Regione-Veneto.json")
        Call<ArrayList<Alberghi>> getUser();
    }
    FirebaseAuth auth;
    FirebaseUser user;
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;

        FirebaseUser user;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView;
        textView= findViewById((R.id.user_details));
        /* https://www.veneto.eu/static/opendata/dove-alloggiare.csv*/
        // https://dati.veneto.it/export/json/Elenco-delle-Strutture-Ricettive-Turistiche-della-Regione-Veneto.json
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);


        Retrofit retrofit= new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

        RequestUser ru= retrofit.create(RequestUser.class);

        TextView finalTextView = textView;
        ru.getUser().enqueue(new Callback<ArrayList<Alberghi>>() {
            @Override
            public void onResponse(Call<ArrayList<Alberghi>> call, Response<ArrayList<Alberghi>> response) {
                finalTextView.setText(response.body().get(12).COMUNE);
            }

            @Override
            public void onFailure(Call<ArrayList<Alberghi>> call, Throwable t) {
                finalTextView.setText(t.getMessage());
            }
        });



        auth=FirebaseAuth.getInstance();
        button=findViewById(R.id.logout);
        textView=findViewById(R.id.user_details);
        user= auth.getCurrentUser();
        if (user==null){
            Intent intent= new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }else{
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}