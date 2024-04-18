package com.example.is_poi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.is_poi.databinding.ActivityEventiBinding;


public class EventiActivity extends AppCompatActivity {
    private ActivityEventiBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEventiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(view ->
                binding.drawerLayout.openDrawer(GravityCompat.START)
        );
    }
}
