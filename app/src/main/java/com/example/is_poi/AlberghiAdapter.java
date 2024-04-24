package com.example.is_poi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import okhttp3.internal.Util;

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
        if(listaAlberghi.get(position).TIPOLOGIA.compareTo("AGRITURISMO")==0){
            holder.stelle.setVisibility(View.GONE);
        }
        holder.icon.setImageResource(listaAlberghi.get(position).icon);
        holder.denominazione.setText(listaAlberghi.get(position).DENOMINAZIONE);
        holder.stelle.setText(listaAlberghi.get(position).STELLE);
        holder.indirizzo.setText(listaAlberghi.get(position).INDIRIZZO+" "+listaAlberghi.get(position).CIVICO+" - "+listaAlberghi.get(position).COMUNE.toLowerCase());
        if(!listaAlberghi.get(position).EMAIL.isEmpty() && !listaAlberghi.get(position).TELEFONO.isEmpty()){
            holder.recapito.setText("Recapiti: \n\t\t"+listaAlberghi.get(position).EMAIL+" \n\t\t"+listaAlberghi.get(position).TELEFONO);
        }else{
            if(!listaAlberghi.get(position).EMAIL.isEmpty())
                holder.recapito.setText("Recapiti: \n\t\t"+listaAlberghi.get(position).EMAIL);
            else if(!listaAlberghi.get(position).TELEFONO.isEmpty())
                holder.recapito.setText("Recapiti: \n\t\t"+listaAlberghi.get(position).TELEFONO);
        }

        if(listaAlberghi.get(position).SITOWEB.isEmpty()){
            holder.sito.setVisibility(View.GONE);
            //holder.sito.setText("Sito web non presente per questo "+ listaAlberghi.get(position).TIPOLOGIA.toLowerCase());
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.recapito.getLayoutParams();
            params.removeRule(RelativeLayout.BELOW);
            params.addRule(RelativeLayout.BELOW, R.id.imageview);
            holder.recapito.setLayoutParams(params);
        }else{
            holder.sito.setText(listaAlberghi.get(position).SITOWEB);
            Linkify.addLinks(holder.sito,Linkify.WEB_URLS);
            holder.sito.setMovementMethod(LinkMovementMethod.getInstance());
            holder.sito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = holder.sito.getText().toString();
                    if(URLUtil.isValidUrl(url)){
                        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(browserIntent);
                    }else{
                        Log.e("url","errore creazione link sito web hotel");
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listaAlberghi.size();
    }
}
