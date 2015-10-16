package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CircleColorView extends View {

	private int color;
	
	private Paint paint;
	
	private int width;
	
	public CircleColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Style.FILL);
		
		width = context.getResources().getDisplayMetrics().widthPixels / 8;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(width, width);
	}
	
	public void setColor(int color) {
		this.color = color;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(color);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
	}

}
