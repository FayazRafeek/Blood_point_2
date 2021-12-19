package com.anzila.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.anzila.bloodpoint.Model.User;
import com.anzila.bloodpoint.databinding.ActivityReg1Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class reg1Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityReg1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReg1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setupSpinner();
        binding.regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    startRegister();
                } else Toast.makeText(reg1Activity.this, "Invalid inputs", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,bloodGrp);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.regbloodSpinner.setAdapter(spinnerAdapter);
        binding.regbloodSpinner.setOnItemSelectedListener(this);

    }

    String name, email,password, bloodg, phone, pincode;
    Boolean validateInput(){

        name = binding.regName.getText().toString();
        email = binding.email.getText().toString();
        password = binding.passwd.getText().toString();
        pincode = binding.pinCode.getText().toString();
        phone = binding.phoneNo.getText().toString();


        if(email != null && password != null)
            return true;
        return false;

    }


    FirebaseAuth mAuth;
    void startRegister(){

        Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            addToDatabase();
                        } else
                            Toast.makeText(reg1Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    FirebaseFirestore db;
    void addToDatabase() {


        User user = new User(mAuth.getUid(),name,email,password,bloodg,phone,pincode);
        db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .document(mAuth.getUid())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(reg1Activity.this, "Registration successfull", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(reg1Activity.this, signinActivity.class));
                                    finish();
                                }
                            },800);

                        } else
                            Toast.makeText(reg1Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        bloodg = bloodGrp.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}