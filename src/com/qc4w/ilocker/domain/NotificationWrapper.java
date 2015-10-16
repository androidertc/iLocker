package com.qc4w.ilocker.domain;

import android.app.Notification;
import android.text.TextUtils;

public class NotificationWrapper {

	private Notification notification;
	
	public NotificationWrapper(Notification notification) {
		super();
		this.notification = notification;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof NotificationWrapper) {
			NotificationWrapper n = (NotificationWrapper) o;
			return TextUtils.equals(getNotification().contentIntent.getTargetPackage(), n.getNotification().contentIntent.getTargetPackage());
		}
		return super.equals(o);
	}
}
