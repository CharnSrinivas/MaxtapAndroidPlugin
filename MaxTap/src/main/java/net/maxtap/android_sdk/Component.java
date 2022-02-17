package net.maxtap.android_sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.maxtap.R;

import net.maxtap.android_sdk.Models.AdData;
import net.maxtap.android_sdk.utils.GaAnalyticsHelper;
import net.maxtap.android_sdk.utils.HttpHandler;
import net.maxtap.android_sdk.utils.ImageCache;
import net.maxtap.android_sdk.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Component extends AppCompatActivity {

    Context context = null;
    ImageView adImage = null;
    TextView adText = null;
    FrameLayout ad_container = null;
    View video_player = null;
    ArrayList<AdData> ads_data = new ArrayList<>();
    JSONArray ad_data_json = new JSONArray();
    String content_id = null;
    int screen_width, screen_height;
    int current_ad_index = -1;
    GaAnalyticsHelper analyticsHelper;
    boolean isInitializing = false;
    boolean isInitialized = false;


    public void init(Context context, View player, String content_id) {
        try {
            if (isInitialized||isInitializing) {
                remove();
            }
            this.context = context;
            this.video_player = player;
            this.content_id = content_id;
            this.analyticsHelper = new GaAnalyticsHelper(context);
            isInitializing = true;
            // Waiting until video player is totally rendered
            utils.log("Check point -1");
            player.post(() -> {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                screen_height = displayMetrics.heightPixels;
                screen_width = displayMetrics.widthPixels;
                utils.log("Check point -2");
                initializeComponent();
            });

            // Asynchronously fetch json ad data and prefetch in new thread
            new Thread(() -> {
                String url;
                // Adding '.json' at the end of content id
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
                    /* Checking if every ad data have required parameters.
                    and removing only that particular ad from ad_data showing rest of ads
                     */
                        for (String parm : Config.AdParms.REQUIRED) {
                            if (!json_ad.has(parm)) {
                                is_valid_data = false;
                                break;
                            }
                        }
                        if (!is_valid_data) continue;

                        ad.startTime = json_ad.getInt(Config.AdParms.START_TIME);
                        ad.end_time = json_ad.getInt(Config.AdParms.END_TIME);
                        ad.ad_text =json_ad.has(Config.AdParms.CAPTION_REGIONAL_LANGUAGE)? json_ad.getString(Config.AdParms.CAPTION_REGIONAL_LANGUAGE) :
                        json_ad.has(Config.AdParms.CAPTION) ? json_ad.getString(Config.AdParms.CAPTION):
                        json_ad.has(Config.AdParms.ARTICLE_TYPE) ? "Get this "+json_ad.getString(Config.AdParms.ARTICLE_TYPE)+" now": "Get this now";

                        ad.imageLink = json_ad.getString(Config.AdParms.IMAGE_LINK);
                        ad.redirect_link = json_ad.getString(Config.AdParms.REDIRECT_LINK);
                        utils.log(i+"  ->  "+ad.startTime+"  "+ad.end_time+"  "+ ad.ad_text);
                        ads_data.add(ad);
                    }
                } catch (Exception e) {
                    utils.printError(e);
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ad_container.setId(R.id.maxtap_container_id);
        ad_container.setVisibility(View.GONE);
        // Ad container layout
        FrameLayout.LayoutParams ad_container_parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(img_width, img_width);
        imageParams.setMargins(10, 10, 10, 10);
        imageParams.gravity = Gravity.RIGHT;
        adImage.setLayoutParams(imageParams);

        // Text layout
        FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, 0, img_width, 0);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        adText.setLayoutParams(textParams);

        ad_container_parms.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        ad_container_parms.rightMargin = this.screen_width / 200;

        // Adding views to FrameLayout
        ad_container.addView(adImage);
        ad_container.addView(adText);

        ((ViewGroup) video_player).addView(ad_container, ad_container_parms);
        isInitialized = true;
        utils.log("Check point -3 "+ad_container);
    }

    public void updateAds(long currentPosition) {
        try {
            currentPosition /= 1000;
            if (ad_container == null) {
                utils.log("no ad container");
                return;
            }
            if (ads_data == null)
            {
                return;
            }
            boolean visible = false;
            for (int index = 0; index < ads_data.size(); index++) {
                AdData ad_data = ads_data.get(index);
                int startTime = ad_data.startTime;
                int endTime = ad_data.end_time;
                /*
                if caption_regional_language available set that else if cation available set that else set DefaultAdCaption
                */

                String redirect_link = ad_data.redirect_link;
                boolean is_in_range = (currentPosition >= startTime && currentPosition <= endTime);
                boolean can_prefetch = (startTime - currentPosition <= Config.AdImagePrecacheingTime && startTime - currentPosition >= 0)
                        && !ad_data.image_loaded && !ad_data.image_loading;

                // Checking if image is already loaded (or) loading
                if (can_prefetch) {
                    ImageCache.getInstance().cache(ad_data);
                }
                if (is_in_range) {
                    visible = true;
                    if (this.current_ad_index != index && ad_data.image_loaded) {
                        utils.log("check point -4 "+this.current_ad_index +"  "+index);
                        this.current_ad_index = index;
                        Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(ad_data.imageLink);
                        if (bitmap == null) break;
                        // Show ads  if we find cached image data
                        ad_container.setVisibility(View.VISIBLE);
                        adImage.setImageBitmap(bitmap);
                        adText.setText(ad_data.ad_text);
                        int finalIndex = index;
                        ad_container.setOnClickListener((View ad) -> {
                            // Increasing no.of ad clicks
                            ad_data.no_of_clicks++;
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(redirect_link)));
                            try {
                                // Triggering analytics click event
                                analyticsHelper.logClickEvent(utils.createGAClickProperties(ad_data_json.getJSONObject(finalIndex), ad_data));
                            } catch (JSONException e) {
                                utils.printError(e);
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
            utils.printError(e);
            e.printStackTrace();
        }

    }

    public void remove() {
        try {
            utils.log("Removing");
            if (context!=null && ((Activity) context).findViewById(R.id.maxtap_container_id) != null) {
                utils.log("Removing component");
                ((ViewGroup) video_player).removeView(((Activity) context).findViewById(R.id.maxtap_container_id));
            }
            this.ad_container = null;
            this.isInitializing = false;
            this.isInitialized = false;
            this.ads_data = new ArrayList<>();
            this.ad_data_json = new JSONArray();
            this.video_player = null;
            this.context = null;
            this.content_id = null;
            this.current_ad_index = -1;
        } catch (Exception e) {
            utils.printError(e);
            e.printStackTrace();
        }
    }

}
