package com.example.yeniprojekotlin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

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

        // Geri butonuna tıklanınca geri gitme işlemi
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Örnek veri
        List<String> titles = new ArrayList<>();
        titles.add("Title 1");
        titles.add("Title 2");
        titles.add("Title 3");

        List<String> descriptions = new ArrayList<>();
        descriptions.add("Description 1");
        descriptions.add("Description 2");
        descriptions.add("Description 3");

        // Özel adaptörü oluştur
        CustomAdapter adapter = new CustomAdapter(this, titles, descriptions);

        // ListView'e adaptörü ayarla
        ListView listView = findViewById(R.id.kocaeli_listView);
        listView.setAdapter(adapter);
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

