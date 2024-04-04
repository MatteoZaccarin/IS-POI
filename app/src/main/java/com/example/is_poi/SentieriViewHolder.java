package com.example.is_poi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class SentieriViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;

    TextView titolo, sintesi, zona, descrizione;

    public SentieriViewHolder(@NonNull View itemView) {
        super(itemView);
        icon=itemView.findViewById(R.id.imageview);
        titolo=itemView.findViewById(R.id.Titolo);
        sintesi=itemView.findViewById(R.id.Sintesi);
        zona=itemView.findViewById(R.id.Zona);
    }
}
