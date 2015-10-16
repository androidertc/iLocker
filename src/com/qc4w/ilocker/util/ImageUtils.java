package com.qc4w.ilocker.util;

import java.io.File;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;

public class ImageUtils {
	
	public static String getMaleImagePath(Context context) {
		File file = new File(context.getFilesDir(), Constants.Path.MALE_IMAGE_NAME);
		return file.getAbsolutePath();
	}
	
	public static String getFemaleImagePath(Context context) {
		File file = new File(context.getFilesDir(), Constants.Path.FEMALE_IMAGE_NAME);
		return file.getAbsolutePath();
	}
	
	public static String getNameImagePath(Context context) {
		File file = new File(context.getFilesDir(), Constants.Path.NAME_IMAGE_NAME);
		return file.getAbsolutePath();
	}
	
	public static String getDPictureImagePath(Context context, int num) {
		File file = new File(context.getFilesDir(), num + Constants.Path.DPICTURE_IMAGE_SUFFIX);
		return file.getAbsolutePath();
	}
	
	public static String getPPictureImagePath(Context context, int num) {
		File file = new File(context.getFilesDir(), num + Constants.Path.PPICTURE_IMAGE_SUFFIX);
		return file.getAbsolutePath();
	}

	public static String getLPictureImagePath(Context context, int num) {
		File file = new File(context.getFilesDir(), num + Constants.Path.LPICTURE_IMAGE_SUFFIX);
		return file.getAbsolutePath();
	}

	public static String getRingImagePath(Context context, int num) {
		File file = new File(context.getFilesDir(), num + Constants.Path.RING_IMAGE_SUFFIX);
		return file.getAbsolutePath();
	}
	
	public static String getWallpaperPath(Context context) {
		File file = new File(context.getFilesDir(), System.currentTimeMillis() + Constants.Path.WALLPAPER_IMAGE_SUFFIX);
		return file.getAbsolutePath();
	}
	
	public static String getCPatternNormalPicture(Context context) {
		File file = new File(context.getFilesDir(), Constants.Path.CPATTERN_NORMAL_IMAGE_NAME);
		return file.getAbsolutePath();
	}
	
	public static String getCPatternTouchedPicture(Context context) {
		File file = new File(context.getFilesDir(), Constants.Path.CPATTERN_TOUCHED_IMAGE_NAME);
		return file.getAbsolutePath();
	}
	
	public static Bitmap getCPatternNormalImage(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), Constants.Path.CPATTERN_NORMAL_IMAGE_NAME);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		int index = config.getCPatternNormalContour();
		if(index != Constants.CommonValue.INVALID_VALUE) {
			Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[index]);
			bitmap = BitmapUtils.getBitmap(bitmap, contour);
		}
		return bitmap;
	}
	
	public static Bitmap getCPatternTouchedImage(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), Constants.Path.CPATTERN_TOUCHED_IMAGE_NAME);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		int index = config.getCPatternTouchedContour();
		if(index != Constants.CommonValue.INVALID_VALUE) {
			Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[index]);
			bitmap = BitmapUtils.getBitmap(bitmap, contour);
		}
		return bitmap;
	}
	
	public static Bitmap getRingImage(Context context, int num) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), num + Constants.Path.RING_IMAGE_SUFFIX);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[config.getRingContour()]);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	}
	
	
	public static Bitmap getLPictureImage(Context context, int num) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), num + Constants.Path.LPICTURE_IMAGE_SUFFIX);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[config.getLPictureContour()]);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	}
	
	public static Bitmap getPPictureImage(Context context, int num) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), num + Constants.Path.PPICTURE_IMAGE_SUFFIX);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[config.getPPictureContour()]);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	}

	public static Bitmap getDPictureImage(Context context, int num) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), num + Constants.Path.DPICTURE_IMAGE_SUFFIX);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[config.getDPictureContour()]);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	}
	
	public static Bitmap getNameImage(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), Constants.Path.NAME_IMAGE_NAME);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), ContourMapper.contourNormals[config.getNameImageContour()]);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	
	}

	public static Bitmap getMaleImage(Context context) {
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), Constants.Path.MALE_IMAGE_NAME);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), R.drawable.lock_normal_circle);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	}
	
	public static Bitmap getFemaleImage(Context context) {
		Bitmap bitmap = null;
		File file = new File(context.getFilesDir(), Constants.Path.FEMALE_IMAGE_NAME);
		if(file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture_add);
		}
		Bitmap contour = BitmapFactory.decodeResource(context.getResources(), R.drawable.lock_normal_circle);
		bitmap = BitmapUtils.getBitmap(bitmap, contour);
		return bitmap;
	}
	
	public static StateListDrawable getDigitalBackspace(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		int color = config.getDigitalColor();
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_backspace_normal);
		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(BitmapUtils.getBitmap(color & 0x88FFFFFF, bitmap)));
		sld.addState(new int[]{}, new BitmapDrawable(BitmapUtils.getBitmap(color, bitmap)));
		return sld;
	}
	
	public static StateListDrawable getDPictureBackspace(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		int color = config.getDPictureColor();
		return getBackspace(context, color);
	}
	
	public static StateListDrawable getHeartBackspace(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		int color = config.getLPictureColor();
		return getBackspace(context, color);
	}
	
	public static StateListDrawable getRingBackspace(Context context) {
		ConfigManager config = ConfigManager.getInstance(context);
		int color = config.getRingColor();
		return getBackspace(context, color);
	}
	
	
	private static StateListDrawable getBackspace(Context context, int color) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_backspace_normal);
		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(BitmapUtils.getBitmap(color & 0x88FFFFFF, bitmap)));
		sld.addState(new int[]{}, new BitmapDrawable(BitmapUtils.getBitmap(color, bitmap)));
		return sld;
	
	}
}
