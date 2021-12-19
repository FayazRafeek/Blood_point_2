package com.anzila.bloodpoint;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.anzila.bloodpoint.databinding.Fragment1Binding;

import java.util.ArrayList;

public class Fragment1 extends Fragment implements AdapterView.OnItemSelectedListener {

    Fragment1Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = Fragment1Binding.inflate(inflater,container,false);

        setupSpinner();
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),dispdonorActivity.class);
                intent.putExtra("GRP",group);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }


    ArrayList<String> bloodGrp = new ArrayList<>();
    void setupSpinner(){

        bloodGrp.add("A+");
        bloodGrp.add("B+");
        bloodGrp.add("AB+");
        bloodGrp.add("A-");
        bloodGrp.add("B-");
        bloodGrp.add("AB-");
        bloodGrp.add("O-");
        bloodGrp.add("O+");

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,bloodGrp);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.bloodSpinnerSearch.setAdapter(spinnerAdapter);
        binding.bloodSpinnerSearch.setOnItemSelectedListener(this);

    }

    String group = "";
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        group = bloodGrp.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}