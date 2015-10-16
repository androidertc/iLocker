package com.qc4w.ilocker.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;

@SuppressLint("ClickableViewAccessibility")
public class FadeButtonView extends RelativeLayout {
	
	private FadeButton mButton;
	
	private OnClickListener listener;
	private int radius;
	private int color;
	private float alpha;
	private ConfigManager config;
	
	private FadeButtonBoder fbb;

	public FadeButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);

		config = ConfigManager.getInstance(context);
		int width = config.getScreenWidth();
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FadeButtonView, 0, 0);
		radius = array.getDimensionPixelSize(R.styleable.FadeButtonView_fbv_radius, 100);
		color = array.getColor(R.styleable.FadeButtonView_fbv_color, Color.WHITE);
		alpha = array.getFloat(R.styleable.FadeButtonView_fbv_alpha, 0.6f);
		array.recycle();
		
		radius = Math.min(radius, width / 5);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		fbb = new FadeButtonBoder(context, radius, color, alpha);
		addView(fbb, params);
		mButton = new FadeButton(context, radius, color, alpha);
		addView(mButton, params);
	}
	
	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(radius * 2, radius * 2);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mButton.startAnim();
				if(listener != null) {
					listener.onClick(this);
				}
				break;
			case MotionEvent.ACTION_UP:
				return false;
			case MotionEvent.ACTION_CANCEL:
				break;
			default:
				break;
		}
		return true;
	}
	
	public void onConfigChanged(Bitmap boder, Bitmap button) {
		fbb.onContourChanged(boder);
		mButton.onContourChanged(button);
	}

}
