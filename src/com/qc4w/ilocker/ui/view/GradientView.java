package com.qc4w.ilocker.ui.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.qc4w.ilocker.R;

public class GradientView extends View {
	
	private float 	mIndex = 0;
	private Shader	mShader;
	private int 	mTextSize;	
	private static final int mMaxWidth = 1500; // 26*25 
	private static final int mMinWidth = 0;  // 5*25
	private int mDefaultColor;
	private int mSlideColor;
	private ValueAnimator animator;
	private int mWidth,mHeight;
	private String mStringToShow;
	private Paint mTextPaint;
	private float mTextHeight;

	private AnimatorUpdateListener mAnimatorUpdateListener = new AnimatorUpdateListener() {
		
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			mIndex =Float.parseFloat(animation.getAnimatedValue().toString());
			mShader = new LinearGradient(0, mHeight,
					mIndex, mHeight, new int[] {  mDefaultColor, mDefaultColor, mSlideColor, mDefaultColor, mDefaultColor }, 
					new float[] { 0.1f, 0.3f, 0.5f, 0.8f, 1f },
					Shader.TileMode.CLAMP);

			postInvalidate();
			
		}
	};
	
	public GradientView(Context context) {
		super(context);
	}

	
	public GradientView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientView);
		mStringToShow = a.getString(R.styleable.GradientView_StringToShow);
		mTextSize = (int)a.getDimensionPixelSize(R.styleable.GradientView_TextSize, 40);
		mDefaultColor = a.getColor(R.styleable.GradientView_TextColor, Color.GRAY);
		mSlideColor = a.getColor(R.styleable.GradientView_SlideColor, Color.WHITE);
		a.recycle();
		
		animator = ValueAnimator.ofFloat(mMinWidth, mMaxWidth);
		animator.setDuration(1200);
		animator.addUpdateListener(mAnimatorUpdateListener);
		animator.setRepeatCount(Animation.INFINITE);//repeat animation
		animator.start();
		
		
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(mSlideColor);
		mTextPaint.setTextSize(mTextSize); 
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		
		mTextHeight = mTextPaint.ascent();
		setFocusable(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mTextPaint.setShader(mShader);
		canvas.drawText(mStringToShow, mWidth / 2, mHeight / 2 - mTextHeight
				/ 2, mTextPaint); // slide_unlock
	} 

	

	public void stopAnimatorAndChangeColor() {
		animator.cancel();
		//reset
		mShader = new LinearGradient(0, mHeight, mIndex, mHeight,
				new int[] {mSlideColor, mSlideColor},
				null, Shader.TileMode.CLAMP);
		invalidate();
	}
	
	public void startAnimator() {
		animator.start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getResources().getDisplayMetrics().widthPixels;
		mHeight = mTextSize + 10;
		setMeasuredDimension(mWidth, mHeight);
	}
	
	public void resetControl(){
		animator.start();
		this.setX(0);
		invalidate();
	}
}