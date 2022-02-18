package net.maxtap.android_sdk.utils;

import android.util.Log;

import net.maxtap.android_sdk.Config;
import net.maxtap.android_sdk.Models.AdData;
import net.maxtap.android_sdk.Models.ClickEvent;
import net.maxtap.android_sdk.Models.ImpressionEvent;

import org.json.JSONException;
import org.json.JSONObject;

public class utils {
    public static void printError(Exception e) {
        Log.i(Config.MaxtapError, "\n message : " + e.getMessage() + "\n cause : " + e.getCause());
    }

    public static void log(String msg) {
        Log.i(Config.MaxtapLog, msg);
    }

    public static ImpressionEvent createGAImpressionProperties(JSONObject json_ad_data, AdData ad_data) throws JSONException {
        ImpressionEvent impressionData = new ImpressionEvent();
        impressionData.client_name = json_ad_data.has(Config.AdParms.CLIENT_NAME) ? json_ad_data.getString(Config.AdParms.CLIENT_NAME) : "null";
        impressionData.client_name = json_ad_data.has(Config.AdParms.CLIENT_NAME) ? json_ad_data.getString(Config.AdParms.CLIENT_NAME) : "null";
        impressionData.content_id = json_ad_data.has(Config.AdParms.CONTENT_ID) ? json_ad_data.getString(Config.AdParms.CONTENT_ID) : "null";
        impressionData.content_name = json_ad_data.has(Config.AdParms.CONTENT_NAME) ? json_ad_data.getString(Config.AdParms.CONTENT_NAME) : "null";
        impressionData.content_type = json_ad_data.has(Config.AdParms.CONTENT_TYPE) ? json_ad_data.getString(Config.AdParms.CONTENT_TYPE) : "null";
        impressionData.show_name = json_ad_data.has(Config.AdParms.SHOW_NAME) ? json_ad_data.getString(Config.AdParms.SHOW_NAME) : "null";
        impressionData.season = json_ad_data.has(Config.AdParms.SEASON) ? json_ad_data.getString(Config.AdParms.SEASON) : "null";
        impressionData.episode_no = json_ad_data.has(Config.AdParms.EPISODE_NO) ? json_ad_data.getInt(Config.AdParms.EPISODE_NO) : 0;
        impressionData.duration = json_ad_data.has(Config.AdParms.DURATION) ? json_ad_data.getInt(Config.AdParms.DURATION) : 0;
        impressionData.content_language = json_ad_data.has(Config.AdParms.CONTENT_LANGUAGE) ? json_ad_data.getString(Config.AdParms.CONTENT_LANGUAGE) : "null";
        impressionData.advertiser_name = json_ad_data.has(Config.AdParms.ADVERTISER_NAME) ? json_ad_data.getString(Config.AdParms.ADVERTISER_NAME) : "null";
        impressionData.ad_id = json_ad_data.has(Config.AdParms.DOCUMENT_ID) ? json_ad_data.getString(Config.AdParms.DOCUMENT_ID) : "null";
        impressionData.caption = json_ad_data.has(Config.AdParms.CAPTION) ? json_ad_data.getString(Config.AdParms.CAPTION) : "null";
        impressionData.start_time = json_ad_data.has(Config.AdParms.START_TIME) ? json_ad_data.getInt(Config.AdParms.START_TIME) : 0;
        impressionData.end_time = json_ad_data.has(Config.AdParms.END_TIME) ? json_ad_data.getInt(Config.AdParms.END_TIME) : 0;
        impressionData.gender = json_ad_data.has(Config.AdParms.GENDER) ? json_ad_data.getString(Config.AdParms.GENDER) : "null";
        impressionData.content_link = json_ad_data.has(Config.AdParms.CONTENT_LINK) ? json_ad_data.getString(Config.AdParms.CONTENT_LINK): "null";
        impressionData.product_details = json_ad_data.has(Config.AdParms.PRODUCT_DETAILS) ? json_ad_data.getString(Config.AdParms.PRODUCT_DETAILS) : "null";
        impressionData.article_type = json_ad_data.has(Config.AdParms.ARTICLE_TYPE) ? json_ad_data.getString(Config.AdParms.ARTICLE_TYPE) : "null";
        impressionData.category = json_ad_data.has(Config.AdParms.CATEGORY) ? json_ad_data.getString(Config.AdParms.CATEGORY) : "null";
        impressionData.subcategory = json_ad_data.has(Config.AdParms.SUBCATEGORY) ? json_ad_data.getString(Config.AdParms.SUBCATEGORY) : "null";
        impressionData.update_time = json_ad_data.has(Config.AdParms.UPDATE_TIME) ? json_ad_data.getString(Config.AdParms.UPDATE_TIME): "null";
        impressionData.redirect_link_type = json_ad_data.has(Config.AdParms.REDIRECT_LINK_TYPE) ? json_ad_data.getString(Config.AdParms.REDIRECT_LINK_TYPE) : "null";
        impressionData.create_time = json_ad_data.has(Config.AdParms.CREATE_TIME) ? json_ad_data.getString(Config.AdParms.CREATE_TIME):"null";

        impressionData.ad_viewed_count = ad_data.no_of_views;

        return impressionData;
    }

    public static ClickEvent createGAClickProperties(JSONObject json_ad_data, AdData ad_data) throws JSONException {
        ClickEvent clickData = new ClickEvent();
        clickData.client_name = json_ad_data.has(Config.AdParms.CLIENT_NAME) ? json_ad_data.getString(Config.AdParms.CLIENT_NAME) : "null";
        clickData.client_name = json_ad_data.has(Config.AdParms.CLIENT_NAME) ? json_ad_data.getString(Config.AdParms.CLIENT_NAME) : "null";
        clickData.content_id = json_ad_data.has(Config.AdParms.CONTENT_ID) ? json_ad_data.getString(Config.AdParms.CONTENT_ID) : "null";
        clickData.content_name = json_ad_data.has(Config.AdParms.CONTENT_NAME) ? json_ad_data.getString(Config.AdParms.CONTENT_NAME) : "null";
        clickData.content_type = json_ad_data.has(Config.AdParms.CONTENT_TYPE) ? json_ad_data.getString(Config.AdParms.CONTENT_TYPE) : "null";
        clickData.show_name = json_ad_data.has(Config.AdParms.SHOW_NAME) ? json_ad_data.getString(Config.AdParms.SHOW_NAME) : "null";
        clickData.season = json_ad_data.has(Config.AdParms.SEASON) ? json_ad_data.getString(Config.AdParms.SEASON) : "null";
        clickData.episode_no = json_ad_data.has(Config.AdParms.EPISODE_NO) ? json_ad_data.getInt(Config.AdParms.EPISODE_NO) : 0;
        clickData.duration = json_ad_data.has(Config.AdParms.DURATION) ? json_ad_data.getInt(Config.AdParms.DURATION) : 0;
        clickData.content_language = json_ad_data.has(Config.AdParms.CONTENT_LANGUAGE) ? json_ad_data.getString(Config.AdParms.CONTENT_LANGUAGE) : "null";
        clickData.advertiser_name = json_ad_data.has(Config.AdParms.ADVERTISER_NAME) ? json_ad_data.getString(Config.AdParms.ADVERTISER_NAME) : "null";
        clickData.ad_id = json_ad_data.has(Config.AdParms.DOCUMENT_ID) ? json_ad_data.getString(Config.AdParms.DOCUMENT_ID) : "null";
        clickData.caption = json_ad_data.has(Config.AdParms.CAPTION) ? json_ad_data.getString(Config.AdParms.CAPTION) : "null";
        clickData.start_time = json_ad_data.has(Config.AdParms.START_TIME) ? json_ad_data.getInt(Config.AdParms.START_TIME) : 0;
        clickData.end_time = json_ad_data.has(Config.AdParms.END_TIME) ? json_ad_data.getInt(Config.AdParms.END_TIME) : 0;
        clickData.gender = json_ad_data.has(Config.AdParms.GENDER) ? json_ad_data.getString(Config.AdParms.GENDER) : "null";
        clickData.content_link = json_ad_data.has(Config.AdParms.CONTENT_LINK) ? json_ad_data.getString(Config.AdParms.CONTENT_LINK): "null";
        clickData.product_details = json_ad_data.has(Config.AdParms.PRODUCT_DETAILS) ? json_ad_data.getString(Config.AdParms.PRODUCT_DETAILS) : "null";
        clickData.article_type = json_ad_data.has(Config.AdParms.ARTICLE_TYPE) ? json_ad_data.getString(Config.AdParms.ARTICLE_TYPE) : "null";
        clickData.category = json_ad_data.has(Config.AdParms.CATEGORY) ? json_ad_data.getString(Config.AdParms.CATEGORY) : "null";
        clickData.subcategory = json_ad_data.has(Config.AdParms.SUBCATEGORY) ? json_ad_data.getString(Config.AdParms.SUBCATEGORY) : "null";
        clickData.update_time = json_ad_data.has(Config.AdParms.UPDATE_TIME) ? json_ad_data.getString(Config.AdParms.UPDATE_TIME): "null";
        clickData.redirect_link_type = json_ad_data.has(Config.AdParms.REDIRECT_LINK_TYPE) ? json_ad_data.getString(Config.AdParms.REDIRECT_LINK_TYPE) : "null";
        clickData.create_time = json_ad_data.has(Config.AdParms.CREATE_TIME) ? json_ad_data.getString(Config.AdParms.CREATE_TIME):"null";

        clickData.times_clicked = ad_data.no_of_clicks;

        return clickData;
    }
}
