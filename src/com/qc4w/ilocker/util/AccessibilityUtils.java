package com.qc4w.ilocker.util;

import java.util.List;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

public class AccessibilityUtils {

	public static boolean isEnable(Context context) {
		AccessibilityManager manager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
		List<AccessibilityServiceInfo> services = manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
		boolean flag = false;
		for (AccessibilityServiceInfo info : services) {
			ResolveInfo i = info.getResolveInfo();
			if(TextUtils.equals(context.getPackageName(), i.serviceInfo.packageName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
