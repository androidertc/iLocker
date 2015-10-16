package com.qc4w.ilocker.receiver;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

public class SystemStateHelper {

	private Context mContext;
	
	private boolean mStarted;
	
	private static SystemStateHelper instance;
	
	protected List<OnScreenListener> mListeners = new ArrayList<OnScreenListener>();
	
	private SystemStateHelper(Context context) {
		mContext = context;
	}
	
	public static SystemStateHelper getInstance(Context context) {
		if(instance == null) {
			synchronized (SystemStateHelper.class) {
				if(instance == null) {
					instance = new SystemStateHelper(context);
				}
			}
		}
		return instance;
	}
	
	public void startService() {
		if(!mStarted) {
			mStarted = true;
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			filter.addAction(Intent.ACTION_SCREEN_ON);
			mContext.registerReceiver(mReceiver, filter);
		}
	}

	public void stopService() {
		if(mStarted) {
			mStarted = false;
			mContext.unregisterReceiver(mReceiver);
		}
	}
	
	public void addListener(OnScreenListener listener) {
		if(!mListeners.contains(listener)) {
			mListeners.add(listener);
		}
	}
	
	public void removeListener(OnScreenListener listener) {
		mListeners.remove(listener);
	}
	
	public static interface OnScreenListener {
		
		public void onScreenOn();
		
		public void onScreenOff();
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent != null) {
				String action = intent.getAction();
				if(TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
					for (OnScreenListener listener : mListeners) {
						listener.onScreenOff();
					}
				} else if(TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {
					for (OnScreenListener listener : mListeners) {
						listener.onScreenOn();
					}
				}
			}
		}
	};
	
}
