package com.maxtap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
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
    ImageView adImage;
    TextView adText;
    FrameLayout ad_container;
    View video_player;
    JSONArray ad_data = null;
    ExoPlayer exoPlayer = null;
    MediaPlayer mediaPlayer = null;
    String content_id;
    int screen_width, screen_height;


    public MaxTap(Context context, View player, String content_id) {
        this.context = context;
        this.video_player = player;
        this.content_id = content_id;
    }

    public MaxTap(Context context, View player, MediaPlayer mediaPlayer, String content_id) {
        this.context = context;
        this.video_player = player;
        this.mediaPlayer = mediaPlayer;
        this.content_id = content_id;
    }

    public void init() {
        // Waiting until video player is totally rendered
        video_player.post(() -> {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screen_height = displayMetrics.heightPixels;
            screen_width = displayMetrics.widthPixels;
            initializeComponent();
        });


        // Asynchronously fetch json ad data and prefetch
        new Thread(() -> {
            String url;
            if (content_id.contains(".json")) {
                url = Config.CloudBucketUrl + content_id;
            } else {
                url = Config.CloudBucketUrl + content_id + ".json";
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
        adImage = new ImageView(context);


        adText = new TextView(context);
        adText.setTextSize(14);
        adText.setTextColor(Color.parseColor(Config.AdTextColor));
        adText.setPadding(20, 0, 0, 0);

        // Initializing frame layout
        ad_container = new FrameLayout(context);
        ad_container.setBackgroundColor(Config.AdBgColor);
        // Adding views to FrameLayout
        ad_container.addView(adImage);
        ad_container.addView(adText);
        ad_container.setVisibility(View.GONE);


        // Ad container layout
        LayoutParams ad_container_parms = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ad_container_parms.gravity = Gravity.RIGHT | Gravity.BOTTOM;

        int img_width;
        if (this.screen_height > this.screen_width) {
            // Portrait config
            img_width = this.screen_height * 8 / 100;
            adText.setMaxWidth(3 * img_width);
            ad_container_parms.bottomMargin = this.screen_width * 10 / 100;
        } else {
            // Landscape config
            img_width = this.screen_width * 10 / 100;
            adText.setMaxWidth(2 * img_width);
            ad_container_parms.bottomMargin = this.screen_height * 10 / 100;
        }

        // Image layout
        LayoutParams imageParams = new LayoutParams(img_width, img_width);
        imageParams.setMargins(10, 10, 10, 10);
        imageParams.gravity = Gravity.RIGHT;
        adImage.setLayoutParams(imageParams);

        // Text layout
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, 0, img_width, 0);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        adText.setLayoutParams(textParams);

        ad_container_parms.rightMargin = this.screen_width / 150;
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

                if (startTime - currentPosition <= Config.AdImagePrecacheingTime && startTime - currentPosition >= 0) {
                    ImageCache.getInstance().cache(imageUrl);
                }

                if (currentPosition >= startTime && currentPosition <= endTime) {

                    ad_container.setVisibility(View.VISIBLE);
                    String imageLink = data.getString(Config.AdParms.IMAGE_LINK);
                    String msg = data.getString(Config.AdParms.CATION_REGIONAL_LANGUAGE);
                    String redirect_link = data.getString(Config.AdParms.REDIRECT_LINK);
                    Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(imageLink);
                    if (bitmap == null) break;
                    adImage.setImageBitmap(bitmap);
                    adText.setText(msg);
                    ad_container.setOnClickListener(v -> {
                        Uri uri = Uri.parse(redirect_link);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    });

                    visible = true;
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
