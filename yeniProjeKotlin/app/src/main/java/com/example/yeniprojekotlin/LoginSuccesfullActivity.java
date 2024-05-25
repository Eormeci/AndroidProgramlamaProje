package com.example.yeniprojekotlin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginSuccesfullActivity extends AppCompatActivity {
    private static final String TAG = "LoginSuccesfullActivity";
    private DatabaseHelper dbHelper;
    private TextView tvBakiye;
    private TextInputEditText eklenecekMiktar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_succesfull);

        Toolbar toolbar = findViewById(R.id.toolbar_loginSuccess);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");
        int bakiye = dbHelper.getBakiye(username);

        TextView tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        tvBakiye = findViewById(R.id.tvBakiye);
        tvBakiye.setText("Bakiye: " + bakiye + " TL");

        eklenecekMiktar = findViewById(R.id.eklenecekMiktar);

        Button btnBakiyeEkle = findViewById(R.id.btn_bakiye_ekle);
        btnBakiyeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String eklenecekMiktarStr = eklenecekMiktar.getText().toString().trim();
                    if (!eklenecekMiktarStr.isEmpty() && eklenecekMiktarStr.matches("\\d+")) {
                        int miktar = Integer.parseInt(eklenecekMiktarStr);
                        int mevcutBakiye = dbHelper.getBakiye(username) + miktar;
                        dbHelper.updateBakiye(username, mevcutBakiye);
                        tvBakiye.setText("Bakiye: " + mevcutBakiye + " TL");
                        eklenecekMiktar.setText("");
                    } else {
                        Toast.makeText(LoginSuccesfullActivity.this, "Geçersiz miktar", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Bakiye eklenirken hata: " + e.getMessage());
                }
            }
        });

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginSuccesfullActivity.this, "Çıkış yapıldı.", Toast.LENGTH_SHORT).show();
                handleLogout();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateBackToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void handleLogout() {
        clearLoginInfo();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void clearLoginInfo() {
        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
