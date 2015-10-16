package com.qc4w.ilocker.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class DragCameraView extends RelativeLayout {

	private Context mContext;

	private Scroller mScroller;

	private int mScreenHeight = 0;
	
	private int mLastDownY = 0;

	private int mCurryY;

	private int mDelY;

	private boolean mCloseFlag = false;

	private OnDragCompleteListener listener;
	public DragCameraView(Context context) {
		super(context);
		mContext = context;
		setupView();
	}

	public DragCameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setupView();
	}
	
	private void setupView() {
		// 这个Interpolator你可以设置别的 我这里选择的是有弹跳效果的Interpolator
		Interpolator polator = new BounceInterpolator();
		mScroller = new Scroller(mContext, polator);
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (mContext.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenHeight = dm.heightPixels;

		// 这里你一定要设置成透明背景,不然会影响你看到底层布局
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
	}

	public void setListener(OnDragCompleteListener listener) {
		this.listener = listener;
	}
	
	// 推动门的动画
	public void startBounceAnim(int startY, int dy, int duration) {
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(ev.getX() >= getWidth() - 100 && ev.getY() >= getHeight() - 100 + mDelY) {
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if(event.getX() >= getWidth() - 100 && event.getY() >= getHeight() - 100 + mDelY) {
				ViewParent parent = getParent().getParent();
				if(parent != null && parent instanceof ScrollerViewPager) {
//					((ScrollerViewPager) parent).setTouchIntercept(false);
				}
			}
			mLastDownY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			mCurryY = (int) event.getY();
			mDelY = mCurryY - mLastDownY;
			// 只准上滑有效
			if(event.getX() >= getWidth() - 100 && event.getY() >= getHeight() - 100 + mDelY) {
				if (mDelY < 0) {
					scrollTo(0, -mDelY);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(event.getX() >= getWidth() - 100 && event.getY() >= getHeight() - 100 + mDelY) {
				ViewParent parent = getParent().getParent();
				if(parent != null && parent instanceof ScrollerViewPager) {
//					((ScrollerViewPager) parent).setTouchIntercept(true);
				}
			}
			mCurryY = (int) event.getY();
			mDelY = mCurryY - mLastDownY;
			if (mDelY < 0) {
				if (Math.abs(mDelY) > mScreenHeight / 2) {
					// 向上滑动超过半个屏幕高的时候 开启向上消失动画
					startBounceAnim(this.getScrollY(), mScreenHeight, 450);
					mCloseFlag = true;
				} else {
					// 向上滑动未超过半个屏幕高的时候 开启向下弹动动画
					startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 不要忘记更新界面
			postInvalidate();
		} else {
			if (mCloseFlag) {
				if(listener != null) {
					listener.onDragComplete();
				}
			}
		}
	}

	public interface OnDragCompleteListener {
		public void onDragComplete();
	}
}
