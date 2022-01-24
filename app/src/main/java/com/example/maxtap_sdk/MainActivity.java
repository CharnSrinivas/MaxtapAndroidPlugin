package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_btn).setOnClickListener((start_button)->{
            startActivity(new Intent(MainActivity.this,VideoPlayer.class));
        });
//         Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserId("123456");
        Bundle bundle = new Bundle();
        bundle.putString("test_data","hello i am from Amsterdam");
        mFirebaseAnalytics.logEvent("hitesh_test_event", bundle);

    }
}
