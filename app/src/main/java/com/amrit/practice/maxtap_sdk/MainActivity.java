package com.amrit.practice.maxtap_sdk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.amrit.practice.maxtap.MaxTap;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExoPlayer exoPlayer = new ExoPlayer.Builder(this).build();

        PlayerView playerView = findViewById(R.id.exoPlayerView);
        //Binding the player with the view that is there in our xml.
        playerView.setPlayer(exoPlayer);
        //Building the media Item.
        MediaItem mediaItem = MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");
        //Setting the media item that is to be played.
        exoPlayer.setMediaItem(mediaItem);
        //preparing the player
        exoPlayer.prepare();
        //playing the video.
        exoPlayer.play();

        String movieId = "test_data";

        new MaxTap(this, this, playerView, exoPlayer, movieId).init();
    }

}
