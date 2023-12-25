package com.example.is_poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.is_poi.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        setContentView(binding.getRoot());

        viewModel.fetchMunicipallyData();

        manageViews();
        initObservers();
    }

    private void initObservers() {
        viewModel.getUiState().observe(this, uiState -> {
            if (uiState.municipalities != null) {
                // Update adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    binding.getRoot().getContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    uiState.municipalities
                );
                binding.autoCompleteTextView.setAdapter(adapter);
            }
        });
    }

    private void manageViews() {
        binding.toolbar.setNavigationOnClickListener(view ->
            binding.drawer.openDrawer(GravityCompat.START)
        );
        binding.autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selected = (String) adapterView.getItemAtPosition(i);
            Toast newToast = Toast.makeText(binding.getRoot().getContext(), "Cliccato " + selected, Toast.LENGTH_SHORT);
            newToast.show();
        });
    }
}