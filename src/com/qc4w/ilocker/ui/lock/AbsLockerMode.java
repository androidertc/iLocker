package com.qc4w.ilocker.ui.lock;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class AbsLockerMode {

	private LayoutInflater mInflater;

	private Context mContext;
	
	public AbsLockerMode(Context context) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		initViews();
	}
	
	protected LayoutInflater getInflater() {
		return mInflater;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public abstract void launchCamera();
	
	public abstract void reset();
	
	protected abstract void initViews();
	
	public abstract View getView();

	public abstract void clearPassword();
	
	public abstract void launchNotification(Notification notification);
}
