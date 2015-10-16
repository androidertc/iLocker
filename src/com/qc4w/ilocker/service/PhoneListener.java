package com.qc4w.ilocker.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class PhoneListener extends PhoneStateListener {
	
	private OnPhoneStateChangeListener listener;
	public PhoneListener(Context context, OnPhoneStateChangeListener listener) {
		this.listener = listener;
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
			context.registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
		}
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				if(listener != null) {
					listener.onIdle();
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				if(listener != null) {
					listener.onRinging();
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if(listener != null) {
					listener.onOffhook();
				}
				break;
			default:
				break;
		}
	}
	
	
	public interface OnPhoneStateChangeListener {
		
		public void onIdle();
		
		public void onRinging();
		
		public void onOffhook();
		
		public void onOutGoingCall();
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent != null) {
				String action = intent.getAction();
				if(TextUtils.equals(action, Intent.ACTION_NEW_OUTGOING_CALL)) {
					if(listener != null) {
						listener.onOutGoingCall();
					}
				}
			}
		}
	};
}
