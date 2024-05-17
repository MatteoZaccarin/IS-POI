package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchCity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Intent intent = getIntent();
        if(intent != null) {
            String valoreRecuperato = intent.getStringExtra("comune");
            TextView textView = findViewById(R.id.textView2);
            textView.setText(valoreRecuperato);
        }
    }
}