package com.qc4w.ilocker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.fragment.FragmentBase;
import com.qc4w.ilocker.ui.fragment.WidgetCameraSettingsFragment;
import com.qc4w.ilocker.ui.fragment.WidgetCountdownSettingsFragment;
import com.qc4w.ilocker.ui.fragment.WidgetNotificationSettingsFragment;
import com.qc4w.ilocker.ui.fragment.WidgetSentenceSettingsFragment;
import com.qc4w.ilocker.ui.fragment.WidgetTimeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.WidgetTimingSettingsFragment;

public class WidgetSettingsActivity extends ActivityBase {

	private int widgetType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_settings);
		
		if(savedInstanceState == null) {
			widgetType = getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_TIME);
		} else {
			widgetType = savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_TIME);
		}
		initType();
	}

	private void initType() {
		initTitle();

		FragmentBase fragment = getFragment();
		if(fragment != null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.fl_fragment_container, fragment, fragment.getTAG());
			ft.commit();
		}
	}

	private void initTitle() {
		String title = null;
		switch (widgetType) {
			case Constants.WidgetType.WIDGET_TYPE_CAMERA:
				title = getString(R.string.widget_label_camera);
				break;
			case Constants.WidgetType.WIDGET_TYPE_COUNTDOWN:
				title = getString(R.string.widget_label_countdown);
				break;
			case Constants.WidgetType.WIDGET_TYPE_NOTIFICATION:
				title = getString(R.string.widget_label_notification);
				break;
			case Constants.WidgetType.WIDGET_TYPE_SENTENCE:
				title = getString(R.string.widget_label_sentence);
				break;
			case Constants.WidgetType.WIDGET_TYPE_TIME:
				title = getString(R.string.widget_label_time);
				break;
			case Constants.WidgetType.WIDGET_TYPE_TIMING:
				title = getString(R.string.widget_label_timing);
				break;
			default:
				title = getString(R.string.widget_settings_label);
				break;
		}
		getActionBar().setTitle(title);
	}

	private FragmentBase getFragment() {
		FragmentBase fragment = null;
		switch (widgetType) {
			case Constants.WidgetType.WIDGET_TYPE_CAMERA:
				fragment = new WidgetCameraSettingsFragment();
				break;
			case Constants.WidgetType.WIDGET_TYPE_COUNTDOWN:
				fragment = new WidgetCountdownSettingsFragment();
				break;
			case Constants.WidgetType.WIDGET_TYPE_NOTIFICATION:
				fragment = new WidgetNotificationSettingsFragment();
				break;
			case Constants.WidgetType.WIDGET_TYPE_SENTENCE:
				fragment = new WidgetSentenceSettingsFragment();
				break;
			case Constants.WidgetType.WIDGET_TYPE_TIME:
				fragment = new WidgetTimeSettingsFragment();
				break;
			case Constants.WidgetType.WIDGET_TYPE_TIMING:
				fragment = new WidgetTimingSettingsFragment();
				break;
			default:
				break;
		}
		return fragment;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Constants.StringConstant.PARAMS_DATA, widgetType);
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
}
