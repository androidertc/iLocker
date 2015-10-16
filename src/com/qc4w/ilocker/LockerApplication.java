package com.qc4w.ilocker;

import android.app.Application;
import android.content.Intent;

import com.qc4w.ilocker.service.LockerService;

public class LockerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Intent service = new Intent(this, LockerService.class);
		startService(service);
	}
    
}
