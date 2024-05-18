package com.example.is_poi;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class EventoViewHolder extends RecyclerView.ViewHolder {
    TextView nomeEvento, Indirizzo, Date, Descrizione, Id;
    Button editButton;
    ImageButton deleteButton;
    public EventoViewHolder(@NonNull View itemView, Context context) {
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
                Intent intent=new Intent(context, EventiModificaActivity.class);
                intent.putExtra("id", Id.getText().toString());
                context.startActivity(intent);
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
