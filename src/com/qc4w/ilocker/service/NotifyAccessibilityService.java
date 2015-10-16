package com.qc4w.ilocker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.view.accessibility.AccessibilityEvent;

import com.qc4w.ilocker.domain.NotificationWrapper;
import com.qc4w.ilocker.util.NotificationContainerUtils;

public class NotifyAccessibilityService extends AccessibilityService {
 
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Notification notification = (Notification)event.getParcelableData();
        if(notification == null) return;
        NotificationWrapper notify = new NotificationWrapper(notification);
        NotificationContainerUtils.getInstance(this).onNotificationReceived(notify);
    }
 
    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
 
    @Override
    public void onInterrupt() {
    }
 }
