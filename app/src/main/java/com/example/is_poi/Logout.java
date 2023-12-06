package com.example.is_poi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Logout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Logout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Logout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Logout.
     */
    // TODO: Rename and change types and number of parameters
    public static Logout newInstance(String param1, String param2) {
        Logout fragment = new Logout();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Button button;
        super.onCreate(savedInstanceState);
        TextView textView;
        textView=(TextView) getView().findViewById(R.id.user_details);


        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES);


        //Retrofit retrofit= new Retrofit.Builder().baseUrl("https://dati.veneto.it").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

        //MainActivity.RequestUser ru= retrofit.create(MainActivity.RequestUser.class);

        TextView finalTextView = textView;
        /*
        ru.getUser().enqueue(new retrofit2.Callback<ArrayList<Alberghi>>() {

            @Override
            public void onResponse(Call<ArrayList<Alberghi>> call, Response<ArrayList<Alberghi>> response) {
                finalTextView.setText(response.body().get(12).COMUNE);
            }

            @Override
            public void onFailure(Call<ArrayList<Alberghi>> call, Throwable t) {
                finalTextView.setText(t.getMessage());
            }
        });


        */
        auth= FirebaseAuth.getInstance();
        button=getView().findViewById(R.id.logout);
        textView=(TextView)getView().findViewById(R.id.user_details);
        user= auth.getCurrentUser();
        if (user==null){
            Intent intent= new Intent(getActivity(), Login.class);
            startActivity(intent);
        }else{
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}