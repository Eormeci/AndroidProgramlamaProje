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
    private static final String IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkLoginStatus();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean(IS_LOGGED_IN, false);
                if (isLoggedIn) {
                    String username = preferences.getString(PREF_USERNAME, null);
                    Intent intent = new Intent(MainActivity.this, LoginSuccesfullActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });

        binding.kocaeliImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KocaeliActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLoginStatus() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(IS_LOGGED_IN, false);

        if (isLoggedIn) {
            String username = preferences.getString(PREF_USERNAME, null);
            binding.tvUsername.setText(username);
            // Giriş yapan kullanıcının adını göstermek için bir TextView ekleyebiliriz.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String username = data.getStringExtra("username");
            saveLoginInfo(username);
            Intent intent = new Intent(this, LoginSuccesfullActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }
    }

    private void saveLoginInfo(String username) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USERNAME, username);
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
    }
}
