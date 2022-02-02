package com.maxtap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.maxtap.Models.AdData;
import com.maxtap.utils.GaAnalyticsHelper;
import com.maxtap.utils.HttpHandler;
import com.maxtap.utils.ImageCache;
import com.maxtap.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MaxTap extends AppCompatActivity {

    Context context;
    ImageView adImage;
    TextView adText;
    FrameLayout ad_container;
    View video_player;
    ArrayList<AdData> ads_data = new ArrayList<>();
    JSONArray ad_data_json = new JSONArray();
    String content_id;
    int screen_width, screen_height;
    int current_ad_index = -1;
    GaAnalyticsHelper analyticsHelper;

    public MaxTap(Context context, View player, String content_id) {
        this.context = context;
        this.video_player = player;
        this.content_id = content_id;
        this.analyticsHelper = new GaAnalyticsHelper(context);
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
            String response_data = new HttpHandler().makeServiceCall(url);
            try {
                ad_data_json = new JSONArray(response_data);
                for (int i = 0; i < ad_data_json.length(); i++) {
                    AdData ad = new AdData();
                    boolean is_valid_data = true;
                    JSONObject json_ad = ad_data_json.getJSONObject(i);
                    for (String parm:Config.AdParms.REQUIRED) {
                        if(!json_ad.has(parm)){ Log.i("maxtap","Doesn't have property "+parm); is_valid_data=false;break;}
                    }
                    if(!is_valid_data){continue;}
                    ad.startTime = json_ad.getInt(Config.AdParms.START_TIME);
                    ad.end_time = json_ad.getInt(Config.AdParms.END_TIME);
                    ad.caption = json_ad.getString(Config.AdParms.CATION_REGIONAL_LANGUAGE);
                    ad.imageLink = json_ad.getString(Config.AdParms.IMAGE_LINK);
                    ad.redirect_link = json_ad.getString(Config.AdParms.REDIRECT_LINK);
                    ads_data.add(ad);
                }
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

        ad_container.setVisibility(View.GONE);

        // Ad container layout
        LayoutParams ad_container_parms = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int img_width;
        if (this.screen_height > this.screen_width) {
            // Portrait config
            img_width = this.screen_height * 8 / 100;
            ad_container_parms.bottomMargin = this.screen_height * 10 / 100;
            adText.setMaxWidth((int) (2.5 * img_width));
        } else {
            // Landscape config
            img_width = this.screen_width * 10 / 100;
            ad_container_parms.bottomMargin = this.screen_height * 12 / 100;
            adText.setMaxWidth((2 * img_width));
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


        ad_container_parms.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        ad_container_parms.rightMargin = this.screen_width / 200;

        // Adding views to FrameLayout
        ad_container.addView(adImage);
        ad_container.addView(adText);

        ((ViewGroup) video_player).addView(ad_container, ad_container_parms);
    }

    public void updateAds(long currentPosition) {
        try {
            currentPosition /= 1000;
            if (ads_data == null)
                return;
            boolean visible = false;
            for (int index = 0; index < ads_data.size(); index++) {
                AdData ad_data = ads_data.get(index);
                int startTime = ad_data.startTime;
                int endTime = ad_data.end_time;
                String ad_text = ad_data.caption;
                String redirect_link = ad_data.redirect_link;
                boolean is_in_range = (currentPosition >= startTime && currentPosition <= endTime);
                boolean can_prefetch = (startTime - currentPosition <= Config.AdImagePrecacheingTime && startTime - currentPosition >= 0) && !ad_data.image_loaded && !ad_data.image_loading;
                if (can_prefetch) {
                    ImageCache.getInstance().cache(ad_data);
                }
                if (is_in_range) {
                    visible = true;
                    if (ad_container.getVisibility() == View.GONE && this.current_ad_index != index && ad_data.image_loaded) {
                        this.current_ad_index = index;
                        Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(ad_data.imageLink);
                        if (bitmap == null) break;
                        // Set visibility iff we find cached image data
                        ad_container.setVisibility(View.VISIBLE);
                        adImage.setImageBitmap(bitmap);
                        adText.setText(ad_text);
                        int finalIndex = index;
                        ad_container.setOnClickListener((View ad) -> {
                            ad_data.no_of_clicks++;
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(redirect_link)));
                            try {
                                analyticsHelper.logClickEvent(utils.createGAClickProperties(ad_data_json.getJSONObject(finalIndex), ad_data));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        ad_data.no_of_views++;
                        analyticsHelper.logImpressionEvent(utils.createGAImpressionProperties(ad_data_json.getJSONObject(index), ad_data));
                    }
                }
            }
            if (!visible) {
                ad_container.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
