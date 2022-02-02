package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.brightcove_watch).setOnClickListener((start_button)->{
            startActivity(new Intent(MainActivity.this, BrightcovePlayerTest.class));
        });
        findViewById(R.id.exoplayer_watch).setOnClickListener((start_button)->{
            startActivity(new Intent(MainActivity.this, ExoplayerTest.class));
        });
    }
}
