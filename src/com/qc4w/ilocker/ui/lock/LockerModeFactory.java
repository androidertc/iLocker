package com.qc4w.ilocker.ui.lock;

import android.content.Context;

import com.qc4w.ilocker.constant.Constants;

public class LockerModeFactory {

	public static AbsLockerMode getLockerMode(Context context, int type) {
		AbsLockerMode lockerMode = null;
		switch (type) {
			case Constants.LockerMode.LOCK_MODE_NONE:
				lockerMode = new LockerModeNone(context);
				break;
			case Constants.LockerMode.LOCK_MODE_DIGIT:
				lockerMode = new LockerModeDigital(context);
				break;
			case Constants.LockerMode.LOCK_MODE_PATTERN:
				lockerMode = new LockerModePattern(context);
				break;
			case Constants.LockerMode.LOCK_MODE_DPICTURE:
				lockerMode = new LockerModeDPicture(context);
				break;
			case Constants.LockerMode.LOCK_MODE_PPICTURE:
				lockerMode = new LockerModePPattern(context);
				break;
			case Constants.LockerMode.LOCK_MODE_LPICTURE:
				lockerMode = new LockerModeLPicture(context);
				break;
			case Constants.LockerMode.LOCK_MODE_CPATTERN:
				lockerMode = new LockerModeCPattern(context);
				break;
			case Constants.LockerMode.LOCK_MODE_RING:
				lockerMode = new LockerModeRing(context);
				break;
			default:
				break;
		}
		return lockerMode;
	}
	
}
