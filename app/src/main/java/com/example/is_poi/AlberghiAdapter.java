package com.example.is_poi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlberghiAdapter extends RecyclerView.Adapter<AlberghiViewHolder> {
    Activity context;
    private ArrayList<Alberghi> listaAlberghi;

    public AlberghiAdapter(Activity context, ArrayList<Alberghi> la){
        this.context=context;
        this.listaAlberghi=la;
    }
    @NonNull
    @Override
    public AlberghiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlberghiViewHolder(LayoutInflater.from(context).inflate(R.layout.albergo_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlberghiViewHolder holder, int position) {
        holder.denominazione.setText(listaAlberghi.get(position).DENOMINAZIONE);
        holder.stelle.setText(listaAlberghi.get(position).TIPOLOGIA+" - "+ listaAlberghi.get(position).STELLE );
        holder.indirizzo.setText(listaAlberghi.get(position).INDIRIZZO);
        holder.recapito.setText(listaAlberghi.get(position).TELEFONO);
    }

    @Override
    public int getItemCount() {
        return listaAlberghi.size();
    }
}
