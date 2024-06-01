package com.example.yeniprojekotlin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KocaeliActivity extends AppCompatActivity {

    private Spinner kategoriSpinner;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ticket_prefs";
    private static final String TICKET_INFO_KEY = "ticket_info_list";
    private static final String LOGIN_PREFS = "login_prefs";
    private static final String BALANCE_KEY = "balance";
    private TextView tvBakiye;
    private int bakiye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kocaeli);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Bakiye TextView'i ayarla
        tvBakiye = findViewById(R.id.tvBakiye);

        // Kategori verilerini hazırlama
        List<String> categoryList = new ArrayList<>();
        categoryList.add("VIP A-B-C-D - ₺300,00");
        categoryList.add("KAPALI SAĞ - SOL - ₺200,00");
        categoryList.add("DOĞU MARATON - ₺150,00");
        categoryList.add("GÜNEY KALE ARKASI - ₺100,00");
        categoryList.add("KUZEY KALE ARKASI - ₺50,00");

        // Spinner
        kategoriSpinner = findViewById(R.id.kategori_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(spinnerAdapter);

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(categoryAdapter);

        // Geri butonuna tıklanınca geri gitme işlemi
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Konum göster
        Button konum = findViewById(R.id.kocaeliKonumGoster);
        konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KocaeliActivity", "Konum butonuna tıklandı.");
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=Yıldız+Entegre+Kocaelispor+Stadyumu");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                // Intent'i başlat
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                    Log.d("KocaeliActivity", "Harita intent'i başlatıldı.");
                } else {
                    Log.e("KocaeliActivity", "Google Haritalar uygulaması bulunamadı.");
                    Toast.makeText(KocaeliActivity.this, "Google Haritalar uygulaması yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Satin Al butonu
        Button satinAlBtn = findViewById(R.id.satinAlBtn);
        satinAlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedTicketInfo = kategoriSpinner.getSelectedItem().toString();
                if (selectedTicketInfo.isEmpty()) {
                    Toast.makeText(KocaeliActivity.this, "Lütfen bir bilet seçin", Toast.LENGTH_SHORT).show();
                } else {
                    int ticketPrice = getTicketPrice(selectedTicketInfo);
                    if (bakiye >= ticketPrice) {
                        // Bakiye yeterli, bileti satın al
                        bakiye -= ticketPrice;
                        updateBalance(bakiye);

                        // Seçilen bilet bilgisini SharedPreferences'a ekle
                        Set<String> ticketInfoSet = sharedPreferences.getStringSet(TICKET_INFO_KEY, new HashSet<>());
                        ticketInfoSet.add(selectedTicketInfo);
                        sharedPreferences.edit().putStringSet(TICKET_INFO_KEY, ticketInfoSet).apply();

                        // Bakiye TextView'ini güncelle
                        tvBakiye.setText("Bakiye: " + bakiye + " TL");

                        // Toast mesajını göster
                        Toast.makeText(KocaeliActivity.this, "Bilet başarıyla satın alındı", Toast.LENGTH_SHORT).show();

                        // LoginSuccesfullActivity'ye geç
                        Intent intent = new Intent(KocaeliActivity.this, LoginSuccesfullActivity.class);
                        startActivity(intent);
                    } else {
                        // Bakiye yetersiz, uyarı göster
                        Toast.makeText(KocaeliActivity.this, "Yetersiz bakiye", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Bakiye bilgisini güncelle
        bakiye = getBalance();
        tvBakiye.setText("Bakiye: " + bakiye + " TL");
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

    // Geri butonuna tıklandığında yapılacak işlemi tanımlama
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
