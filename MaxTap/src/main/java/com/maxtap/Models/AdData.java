package com.maxtap.Models;

public final class AdData {
    public int startTime;
    public int end_time;
    public String imageLink;
    public String caption_regional_language;
    public String redirect_link;
    public int no_of_clicks;
    public int no_of_views;

    public AdData() {
        this.no_of_clicks = 0;
        this.no_of_views = 0;
    }
}
