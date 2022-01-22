package com.example.maxtap_sdk;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcovePlayer;
import com.brightcove.player.view.BrightcoveVideoView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.maxtap.MaxTap;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import java.net.URI;
import java.net.URISyntaxException;


public class MainActivity extends BrightcovePlayer {

    MaxTap maxTapAds;
//    ExoPlayer exoPlayer;
    FirebaseAnalytics    mFirebaseAnalytics;
    Handler maxtapAdHandler = new Handler();
    Runnable maxtapAdRunnable = new Runnable() {
        @Override
        public void run() {
            maxTapAds.updateAds(brightcoveVideoView.getCurrentPosition());
            maxtapAdHandler.postDelayed(maxtapAdRunnable,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        brightcoveVideoView =  findViewById(R.id.video_player);
        Video video = Video.createVideo("http://sdks.support.brightcove.com/assets/videos/hls/greatblueheron/greatblueheron.m3u8",
                DeliveryType.HLS);
        try {
            java.net.URI myposterImage = new URI("https://sdks.support.brightcove.com/assets/images/general/Great-Blue-Heron.png");
            video.getProperties().put(Video.Fields.STILL_IMAGE_URI, myposterImage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        brightcoveVideoView.add(video);
        brightcoveVideoView.start();

        // Obtain the FirebaseAnalytics instance.



//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        Bundle bundle = new Bundle();
//        bundle.putString("test_data","Just seen");
//        mFirebaseAnalytics.logEvent("android_test_event", bundle);

//
//
//
//        exoPlayer = new ExoPlayer.Builder(this).build();
//        PlayerView playerView = findViewById(R.id.video_player);
//        //Binding the player with the view that is there in our xml.
//        playerView.setPlayer(exoPlayer);
//        //Building the media Item.
//        MediaItem mediaItem = MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");
//        //Setting the media item that is to be played.
//        exoPlayer.setMediaItem(mediaItem);
//        //preparing the player
//        exoPlayer.prepare();
//        //playing the video.
//        exoPlayer.play();

        maxTapAds =  new MaxTap(this,  brightcoveVideoView, "test_data");
        maxTapAds.init();
        maxtapAdHandler.postDelayed(maxtapAdRunnable,1000);
    }

}
