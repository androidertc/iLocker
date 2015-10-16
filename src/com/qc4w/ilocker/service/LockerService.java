package com.qc4w.ilocker.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.receiver.SystemStateHelper;
import com.qc4w.ilocker.receiver.SystemStateHelper.OnScreenListener;
import com.qc4w.ilocker.service.PhoneListener.OnPhoneStateChangeListener;
import com.qc4w.ilocker.ui.DismissActivity;
import com.qc4w.ilocker.ui.lock.LockerManager;
import com.qc4w.ilocker.util.NotificationUtils;

public class LockerService extends Service implements OnScreenListener, OnPhoneStateChangeListener {

	private SystemStateHelper mScreenStateHelper;
	
	private LockerManager mLockerManager;
	
	private ConfigManager mConfig;
	
	private boolean isCalling;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mLockerManager = LockerManager.getInstance(this);
		mScreenStateHelper = SystemStateHelper.getInstance(this);
		mScreenStateHelper.addListener(this);
		mScreenStateHelper.startService();
		
		if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			telephonyManager.listen(new PhoneListener(this, this), PhoneStateListener.LISTEN_CALL_STATE);
		}
		
		mConfig = ConfigManager.getInstance(this);
		NotificationUtils.getInstance(this).sendMainNotification();
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		if(!pm.isScreenOn()) {
			onScreenOff();
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onScreenOn() {
		// Do nothing
	}

	@Override
	public void onScreenOff() {
		if(mConfig.isServiceEnable() && !isCalling) {
			// ¿ªÆô×ÔÓÐËøÆÁ
			mLockerManager.lock();
			
			Intent intent = new Intent(this, DismissActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	@Override
	public void onIdle() {
		isCalling = false;
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		if(!pm.isScreenOn()) {
			onScreenOff();
		}
	}

	@Override
	public void onRinging() {
		isCalling = true;
		mLockerManager.unlock();
	}

	@Override
	public void onOffhook() {
		
	}

	@Override
	public void onOutGoingCall() {
		isCalling = true;
	}

}
