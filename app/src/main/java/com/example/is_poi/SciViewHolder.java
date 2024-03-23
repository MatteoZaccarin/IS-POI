package com.example.is_poi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SciViewHolder extends RecyclerView.ViewHolder {
    TextView titolo, partenza, arrivo;

    public SciViewHolder(@NonNull View itemView) {
        super(itemView);
        titolo=itemView.findViewById(R.id.Titolo);
        partenza=itemView.findViewById(R.id.Partenza);
        arrivo=itemView.findViewById(R.id.Arrivo);
    }
}
