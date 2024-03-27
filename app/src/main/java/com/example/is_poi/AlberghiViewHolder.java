package com.example.is_poi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlberghiViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView denominazione, stelle, indirizzo, recapito;

    public AlberghiViewHolder(@NonNull View itemView) {
        super(itemView);
        icon=itemView.findViewById(R.id.imageview);//occhio ha lo stesso id forse fa merda
        denominazione=itemView.findViewById(R.id.denominazione);
        stelle=itemView.findViewById(R.id.stelle);
        indirizzo=itemView.findViewById(R.id.indirizzo);
        recapito=itemView.findViewById(R.id.recapiti);
    }
}
