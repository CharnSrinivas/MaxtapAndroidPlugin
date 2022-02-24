package net.maxtap.android_sdk;


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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.maxtap.android_sdk.Models.AdData;
import net.maxtap.android_sdk.utils.AnalyticsHelper;
import net.maxtap.android_sdk.utils.HttpHandler;
import net.maxtap.android_sdk.utils.ImageCache;
import net.maxtap.android_sdk.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Component extends AppCompatActivity {

    public WebView webView;
    Context host_context = null;
    Activity host_activity = null;
    ImageView adImage = null;
    TextView adText = null;
    FrameLayout ad_container = null;
    View video_player = null;
    ArrayList<AdData> ads_data = new ArrayList<>();
    JSONArray ad_data_json = new JSONArray();
    String content_id = null;
    int screen_width, screen_height;
    int current_ad_index = -1;
    AnalyticsHelper analyticsHelper;
    boolean isInitializing = false;
    boolean isInitialized = false;
    private boolean web_view_closed = false;

    public void openWebView(String url) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            web_view_closed = false;
            host_activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            webView = new WebView(this.host_context.getApplicationContext());
            WebViewClient webViewClient = new WebViewClient();
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(webViewClient);
            webView.loadUrl(url);
            webView.setVisibility(View.VISIBLE);
            host_activity.addContentView(webView, new ViewGroup.LayoutParams(dm.widthPixels, dm.heightPixels));
        }catch (Exception e){
            utils.printError(e);
            e.printStackTrace();
        }
    }

    public boolean closeWebView() {
        if (!web_view_closed) {
            webView.setVisibility(View.INVISIBLE);
            webView.destroy();
            web_view_closed = true;
            return true;
        } else {
            return false;
        }
    }

    public void init(Activity activity, View player, String content_id) {
        try {


            if (isInitialized || isInitializing) {
                remove();
            }
            this.host_context = activity.getApplicationContext();
            this.host_activity = activity;
            this.video_player = player;
            this.content_id = content_id;
            this.analyticsHelper = new AnalyticsHelper(host_context);
            isInitializing = true;
            //
//            new FlurryAgent.Builder()
//                      .withLogEnabled(true).build(host_context, "3WXJKNQB4S8NHDXMKSM6");
//            // Waiting until video player is totally rendered

            player.post(() -> {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                host_activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                screen_height = displayMetrics.heightPixels;
                screen_width = displayMetrics.widthPixels;
                initializeComponent();
            });

            // Asynchronously fetch json ad data and prefetch in new thread
            new Thread(() -> {
                String dataUrl;
                // Adding '.json' at the end of content id
                if (content_id.contains(".json")) {
                    dataUrl = Config.CloudBucketUrl + content_id;
                } else {
                    dataUrl = Config.CloudBucketUrl + content_id + ".json";
                }
                String response_data = new HttpHandler().makeServiceCall(dataUrl);
                try {
                    ad_data_json = new JSONArray(response_data);
                    for (int i = 0; i < ad_data_json.length(); i++) {
                        boolean is_valid_data = true;
                        JSONObject json_ad = ad_data_json.getJSONObject(i);
                    /*
                      Checking if every ad data have required parameters.
                      if not then removing or (not showing)  only that particular ad
                     */
                        for (String parm : Config.AdParms.REQUIRED) {
                            if (!json_ad.has(parm)) {
                                is_valid_data = false;
                                break;
                            }
                        }
                        // If not valid (requiied)
                        if (!is_valid_data) continue;

                        /*Creating ad object with required data and pushing to ads_data array*/
                        AdData ad = new AdData();
                        ad.startTime = json_ad.getInt(Config.AdParms.START_TIME);
                        ad.end_time = json_ad.getInt(Config.AdParms.END_TIME);
                        ad.ad_text = json_ad.has(Config.AdParms.CAPTION_REGIONAL_LANGUAGE) ? json_ad.getString(Config.AdParms.CAPTION_REGIONAL_LANGUAGE) :
                                json_ad.has(Config.AdParms.CAPTION) ? json_ad.getString(Config.AdParms.CAPTION) :
                                        json_ad.has(Config.AdParms.ARTICLE_TYPE) ? "Get this " + json_ad.getString(Config.AdParms.ARTICLE_TYPE) + " now" : Config.DefaultAdCaption;

                        ad.imageLink = json_ad.getString(Config.AdParms.IMAGE_LINK);
                        ad.redirect_link = json_ad.getString(Config.AdParms.REDIRECT_LINK);
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
        adImage = new ImageView(host_context);
        adText = new TextView(host_context);
        adText.setTextSize(14);
        adText.setTextColor(Color.parseColor(Config.AdTextColor));
        adText.setPadding(20, 0, 0, 0);

        // Initializing frame layout
        ad_container = new FrameLayout(host_context);
        ad_container.setBackgroundColor(Config.AdBgColor);
        ad_container.setId(R.id.maxtap_container_id);
        ad_container.setVisibility(View.GONE);
        // Ad container layout
        FrameLayout.LayoutParams ad_container_parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*          Configuring ad layout according screen width and screen height          */
        int img_width;
        if (this.screen_height > this.screen_width) {
            // Ad Portrait config
            img_width = this.screen_height * 8 / 100;
            ad_container_parms.bottomMargin = this.screen_height * 10 / 100;
            adText.setMaxWidth((int) (2.5 * img_width));
        } else {
            // Ad Landscape config
            img_width = this.screen_width * 10 / 100;
            ad_container_parms.bottomMargin = this.screen_height * 12 / 100;
            adText.setMaxWidth((2 * img_width));
        }
        // Ad Image layout
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

        // Adding views to FrameLayout (Ad container)
        ad_container.addView(adImage);
        ad_container.addView(adText);
        // Putting Ad Container in video view
        ((ViewGroup) video_player).addView(ad_container, ad_container_parms);
        isInitialized = true;
    }

    public void updateAds(long currentPosition) {
        try {
            currentPosition /= 1000;
            if (ad_container == null) return;
            if (ads_data == null) return;
            boolean can_ad_visible = false;
            for (int index = 0; index < ads_data.size(); index++) {
                AdData ad_data = ads_data.get(index);
                int startTime = ad_data.startTime;
                int endTime = ad_data.end_time;
                /*
                if caption_regional_language available
                    set that as ad_text
                 else if cation available
                    set that as ad_text
                 else if article type
                    set "Get this "+ article_type + "now"
                 else
                    set "Get this now"
                */
                String redirect_link = ad_data.redirect_link;
                boolean is_in_range = (currentPosition >= startTime && currentPosition <= endTime);
                boolean can_prefetch =
                        (startTime - currentPosition <= Config.AdImagePrecacheingTime)
                                && !ad_data.image_loaded && !ad_data.image_loading;

                // Checking if image is already loaded (or) loading
                if (can_prefetch) {
                    ImageCache.getInstance().cache(ad_data);
                }

                if (is_in_range) {
                    can_ad_visible = true;
                    if (this.current_ad_index != index && ad_data.image_loaded) {
                        this.current_ad_index = index;
                        Bitmap bitmap = ImageCache.getInstance().retrieveBitmapFromCache(ad_data.imageLink);
                        // Show ads if we find cached image data (or) show ad only when image is loaded
                        if (bitmap == null) break;
                        ad_container.setVisibility(View.VISIBLE);
                        adImage.setImageBitmap(bitmap);
                        adText.setText(ad_data.ad_text);
                        int finalIndex = index;
                        ad_container.setOnClickListener((View ad) -> {
                            // ⬆️ Increasing no.of ad clicks
                            try {
                                ad_data.no_of_clicks++;
                                // 📈 Triggering analytics click event
                                String domain_name=  utils.getDomainName(redirect_link);
                                if(domain_name != null &&  utils.isApplicationInstalled(host_activity,domain_name)){
                                    host_activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(redirect_link)));
                                }else{
                                    openWebView(redirect_link);
                                }
                                analyticsHelper.logClickEvent(utils.createClickProperties(ad_data_json.getJSONObject(finalIndex), ad_data));
                            } catch (JSONException e) {
                                utils.printError(e);
                                e.printStackTrace();
                            }
                        });
                        //⬆️ Increasing no.of ad views
                        ad_data.no_of_views++;
                        // Triggering analytics click event
                        analyticsHelper.logImpressionEvent(utils.createImpressionProperties(ad_data_json.getJSONObject(index), ad_data));
                    }
                }
            }
            if (!can_ad_visible) {
                this.current_ad_index = -1;
                ad_container.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            utils.printError(e);
            e.printStackTrace();
        }

    }

    public void remove() {
        try {
            //❌ Removing ad component from layout
            if (host_activity != null && (host_activity.findViewById(R.id.maxtap_container_id)) != null) {
                ((ViewGroup) video_player).removeView(((Activity) host_context).findViewById(R.id.maxtap_container_id));
            }
            this.ad_container = null;
            this.isInitializing = false;
            this.isInitialized = false;
            this.ads_data = new ArrayList<>();
            this.ad_data_json = new JSONArray();
            this.video_player = null;
            this.host_context = null;
            this.content_id = null;
            this.current_ad_index = -1;
        } catch (Exception e) {
            utils.printError(e);
            e.printStackTrace();
        }
    }
}
