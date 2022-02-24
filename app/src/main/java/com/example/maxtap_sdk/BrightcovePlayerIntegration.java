package com.example.maxtap_sdk;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcovePlayer;
import net.maxtap.android_sdk.Maxtap;

import java.net.URI;
import java.net.URISyntaxException;


public class BrightcovePlayerIntegration extends BrightcovePlayer {
    //    ExoPlayer exoPlayer;
    Handler maxtapAdHandler = new Handler();
    Runnable maxtapAdRunnable = new Runnable() {
        @Override
        public void run() {
            Maxtap.MaxtapComponent().updateAds(brightcoveVideoView.getCurrentPosition());
            maxtapAdHandler.postDelayed(maxtapAdRunnable, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brightcove_player);
        brightcoveVideoView = findViewById(R.id.video_player);
        Video video = Video.createVideo("android.resource://"+getPackageName()+"/"+R.raw.sample_mp4, DeliveryType.MP4);

        try {
            java.net.URI myposterImage = new URI("https://sdks.support.brightcove.com/assets/images/general/Great-Blue-Heron.png");
            video.getProperties().put(Video.Fields.STILL_IMAGE_URI, myposterImage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        brightcoveVideoView.add(video);
        brightcoveVideoView.start();
        String content_id = getIntent().getStringExtra("content_id") != null ?getIntent().getStringExtra("content_id") : "6272262646001";
        Maxtap.MaxtapComponent().init(this,brightcoveVideoView,content_id);
        maxtapAdHandler.postDelayed(maxtapAdRunnable, 500);
    }
    @Override
    protected void onDestroy() {
        maxtapAdHandler.removeCallbacks(maxtapAdRunnable);
        Maxtap.MaxtapComponent().remove();
        super.onDestroy();
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
