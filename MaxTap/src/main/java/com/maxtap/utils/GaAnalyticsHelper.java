package com.maxtap.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.maxtap.Models.AdData;
import com.maxtap.Models.Click;
import com.maxtap.Models.Impression;

public class GaAnalyticsHelper {
    Context context;
    FirebaseAnalytics analytics;
    public GaAnalyticsHelper(Context context) {
        this.context = context;
        analytics = FirebaseAnalytics.getInstance(context);
    }

    public void logImpressionEvent(@NonNull Impression impressionData) {
        Bundle impressionProperties = new Bundle();

        impressionProperties.putString("client_id",impressionData.client_id);
        impressionProperties.putString("client_name",impressionData.client_name);
        impressionProperties.putString("content_id",impressionData.content_id);
        impressionProperties.putString("content_name",impressionData.content_name);
        impressionProperties.putString("content_type",impressionData.content_type);
        impressionProperties.putString("show_name",impressionData.show_name);
        impressionProperties.putString("season",impressionData.season);
        impressionProperties.putInt("episode_no",impressionData.episode_no);
        impressionProperties.putInt("content_duration",impressionData.content_duration);
        impressionProperties.putString("content_language",impressionData.content_language);
        impressionProperties.putString("advertiser",impressionData.advertiser);
        impressionProperties.putString("ad_id",impressionData.ad_id);
        impressionProperties.putString("caption_regional_language",impressionData.caption_regional_language);
        impressionProperties.putString("caption_english",impressionData.caption_english);
        impressionProperties.putInt("start_time",impressionData.start_time);
        impressionProperties.putInt("end_time",impressionData.end_time);
        impressionProperties.putInt("ad_duration",impressionData.ad_duration);
        impressionProperties.putString("gender",impressionData.gender);
        impressionProperties.putString("product_details",impressionData.product_details);
        impressionProperties.putString("product_article_type",impressionData.product_article_type);
        impressionProperties.putString("product_category",impressionData.product_category);
        impressionProperties.putString("product_subcategory",impressionData.product_subcategory);
        impressionProperties.putString("product_link",impressionData.product_link);
        impressionProperties.putString("product_image_link",impressionData.product_image_link);
        impressionProperties.putString("redirect_link",impressionData.redirect_link);
        impressionProperties.putInt("ad_viewed_count",impressionData.ad_viewed_count);

        analytics.logEvent("impression",impressionProperties);
    }

    public void logClickEvent(Click clickData){

        Bundle impressionProperties = new Bundle();

        impressionProperties.putString("client_id",clickData.client_id);
        impressionProperties.putString("client_name",clickData.client_name);
        impressionProperties.putString("content_id",clickData.content_id);
        impressionProperties.putString("content_name",clickData.content_name);
        impressionProperties.putString("content_type",clickData.content_type);
        impressionProperties.putString("show_name",clickData.show_name);
        impressionProperties.putString("season",clickData.season);
        impressionProperties.putInt("episode_no",clickData.episode_no);
        impressionProperties.putInt("content_duration",clickData.content_duration);
        impressionProperties.putString("content_language",clickData.content_language);
        impressionProperties.putString("advertiser",clickData.advertiser);
        impressionProperties.putString("ad_id",clickData.ad_id);
        impressionProperties.putString("caption_regional_language",clickData.caption_regional_language);
        impressionProperties.putString("caption_english",clickData.caption_english);
        impressionProperties.putInt("start_time",clickData.start_time);
        impressionProperties.putInt("end_time",clickData.end_time);
        impressionProperties.putInt("ad_duration",clickData.ad_duration);
        impressionProperties.putString("gender",clickData.gender);
        impressionProperties.putString("product_details",clickData.product_details);
        impressionProperties.putString("product_article_type",clickData.product_article_type);
        impressionProperties.putString("product_category",clickData.product_category);
        impressionProperties.putString("product_subcategory",clickData.product_subcategory);
        impressionProperties.putString("product_link",clickData.product_link);
        impressionProperties.putString("product_image_link",clickData.product_image_link);
        impressionProperties.putString("redirect_link",clickData.redirect_link);
        impressionProperties.putInt("times_clicked",clickData.times_clicked);
        analytics.logEvent("click",impressionProperties);
    }
}
