package com.example.yeniprojekotlin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText registerAd, registerSoyad, registerUser, registerPassword, registerTc, registerGmail;
    private Button registerBtn;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        registerAd = findViewById(R.id.registerad);
        registerSoyad = findViewById(R.id.registersoyad);
        registerUser = findViewById(R.id.registeruser);
        registerPassword = findViewById(R.id.registerpassword);
        registerTc = findViewById(R.id.registertc);
        registerGmail = findViewById(R.id.registergmail);
        registerBtn = findViewById(R.id.RegisterBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = registerAd.getText().toString().trim();
                String soyad = registerSoyad.getText().toString().trim();
                String username = registerUser.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String tckn = registerTc.getText().toString().trim();
                String gmail = registerGmail.getText().toString().trim();

                if (ad.isEmpty() || soyad.isEmpty() || username.isEmpty() || password.isEmpty() || tckn.isEmpty() || gmail.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isRegistered = databaseHelper.registerUser(ad, soyad, tckn, gmail, username, password);
                if (isRegistered) {
                    Toast.makeText(RegisterActivity.this, "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Kayıt başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
