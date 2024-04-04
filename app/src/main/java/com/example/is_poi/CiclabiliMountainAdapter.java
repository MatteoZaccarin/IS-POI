package com.example.is_poi;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CiclabiliMountainAdapter extends RecyclerView.Adapter<CiclabiliViewHolder> {
    Activity context;
    private ArrayList<Esperienze> listaCiclabiliMountain;

    public CiclabiliMountainAdapter(Activity context, ArrayList<Esperienze> listaCiclabiliMountain) {
        this.context = context;
        this.listaCiclabiliMountain = listaCiclabiliMountain;
    }
    @NonNull
    @Override
    public CiclabiliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CiclabiliViewHolder(LayoutInflater.from(context).inflate(R.layout.ciclabili_view, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CiclabiliViewHolder holder, int position) {
        holder.icon.setImageResource(listaCiclabiliMountain.get(position).image);
        holder.titolo.setText(listaCiclabiliMountain.get(position).Titolo.replace("?","'"));

        String dirtyText=listaCiclabiliMountain.get(position).Sintesi.split("\\.")[0];

        try{
            byte[] bytes = dirtyText.getBytes("Windows-1252");
            String cleanText = new String(bytes, "Windows-1252");
            cleanText.replace("?","'");
            holder.sintesi.setText(cleanText);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        holder.categoria.setText("Categoria: "+listaCiclabiliMountain.get(position).Categoria);
        if(listaCiclabiliMountain.get(position).Categoria!="Piste Ciclabili"){
            if(listaCiclabiliMountain.get(position).PuntoDiPartenza.compareTo(listaCiclabiliMountain.get(position).PuntoDiArrivo)!=0){
                holder.zona.setText("Percorso: "+listaCiclabiliMountain.get(position).PuntoDiPartenza+" -> "+listaCiclabiliMountain.get(position).PuntoDiArrivo);
            }else{
                holder.zona.setText("Zona: "+listaCiclabiliMountain.get(position).PuntoDiArrivo);
            }
        }else{
            holder.zona.setText("Zona: "+listaCiclabiliMountain.get(position).PuntoDiArrivo);
        }

    }

    @Override
    public int getItemCount() {
        return listaCiclabiliMountain.size();
    }
}
