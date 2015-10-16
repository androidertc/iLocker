package com.qc4w.ilocker.ui.lock;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.qc4w.ilocker.ui.DismissActivity;
import com.qc4w.ilocker.ui.lock.LockerView.OnLockerListener;
import com.qc4w.ilocker.util.UIUtils;

public class LockerManager implements OnLockerListener {
	public static int WINDOW_FLAG = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;

	private static LockerManager instance;
	
	private WindowManager.LayoutParams mParams;
	
	private Context mContext;
	
	private WindowManager mWM;
	
	private boolean mIsAdded;
	
	private View mLockerView;
	
	private LockerView lockerView;
	
	private LockerManager(Context context) {
        mParams = createWindowParams();	
        mContext = context;
        mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}
	
	public static LockerManager getInstance(Context context) {
		if(instance == null) {
			synchronized (LockerManager.class) {
				if(instance == null) {
					instance = new LockerManager(context);
				}
			}
		}
		return instance;
	}

	public void lock() {
		if(lockerView == null) {
			lockerView = new LockerView(mContext);
			lockerView.setListener(this);
			lockerView.initViews();
		}
		
		if(!mIsAdded) {
			mIsAdded = true;
			mLockerView = lockerView.getLockerView();
			mWM.addView(mLockerView, mParams);
			if(UIUtils.hasNavBar(mContext.getResources())) {
				mLockerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			}
			lockerView.onLock();
		} else {
			lockerView.lock();
		}
	}


	@TargetApi(19) 
	private static WindowManager.LayoutParams setTranslucentStatus(WindowManager.LayoutParams winParams) {
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		winParams.flags |= bits;
		return winParams;
	}
	
	
	@Override
	public void onUnlock() {
		if(mIsAdded) {
			Activity activity = DismissActivity.getInstance();
			if(activity != null) {
				activity.finish();
			}
			
			mIsAdded = false;
			mWM.removeView(mLockerView);
		}
	}
	
	public void unlock() {
		if(mIsAdded) {
			Activity activity = DismissActivity.getInstance();
			if(activity != null) {
				activity.finish();
			}
			
			mIsAdded = false;
			mWM.removeView(mLockerView);
		}
	}
	
	public static WindowManager.LayoutParams createWindowParams() {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
        		WindowManager.LayoutParams.MATCH_PARENT,
        		WindowManager.LayoutParams.MATCH_PARENT,
    			WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
    			WINDOW_FLAG, PixelFormat.TRANSPARENT
                );
		params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		// params.windowAnimations = android.R.style.Animation_Toast;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			params = setTranslucentStatus(params);
		}
		params.gravity = Gravity.TOP;
		return params;
	}
	
	public void onConfigChanged() {
		lockerView = null;
		System.gc();
		lockerView = new LockerView(mContext);
		lockerView.setListener(this);
		lockerView.initViews();
	}
}
