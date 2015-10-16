package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class NoSizeCircleColorView extends View {

	private int color;
	
	private Paint paint;
	
	public NoSizeCircleColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Style.FILL);
		
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
