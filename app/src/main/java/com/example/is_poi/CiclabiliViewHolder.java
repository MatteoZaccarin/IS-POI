package com.example.is_poi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class CiclabiliViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView titolo, sintesi, zona, categoria;
    public CiclabiliViewHolder(@NonNull View itemView) {
        super(itemView);
        icon= itemView.findViewById(R.id.imageview);
        categoria=itemView.findViewById(R.id.Categoria);
        titolo=itemView.findViewById(R.id.Titolo);
        sintesi=itemView.findViewById(R.id.Sintesi);
        zona=itemView.findViewById(R.id.Zona);
    }
}
