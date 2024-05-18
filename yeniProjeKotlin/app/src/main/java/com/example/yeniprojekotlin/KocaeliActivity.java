package com.example.yeniprojekotlin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class KocaeliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kocaeli);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Spinner
        Spinner kategoriSpinner = findViewById(R.id.kategori_spinner);
        String[] spinnerArray = {"KAPALI SAĞ - SOL", "DOĞU MARATON", "GÜNEY KALE ARKASI", "KUZEY KALE ARKASI"};

        // ArrayAdapter oluştur ve Spinner'a bağla
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapter2);

        // Geri butonuna tıklanınca geri gitme işlemi
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Örnek veri
        List<String> titles = new ArrayList<>();
        titles.add("VIP A-B-C-D");
        titles.add("Title 3");
        List<String> descriptions = new ArrayList<>();
        descriptions.add("₺300,00");
        descriptions.add("Description 3");

        // Özel adaptörü oluştur
        CustomAdapter adapter = new CustomAdapter(this, titles, descriptions);

        // ListView'e adaptörü ayarla
        ListView listView = findViewById(R.id.kocaeli_listView);
        listView.setAdapter(adapter);

        // Satin Al butonu
        Button satinAlBtn = findViewById(R.id.satinAlBtn);
        satinAlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast mesajını göster
                Toast.makeText(KocaeliActivity.this, "Bilet başarıyla satın alındı", Toast.LENGTH_SHORT).show();
            }
        });

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
