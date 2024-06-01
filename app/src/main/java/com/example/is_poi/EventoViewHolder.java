package com.example.is_poi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class EventoViewHolder extends RecyclerView.ViewHolder {
    TextView nomeEvento, Indirizzo, Date, Descrizione, Id;
    MainActivityViewModel viewModel;
    Button editButton;
    ImageButton deleteButton;
    View secondoLinea;
    EventiAdapter adapter;
    public EventoViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        nomeEvento=itemView.findViewById(R.id.nomeEvento);
        Indirizzo=itemView.findViewById(R.id.indirizzo);
        Date=itemView.findViewById(R.id.date);
        Descrizione=itemView.findViewById(R.id.descrizione);
        Id=itemView.findViewById(R.id.id);
        editButton=itemView.findViewById(R.id.modify);
        deleteButton=itemView.findViewById(R.id.cancel);
        secondoLinea=itemView.findViewById(R.id.secondaLinea);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, EventiModificaActivity.class);
                intent.putExtra("id", Id.getText().toString());
                context.startActivity(intent);
            }
        });


        EventiAdapter finalAdapter = adapter;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancella evento
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Cancellare "+nomeEvento.getText().toString()+"?");
                builder.setMessage("Sei sicuro di voler cancellare questo evento?");
                builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app").getReference("eventi/"+Id.getText().toString());
                        dbRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Evento cancellato con successo", Toast.LENGTH_SHORT).show();
                                MainActivity.viewModel.fetchEventi();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Errore nella cancellazione del evento, RIPROVA", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.show();
            }
        });
    }
}
