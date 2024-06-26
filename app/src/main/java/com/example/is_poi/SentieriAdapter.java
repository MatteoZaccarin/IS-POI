package com.example.is_poi;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import java.io.UnsupportedEncodingException;
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
        holder.icon.setImageResource(listaSentieri.get(position).image);
        holder.titolo.setText(listaSentieri.get(position).Titolo.replace("?","'"));

        String dirtyTextSintesi=listaSentieri.get(position).Sintesi.replace("?","'").toLowerCase();
        if(!dirtyTextSintesi.isEmpty()){
            holder.sintesi.setVisibility(View.VISIBLE);
            holder.sintesi.setTypeface(holder.sintesi.getTypeface(), Typeface.ITALIC);
            holder.sintesi.setText(dirtyTextSintesi.split("\\.")[0]);//prendo solo la prima frase, alcune sintesi erano troppo lunghe
        }else{
            holder.sintesi.setVisibility(View.GONE);
        }

        String zona=listaSentieri.get(position).PuntoDiPartenza.replace("?","'");
        if(!zona.isEmpty()){
            holder.zona.setVisibility(View.VISIBLE);
            holder.zona.setText(zona);
        }else{
            holder.zona.setVisibility(View.GONE);
        }

        //in questo blocco cerco di prendere una frase e tenere la prima in maiuscolo e il resto minuscolo ma crasha l'emulatore
        /*String inizio=dirtyTextSintesi.substring(0,1);
        String fine=dirtyTextSintesi.substring(1);
        fine=fine.toLowerCase();
        dirtyTextSintesi=inizio+fine;*/
    }

    @Override
    public int getItemCount() {
        return listaSentieri.size();
    }
}
