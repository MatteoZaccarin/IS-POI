package com.example.is_poi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SentieriAdapter extends RecyclerView.Adapter<SentieriViewHolder> {
    Activity context;

    private ArrayList<SentieriPanoramici> listaSentieri;

    public SentieriAdapter(Activity context, ArrayList<SentieriPanoramici> listaSentieri) {
        this.context = context;
        this.listaSentieri = listaSentieri;
    }

    @NonNull
    @Override
    public SentieriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SentieriViewHolder(LayoutInflater.from(context).inflate(R.layout.sentieri_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SentieriViewHolder holder, int position) {
        //holder.icon.setImageResource(listaSentieri.get(position).icon); immagine da fare
        holder.titolo.setText(listaSentieri.get(position).Titolo);
        holder.sintesi.setText(listaSentieri.get(position).Sintesi);
        holder.zona.setText(listaSentieri.get(position).PuntoDiPartenza);
        holder.descrizione.setText(listaSentieri.get(position).Descrizione);
    }

    @Override
    public int getItemCount() {
        return listaSentieri.size();
    }
}
