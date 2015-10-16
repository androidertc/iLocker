package com.qc4w.ilocker.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.domain.Wallpaper;
import com.squareup.picasso.Picasso;

public class WallpaperUtils {

	public static void showWallpaper(Context context, ImageView image, Wallpaper wallpaper) {
		Uri uri = null;
		if(wallpaper.isPreSet()) {
			uri = Uri.parse(wallpaper.getPath());
		} else {
			uri = Uri.fromFile(new File(wallpaper.getPath()));
		}
		if(uri != null) {
			Picasso.with(context).load(uri).into(image);
		}
	}
	
	public static Bitmap getWallpaper(Context context, Wallpaper wallpaper) {
		Uri uri = Uri.parse(wallpaper.getPath());
		if(uri != null) {
			try {
				return Picasso.with(context).load(uri).placeholder(R.drawable.ic_launcher).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Bitmap getSystemWallpaper(Context context) {
		BitmapDrawable bd = (BitmapDrawable) context.getWallpaper();
		return bd.getBitmap();
	}
}
