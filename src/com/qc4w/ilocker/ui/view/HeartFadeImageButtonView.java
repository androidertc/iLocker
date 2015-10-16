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
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.ImageUtils;

@SuppressLint("ClickableViewAccessibility")
public class HeartFadeImageButtonView extends RelativeLayout {
	
	public static final int TYPE_DPICTURE = 0x01;
	public static final int TYPE_PPICTURE = 0x02;
	public static final int TYPE_LPICTURE = 0x03;
	
	private FadeImageButton mButton;
	
	private OnClickListener listener;
	private int radius;
	private int color;
	private float alpha;
	private int num;
	private ConfigManager config;
	
	private FadeImageButtonBoder fbb;

	public HeartFadeImageButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);

		config = ConfigManager.getInstance(context);
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FadeImageButtonView, 0, 0);
		radius = array.getDimensionPixelSize(R.styleable.FadeImageButtonView_fibv_radius, 100);
		color = array.getColor(R.styleable.FadeImageButtonView_fibv_color, Color.WHITE);
		alpha = array.getFloat(R.styleable.FadeImageButtonView_fibv_alpha, 1f);
		num = array.getInt(R.styleable.FadeImageButtonView_fibv_number, 0);
		array.recycle();

		radius = (int) Math.max(radius, config.getScreenWidth() / 6f);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		mButton = new FadeImageButton(context, radius / 2, color, alpha);
		addView(mButton, params);
		fbb = new FadeImageButtonBoder(context, radius / 2, color, alpha);
		addView(fbb, params);
	}
	
	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(radius, radius);
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
	
	public void onConfigChanged(int type, Bitmap boder, Bitmap contour) {
		fbb.onContourChanged(boder);
		Bitmap button = null;
		if(type == TYPE_DPICTURE) {
			button = ImageUtils.getDPictureImage(getContext(), num);
		} else if(type == TYPE_PPICTURE) {
			button = ImageUtils.getPPictureImage(getContext(), num);
		} else {
//			if(type == TYPE_LPICTURE)
			button = ImageUtils.getLPictureImage(getContext(), num);
		}
		mButton.onContourChanged(button);
	}

}
