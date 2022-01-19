package com.amrit.practice.maxtap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amrit.practice.maxtap.utils.HttpHandler;
import com.amrit.practice.maxtap.utils.ImageCache;
import com.google.android.exoplayer2.ExoPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class MaxTap extends AppCompatActivity {

    Context context;
    Activity activity;
    ImageView imageView;
    TextView textView;
    FrameLayout ad_container;
    View video_player;
    JSONArray ad_data = null;
    ExoPlayer exoPlayer = null;
    MediaPlayer mediaPlayer = null;
    String movieId;
    int video_player_width, video_player_height;
    boolean is_fist_image_loaded = false;

    Handler handler = new Handler();

    Runnable adsRunnable = new Runnable() {
        @Override
        public void run() {
            if (exoPlayer != null)
                updateAds(exoPlayer.getCurrentPosition());
            else if (mediaPlayer != null)
                updateAds(mediaPlayer.getCurrentPosition());

            handler.postDelayed(adsRunnable, 1000);
        }
    };

    Thread loadAndCacheAdImages = new Thread(() -> {
        try {

            for (int i = 0; i < ad_data.length(); i++) {
                String url = null;
                url = ad_data.getJSONObject(i).getString("image_link");
                // Caching images
                InputStream is = new URL(url).openStream();
                Bitmap image = BitmapFactory.decodeStream(is);
                ImageCache.getInstance().saveBitmapToCache(url, image);
                if (!is_fist_image_loaded) {
                    is_fist_image_loaded = true;
                    startAds();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    });

    Thread fetchRequiredData = new Thread(new Runnable() {
        @Override
        public void run() {
            fetchAdData();
            loadAndCacheAdImages.start();
        }
    });

    public MaxTap(Context context, Activity activity, View player, ExoPlayer exoPlayer, String movieId) {
        this.context = context;
        this.activity = activity;
        this.video_player = player;
        this.exoPlayer = exoPlayer;
        this.movieId = movieId;
    }

    public MaxTap(Context context, Activity activity, View player, MediaPlayer mediaPlayer, String movieId) {
        this.context = context;
        this.activity = activity;
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
        });
        // Asynchronously fetch json ad data and prefetch , cache ad images.

        fetchRequiredData.start();
    }

    private void initializeComponent() {
        imageView = new ImageView(context);
        int img_width;
        if (this.video_player_height > this.video_player_width) {
            img_width = this.video_player_height * 10 / 100;
        } else {
            img_width = this.video_player_width * 10 / 100;
        }
        Log.i("My_tag", img_width + "");
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
        ad_container_parms.rightMargin = this.video_player_width / 100;

        ((ViewGroup) video_player).addView(ad_container, ad_container_parms);
    }

    private void fetchAdData() {
        String url = "https://storage.googleapis.com/maxtap-adserver-dev.appspot.com/" + movieId + ".json";
        // String url =
        // "https://firebasestorage.googleapis.com/v0/b/maxtap-adserver-dev.appspot.com/o/Naagin.json?alt=media&token=7b26b182-2da0-4174-afe0-697fe96ed287";

        String data = new HttpHandler().makeServiceCall(url);

        try {
            ad_data = new JSONArray(data);
            for (int i = 0; i < ad_data.length(); i++) {
                JSONObject object = ad_data.getJSONObject(i);
                int startTime = object.getInt("start_time");
                int endTime = object.getInt("end_time");
                String imageUrl = object.getString("image_link");
                String captionRegionalLanguage = object.getString("caption_regional_language");
                String productLink = object.getString("product_link");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startAds() {
        handler.postDelayed(adsRunnable, 1000);
    }

    private void updateAds(long currentPosition) {
        try {
            currentPosition /= 1000;
            if (ad_data == null)
                return;

            boolean visible = false;

            for (int i = 0; i < ad_data.length(); i++) {
                JSONObject data = ad_data.getJSONObject(i);
                int startTime = data.getInt("start_time");
                int endTime = data.getInt("end_time");

                if (currentPosition >= startTime && currentPosition <= endTime) {
                    ad_container.setVisibility(View.VISIBLE);
                    String imageLink = data.getString("image_link");
                    String msg = data.getString("caption_regional_language");
                    String redirect_link = data.getString("redirect_link");
                    Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(imageLink);
                    imageView.setImageBitmap(bitmap);
                    textView.setText(msg);
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
            Log.e("error", e.toString());
        }

    }
}
