package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qc4w.ilocker.util.DateFormatUtils;

public class TimingTextView extends TextView {

	private boolean mTickerStopped = false;
	private Runnable mTicker;
	private Handler mHandler;
	private long mTimestamp;
	
	public TimingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setTime(long timestamp, boolean stopped) {
		mTimestamp = timestamp;
		mTickerStopped = stopped;
	}
	
	public void setTime(long timestamp) {
		mTimestamp = timestamp;
	}
	
	@Override
	protected void onAttachedToWindow() {
		mTickerStopped = false;
		super.onAttachedToWindow();
		mHandler = new Handler();

		/**
		 * requests a tick on the next hard-second boundary
		 */
		mTicker = new Runnable() {
			public void run() {
				if (mTickerStopped)
					return;
				
				long diff = System.currentTimeMillis() - mTimestamp;
				if(diff <= 0) {
					diff = 0;
				}
				setText(DateFormatUtils.getTimeString(getContext(), diff));
				invalidate();
				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				mHandler.postAtTime(mTicker, next);
			}
		};
		mTicker.run();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mTickerStopped = true;
	}
}