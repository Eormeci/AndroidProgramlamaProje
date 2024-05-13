package com.example.yeniprojekotlin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yeniprojekotlin.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Login Activity
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        // Kocaeli Activity
        binding.kocaeliImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KocaeliActivity.java dosyasını başlatmak için Intent oluşturun
                Intent intent = new Intent(MainActivity.this, KocaeliActivity.class);
                startActivity(intent);
            }
        });





    }
}
