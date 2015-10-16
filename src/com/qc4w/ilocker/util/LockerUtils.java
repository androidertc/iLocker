package com.qc4w.ilocker.util;

import java.lang.reflect.Method;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;

public class LockerUtils {

	private static final String TAG = LockerUtils.class.getSimpleName();

	private static LockerUtils instance;
	private KeyguardLock lock;

	public static boolean isLockScreenDisabled(Context context) {
		String LOCKSCREEN_UTILS = "com.android.internal.widget.LockPatternUtils";

		try {
			Class<?> lockUtilsClass = Class.forName(LOCKSCREEN_UTILS);
			Object lockUtils = lockUtilsClass.getConstructor(Context.class).newInstance(context);

			Method method = lockUtilsClass.getMethod("isLockScreenDisabled");
			boolean isDisabled = Boolean.valueOf(String.valueOf(method.invoke(lockUtils)));

			return isDisabled;
		} catch (Exception e) {
		}
		return false;
	}

	private LockerUtils(Context context) {

		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		lock = keyguardManager.newKeyguardLock("FxLock");
	}

	public static LockerUtils getInstance(Context context) {
		if (instance == null) {
			synchronized (LockerUtils.class) {
				if (instance == null) {
					instance = new LockerUtils(context);
				}
			}
		}
		return instance;
	}

	public void enableSysLock() {
		// lock.reenableKeyguard();
	}

	public void disableSysLock() {
		// 屏蔽手机内置的锁屏
		// lock.disableKeyguard();
	}

}
