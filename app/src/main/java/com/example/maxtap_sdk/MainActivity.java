package com.example.maxtap_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import net.maxtap.android_sdk.Maxtap;


public class MainActivity extends AppCompatActivity {
    //    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.brightcove_watch).setOnClickListener((start_button) -> {
            startActivity(new Intent(MainActivity.this, ContentId.class));
        });
        findViewById(R.id.exoplayer_watch).setOnClickListener((start_button) -> {
            startActivity(new Intent(MainActivity.this, ExoplayerIntegration.class));
        });
        findViewById(R.id.open_web_view).setOnClickListener((open_btn) -> {
            Maxtap.MaxtapComponent().openWebView( "https://google.com");
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((Maxtap.MaxtapComponent().webView != null && keyCode == KeyEvent.KEYCODE_BACK) && Maxtap.MaxtapComponent().webView.canGoBack()) {
            Maxtap.MaxtapComponent().webView.goBack();
            return true;
        }
        if((Maxtap.MaxtapComponent().webView != null && keyCode == KeyEvent.KEYCODE_BACK) && ! Maxtap.MaxtapComponent().webView.canGoBack()){
            if(Maxtap.MaxtapComponent().closeWebView()){
                return true;
            }
            return  super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
