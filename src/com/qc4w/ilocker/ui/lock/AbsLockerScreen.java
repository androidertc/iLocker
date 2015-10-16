package com.qc4w.ilocker.ui.lock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.qc4w.ilocker.domain.NotificationWrapper;
import com.qc4w.ilocker.ui.adapter.NotificationAdapter;
import com.qc4w.ilocker.util.NotificationContainerUtils;
import com.qc4w.ilocker.util.NotificationContainerUtils.OnNotificationListener;

public abstract class AbsLockerScreen implements OnNotificationListener {
	
	private LayoutInflater mInflater;

	private Context mContext;
	
	private LockerView lockerView;

	public AbsLockerScreen(Context context, LockerView lockerView) {
		this.mContext = context;
		this.lockerView = lockerView;
		mInflater = LayoutInflater.from(context);
		initViews();
		NotificationContainerUtils.getInstance(mContext).setListener(this);
	}
	
	protected LayoutInflater getInflater() {
		return mInflater;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public LockerView getLockerView() {
		return lockerView;
	}
	
	protected abstract void initViews();
	
	public abstract View getView();
	
	public abstract NotificationAdapter getNotificationAdapter();

	@Override
	public void onNotificationAdd(NotificationWrapper notification) {
		NotificationAdapter adapter = getNotificationAdapter();
		if(getNotificationAdapter() != null) {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onNotificationRemove(NotificationWrapper notification) {
		NotificationAdapter adapter = getNotificationAdapter();
		if(getNotificationAdapter() != null) {
			adapter.notifyDataSetChanged();
		}
	}
}
