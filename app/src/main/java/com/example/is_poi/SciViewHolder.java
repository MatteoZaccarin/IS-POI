package com.example.is_poi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SciViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView titolo, sintesi, zona, link;

    public SciViewHolder(@NonNull View itemView) {
        super(itemView);
        icon=itemView.findViewById(R.id.imageview);
        titolo=itemView.findViewById(R.id.Titolo);
        sintesi=itemView.findViewById(R.id.Sintesi);
        zona=itemView.findViewById(R.id.Zona);
        link=itemView.findViewById(R.id.Link);
    }
}
