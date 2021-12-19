package com.anzila.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anzila.bloodpoint.Model.User;
import com.anzila.bloodpoint.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;


public class
signinActivity extends AppCompatActivity {

    ActivitySigninBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                    startLogin();
            }
        });

        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signinActivity.this, reg1Activity.class));
                finish();
            }
        });
    }

    String email,password;
    Boolean validateInput(){

        email = binding.usEmail.getText().toString();
        password = binding.usPassword.getText().toString();

        if(email != null && password != null) return true;
        return false;
    }

    FirebaseAuth mAuth;
    void startLogin(){

        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            getUserFromDb();
                        } else Toast.makeText(signinActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    FirebaseFirestore db;
    void getUserFromDb(){

        db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .document(mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){

                            User user = task.getResult().toObject(User.class);
                            saveToPref(user);

                            Toast.makeText(signinActivity.this, "Loggin successfull", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(signinActivity.this,homeActivity.class));
                                    finish();
                                }
                            },800);
                        } else Toast.makeText(signinActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void saveToPref(User user){

        Gson gson = new Gson();
        String objString = gson.toJson(user);
        SharedPreferences pref = getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("USER",objString);
        editor.putBoolean("IS_LOGIN",true);
        editor.apply();

    }
}