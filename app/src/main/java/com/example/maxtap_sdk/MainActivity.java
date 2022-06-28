package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    //    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        findViewById(R.id.brightcove_watch).setOnClickListener((start_button) -> {
//            startActivity(new Intent(MainActivity.this, ContentId.class));
//        });
        findViewById(R.id.start_btn).setOnClickListener((start_button) -> {
            startActivity(new Intent(MainActivity.this, HotstarDemo.class));
        });
//        findViewById(R.id.open_web_view).setOnClickListener((open_btn) -> {
//            //com.whatsapp
//            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
//            if (launchIntent != null) {
//                startActivity(launchIntent);//null pointer check in case package name was not found
//            }
////            Maxtap.MaxtapComponent().openWebView( "https://google.com");
//        });
    }

}
