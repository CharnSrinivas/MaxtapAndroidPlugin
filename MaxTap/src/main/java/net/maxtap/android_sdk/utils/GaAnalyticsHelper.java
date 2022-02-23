package net.maxtap.android_sdk.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.maxtap.android_sdk.Config;
import net.maxtap.android_sdk.Models.ClickEvent;
import net.maxtap.android_sdk.Models.ImpressionEvent;

import java.util.HashMap;
import java.util.Map;

public class GaAnalyticsHelper {
    Context context;
    FirebaseAnalytics analytics;

    public GaAnalyticsHelper(Context context) {
        this.context = context;
        analytics = FirebaseAnalytics.getInstance(context);
    }

    public void logImpressionEvent(@NonNull ImpressionEvent impressionData) {
        try {
            Bundle gaImpressionProps = new Bundle();
            // Google Analytics
            gaImpressionProps.putString("advertiser_name", impressionData.advertiser_name);
            gaImpressionProps.putString("article_type", impressionData.article_type);
            gaImpressionProps.putString("caption", impressionData.caption);
            gaImpressionProps.putString("caption_regional_language", impressionData.caption_regional_language);
            gaImpressionProps.putString("category", impressionData.category);
            gaImpressionProps.putString("client_name", impressionData.client_name);
            gaImpressionProps.putString("content_id", impressionData.content_id);
            gaImpressionProps.putString("content_language", impressionData.content_language);
            gaImpressionProps.putString("content_link", impressionData.content_link);
            gaImpressionProps.putString("content_name", impressionData.content_name);
            gaImpressionProps.putString("content_type", impressionData.content_type);
            gaImpressionProps.putString("document_id", impressionData.document_id);
            gaImpressionProps.putInt("duration", impressionData.duration);
            gaImpressionProps.putInt("end_time", impressionData.end_time);
            gaImpressionProps.putInt("episode_no", impressionData.episode_no);
            gaImpressionProps.putString("gender", impressionData.gender);
            gaImpressionProps.putString("product_details", impressionData.product_details);
            gaImpressionProps.putString("redirect_link", impressionData.redirect_link);
            gaImpressionProps.putString("redirect_link_type", impressionData.redirect_link_type);
            gaImpressionProps.putInt("season", impressionData.season);
            gaImpressionProps.putString("show_name", impressionData.show_name);
            gaImpressionProps.putInt("start_time", impressionData.start_time);
            gaImpressionProps.putString("subcategory", impressionData.subcategory);
            gaImpressionProps.putString("update_time", impressionData.update_time);
            gaImpressionProps.putInt("ad_viewed_count", impressionData.ad_viewed_count);

            // Flurry
            FlurryAgent.logEvent("advertiser_name-"+impressionData.advertiser_name);
            FlurryAgent.logEvent("gender-"+impressionData.gender);
            FlurryAgent.logEvent("content-language"+impressionData.content_language);
            FlurryAgent.logEvent("article-type"+impressionData.article_type);
            FlurryAgent.logEvent("client_name-"+impressionData.client_name);
            FlurryAgent.logEvent("content_name-"+impressionData.content_name);
            FlurryAgent.logEvent("subcategory-"+impressionData.subcategory);
            FlurryAgent.logEvent("category-"+impressionData.category);
            FlurryAgent.logEvent("show_name-"+impressionData.show_name);

            analytics.logEvent(Config.ImpressionEventName, gaImpressionProps);

        } catch (Exception e) {
            utils.printError(e);
            e.printStackTrace();
        }
    }

    public void logClickEvent(ClickEvent clickData) {

        Bundle gaClickProps = new Bundle();
        Map<String, String> flurryClickProps = new HashMap<>();
        try {
            // Google Analytics
            gaClickProps.putString("advertiser_name", clickData.advertiser_name);
            gaClickProps.putString("article_type", clickData.article_type);
            gaClickProps.putString("caption", clickData.caption);
            gaClickProps.putString("caption_regional_language", clickData.caption_regional_language);
            gaClickProps.putString("category", clickData.category);
            gaClickProps.putString("client_name", clickData.client_name);
            gaClickProps.putString("content_id", clickData.content_id);
            gaClickProps.putString("content_language", clickData.content_language);
            gaClickProps.putString("content_link", clickData.content_link);
            gaClickProps.putString("content_name", clickData.content_name);
            gaClickProps.putString("content_type", clickData.content_type);
            gaClickProps.putString("document_id", clickData.document_id);
            gaClickProps.putInt("duration", clickData.duration);
            gaClickProps.putInt("end_time", clickData.end_time);
            gaClickProps.putInt("episode_no", clickData.episode_no);
            gaClickProps.putString("gender", clickData.gender);
            gaClickProps.putString("product_details", clickData.product_details);
            gaClickProps.putString("redirect_link", clickData.redirect_link);
            gaClickProps.putString("redirect_link_type", clickData.redirect_link_type);
            gaClickProps.putInt("season", clickData.season);
            gaClickProps.putString("show_name", clickData.show_name);
            gaClickProps.putInt("start_time", clickData.start_time);
            gaClickProps.putString("subcategory", clickData.subcategory);
            gaClickProps.putString("update_time", clickData.update_time);
            // clickProperties.putString("create_time",clickData.create_time);
            gaClickProps.putInt("no_of_clicks", clickData.times_clicked);

            // Flurry
            FlurryAgent.logEvent("click-advertiser_name-"+clickData.advertiser_name);
            FlurryAgent.logEvent("click-gender-"+clickData.gender);
            FlurryAgent.logEvent("click-content-language-"+clickData.content_language);
            FlurryAgent.logEvent("click-article-type-"+clickData.article_type);
            FlurryAgent.logEvent("click-client_name-"+clickData.client_name);
            FlurryAgent.logEvent("click-content_name-"+clickData.content_name);
            FlurryAgent.logEvent("click-subcategory-"+clickData.subcategory);
            FlurryAgent.logEvent("click-category-"+clickData.category);
            FlurryAgent.logEvent("click-show_name-"+clickData.show_name);

            
            analytics.logEvent(Config.ClickEventName, gaClickProps);

        } catch (Exception e) {
            utils.printError(e);
            e.printStackTrace();
        }
    }
}
