package com.example.is_poi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlberghiAdapter extends RecyclerView.Adapter<AlberghiAdapter.AlberghiViewHolder> {
    private ArrayList<Alberghi> listaAlberghi;

    public AlberghiAdapter(ArrayList<Alberghi> la){
        this.listaAlberghi=la;
    }
    @NonNull
    @Override
    public AlberghiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlberghiViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class AlberghiViewHolder extends RecyclerView.ViewHolder{
        public TextView nome;
        public AlberghiViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
