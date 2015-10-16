package com.qc4w.ilocker.ui.lock;

import com.qc4w.ilocker.R;

import android.app.Notification;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.view.View;

public class LockerModeNone extends AbsLockerMode {

	private View mRootView;
	
	public LockerModeNone(Context context) {
		super(context);
	}

	@Override
	protected void initViews() {
		mRootView = getInflater().inflate(R.layout.layout_locker_mode_none, null, false);
	}

	@Override
	public View getView() {
		return mRootView;
	}

	public void clearPassword() {
		// DO NOTHING
	}

	@Override
	public void launchCamera() {
		
	}

	@Override
	public void reset() {
		
	}
	
	@Override
	public void launchNotification(Notification notification) {
		try {
			notification.contentIntent.send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}
}
