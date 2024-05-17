package com.example.is_poi;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventoViewHolder extends RecyclerView.ViewHolder {
    TextView nomeEvento, Indirizzo, Date, Descrizione, Id;
    Button editButton;
    ImageButton deleteButton;
    public EventoViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeEvento=itemView.findViewById(R.id.nomeEvento);
        Indirizzo=itemView.findViewById(R.id.indirizzo);
        Date=itemView.findViewById(R.id.date);
        Descrizione=itemView.findViewById(R.id.descrizione);
        Id=itemView.findViewById(R.id.id);
        editButton=itemView.findViewById(R.id.modify);
        deleteButton=itemView.findViewById(R.id.cancel);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modifica evento
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancella evento
            }
        });
    }
}
