package com.example.is_poi;

import android.content.Context;
import android.text.util.Linkify;
import android.util.Log;
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
        holder.icon.setImageResource(items.get(position).image);
        holder.titolo.setText(items.get(position).Titolo);
        holder.sintesi.setText(items.get(position).Sintesi);
        holder.zona.setText(items.get(position).PuntoDiPartenza);
        holder.link.setText(items.get(position).consigli);
        Linkify.addLinks(holder.link,Linkify.WEB_URLS);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
