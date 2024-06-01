package com.example.yeniprojekotlin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginSuccesfullActivity extends AppCompatActivity implements TicketAdapter.OnTicketClickListener {

    private static final String TAG = "LoginSuccesfullActivity";
    private DatabaseHelper dbHelper;
    private TextView tvBakiye;
    private TextInputEditText eklenecekMiktar;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private List<String> ticketList;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ticket_prefs";
    private static final String TICKET_INFO_KEY = "ticket_info_list";
    private static final String LOGIN_PREFS = "login_prefs";
    private static final String BALANCE_KEY = "balance";
    private int bakiye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_succesfull);

        Toolbar toolbar = findViewById(R.id.toolbar_loginSuccess);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");
        bakiye = getBalance();

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
                        bakiye += miktar;
                        updateBalance(bakiye);
                        tvBakiye.setText("Bakiye: " + bakiye + " TL");
                        eklenecekMiktar.setText("");
<<<<<<< HEAD
                        // AlertDialog oluşturma
                        new AlertDialog.Builder(LoginSuccesfullActivity.this)
                                .setTitle("Başarılı")
                                .setMessage("Bakiye başarıyla eklendi!")
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Tamam butonuna basıldığında yapılacak işlemler (varsa)
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
=======
>>>>>>> 6cbdaabe338e36f5ef27bd783c7218d5c427574f
                    } else {
                        Toast.makeText(LoginSuccesfullActivity.this, "Geçersiz miktar", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Bakiye eklenirken hata: " + e.getMessage());
                }
            }
        });

<<<<<<< HEAD

=======
>>>>>>> 6cbdaabe338e36f5ef27bd783c7218d5c427574f
        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginSuccesfullActivity.this, "Çıkış yapıldı.", Toast.LENGTH_SHORT).show();
                handleLogout();
            }
        });

        // Seçilen bilet bilgilerini al
        ticketList = new ArrayList<>(sharedPreferences.getStringSet(TICKET_INFO_KEY, new HashSet<>()));

        // RecyclerView ve Adapter'i ayarla
        recyclerView = findViewById(R.id.recyclerViewTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketAdapter(ticketList, this);
        recyclerView.setAdapter(ticketAdapter);
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

    private int getBalance() {
        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        return loginPrefs.getInt(BALANCE_KEY, 0);
    }

    private void updateBalance(int newBalance) {
        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putInt(BALANCE_KEY, newBalance);
        editor.apply();
    }

    @Override
    public void onDeleteClick(int position) {
        String ticketInfo = ticketList.get(position);
        int ticketPrice = getTicketPrice(ticketInfo);

        // Bileti sil
        ticketList.remove(position);
        ticketAdapter.notifyItemRemoved(position);

        // Bileti sildiğimizde bakiyeyi güncelle
        bakiye += ticketPrice;
        updateBalance(bakiye);
        tvBakiye.setText("Bakiye: " + bakiye + " TL");

        // SharedPreferences güncelle
        Set<String> ticketInfoSet = new HashSet<>(ticketList);
        sharedPreferences.edit().putStringSet(TICKET_INFO_KEY, ticketInfoSet).apply();
    }

    private int getTicketPrice(String ticketInfo) {
        if (ticketInfo.contains("₺300,00")) {
            return 300;
        } else if (ticketInfo.contains("₺200,00")) {
            return 200;
        } else if (ticketInfo.contains("₺150,00")) {
            return 150;
        } else if (ticketInfo.contains("₺100,00")) {
            return 100;
        } else if (ticketInfo.contains("₺50,00")) {
            return 50;
        } else {
            return 0;
        }
    }
}
