package net.maxtap.android_sdk;

import android.graphics.Color;

public final class Config {

    public final static String CloudBucketUrl = "https://storage.googleapis.com/publicmaxtap-prod.appspot.com/data/";
    public final static int AdImagePrecacheingTime = 15;
    public final static String AdTextColor = "#ffffff";
    public final static int AdBgColor = Color.argb(80, 0, 0, 0);
    public  final static String DefaultAdCaption = "Get this now";
    public final static String MaxtapLog ="maxtap_log";
    public final static String MaxtapError = "maxtap_err";

    public static class AdParms {
        public final static String UPDATE_TIME = "update_time";
        public final static String REDIRECT_LINK_TYPE ="redirect_link_type";
        public final static String START_TIME = "start_time";
        public final static String END_TIME = "end_time";
        public final static String IMAGE_LINK = "image_link";
        public final static String CAPTION_REGIONAL_LANGUAGE = "caption_regional_language";
        public final static String CAPTION="caption";
        public final static String CLIENT_NAME = "client_name";
        public final static String CONTENT_ID = "content_id";
        public final static String CONTENT_NAME = "content_name";
        public final static String CONTENT_TYPE = "content_type";
        public final static String SHOW_NAME = "show_name";
        public final static String SEASON = "season";
        public final static String EPISODE_NO = "episode_no";
        public final static String CONTENT_LANGUAGE = "content_language";
        public final static String ADVERTISER_NAME = "advertiser_name";
        public final static String DURATION = "duration";
        public final static String GENDER = "gender";
        public final static String PRODUCT_DETAILS = "product_details";
        public final static String ARTICLE_TYPE = "article_type";
        public final static String CATEGORY = "category";
        public final static String SUBCATEGORY = "subcategory";
        public final static String DOCUMENT_ID = "document_id";
        public final static String CONTENT_LINK = "content_link";
        public  final static String REDIRECT_LINK = "redirect_link";
        public final static String CREATE_TIME ="create_time";

        public final  static String[] REQUIRED ={START_TIME,END_TIME,IMAGE_LINK};
    }
}
