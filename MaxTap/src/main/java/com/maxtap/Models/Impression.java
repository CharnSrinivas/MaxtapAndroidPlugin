package com.maxtap.Models;

public final class Impression  {

    public String client_id = "null";
    public String client_name = "null";
    public String content_id = "null";
    public String content_name = "null";
    public String content_type = "null";
    public String show_name = "null";
    public String season = "null";
    public int episode_no = 0;
    public int content_duration = 0;
    public String content_language = "null";

    //advertiser
    public String advertiser = "null";

    //ad
    public String ad_id = "null";
    public String caption_regional_language = "null";
    public String caption_english = "null";
    public int start_time = 0;
    public int end_time = 0;
    public int ad_duration = 0;
        //product
    public String gender = "null";
    public String product_details = "null";
    public String product_article_type = "null";
    public String product_category = "null";
    public String product_subcategory = "null";
    public String product_link = "null";
    public String product_image_link = "null";
    public String redirect_link = "null";
    //user
    public int ad_viewed_count = 0;

    // device
//    public String browser_name = "null;
//    public String os_family = "null;
//    public String device_manufacturer = "null;
//    public String os_architecture = "null;
//    public String os_version = "null;
//    public String screen_resolution = "null;
//    public String screen_orientation = "null;
//    public String full_screen = "null;


    public Impression(){
    }
}