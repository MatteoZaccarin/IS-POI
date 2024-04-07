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
        String dirtyTextTitle=listaSentieri.get(position).Titolo.replace("?","'");
        String dirtyTextSintesi=listaSentieri.get(position).Sintesi.replace("?","'");
        String dirtyTextZona=listaSentieri.get(position).PuntoDiPartenza;
        try{
            byte[] bytes= dirtyTextTitle.getBytes("Windows-1252");
            String cleanTitle=new String(bytes,"Windows-1252" );
            holder.titolo.setText(cleanTitle);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        //in questo blocco cerco di prendere una frase e tenere la prima in maiuscolo e il resto minuscolo ma crasha l'emulatore
        /*String inizio=dirtyTextSintesi.substring(0,1);
        String fine=dirtyTextSintesi.substring(1);
        fine=fine.toLowerCase();
        dirtyTextSintesi=inizio+fine;*/

        String cleanSintesi=Utili.formattaTesto(dirtyTextSintesi);
        cleanSintesi=cleanSintesi.toLowerCase();
        if(!cleanSintesi.isEmpty()){
            holder.sintesi.setVisibility(View.VISIBLE);
            holder.sintesi.setTypeface(holder.sintesi.getTypeface(), Typeface.ITALIC);
            holder.sintesi.setText(cleanSintesi.split("\\.")[0]);
        }else{
            holder.sintesi.setVisibility(View.GONE);
        }
        try{
            byte[] bytes= dirtyTextZona.getBytes("Windows-1252");
            String cleanZona=new String(bytes,"Windows-1252" );
            //altra roba strana perche quando dovrebbe essere vuoto mi mette il testo del primo elemento della lista
            //SISTEMATO MA TENGO PERCHE E' PAZZIA
            if(!cleanZona.isEmpty()){
                holder.zona.setVisibility(View.VISIBLE);
                holder.zona.setText(cleanZona);
            }else{
                holder.zona.setVisibility(View.GONE);
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaSentieri.size();
    }
}
