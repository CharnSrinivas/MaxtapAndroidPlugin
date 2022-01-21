package com.example.maxtap_sdk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import com.maxtap.MaxTap;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;


public class MainActivity extends AppCompatActivity {

    MaxTap maxTapAds;
    ExoPlayer exoPlayer;

    Handler maxtapAdHandler = new Handler();
    Runnable maxtapAdRunnable = new Runnable() {
        @Override
        public void run() {
            maxTapAds.updateAds(exoPlayer.getCurrentPosition());
            maxtapAdHandler.postDelayed(maxtapAdRunnable,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        Bundle bundle = new Bundle();
//        bundle.putString("test_data","Just seen");
//        mFirebaseAnalytics.logEvent("android_test_event", bundle);

        exoPlayer = new ExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.video_player);
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

        maxTapAds =  new MaxTap(this,  playerView, "test_data");
        maxTapAds.init();
        maxtapAdHandler.postDelayed(maxtapAdRunnable,1000);
    }

}
