package com.example.is_poi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SciAdapter extends RecyclerView.Adapter<SciViewHolder> {
    Context context;
    ArrayList<Esperienze> items;

    public SciAdapter(Context context, ArrayList<Esperienze> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SciViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SciViewHolder(LayoutInflater.from(context).inflate(R.layout.esperienze_view, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SciViewHolder holder, int position) {
        holder.titolo.setText(items.get(position).Titolo);
        holder.partenza.setText(items.get(position).PuntoDiPartenza);
        holder.arrivo.setText(items.get(position).PuntoDiArrivo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
