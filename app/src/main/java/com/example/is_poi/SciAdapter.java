package com.example.is_poi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SciAdapter extends RecyclerView.Adapter<SciViewHolder> {
    Activity context;
    ArrayList<Esperienze> items;

    public SciAdapter(Activity context, ArrayList<Esperienze> items) {
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
        holder.titolo.setText(items.get(position).Titolo.replace("?","'"));
        holder.sintesi.setText(items.get(position).Sintesi.replace("?","'"));
        holder.zona.setText(items.get(position).PuntoDiPartenza);
        holder.link.setText(items.get(position).consigli);
        Linkify.addLinks(holder.link,Linkify.WEB_URLS);

        //apertura browser
        holder.link.setMovementMethod(LinkMovementMethod.getInstance());
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = holder.link.getText().toString();
                if(URLUtil.isValidUrl(url)){
                    Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                }else{
                    Log.e("url","AAAAAAAAAAAAAAAAA");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
