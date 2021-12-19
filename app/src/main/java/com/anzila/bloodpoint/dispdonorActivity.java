package com.anzila.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anzila.bloodpoint.databinding.ActivityDispdonorBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class dispdonorActivity extends AppCompatActivity {

    String group;
    ActivityDispdonorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDispdonorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        group = intent.getStringExtra("GRP");

        fetchDonours();

        binding.donorRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDonours();
            }
        });
    }

    FirebaseFirestore db;
    void fetchDonours(){

        binding.donorRefresh.setRefreshing(true);

        db = FirebaseFirestore.getInstance();
        db.collection("Donors")
                .whereEqualTo("blood_group",group)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        binding.donorRefresh.setRefreshing(false);
                        if(task.isSuccessful()){

                            List<donordata> list = new ArrayList<>();
                            for(DocumentSnapshot doc : task.getResult()){
                                donordata data = doc.toObject(donordata.class);
                                list.add(data);
                            }
                            updateRecycler(list);

                        } else
                            Toast.makeText(dispdonorActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    DonorAdapter donorAdapter;
    void updateRecycler(List<donordata> list){
        if(donorAdapter == null){
            donorAdapter = new DonorAdapter(list);
            binding.donorRecycler.setAdapter(donorAdapter);
            binding.donorRecycler.setLayoutManager(new LinearLayoutManager(this));

        } else donorAdapter.updateList(list);
    }
}