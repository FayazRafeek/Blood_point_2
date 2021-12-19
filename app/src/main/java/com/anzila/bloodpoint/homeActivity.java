package com.anzila.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anzila.bloodpoint.databinding.ActivityHomeBinding;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class homeActivity extends AppCompatActivity {

    PageAdapter pageAdapter;


    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pageAdapter=new PageAdapter(getSupportFragmentManager(),binding.tablayout1.getTabCount());
        binding.vpager.setAdapter(pageAdapter);

        binding.tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpager.setCurrentItem(tab.getPosition());
                pageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.vpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    FirebaseAuth mAuth;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.item1){
            AlertDialog.Builder builder=new AlertDialog.Builder(homeActivity.this);
            builder.setMessage("Do You Want To Exit?");
            builder.setCancelable(true);
            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handleLogout();
                }
            });
            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        return true;
    }


    void handleLogout(){

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        SharedPreferences pref = getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("IS_LOGIN",false);
        editor.apply();

        Toast.makeText(this, "Logging...", Toast.LENGTH_SHORT).show();

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(homeActivity.this,MainActivity.class));
                        finish();
                    }
                },800);

    }

}