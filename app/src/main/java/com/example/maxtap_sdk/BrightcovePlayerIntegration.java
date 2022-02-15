package com.example.maxtap_sdk;

import android.os.Bundle;
import android.os.Handler;

import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcovePlayer;
import com.maxtap.MaxTap;

import java.net.URI;
import java.net.URISyntaxException;


public class BrightcovePlayerIntegration extends BrightcovePlayer {
    MaxTap maxTapAds;
    //    ExoPlayer exoPlayer;
    Handler maxtapAdHandler = new Handler();
    Runnable maxtapAdRunnable = new Runnable() {
        @Override
        public void run() {
            maxTapAds.updateAds(brightcoveVideoView.getCurrentPosition());
            maxtapAdHandler.postDelayed(maxtapAdRunnable, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brightcove_player);
        brightcoveVideoView = findViewById(R.id.video_player);
        Video video = Video.createVideo("android.resource://"+getPackageName()+"/"+R.raw.sample_video, DeliveryType.MP4);

        try {
            java.net.URI myposterImage = new URI("https://sdks.support.brightcove.com/assets/images/general/Great-Blue-Heron.png");
            video.getProperties().put(Video.Fields.STILL_IMAGE_URI, myposterImage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        brightcoveVideoView.add(video);
        brightcoveVideoView.start();
        maxTapAds = new MaxTap(this, brightcoveVideoView, "test_data");
        maxTapAds.init();
        maxtapAdHandler.postDelayed(maxtapAdRunnable, 500);
    }

}
