package com.anzila.bloodpoint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anzila.bloodpoint.Model.User;
import com.anzila.bloodpoint.databinding.Fragment3Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;


public class Fragment3 extends Fragment {


    Fragment3Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = Fragment3Binding.inflate(inflater,container,false);

        updateUi();
        return binding.getRoot();
    }

    User user;
    void updateUi(){

        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userSt = pref.getString("USER","");
        Gson gson = new Gson();
        user = gson.fromJson(userSt, User.class);

        binding.myName.setText("Name : " + user.getName());
        binding.myAge.setText("Age : " + user.getBloodGroup());
        binding.myEmail.setText("Email : " + user.getEmail());
        binding.myPhn.setText("Phone : " + user.getPhone());
        binding.myBlood.setText("Blood group : " + user.getBloodGroup());
        binding.myDistrict.setText("Pincode : " + user.getPincode());



    }
}