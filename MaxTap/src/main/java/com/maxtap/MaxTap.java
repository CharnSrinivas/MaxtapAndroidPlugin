package com.maxtap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.exoplayer2.ExoPlayer;
import com.maxtap.utils.HttpHandler;
import com.maxtap.utils.ImageCache;

import org.json.JSONArray;
import org.json.JSONObject;


public class MaxTap extends AppCompatActivity {

    Context context;
    ImageView imageView;
    TextView textView;
    FrameLayout ad_container;
    View video_player;
    JSONArray ad_data = null;
    ExoPlayer exoPlayer = null;
    MediaPlayer mediaPlayer = null;
    String movieId;
    int video_player_width, video_player_height;


    public MaxTap(Context context, View player,  String movieId) {
        this.context = context;
        this.video_player = player;
        this.movieId = movieId;
    }

    public MaxTap(Context context,  View player, MediaPlayer mediaPlayer, String movieId) {
        this.context = context;
        this.video_player = player;
        this.mediaPlayer = mediaPlayer;
        this.movieId = movieId;
    }

    public void init() {
        // Waiting until video player is totally rendered
        video_player.post(() -> {
            this.video_player_width = video_player.getWidth();
            this.video_player_height = video_player.getHeight();
            initializeComponent();
//            startAds();
        });

        // Asynchronously fetch json ad data and prefetch , cache ad images.

        new Thread(()->{
            String url ;
            if(movieId.contains(".json")){
                url = Config.CloudBucketUrl + movieId ;
            }else{
                url=Config.CloudBucketUrl + movieId + ".json";
            }
            String data = new HttpHandler().makeServiceCall(url);
            try {
                ad_data = new JSONArray(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initializeComponent() {
        imageView = new ImageView(context);
        int img_width;
        if (this.video_player_height > this.video_player_width) {
            img_width = this.video_player_height * 10 / 100;
        } else {
            img_width = this.video_player_width * 10 / 100;
        }
        LayoutParams imageParams = new LayoutParams(img_width, img_width);
        imageParams.setMargins(10, 10, 10, 10);
        imageParams.gravity = Gravity.RIGHT;
        imageView.setLayoutParams(imageParams);

        textView = new TextView(context);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setPadding(20, 0, 0, 0);
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setMaxWidth(2 * img_width);
        textParams.setMargins(0, 0, img_width, 0);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(textParams);

        // Initializing frame layout
        ad_container = new FrameLayout(context);
        ad_container.setBackgroundColor(Color.argb(80, 0, 0, 0));

        // Adding views to FrameLayout
        ad_container.addView(imageView);
        ad_container.addView(textView);
        ad_container.setVisibility(View.GONE);

        LayoutParams ad_container_parms = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ad_container_parms.gravity = Gravity.RIGHT | Gravity.BOTTOM;

        if (this.video_player_height > this.video_player_width) {
            ad_container_parms.bottomMargin = this.video_player_width * 10 / 100;
        } else {
            ad_container_parms.bottomMargin = this.video_player_height * 10 / 100;
        }
        ad_container_parms.rightMargin = this.video_player_width / 120;

        ((ViewGroup) video_player).addView(ad_container, ad_container_parms);
    }


    public void updateAds(long currentPosition) {
        try {
            currentPosition /= 1000;
            if (ad_data == null)
                return;

            boolean visible = false;

            for (int i = 0; i < ad_data.length(); i++) {
                JSONObject data = ad_data.getJSONObject(i);
                int startTime = data.getInt(Config.AdParms.START_TIME);
                int endTime = data.getInt(Config.AdParms.END_TIME);
                String imageUrl = data.getString(Config.AdParms.IMAGE_LINK);
//                ImageCache.getInstance().retrieveBitmapFromCache('');

                if(startTime - currentPosition <= 15 && startTime - currentPosition >=0){

                    ImageCache.getInstance().cache(imageUrl);
                }
                if (currentPosition >= startTime && currentPosition <= endTime) {

                    ad_container.setVisibility(View.VISIBLE);
                    String imageLink = data.getString(Config.AdParms.IMAGE_LINK);
                    String msg = data.getString(Config.AdParms.CATION_REGIONAL_LANGUAGE);
                    String redirect_link = data.getString(Config.AdParms.REDIRECT_LINK);
                    Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(imageLink);
                    if(bitmap!=null){
                        imageView.setImageBitmap(bitmap);
                        textView.setText(msg);
                        ad_container.setOnClickListener(v -> {
                            Uri uri = Uri.parse(redirect_link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                        });
                        visible = true;
                    }
                    break;
                }
            }

            if (!visible)
                ad_container.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
