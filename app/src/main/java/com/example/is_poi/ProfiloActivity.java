package com.example.is_poi;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.example.is_poi.databinding.ActivityProfiloBinding;

public class ProfiloActivity extends AppCompatActivity {
    private ActivityProfiloBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfiloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
