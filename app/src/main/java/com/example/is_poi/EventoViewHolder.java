package com.example.is_poi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventoViewHolder extends RecyclerView.ViewHolder {
    TextView nomeEvento, Indirizzo, Date, Descrizione;
    public EventoViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeEvento=itemView.findViewById(R.id.nomeEvento);
        Indirizzo=itemView.findViewById(R.id.indirizzo);
        Date=itemView.findViewById(R.id.date);
        Descrizione=itemView.findViewById(R.id.descrizione);
    }
}
