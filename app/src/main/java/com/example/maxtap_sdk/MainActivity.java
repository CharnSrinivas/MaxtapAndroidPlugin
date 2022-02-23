package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.brightcove_watch).setOnClickListener((start_button)->{
            startActivity(new Intent(MainActivity.this, ContentId.class));
        });
        findViewById(R.id.exoplayer_watch).setOnClickListener((start_button)->{
            startActivity(new Intent(MainActivity.this, ExoplayerIntegration.class));
        });
    }
}
