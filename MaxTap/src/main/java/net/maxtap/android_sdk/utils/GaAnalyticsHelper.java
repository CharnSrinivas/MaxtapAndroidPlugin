package net.maxtap.android_sdk.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.firebase.analytics.FirebaseAnalytics;
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
        Bundle impressionProperties = new Bundle();
        impressionProperties.putString("update_time",impressionData.update_time);
        impressionProperties.putString("client_name",impressionData.client_name);
        impressionProperties.putString("content_id",impressionData.content_id);
        impressionProperties.putString("content_name",impressionData.content_name);
        impressionProperties.putString("content_type",impressionData.content_type);
        impressionProperties.putString("show_name",impressionData.show_name);
        impressionProperties.putString("season",impressionData.season);
        impressionProperties.putInt("episode_no",impressionData.episode_no);
        impressionProperties.putInt("duration",impressionData.duration);
        impressionProperties.putString("content_language",impressionData.content_language);
        impressionProperties.putString("advertiser_name",impressionData.advertiser_name);
        impressionProperties.putString("document_id",impressionData.document_id);
        impressionProperties.putString("redirect_link_type",impressionData.redirect_link_type);
        impressionProperties.putString("caption",impressionData.caption);
        impressionProperties.putString("caption_regional_language",impressionData.caption_regional_language);
        impressionProperties.putInt("start_time",impressionData.start_time);
        impressionProperties.putInt("end_time",impressionData.end_time);
        impressionProperties.putInt("duration",impressionData.duration);
        impressionProperties.putString("gender",impressionData.gender);
        impressionProperties.putString("details",impressionData.details);
        impressionProperties.putString("article_type",impressionData.article_type);
        impressionProperties.putString("category",impressionData.category);
        impressionProperties.putString("subcategory",impressionData.subcategory);

        impressionProperties.putInt("ad_viewed_count",impressionData.ad_viewed_count);
        utils.log("Impression Event ");
        analytics.logEvent("impression",impressionProperties);
    }

    public void logClickEvent(ClickEvent clickData){

        Bundle clickProperties = new Bundle();

        clickProperties.putString("update_time",clickData.update_time);
        clickProperties.putString("client_name",clickData.client_name);
        clickProperties.putString("content_id",clickData.content_id);
        clickProperties.putString("content_name",clickData.content_name);
        clickProperties.putString("content_type",clickData.content_type);
        clickProperties.putString("show_name",clickData.show_name);
        clickProperties.putString("season",clickData.season);
        clickProperties.putInt("episode_no",clickData.episode_no);
        clickProperties.putInt("duration",clickData.duration);
        clickProperties.putString("content_language",clickData.content_language);
        clickProperties.putString("advertiser_name",clickData.advertiser_name);
        clickProperties.putString("document_id",clickData.document_id);
        clickProperties.putString("redirect_link_type",clickData.redirect_link_type);
        clickProperties.putString("caption",clickData.caption);
        clickProperties.putString("caption_regional_language",clickData.caption_regional_language);
        clickProperties.putInt("start_time",clickData.start_time);
        clickProperties.putInt("end_time",clickData.end_time);
        clickProperties.putInt("duration",clickData.duration);
        clickProperties.putString("gender",clickData.gender);
        clickProperties.putString("details",clickData.details);
        clickProperties.putString("article_type",clickData.article_type);
        clickProperties.putString("category",clickData.category);
        clickProperties.putString("subcategory",clickData.subcategory);

        clickProperties.putInt("times_clicked",clickData.times_clicked);
        analytics.logEvent("click",clickProperties);
    }
}
