package com.qc4w.ilocker.ui.lock;

import android.content.Context;

import com.qc4w.ilocker.constant.Constants;

public class LockerScreenFactory {

	public static AbsLockerScreen getLockerScreen(Context context, int type, LockerView lockerView) {
		AbsLockerScreen lockerScreen = null;
		switch (type) {
			case Constants.LockerScreen.LOCK_SCREEN_SIMPLE:
				lockerScreen = new LockerScreenSimple(context, lockerView);
				break;
			case Constants.LockerScreen.LOCK_SCREEN_MYLOVER:
				lockerScreen = new LockerScreenLovers(context, lockerView);
				break;
			case Constants.LockerScreen.LOCK_SCREEN_MYNAME:
				lockerScreen = new LockerScreenName(context, lockerView);
				break;
			default:
				break;
		}
		return lockerScreen;
	}
	
	
}
