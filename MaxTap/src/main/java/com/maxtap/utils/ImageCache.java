package com.maxtap.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.collection.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ImageCache {

    @SuppressLint("StaticFieldLeak")
    private static ImageCache instance;
    private final LruCache<Object, Object> lru;
    private final ArrayList<String> cachedImages = new ArrayList<String>();
    private ImageCache() {
        lru = new LruCache<>(2048);
    }

    public static ImageCache getInstance() {
        if (instance == null) instance = new ImageCache();
        return instance;
    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }

    public void cache(String url){
        if(ImageCache.getInstance().retrieveBitmapFromCache(url)==null && !cachedImages.contains(url)) {
            try {
                cachedImages.add(url);
                new Thread(() -> {
                    InputStream is = null;
                    try {
                        is = new URL(url).openStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap image = BitmapFactory.decodeStream(is);
                        ImageCache.getInstance().getLru().put(url, image);
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap retrieveBitmapFromCache(String key){
        try {
            return (Bitmap) ImageCache.getInstance().getLru().get(key);
        }catch (Exception e){
            return null;
        }
    }

}
