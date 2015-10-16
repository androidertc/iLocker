package com.qc4w.ilocker.ui;

import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.util.MIUIHelper;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DismissActivity extends Activity {

	private static Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		
		if(MIUIHelper.isMiuiV5() && MIUIHelper.disableWindowAlter(this)) {
			ConfigManager config = ConfigManager.getInstance(this);
			if(!config.isMIUISettingsTip()) {
				Intent intent = new Intent(this, MIUISettingsActivity.class);
				startActivity(intent);
				config.setMIUISettingsTip(true);
			}
			finish();
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName());
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}
	
	public static Activity getInstance() {
		return instance;
	}
}
