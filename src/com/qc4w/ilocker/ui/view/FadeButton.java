package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class FadeButton extends View {
	private Paint paint;
	private int color;
	private int radius;
	private float paintAlpha;
	private float alpha;

	private Bitmap mBitmap;
	private Matrix matrix;
	
	public float getPaintAlpha() {
		return paintAlpha;
	}

	public void setPaintAlpha(float alpha) {
		this.paintAlpha = alpha;
		invalidate();
	}

	public FadeButton(Context context, int radius, int color, float alpha) {
		super(context);
		this.radius = radius;
		this.color = color;
		this.paintAlpha = alpha;
		this.alpha = alpha;
		
		matrix = new Matrix();
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(radius * 2, radius * 2);
	}

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setAlpha((int) (paintAlpha * 255));
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		paint.setAlpha((int) (getPaintAlpha() * 255));
		canvas.drawBitmap(mBitmap, matrix, paint);
	}

	public void startAnim() {
        ValueAnimator bounceAnim = ObjectAnimator.ofFloat(this, "paintAlpha", 0f, alpha);
        bounceAnim.setDuration(600);
        bounceAnim.setInterpolator(new AccelerateInterpolator());
        bounceAnim.start();
	}
	
	public void onContourChanged(Bitmap bitmap) {
		mBitmap = bitmap;
		matrix.setScale((radius * 2) / (mBitmap.getWidth() * 1.0f), (radius * 2) / (mBitmap.getHeight() * 1.0f));
		invalidate();
	}
}
