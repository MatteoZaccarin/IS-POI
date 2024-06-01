package com.example.is_poi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventiGeneralAdapter extends RecyclerView.Adapter<EventoViewHolder> {
    Activity context;
    ArrayList<Evento> listaEventi;

    public EventiGeneralAdapter(Activity context, ArrayList<Evento> listaEventi) {
        this.context = context;
        this.listaEventi = listaEventi;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventoViewHolder(LayoutInflater.from(context).inflate(R.layout.evento_view,parent,false), this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        holder.nomeEvento.setText(listaEventi.get(position).NomeEvento);
        if(listaEventi.get(position).Civico.isEmpty()){
            holder.Indirizzo.setText("Luogo: "+listaEventi.get(position).Indirizzo+" - "+listaEventi.get(position).Comune);
        }else{
            holder.Indirizzo.setText("Luogo: "+listaEventi.get(position).Indirizzo+" n "+listaEventi.get(position).Civico +" - "+listaEventi.get(position).Comune);
        }
        holder.Date.setText("Inizio: "+listaEventi.get(position).DataOraInizio+"\nFine: "+ listaEventi.get(position).DataOraFine);
        holder.deleteButton.setVisibility(View.GONE);
        holder.editButton.setVisibility(View.GONE);
        holder.secondoLinea.setVisibility(View.GONE);
        holder.Descrizione.setText(listaEventi.get(position).Descrizione);
        holder.Id.setText(listaEventi.get(position).id);
    }

    @Override
    public int getItemCount() {
        return listaEventi.size();
    }
}
