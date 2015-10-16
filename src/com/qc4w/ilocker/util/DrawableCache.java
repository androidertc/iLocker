package com.qc4w.ilocker.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

/**
 * 图标缓存
 * @author WangTC
 */
public class DrawableCache {

    @SuppressWarnings("unused")
    private static final String TAG = DrawableCache.class.getSimpleName();
    
    private LruCache<String, Drawable> mCache;
    
    private static DrawableCache instance;
    
    private DrawableCache(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int appMemory = am.getMemoryClass();
        mCache = new LruCache<String, Drawable>(1024 * 1024 * appMemory / 8) {
            @Override
            protected int sizeOf(String key, Drawable bitmap) {
                if(bitmap instanceof BitmapDrawable) {
                    BitmapDrawable bd = (BitmapDrawable)bitmap;
                    return bd.getBitmap().getRowBytes() * bd.getBitmap().getHeight();
                }
                return 0;
            }
        };
    }
    
    public static DrawableCache getInstance(Context context) {
        if(instance == null) {
            synchronized (DrawableCache.class) {
                if(instance == null) {
                    instance = new DrawableCache(context);
                }
            }
        }
        return instance;
    }

    public void addBitmapToMemoryCache(String key, Drawable bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mCache.put(key, bitmap);
        }
    }

    public Drawable getBitmapFromMemCache(String key) {
        return mCache.get(key);
    }

    public void release() {
        mCache.evictAll();
    }

}
