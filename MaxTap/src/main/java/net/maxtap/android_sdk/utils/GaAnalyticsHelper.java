package net.maxtap.android_sdk.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.maxtap.android_sdk.Config;
import net.maxtap.android_sdk.Models.ClickEvent;
import net.maxtap.android_sdk.Models.ImpressionEvent;

public class GaAnalyticsHelper {
    Context context;
    FirebaseAnalytics analytics;
    public GaAnalyticsHelper(Context context) {
        this.context = context;
        analytics = FirebaseAnalytics.getInstance(context);
    }

    public void logImpressionEvent(@NonNull ImpressionEvent impressionData) {
        try {
            Bundle impressionProperties = new Bundle();
            impressionProperties.putString("advertiser_name", impressionData.advertiser_name);
            impressionProperties.putString("article_type", impressionData.article_type);
            impressionProperties.putString("caption", impressionData.caption);
            impressionProperties.putString("caption_regional_language", impressionData.caption_regional_language);
            impressionProperties.putString("category", impressionData.category);
            impressionProperties.putString("client_name", impressionData.client_name);
            impressionProperties.putString("content_id", impressionData.content_id);
            impressionProperties.putString("content_language", impressionData.content_language);
            impressionProperties.putString("content_link", impressionData.content_link);
            impressionProperties.putString("content_name", impressionData.content_name);
            impressionProperties.putString("content_type", impressionData.content_type);
            impressionProperties.putString("document_id", impressionData.document_id);
            impressionProperties.putInt("duration", impressionData.duration);
            impressionProperties.putInt("end_time", impressionData.end_time);
            impressionProperties.putInt("episode_no", impressionData.episode_no);
            impressionProperties.putString("gender", impressionData.gender);
            impressionProperties.putString("product_details", impressionData.product_details);
            impressionProperties.putString("redirect_link", impressionData.redirect_link);
            impressionProperties.putString("redirect_link_type", impressionData.redirect_link_type);
            impressionProperties.putInt("season", impressionData.season);
            impressionProperties.putString("show_name", impressionData.show_name);
            impressionProperties.putInt("start_time", impressionData.start_time);
            impressionProperties.putString("subcategory", impressionData.subcategory);
            impressionProperties.putString("update_time", impressionData.update_time);
            //impressionProperties.putString("create_time",impressionData.create_time);
            impressionProperties.putInt("ad_viewed_count", impressionData.ad_viewed_count);
            analytics.logEvent(Config.ImpressionEventName, impressionProperties);
        }catch (Exception e){
            utils.printError(e);
            e.printStackTrace();
        }
    }

    public void logClickEvent(ClickEvent clickData){

        Bundle clickProperties = new Bundle();
        try {

        clickProperties.putString("advertiser_name",clickData.advertiser_name);
        clickProperties.putString("article_type",clickData.article_type);
        clickProperties.putString("caption",clickData.caption);
        clickProperties.putString("caption_regional_language",clickData.caption_regional_language);
        clickProperties.putString("category",clickData.category);
        clickProperties.putString("client_name",clickData.client_name);
        clickProperties.putString("content_id",clickData.content_id);
        clickProperties.putString("content_language",clickData.content_language);
        clickProperties.putString("content_link",clickData.content_link);
        clickProperties.putString("content_name",clickData.content_name);
        clickProperties.putString("content_type",clickData.content_type);
        clickProperties.putString("document_id",clickData.document_id);
        clickProperties.putInt("duration",clickData.duration);
        clickProperties.putInt("end_time",clickData.end_time);
        clickProperties.putInt("episode_no",clickData.episode_no);
        clickProperties.putString("gender",clickData.gender);
        clickProperties.putString("product_details",clickData.product_details);
        clickProperties.putString("redirect_link",clickData.redirect_link);
        clickProperties.putString("redirect_link_type",clickData.redirect_link_type);
        clickProperties.putInt("season",clickData.season);
        clickProperties.putString("show_name",clickData.show_name);
        clickProperties.putInt("start_time",clickData.start_time);
        clickProperties.putString("subcategory",clickData.subcategory);
        clickProperties.putString("update_time",clickData.update_time);
        //clickProperties.putString("create_time",clickData.create_time);
        clickProperties.putInt("no_of_clicks",clickData.times_clicked);
        analytics.logEvent(Config.ClickEventName,clickProperties);
        }catch (Exception e){
            utils.printError(e);
            e.printStackTrace();
        }
    }
}
