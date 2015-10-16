package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class FadeImageButtonBoder extends View {

	private int color;
	private float alpha;
	private int radius;
	
	private Paint paint;

	private Bitmap mBitmap;
	private Matrix matrix;
	public FadeImageButtonBoder(Context context, int radius, int color, float alpha) {
		super(context);
		this.radius = radius;
		this.color = color;
		this.alpha = alpha;
		
		matrix = new Matrix();
		init();
	}
	
	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setAlpha((int) (alpha * 0xff));
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(radius * 2, radius * 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mBitmap, matrix, paint);
	}
	
	public void onContourChanged(Bitmap bitmap) {
		mBitmap = bitmap;
		matrix.setScale((radius * 2) / (mBitmap.getWidth() * 1.0f), (radius * 2) / (mBitmap.getHeight() * 1.0f));
		invalidate();
	}

}
