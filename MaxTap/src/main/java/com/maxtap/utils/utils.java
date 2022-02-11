package com.maxtap.utils;

import com.maxtap.Config;
import com.maxtap.Models.AdData;
import com.maxtap.Models.ClickEvent;
import com.maxtap.Models.ImpressionEvent;

import org.json.JSONException;
import org.json.JSONObject;

public class utils {

    public static ImpressionEvent createGAImpressionProperties(JSONObject json_ad_data, AdData ad_data) throws JSONException {
        ImpressionEvent impressionData = new ImpressionEvent();
        impressionData.client_id = json_ad_data.has(Config.AdParms.CLIENT_ID) ? json_ad_data.getString(Config.AdParms.CLIENT_ID) : "null";
        impressionData.client_name = json_ad_data.has(Config.AdParms.CLIENT_NAME) ? json_ad_data.getString(Config.AdParms.CLIENT_NAME) : "null";
        impressionData.content_id = json_ad_data.has(Config.AdParms.CONTENT_ID) ? json_ad_data.getString(Config.AdParms.CONTENT_ID) : "null";
        impressionData.content_name = json_ad_data.has(Config.AdParms.CONTENT_NAME) ? json_ad_data.getString(Config.AdParms.CONTENT_NAME) : "null";
        impressionData.content_type = json_ad_data.has(Config.AdParms.CONTENT_TYPE) ? json_ad_data.getString(Config.AdParms.CONTENT_TYPE) : "null";
        impressionData.show_name = json_ad_data.has(Config.AdParms.SHOW_NAME) ? json_ad_data.getString(Config.AdParms.SHOW_NAME) : "null";
        impressionData.season = json_ad_data.has(Config.AdParms.SEASON) ? json_ad_data.getString(Config.AdParms.SEASON) : "null";
        impressionData.episode_no = json_ad_data.has(Config.AdParms.EPISODE_NO) ? json_ad_data.getInt(Config.AdParms.EPISODE_NO) : 0;
        impressionData.content_duration = json_ad_data.has(Config.AdParms.CONTENT_DURATION) ? json_ad_data.getInt(Config.AdParms.CONTENT_DURATION) : 0;
//        impressionData.content_language = json_ad_data.has(Config.AdParms.CONTENT_LANGUAGE) ? json_ad_data.getString(Config.AdParms.CONTENT_LANGUAGE) : "null";
        impressionData.advertiser = json_ad_data.has(Config.AdParms.ADVERTISER) ? json_ad_data.getString(Config.AdParms.ADVERTISER) : "null";
        impressionData.ad_id = json_ad_data.has(Config.AdParms.AD_ID) ? json_ad_data.getString(Config.AdParms.AD_ID) : "null";
        impressionData.caption = json_ad_data.has(Config.AdParms.CAPTION) ? json_ad_data.getString(Config.AdParms.CAPTION):"null";
//        impressionData.caption_regional_language =json_ad_data.has(Config.AdParms.CAPTION_REGIONAL_LANGUAGE) ? json_ad_data.getString(Config.AdParms.CAPTION_REGIONAL_LANGUAGE):"null";
//        impressionData.caption_english =json_ad_data.has(Config.AdParms.CAPTION_ENGLISH) ? json_ad_data.getString(Config.AdParms.CAPTION_ENGLISH):"null";
        impressionData.start_time = json_ad_data.has(Config.AdParms.START_TIME) ? json_ad_data.getInt(Config.AdParms.START_TIME) : 0;
        impressionData.end_time = json_ad_data.has(Config.AdParms.END_TIME) ? json_ad_data.getInt(Config.AdParms.END_TIME) : 0;
        impressionData.ad_duration = json_ad_data.has(Config.AdParms.AD_DURATION) ? json_ad_data.getInt(Config.AdParms.AD_DURATION) : 0;
        impressionData.gender = json_ad_data.has(Config.AdParms.GENDER) ? json_ad_data.getString(Config.AdParms.GENDER) : "null";
        impressionData.product_details = json_ad_data.has(Config.AdParms.PRODUCT_DETAILS) ? json_ad_data.getString(Config.AdParms.PRODUCT_DETAILS) : "null";
        impressionData.product_article_type = json_ad_data.has(Config.AdParms.PRODUCT_ARTICLE_TYPE) ? json_ad_data.getString(Config.AdParms.PRODUCT_ARTICLE_TYPE) : "null";
//        impressionData.product_category = json_ad_data.has(Config.AdParms.PRODUCT_CATEGORY) ? json_ad_data.getString(Config.AdParms.PRODUCT_CATEGORY) : "null";
//        impressionData.product_subcategory = json_ad_data.has(Config.AdParms.PRODUCT_SUBCATEGORY) ? json_ad_data.getString(Config.AdParms.PRODUCT_SUBCATEGORY) : "null";
//        impressionData.product_link = json_ad_data.has(Config.AdParms.PRODUCT_LINK) ? json_ad_data.getString(Config.AdParms.PRODUCT_LINK) : "null";
//        impressionData.product_image_link = json_ad_data.has(Config.AdParms.PRODUCT_IMAGE_LINK) ? json_ad_data.getString(Config.AdParms.PRODUCT_IMAGE_LINK) : "null";
        impressionData.redirect_link = json_ad_data.has(Config.AdParms.REDIRECT_LINK) ? json_ad_data.getString(Config.AdParms.REDIRECT_LINK) : "null";


        impressionData.ad_viewed_count = ad_data.no_of_views;

        return impressionData;
    }

    public static ClickEvent createGAClickProperties(JSONObject json_ad_data, AdData ad_data) throws JSONException {
        ClickEvent clickProperties = new ClickEvent();
        clickProperties.client_id = json_ad_data.has(Config.AdParms.CLIENT_ID) ? json_ad_data.getString(Config.AdParms.CLIENT_ID) : "null";
        clickProperties.client_name = json_ad_data.has(Config.AdParms.CLIENT_NAME) ? json_ad_data.getString(Config.AdParms.CLIENT_NAME) : "null";
        clickProperties.content_id = json_ad_data.has(Config.AdParms.CONTENT_ID) ? json_ad_data.getString(Config.AdParms.CONTENT_ID) : "null";
        clickProperties.content_name = json_ad_data.has(Config.AdParms.CONTENT_NAME) ? json_ad_data.getString(Config.AdParms.CONTENT_NAME) : "null";
        clickProperties.content_type = json_ad_data.has(Config.AdParms.CONTENT_TYPE) ? json_ad_data.getString(Config.AdParms.CONTENT_TYPE) : "null";
        clickProperties.show_name = json_ad_data.has(Config.AdParms.SHOW_NAME) ? json_ad_data.getString(Config.AdParms.SHOW_NAME) : "null";
        clickProperties.season = json_ad_data.has(Config.AdParms.SEASON) ? json_ad_data.getString(Config.AdParms.SEASON) : "null";
        clickProperties.episode_no = json_ad_data.has(Config.AdParms.EPISODE_NO) ? json_ad_data.getInt(Config.AdParms.EPISODE_NO) : 0;
        clickProperties.content_duration = json_ad_data.has(Config.AdParms.CONTENT_DURATION) ? json_ad_data.getInt(Config.AdParms.CONTENT_DURATION) : 0;
//        clickProperties.content_language = json_ad_data.has(Config.AdParms.CONTENT_LANGUAGE) ? json_ad_data.getString(Config.AdParms.CONTENT_LANGUAGE) : "null";
        clickProperties.advertiser = json_ad_data.has(Config.AdParms.ADVERTISER) ? json_ad_data.getString(Config.AdParms.ADVERTISER) : "null";
        clickProperties.ad_id = json_ad_data.has(Config.AdParms.AD_ID) ? json_ad_data.getString(Config.AdParms.AD_ID) : "null";
        clickProperties.caption = json_ad_data.has(Config.AdParms.CAPTION) ? json_ad_data.getString(Config.AdParms.CAPTION):"null";
//        impressionData.caption_regional_language =json_ad_data.has(Config.AdParms.CAPTION_REGIONAL_LANGUAGE) ? json_ad_data.getString(Config.AdParms.CAPTION_REGIONAL_LANGUAGE):"null";
//        impressionData.caption_english =json_ad_data.has(Config.AdParms.CAPTION_ENGLISH) ? json_ad_data.getString(Config.AdParms.CAPTION_ENGLISH):"null";
        clickProperties.start_time = json_ad_data.has(Config.AdParms.START_TIME) ? json_ad_data.getInt(Config.AdParms.START_TIME) : 0;
        clickProperties.end_time = json_ad_data.has(Config.AdParms.END_TIME) ? json_ad_data.getInt(Config.AdParms.END_TIME) : 0;
        clickProperties.ad_duration = json_ad_data.has(Config.AdParms.AD_DURATION) ? json_ad_data.getInt(Config.AdParms.AD_DURATION) : 0;
        clickProperties.gender = json_ad_data.has(Config.AdParms.GENDER) ? json_ad_data.getString(Config.AdParms.GENDER) : "null";
        clickProperties.product_details = json_ad_data.has(Config.AdParms.PRODUCT_DETAILS) ? json_ad_data.getString(Config.AdParms.PRODUCT_DETAILS) : "null";
        clickProperties.product_article_type = json_ad_data.has(Config.AdParms.PRODUCT_ARTICLE_TYPE) ? json_ad_data.getString(Config.AdParms.PRODUCT_ARTICLE_TYPE) : "null";
//        clickProperties.product_category = json_ad_data.has(Config.AdParms.PRODUCT_CATEGORY) ? json_ad_data.getString(Config.AdParms.PRODUCT_CATEGORY) : "null";
//        clickProperties.product_subcategory = json_ad_data.has(Config.AdParms.PRODUCT_SUBCATEGORY) ? json_ad_data.getString(Config.AdParms.PRODUCT_SUBCATEGORY) : "null";
//        clickProperties.product_link = json_ad_data.has(Config.AdParms.PRODUCT_LINK) ? json_ad_data.getString(Config.AdParms.PRODUCT_LINK) : "null";
//        clickProperties.product_image_link = json_ad_data.has(Config.AdParms.PRODUCT_IMAGE_LINK) ? json_ad_data.getString(Config.AdParms.PRODUCT_IMAGE_LINK) : "null";
        clickProperties.redirect_link = json_ad_data.has(Config.AdParms.REDIRECT_LINK) ? json_ad_data.getString(Config.AdParms.REDIRECT_LINK) : "null";
        
        clickProperties.times_clicked = ad_data.no_of_clicks;

        return clickProperties;
    }
}
