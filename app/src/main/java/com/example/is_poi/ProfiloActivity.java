package com.example.is_poi;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.is_poi.databinding.ActivityEventiBinding;
import com.example.is_poi.databinding.ActivityProfiloBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfiloActivity extends AppCompatActivity {
    private ActivityProfiloBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfiloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        name=name.split("@")[0];
        binding.name.setText(name.substring(0,1).toUpperCase()+name.substring(1));
        binding.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://is-poi-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("message24");
        myRef.setValue("Hello, World!");
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
