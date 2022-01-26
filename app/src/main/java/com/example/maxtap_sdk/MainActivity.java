package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("advertiser","myntra");
        mFirebaseAnalytics.logEvent("charan_test_event", bundle);

        mTracker = getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Android_Test_Category")
                .setAction("Android_Test_Action").set("some_data","charan 123").set("more_data","Some random data.")
                .build());
    }
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}