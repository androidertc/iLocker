package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class EnterDigitalIndicatorItemView extends View {

	private int radius;
	
	private Paint paint;
	
	public EnterDigitalIndicatorItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, dm);
		
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1f);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(radius * 2, radius * 2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(isSelected()) {
			paint.setStyle(Paint.Style.FILL);
		} else {
			paint.setStyle(Paint.Style.STROKE);
		}
		canvas.drawCircle(radius, radius, radius - 1, paint);
	}
}
