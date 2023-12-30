package com.example.is_poi;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.is_poi.databinding.ItemComuniBinding;
import java.util.List;

public class ComuniAdapter extends RecyclerView.Adapter<ComuniAdapter.BindingHolder> {
    private List<String> comuni;

    public void setComuni(List<String> comuni) {
        this.comuni = comuni;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingHolder(
            ItemComuniBinding.inflate(
                LayoutInflater.from(
                    parent.getContext()
                ),
                parent,
                false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        String name = comuni.get(position);
        if (name.contains("ce")) {
            holder.itemBinding.name.setText(name);
            holder.itemBinding.name.setOnClickListener(view -> {
                // Click
            });
        }
    }

    @Override
    public int getItemCount() {
        return comuni == null ? 0 : comuni.size();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private final ItemComuniBinding itemBinding;

        public BindingHolder(ItemComuniBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public ItemComuniBinding getItemBinding() {
            return itemBinding;
        }
    }
}
