package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.qc4w.ilocker.R;

public class ViewPagerTab extends ViewGroup{
	
	private ViewPager mViewPager;
	private PageListener mPageListener = new PageListener();
	private Context mContext;
	
	private int mWidth;
	private int mHeight;
	private Scroller mScroller;  
	private int mCount;

	public ViewPagerTab(Context context, int count) {
		super(context);	
		mCount = count;
		this.mContext = context;
		mScroller = new Scroller(mContext);

        View childView = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        childView.setBackgroundColor(getResources().getColor(R.color.app_main_color));
        childView.setLayoutParams(params);
        addView(childView);
	}
	
	public ViewPagerTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		mScroller = new Scroller(mContext);

        View childView = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        childView.setBackgroundColor(getResources().getColor(R.color.app_main_color));
        childView.setLayoutParams(params);
        addView(childView);
	}



	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(getChildCount() > 0){
			getChildAt(0).layout(mWidth / (10*mCount), 0, (mWidth*9) / (10*mCount), mHeight);    
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = MeasureSpec.getSize(widthMeasureSpec);  
        mHeight = MeasureSpec.getSize(heightMeasureSpec); 
	}
	
	public void setViewPager(ViewPager viewPager){
		this.mViewPager = viewPager;
		mViewPager.setOnPageChangeListener(mPageListener);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();  
        if(mScroller.computeScrollOffset()){  
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());  
            postInvalidate();  
        }  
	}
	
	
	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			scrollTo(- position * mWidth / mCount - Math.round(positionOffset * mWidth / mCount), 0);
			if(mOnPageListener != null) {
				mOnPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public void onPageSelected(int position) {
			if(mOnPageListener != null) {
				mOnPageListener.onPageSelected(position);
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if(mOnPageListener != null) {
				mOnPageListener.onPageScrollStateChanged(arg0);
			}
		}

	}
	
	private OnPageListener mOnPageListener;
	public void setOnPageListener(OnPageListener listener){
		mOnPageListener = listener;
	}
	public interface OnPageListener{
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
		public void onPageSelected(int position);
		public void onPageScrollStateChanged(int position);
	}
	
}
