package com.example.kameranezo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class CameraList extends AppCompatActivity {

    private List<String> cameraNames = Arrays.asList(
            "Kamera 1 – Debrecen - Békás tó",
            "Kamera 2 - Debrecen - Kossuth tér",
            "Kamera 3 - Debrecen - Köd Színház",
            "Kamera 4 - Debrecen - Egyetem Tér"
    );

    private List<String> cameraUrls = Arrays.asList(
            "https://g0.ipcamlive.com/player/player.php?alias=5a1d2f277c3cb",
            "https://g0.ipcamlive.com/player/player.php?alias=5a1d2f80f15fb",
            "https://g0.ipcamlive.com/player/player.php?alias=5b3b5d9a29e15",
            "https://www.debrecen.hu/assets/media/gallery/hu/47176/embed_ud_kk2.html"

    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView listView = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cameraNames
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String url = cameraUrls.get(position);
            Intent intent = new Intent(this, CameraWebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        });
    }
}
