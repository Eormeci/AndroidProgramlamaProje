package com.example.yeniprojekotlin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yeniprojekotlin.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String PREFS_NAME = "login_prefs";
    private static final String PREF_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Kullanıcı daha önce giriş yapmış mı kontrol et
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (preferences.contains(PREF_USERNAME)) {
            String username = preferences.getString(PREF_USERNAME, null);
            Intent intent = new Intent(this, LoginSuccesfullActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish(); // MainActivity'yi kapat
        }

        // Login Activity
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Kocaeli Activity
        binding.kocaeliImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KocaeliActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Giriş başarılı, kullanıcı adını al
            String username = data.getStringExtra("username");
            saveLoginInfo(username);

            // LoginSuccesfullActivity'yi başlat
            Intent intent = new Intent(this, LoginSuccesfullActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish(); // MainActivity'yi kapat
        }
    }

    private void saveLoginInfo(String username) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USERNAME, username);
        editor.apply();
    }
}
