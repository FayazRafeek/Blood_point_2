package com.anzila.bloodpoint;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anzila.bloodpoint.databinding.Fragment2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Executor;


public class Fragment2 extends Fragment implements AdapterView.OnItemSelectedListener {


    Fragment2Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = Fragment2Binding.inflate(inflater,container,false);
        setupSpinner();



        return binding.getRoot();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateInp())
                    addDonor();
            }
        });
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
        binding.grpSpinner.setAdapter(spinnerAdapter);
        binding.grpSpinner.setOnItemSelectedListener(this);

    }

    String group;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        group = bloodGrp.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    String name, age,addr, email,phone;
    Boolean validateInp(){

        name = binding.nameInp.getText().toString();
        age = binding.ageInp.getText().toString();
        addr = binding.addrInp.getText().toString();
        email = binding.emailInp.getText().toString();
        phone = binding.phoneInp.getText().toString();


        if(name != null && age != null && addr != null && phone != null) return true;
        return false;
    }

    FirebaseFirestore db;
    void addDonor(){

        Toast.makeText(getContext(), "Adding donor..", Toast.LENGTH_SHORT).show();
        db = FirebaseFirestore.getInstance();

        donordata data = new donordata(String.valueOf(System.currentTimeMillis()),name,age,email,phone,addr,group);

        db.collection("Donors")
                .document(data.getDonorId())
                .set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getContext(), "Adding succesfull", Toast.LENGTH_SHORT).show();

                        } else Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}