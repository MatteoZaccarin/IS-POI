package com.example.is_poi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventiActivity extends AppCompatActivity {
    private ActivityEventiBinding binding;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("eventi");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEventiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {printEventi();}
        });
        printEventi();

    }

    public void printEventi(){
        Query q= myRef.orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Evento> eventi=new ArrayList<>();
                // Itera su ogni risultato della query
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Evento e=childSnapshot.getValue(Evento.class);
                    System.out.println(e.Creatore);
                    if(e.Creatore.compareTo(FirebaseAuth.getInstance().getCurrentUser().getEmail())==0){
                        eventi.add(e);
                    }
                }
                RecyclerView RW =findViewById((R.id.recyclerview));
                RW.setLayoutManager(new LinearLayoutManager(EventiActivity.this));
                RW.setAdapter(new EventiAdapter(EventiActivity.this, eventi));
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                System.out.println(eventi.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("errore interrogazione eventi firebase", error.getDetails());
            }

        });
        swipeRefreshLayout.setRefreshing(false);
    }
}
