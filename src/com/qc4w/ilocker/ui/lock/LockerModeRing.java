package com.qc4w.ilocker.ui.lock;

import android.app.Notification;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.ui.view.EnterDigitalIndicator;
import com.qc4w.ilocker.ui.view.HeartPasswordView;
import com.qc4w.ilocker.ui.view.RingPasswordView;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.MD5Utils;

public class LockerModeRing extends AbsLockerMode implements OnClickListener {

	private View mRootView;

	private String password;
	
	private EnterDigitalIndicator indicator;
	
	private ConfigManager config;
	
	private boolean isLaunchCamera;
	private RingPasswordView rpv;
	private Notification notification;
	
	public LockerModeRing(Context context) {
		super(context);
		config = ConfigManager.getInstance(context);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(config.getScreenWidth(), config.getScreenWidth());
		params.addRule(RelativeLayout.BELOW, R.id.edi_indicator);
		rpv.setLayoutParams(params);
	}

	@Override
	protected void initViews() {
		mRootView = getInflater().inflate(R.layout.layout_locker_mode_ring, null, false);
		
		indicator = (EnterDigitalIndicator) mRootView.findViewById(R.id.edi_indicator);
		rpv = (RingPasswordView) mRootView.findViewById(R.id.rpv_password_view);
		rpv.setOnClickListener(this);
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
				password += 0;
				break;
			case R.id.fibv_1:
				password += 1;
				break;
			case R.id.fibv_2:
				password += 2;
				break;
			case R.id.fibv_3:
				password += 3;
				break;
			case R.id.fibv_4:
				password += 4;
				break;
			case R.id.fibv_5:
				password += 5;
				break;
			case R.id.fibv_6:
				password += 6;
				break;
			case R.id.fibv_7:
				password += 7;
				break;
			case R.id.fibv_8:
				password += 8;
				break;
			case R.id.fibv_9:
				password += 9;
				break;
			case R.id.btn_del:
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
