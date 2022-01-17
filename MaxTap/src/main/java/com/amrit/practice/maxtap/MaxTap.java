package com.amrit.practice.maxtap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import com.google.android.exoplayer2.ExoPlayer;

public class MaxTap {

    Context context;
    Activity activity;
    ImageView imageView;
    TextView textView;
    FrameLayout framelayout;
    View player;
    ArrayList<MovieData> mResult = null;
    ExoPlayer exoPlayer = null;
    MediaPlayer mediaPlayer = null;
    String movieId;

    Handler handler = new Handler();

    public MaxTap(Context context, Activity activity, View player, ExoPlayer exoPlayer, String movieId) {
        this.context = context;
        this.activity = activity;
        this.player = player;
        this.exoPlayer = exoPlayer;
        this.movieId = movieId;
    }

    public MaxTap(Context context, Activity activity, View player, MediaPlayer mediaPlayer, String movieId){
        this.context = context;
        this.activity = activity;
        this.player = player;
        this.mediaPlayer = mediaPlayer;
        this.movieId = movieId;
    }

    public void loadAds() {
        imageView = new ImageView(context);
        LayoutParams imageParams = new LayoutParams(300, LayoutParams.WRAP_CONTENT);
        imageParams.setMargins(10, 10, 10, 10);
        imageParams.gravity = Gravity.RIGHT;
        imageView.setLayoutParams(imageParams);

        textView = new TextView(context);
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setPadding(20, 0, 0, 0);
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0,0,300,0);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(textParams);

        //Initializing frame layout
        framelayout = new FrameLayout(context);
        framelayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        framelayout.setBackgroundColor(Color.argb(60, 0, 0, 0));

        //Adding views to FrameLayout
        framelayout.addView(imageView);
        framelayout.addView(textView);
        framelayout.setVisibility(View.GONE);

        LayoutParams params = new LayoutParams(500, 200);
        params.gravity = Gravity.RIGHT|Gravity.BOTTOM;
        activity.addContentView(framelayout, params);

        gettingJSON.start();
    }

    Thread gettingJSON = new Thread(new Runnable() {
        @Override
        public void run() {
            getData();
            gettingImage.start();
        }
    });

    private void getData(){
        String url = "https://storage.googleapis.com/maxtap-adserver-dev.appspot.com/" + movieId + ".json";
//        String url = "https://firebasestorage.googleapis.com/v0/b/maxtap-adserver-dev.appspot.com/o/Naagin.json?alt=media&token=7b26b182-2da0-4174-afe0-697fe96ed287";

        String data = new HttpHandler().makeServiceCall(url);
        mResult = new ArrayList<>();

        try {
            JSONArray jsonArr = new JSONArray(data);
            for(int i = 0; i < jsonArr.length(); i++){
                JSONObject object = jsonArr.getJSONObject(i);
                int startTime = object.getInt("start_time");
                int endTime = object.getInt("end_time");
                String imageUrl = object.getString("image_link");
                String captionRegionalLanguage = object.getString("caption_regional_language");
                String productLink = object.getString("product_link");
                MovieData movie = new MovieData(startTime, endTime, productLink, imageUrl, captionRegionalLanguage);
                mResult.add(movie);
                MovieData.sort(mResult);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Thread gettingImage = new Thread(this::saveImage);

    private void saveImage() {
        for(MovieData data: mResult){
            String url = data.getImageLink();
            try {
                InputStream is = new URL(url).openStream();
                Bitmap image = BitmapFactory.decodeStream(is);
                ImageCache.getInstance().saveBitmapToCache(url, image);
                updateAds();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAds() {
        handler.postDelayed(adsRunnable, 1000);
    }

    Runnable adsRunnable = new Runnable() {
        @Override
        public void run() {
            if(exoPlayer != null) updateAds(exoPlayer.getCurrentPosition());
            else if(mediaPlayer != null) updateAds(mediaPlayer.getCurrentPosition());

            handler.postDelayed(adsRunnable, 1000);
        }
    };

    @SuppressLint("QueryPermissionsNeeded")
    private void updateAds(long currentPosition) {
        currentPosition /= 1000;
        if(mResult == null) return;

        boolean visible = false;
        for(MovieData data: mResult){
            int startTime = data.getStartTime();
            int endTime = data.getEndTime();

            if(currentPosition >= startTime && currentPosition <= endTime){
                framelayout.setVisibility(View.VISIBLE);
                String imageLink = data.getImageLink();
                String msg = data.getCaption();
                String productLink = data.getProductLink();
                Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(imageLink);
                imageView.setImageBitmap(bitmap);
                textView.setText(msg);
                framelayout.setOnClickListener(v -> {
                    Uri uri = Uri.parse(productLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                });
                visible = true;
                break;
            }
        }
        if(!visible) framelayout.setVisibility(View.GONE);
    }

}
