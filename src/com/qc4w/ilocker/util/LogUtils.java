package com.qc4w.ilocker.util;

import com.qc4w.ilocker.BuildConfig;

import android.util.Log;

public class LogUtils {

	public static final String TAG = "WODFAN_UNIVERSAL";

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void d(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void e(String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, Throwable e) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, "", e);
		}
	}
	public static void e(String tag, String content, Throwable e) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, content, e);
		}
	}

}
