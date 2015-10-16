package com.qc4w.ilocker.ui;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.util.MIUIHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MIUISettingsActivity extends ActivityBase implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_miui_settings);
		
		findViewById(R.id.rl_enable_system_alert).setOnClickListener(this);
		findViewById(R.id.rl_enable_auto_start).setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);                                                                     
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_enable_system_alert:
			MIUIHelper.openMiuiPermissionActivity(this);
			break;
		case R.id.rl_enable_auto_start:
			MIUIHelper.openMiuiAutostartActivity(this);
			break;
		default:
			break;
		}
	}
}
