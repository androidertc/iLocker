package com.qc4w.ilocker.util;

import java.io.File;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class PackageImageLoader {

    private static final String TAG = PackageImageLoader.class.getSimpleName();

    private static DrawableCache mCache;

    private final Context mContext;

    private static PackageImageLoader instance;

    private final PackageManager mPM;

    private final BitmapDrawable mDefault;

    public static PackageImageLoader getInstance(Context context, int defaultRes) {
        if(instance == null) {
            synchronized (PackageImageLoader.class) {
                if(instance == null) {
                    instance = new PackageImageLoader(context, defaultRes);
                }
            }
        }
        return instance;
    }

    private PackageImageLoader(Context context, int defaultRes) {
        mContext = context;
        mPM = context.getPackageManager();
        mDefault = new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), defaultRes));
    }

    public void display(ImageView imageView, String packageName) {
        if(mCache == null) {
            mCache = DrawableCache.getInstance(mContext);
        }
        Drawable drawable = mCache.getBitmapFromMemCache(packageName);//key=pkg+icon
        if(drawable == null) {
            new LoadIconTask(imageView, packageName).execute();
        } else {
        	imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        	imageView.setImageDrawable(drawable);
        }
    }

    class LoadIconTask extends AsyncTask<Void, Void, Drawable> {

        private final ImageView imageView;
        private final String packageName;

        public LoadIconTask(ImageView imageView, String packageName) {
            this.imageView   = imageView;
            this.packageName = packageName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageView.setImageDrawable(mDefault);
        }

        @Override
        protected Drawable doInBackground(Void... params) {
            ApplicationInfo info;
            try {
                info = mPM.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                File apk = new File(info.sourceDir);
                if(apk.exists()) {
                	Drawable drawable = info.loadIcon(mPM);
                    return drawable;
                }
            } catch (NameNotFoundException e) {
            }
            return mDefault;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            imageView.setImageDrawable(drawable);
            mCache.addBitmapToMemoryCache(packageName, drawable);
        }
    }


}
