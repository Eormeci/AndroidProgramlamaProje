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
    private boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_succesfull);

        Log.d(TAG, "onCreate: started");

        Toolbar toolbar = findViewById(R.id.toolbar_loginSuccess);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Log.d(TAG, "onCreate: Toolbar is set");
        } else {
            Log.e(TAG, "onCreate: getSupportActionBar() returned null");
        }

        dbHelper = new DatabaseHelper(this);

        // Kullanıcı adını ve bakiye bilgisini al
        String username = getIntent().getStringExtra("username");
        int bakiye = dbHelper.getBakiye(username);
        Log.d(TAG, "onCreate: Başlangıç Bakiyesi: " + bakiye);

        TextView tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        tvBakiye = findViewById(R.id.tvBakiye);
        tvBakiye.setText("Bakiye: " + bakiye + " TL");

        // Eklenecek miktarı almak için TextInputEditText'i tanımla
        eklenecekMiktar = findViewById(R.id.eklenecekMiktar);

        // Bakiye ekle butonuna onClickListener ekle
        Button btnBakiyeEkle = findViewById(R.id.btn_bakiye_ekle);
        btnBakiyeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int mevcutBakiye = dbHelper.getBakiye(username);
                    Log.d(TAG, "onClick: Mevcut Bakiye: " + mevcutBakiye);
                    String eklenecekMiktarStr = eklenecekMiktar.getText().toString().trim();
                    int miktar = Integer.parseInt(eklenecekMiktarStr);

                    if (!eklenecekMiktarStr.isEmpty()) {
                        if (eklenecekMiktarStr.matches("\\d+")) {
                            mevcutBakiye += miktar;
                            Log.d(TAG, "onClick: Yeni Bakiye: " + mevcutBakiye);
                            dbHelper.updateBakiye(username, mevcutBakiye);
                            tvBakiye.setText("Bakiye: " + mevcutBakiye + " TL");
                            eklenecekMiktar.setText("");
                        } else {
                            Toast.makeText(LoginSuccesfullActivity.this, "Geçersiz miktar", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onClick: Geçersiz miktar girildi.");
                        }
                    } else {
                        Toast.makeText(LoginSuccesfullActivity.this, "Geçersiz miktar", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: Boş miktar girildi.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onClick: Bakiye eklenirken hata: " + e.getMessage());
                }
            }
        });

        // Çıkış butonuna onClickListener ekle
        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogged = false;
                Log.d(TAG, "btnLogout onClick: isLogged set to false.");
                Toast.makeText(LoginSuccesfullActivity.this, "Çıkış yapıldı.", Toast.LENGTH_SHORT).show();
                handleLogout();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: itemId = " + item.getItemId());
        if (item.getItemId() == android.R.id.home) {
            isLogged = true;
            Log.d(TAG, "onOptionsItemSelected: isLogged set to true.");
            Log.d(TAG, "onOptionsItemSelected: Geri butonuna tıklandı.");
            handleLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleLogout() {
        if (!isLogged) {
            clearLoginInfo();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Şu anki aktiviteyi sonlandır
    }
    private void clearLoginInfo() {
        Log.d(TAG, "clearLoginInfo: Giriş bilgileri temizleniyor.");
        // SharedPreferences veya uygun başka bir yöntemi kullanarak giriş bilgilerini temizleyin
        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Log.d(TAG, "clearLoginInfo: Giriş bilgileri temizlendi.");
    }
}
