package com.example.is_poi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Search_poi extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_poi, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        String[] s=getResources().getStringArray(R.array.poi_type);
        ArrayAdapter<String> arrayApp=new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, s);
        AutoCompleteTextView textView= (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView2);
        textView.setAdapter(arrayApp);

    }
}