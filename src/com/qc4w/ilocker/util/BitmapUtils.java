package com.qc4w.ilocker.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

public class BitmapUtils {

	public static Bitmap getBitmap(int background, Bitmap contour) {
		return getBitmap(background, contour, 1f);
	}
	
	public static Bitmap getBitmap(Bitmap background, Bitmap contour) {
		return getBitmap(background, contour, 1f);
	}

	/**
	 * 获取按轮廓裁剪的图片
	 * @param background 要裁剪的图片
	 * @param contour 要裁剪成的轮廓
	 * @return 裁剪完成的图片
	 */
	public static Bitmap getBitmap(Bitmap background, Bitmap contour, float alpha) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setDither(false);
		Bitmap bitmap = Bitmap.createBitmap(contour.getWidth(), contour.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Matrix m = new Matrix();
		m.setScale(contour.getWidth() * 1.0f / background.getWidth(), contour.getHeight() * 1.0f / background.getHeight());
		paint.setAlpha((int) (alpha * 0xff));
		canvas.drawBitmap(background, m, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		paint.setAlpha(0xff);
		canvas.drawBitmap(contour, 0, 0, paint);
		return bitmap;
	}

	/**
	 * 给轮廓设置颜色
	 * @param background 要设置的颜色
	 * @param contour 要设置颜色的轮廓
	 * @return 设置完成颜色的轮廓
	 */
	public static Bitmap getBitmap(int background, Bitmap contour, float alpha) {
		Bitmap bitmap = Bitmap.createBitmap(contour.getWidth(), contour.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(background);
		return getBitmap(bitmap, contour, alpha);
	}

}
