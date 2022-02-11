package com.example.maxtap_sdk;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.maxtap.MaxTap;

public class ExoplayerIntegration extends AppCompatActivity {
    ExoPlayer exoPlayer;
    MaxTap maxTapAds;


    Handler maxtapAdHandler = new Handler();
    Runnable maxtapAdRunnable = new Runnable() {
        @Override
        public void run() {
            maxTapAds.updateAds(exoPlayer.getCurrentPosition());
            maxtapAdHandler.postDelayed(maxtapAdRunnable, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exoplayer);
        exoPlayer= new ExoPlayer.Builder(this).build();

        PlayerView playerView = findViewById(R.id.video_player);
        //Binding the player with the view that is there in our xml.
        playerView.setPlayer(exoPlayer);
        //Building the media Item.
        MediaItem mediaItem = MediaItem.fromUri("android.resource://"+getPackageName()+"/"+R.raw.sample_video);
        //Setting the media item that is to be played.
        exoPlayer.setMediaItem(mediaItem);
        //preparing the player
        exoPlayer.prepare();
        //playing the video.
        exoPlayer.play();


        // Initializing
        maxTapAds = new MaxTap(this,playerView,"test_data");
        maxTapAds.init();
        // Update ads for every one second
        maxtapAdHandler.postDelayed(maxtapAdRunnable, 500);
    }

}