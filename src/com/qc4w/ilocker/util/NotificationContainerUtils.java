package com.qc4w.ilocker.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.qc4w.ilocker.db.NotificationAppDAO;
import com.qc4w.ilocker.domain.NotificationWrapper;

public class NotificationContainerUtils {

	private static NotificationContainerUtils instance;
	
	private List<NotificationWrapper> notifications;
	
	private OnNotificationListener listener;
	
	private NotificationAppDAO mDAO;
	
	private NotificationContainerUtils(Context context) {
		notifications = new ArrayList<NotificationWrapper>();
		mDAO = NotificationAppDAO.getInstance(context);
	}
	
	public static NotificationContainerUtils getInstance(Context context) {
		if(instance == null) {
			synchronized (NotificationContainerUtils.class) {
				if(instance == null) {
					instance = new NotificationContainerUtils(context);
				}
			}
		}
		return instance;
	}
	
	public List<NotificationWrapper> getAllNotifications() {
		return notifications;
	}
	
	public void setListener(OnNotificationListener listener) {
		this.listener = listener;
	}
	
	public void onNotificationReceived(NotificationWrapper notification) {
		if(notification.getNotification().contentIntent == null 
				|| !mDAO.isAppExists(notification.getNotification().contentIntent.getTargetPackage())) {
			return;
		}
		if(notifications.contains(notification)) {
			notifications.remove(notification);
		}
		if(!TextUtils.isEmpty(notification.getNotification().tickerText)) {
			notifications.add(notification);
			if(listener != null) {
				listener.onNotificationAdd(notification);
			}
		}
	}
	
	public void onNotificationHandleed(NotificationWrapper notification) {
		if(notifications.contains(notification)) {
			notifications.remove(notification);
			if(listener != null) {
				listener.onNotificationRemove(notification);
			}
		}
	}

	public void onNotificationHandleed() {
		notifications.clear();
		if(listener != null) {
			listener.onNotificationRemove(null);
		}
	}
	
	public interface OnNotificationListener {
		public void onNotificationAdd(NotificationWrapper notification);
		
		public void onNotificationRemove(NotificationWrapper notification);
	}
}
