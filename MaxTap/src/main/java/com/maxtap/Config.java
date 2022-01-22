package com.maxtap;

import android.graphics.Color;

public class Config {

    public static String CloudBucketUrl = "https://storage.googleapis.com/maxtap-adserver-dev.appspot.com/";
    public static int AdImagePrecacheingTime = 15;
    public static String AdTextColor ="#ffffff";
    public static int AdBgColor = Color.argb(80, 0, 0, 0);

    static  class AdParms{
        public  static String  START_TIME ="start_time";
        public  static  String END_TIME = "end_time";
        public  static  String IMAGE_LINK = "image_link";
        public static String CATION_REGIONAL_LANGUAGE="caption_regional_language";
        public static  String REDIRECT_LINK = "redirect_link";
    }
}
