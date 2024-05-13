package com.example.is_poi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.example.is_poi.databinding.ActivityProfiloBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfiloActivity extends AppCompatActivity {
    private ActivityProfiloBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("eventi");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfiloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        name=name.split("@")[0];
        binding.name.setText(name.substring(0,1).toUpperCase()+name.substring(1));
        binding.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Evento festaGrande=new Evento("partita calcio a 5", "via napoleone", "18", "Auronzo", "fabio@gmail.com", Evento.dateToString(LocalDateTime.now().minusDays(5)), Evento.dateToString(LocalDateTime.now().minusDays(2)), "Divertimento e giochiamo assieme"
                , "Belluno");
        myRef.child("festagrande3").setValue(festaGrande);
        LocalDateTime a=Evento.stringToDate(festaGrande.DataOraInizio);
        numEventi();
    }
    public String numEventi(){
        String eventi="";
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        System.out.println(formattedDate);
        Query q= myRef.orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int attivi=0, passati=0;
                // Itera su ogni risultato della query
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Evento e=childSnapshot.getValue(Evento.class);
                    //System.out.println(e.Creatore);
                    if(e.Creatore.compareTo(FirebaseAuth.getInstance().getCurrentUser().getEmail())==0){
                        if(Evento.stringToDate(e.DataOraFine).isBefore(LocalDateTime.now())){
                            passati++;
                        }else{
                            attivi++;
                        }
                    }
                }
                binding.eventi.setText(attivi+" attivi e "+passati+" passati");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("errore interrogazione eventi firebase", error.getDetails());
            }
        });
        return eventi;
    }
}
