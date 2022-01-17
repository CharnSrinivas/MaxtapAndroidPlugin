package com.amrit.practice.maxtap;

import java.util.ArrayList;
import java.util.Collections;

public class MovieData {

    private final int startTime;
    private final int endTime;
    private final String productLink;
    private final String imageLink;
    private final String caption;

    public MovieData(int startTime, int endTime, String productLink, String imageLink, String caption) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.productLink = productLink;
        this.imageLink = imageLink;
        this.caption = caption;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getProductLink() {
        return productLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getCaption() {
        return caption;
    }

    public static void sort(ArrayList<MovieData> movieData){
        Collections.sort(movieData, (o1, o2) -> {
            return o1.startTime - o2.startTime;
        });
    }

}
