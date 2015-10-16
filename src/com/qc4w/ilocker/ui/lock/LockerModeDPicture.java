package com.qc4w.ilocker.ui.lock;

import android.app.Notification;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.ui.view.EnterDigitalIndicator;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.MD5Utils;

public class LockerModeDPicture extends AbsLockerMode implements OnClickListener {

	private View mRootView;

	private String password;
	
	private EnterDigitalIndicator indicator;
	
	private ConfigManager config;
	
	private boolean isLaunchCamera;
	private Notification notification;
	
	public LockerModeDPicture(Context context) {
		super(context);
		config = ConfigManager.getInstance(context);
	}

	@Override
	protected void initViews() {
		mRootView = getInflater().inflate(R.layout.layout_locker_mode_dpicture, null, false);
		
		indicator = (EnterDigitalIndicator) mRootView.findViewById(R.id.edi_indicator);
		mRootView.findViewById(R.id.dppv_password_view).setOnClickListener(this);
	}

	@Override
	public View getView() {
		return mRootView;
	}

	@Override
	public void onClick(View v) {
		if(password == null) {
			password = "";
		}
		switch (v.getId()) {
			case R.id.fibv_0:
				password += ((TextView)mRootView.findViewById(R.id.tv_0)).getText().toString();
				break;
			case R.id.fibv_1:
				password += ((TextView)mRootView.findViewById(R.id.tv_1)).getText().toString();
				break;
			case R.id.fibv_2:
				password += ((TextView)mRootView.findViewById(R.id.tv_2)).getText().toString();
				break;
			case R.id.fibv_3:
				password += ((TextView)mRootView.findViewById(R.id.tv_3)).getText().toString();
				break;
			case R.id.fibv_4:
				password += ((TextView)mRootView.findViewById(R.id.tv_4)).getText().toString();
				break;
			case R.id.fibv_5:
				password += ((TextView)mRootView.findViewById(R.id.tv_5)).getText().toString();
				break;
			case R.id.fibv_6:
				password += ((TextView)mRootView.findViewById(R.id.tv_6)).getText().toString();
				break;
			case R.id.fibv_7:
				password += ((TextView)mRootView.findViewById(R.id.tv_7)).getText().toString();
				break;
			case R.id.fibv_8:
				password += ((TextView)mRootView.findViewById(R.id.tv_8)).getText().toString();
				break;
			case R.id.fibv_9:
				password += ((TextView)mRootView.findViewById(R.id.tv_9)).getText().toString();
				break;
			case R.id.fibv_del:
				if(password.length() > 0) {
					password = password.substring(0, password.length() - 1);
				}
				break;
			default:
				break;
		}
		
		indicator.passwordChanged(password);
		mHandler.postDelayed(r, 200);
	}
	
	public void clearPassword() {
		password = "";
		indicator.passwordChanged(password);
	}

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			if(password.length() > 4) {
				password = password.substring(0, 4);
			}
			if(password.length() == 4) {
				if(TextUtils.equals(MD5Utils.MD5(password), config.getDigitalPassword())) {
					if(isLaunchCamera) {
						AppUtils.launchCamera(getContext());
					} else if(notification != null) {
						try {
							notification.contentIntent.send();
						} catch (CanceledException e) {
							e.printStackTrace();
						}
					}
					LockerManager.getInstance(getContext()).unlock();
				} else {
					indicator.passwordWrong();
					password = "";
				}
			}
		}
	};
	
	private Handler mHandler = new Handler();
	
	@Override
	public void launchNotification(Notification notification) {
		this.notification = notification;
	}
	
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
