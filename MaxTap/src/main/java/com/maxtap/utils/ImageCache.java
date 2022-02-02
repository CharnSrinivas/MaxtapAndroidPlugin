package com.maxtap.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.collection.LruCache;

import com.maxtap.Models.AdData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

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

    public void cache(AdData ad){
            try {
                ad.image_loading=true;
                if(retrieveBitmapFromCache(ad.imageLink)!= null){
                    ad.image_loaded=true;return;
                }
                Log.i("cache","Loading "+ad.imageLink);
                new Thread(() -> {
                    InputStream is = null;
                    try {
                        is = new URL(ad.imageLink).openStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap image = BitmapFactory.decodeStream(is);
                    ImageCache.getInstance().getLru().put(ad.imageLink, image);
                    Log.i("cache","Loaded "+ad.imageLink);
                    ad.image_loaded=true;
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
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
