package com.example.yeniprojekotlin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginSuccesfullActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView tvBakiye;
    private TextInputEditText eklenecekMiktar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_succesfull);

        Toolbar toolbar = findViewById(R.id.toolbar_loginSuccess);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        // Kullanıcı adını ve bakiye bilgisini al
        String username = getIntent().getStringExtra("username");
        int bakiye = dbHelper.getBakiye(username);

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
                    int mevcutBakiye = dbHelper.getBakiye(username); // Mevcut bakiyeyi al
                    String eklenecekMiktarStr = eklenecekMiktar.getText().toString().trim();
                    int miktar = Integer.parseInt(eklenecekMiktarStr);

                    if (!eklenecekMiktarStr.isEmpty()) {

                        if (eklenecekMiktarStr.matches("\\d+")) {
                            mevcutBakiye += miktar; // Mevcut bakiyeye ekleyin
                            dbHelper.updateBakiye(username, mevcutBakiye);
                            tvBakiye.setText("Bakiye: " + mevcutBakiye + " TL");
                            eklenecekMiktar.setText("");
                        } else {
                            Toast.makeText(LoginSuccesfullActivity.this, "Geçersiz miktar", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginSuccesfullActivity.this, "Geçersiz miktar", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("LoginSuccesfullActivity", "Bakiye eklenirken hata: " + e.getMessage());
                }
            }
        });

        // Geri butonuna tıklanınca geri gitme işlemi
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Geri butonuna tıklandığında yapılacak işlemi tanımlama
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Ana sayfaya gitmek için MainActivity'yi başlat
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Bu aktiviteyi kapat
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
