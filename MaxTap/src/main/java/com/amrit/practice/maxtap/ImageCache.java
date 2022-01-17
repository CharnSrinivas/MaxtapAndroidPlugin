package com.amrit.practice.maxtap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import androidx.collection.LruCache;

public class ImageCache {

    @SuppressLint("StaticFieldLeak")
    private static ImageCache instance;
    private final LruCache<Object, Object> lru;

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

    public void saveBitmapToCache(String key, Bitmap bitmap){
        try {
            ImageCache.getInstance().getLru().put(key, bitmap);
        }catch (Exception ignored){}
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Bitmap retrieveBitmapFromCache(String key){
        try {
            return (Bitmap) ImageCache.getInstance().getLru().get(key);
        }catch (Exception e){
            return null;
        }
    }

}
