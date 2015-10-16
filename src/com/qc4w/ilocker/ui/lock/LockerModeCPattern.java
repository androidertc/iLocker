package com.qc4w.ilocker.ui.lock;

import java.util.List;

import android.app.Notification;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.ui.view.Cell;
import com.qc4w.ilocker.ui.view.LockCPicturePatternView;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.LockPatternUtils;
import com.qc4w.ilocker.util.MD5Utils;

public class LockerModeCPattern extends AbsLockerMode implements LockCPicturePatternView.OnPatternListener {

	private View mRootView;

	private ConfigManager config;
	
	private LockCPicturePatternView mPatternView;
	private TextView mTVDrawPattern;
	
	private boolean isLaunchCamera;
	private Notification notification;
	
	public LockerModeCPattern(Context context) {
		super(context);
		config = ConfigManager.getInstance(context);
	}

	@Override
	protected void initViews() {
		mRootView = getInflater().inflate(R.layout.layout_locker_mode_cpattern, null, false);
		mPatternView = (LockCPicturePatternView) mRootView.findViewById(R.id.lppv_lockview);
		mPatternView.setOnPatternListener(this);
		mTVDrawPattern = (TextView) mRootView.findViewById(R.id.tv_draw_pattern);
	}

	@Override
	public View getView() {
		return mRootView;
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mPatternView.clearPattern();
			mTVDrawPattern.setText(R.string.draw_pattern_password);
		}
	};

	@Override
	public void clearPassword() {
		// DO NOTHING
	}
	
	public void launchNotification(Notification notification) {
		this.notification = notification;
	}

	@Override
	public void onPatternStart() {
		mPatternView.removeCallbacks(mClearPatternRunnable);
	}

	@Override
	public void onPatternCleared() {
		mPatternView.removeCallbacks(mClearPatternRunnable);
	}

	@Override
	public void onPatternDetected(List<Cell> pattern) {
		if (pattern == null) {
			return;
		}
		if (TextUtils.equals(MD5Utils.MD5(LockPatternUtils.patternToString(pattern)), config.getPatternPassword())) {
			if(isLaunchCamera) {
				AppUtils.launchCamera(getContext());
			} else if(notification != null) {
				try {
					notification.contentIntent.send();
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}
			mPatternView.setDisplayMode(LockCPicturePatternView.DisplayMode.Right);
			LockerManager.getInstance(getContext()).onUnlock();
			mPatternView.post(mClearPatternRunnable);
		} else {
			mPatternView.setDisplayMode(LockCPicturePatternView.DisplayMode.Wrong);
			mPatternView.postDelayed(mClearPatternRunnable, 1000);
			mTVDrawPattern.setText(R.string.password_wrong);
			YoYo.with(Techniques.Shake).duration(1000).playOn(mTVDrawPattern);
		}
	
	}

	@Override
	public void onPatternCellAdded(List<Cell> pattern) {}

	@Override
	public void launchCamera() {
		isLaunchCamera = true;
	}

	@Override
	public void reset() {
		isLaunchCamera = false;
		notification = null;
	}
}