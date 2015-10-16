package com.qc4w.ilocker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.fragment.CPatternLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.DPictureLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.DigitalLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.FragmentBase;
import com.qc4w.ilocker.ui.fragment.HeartLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.LoversLockerScreenSettingsFragment;
import com.qc4w.ilocker.ui.fragment.MoodLockerScreenSettingsFragment;
import com.qc4w.ilocker.ui.fragment.NameLockerScreenSettingsFragment;
import com.qc4w.ilocker.ui.fragment.PPictureLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.PatternLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.RingLockerModeSettingsFragment;
import com.qc4w.ilocker.ui.fragment.SimpleLockerScreenSettingsFragment;

public class PreViewActivity extends ActivityBase {

	public static final int PREVIEW_TYPE_LOCKER_SCREEN = 0x01;
	public static final int PREVIEW_TYPE_LOCKER_MODE = 0x02;
	
	private FragmentBase fragment;
	
	private int page;
	private int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		if(savedInstanceState == null) {
			page = getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA, PREVIEW_TYPE_LOCKER_SCREEN);
			type = getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA1, 0);
		} else {
			page = savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA, PREVIEW_TYPE_LOCKER_SCREEN);
			type = savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA1, 0);
		}
		initType(page, type);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Constants.StringConstant.PARAMS_DATA, page);
		outState.putInt(Constants.StringConstant.PARAMS_DATA1, type);
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
	
	private void initType(int page, int type) {
		initTitle(page, type);
		
		FragmentBase fragment = getFragment(page, type);
		if(fragment != null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.fl_fragment_container, fragment, fragment.getTAG());
			ft.commit();
		}
	}
	
	private FragmentBase getFragment(int page, int type) {
		if(page == PREVIEW_TYPE_LOCKER_SCREEN) {
			switch (type) {
				case Constants.LockerScreen.LOCK_SCREEN_SIMPLE:
					fragment = new SimpleLockerScreenSettingsFragment();
					break;
				case Constants.LockerScreen.LOCK_SCREEN_MYLOVER:
					fragment = new LoversLockerScreenSettingsFragment();
					break;
				case Constants.LockerScreen.LOCK_SCREEN_MYNAME:
					fragment = new NameLockerScreenSettingsFragment();
					break;
				case Constants.LockerScreen.LOCK_SCREEN_MOOD:
					fragment = new MoodLockerScreenSettingsFragment();
					break;
				default:
					break;
			}
		} else {
			switch (type) {
				case Constants.LockerMode.LOCK_MODE_DIGIT:
					fragment = new DigitalLockerModeSettingsFragment();
					break;
				case Constants.LockerMode.LOCK_MODE_DPICTURE:
					fragment = new DPictureLockerModeSettingsFragment();
					break;
				case Constants.LockerMode.LOCK_MODE_LPICTURE:
					fragment = new HeartLockerModeSettingsFragment();
					break;
				case Constants.LockerMode.LOCK_MODE_PATTERN:
					fragment = new PatternLockerModeSettingsFragment();
					break;
				case Constants.LockerMode.LOCK_MODE_PPICTURE:
					fragment = new PPictureLockerModeSettingsFragment();
					break;
				case Constants.LockerMode.LOCK_MODE_CPATTERN:
					fragment = new CPatternLockerModeSettingsFragment();
					break;
				case Constants.LockerMode.LOCK_MODE_RING:
					fragment = new RingLockerModeSettingsFragment();
					break;
				default:
					break;
			}
		}
		return fragment;
	}
	

	private void initTitle(int page, int type) {
		String title = null;
		if(page == PREVIEW_TYPE_LOCKER_SCREEN) {
			switch (type) {
				case Constants.LockerScreen.LOCK_SCREEN_SIMPLE:
					title = getString(R.string.label_lock_type_slide);
					break;
				case Constants.LockerScreen.LOCK_SCREEN_MYLOVER:
					title = getString(R.string.label_lock_type_mylover);
					break;
				case Constants.LockerScreen.LOCK_SCREEN_MYNAME:
					title = getString(R.string.label_lock_type_myname);
					break;
				default:
					title = getString(R.string.label_preview);
					break;
			}
		} else {
			switch (type) {
				case Constants.LockerMode.LOCK_MODE_NONE:
					title = getString(R.string.label_lock_type_none);
					break;
				case Constants.LockerMode.LOCK_MODE_DIGIT:
					title = getString(R.string.label_lock_type_digit);
					break;
				case Constants.LockerMode.LOCK_MODE_DPICTURE:
					title = getString(R.string.label_lock_type_dpicture);
					break;
				case Constants.LockerMode.LOCK_MODE_LPICTURE:
					title = getString(R.string.label_lock_type_lpicture);
					break;
				case Constants.LockerMode.LOCK_MODE_PATTERN:
					title = getString(R.string.label_lock_type_pattern);
					break;
				case Constants.LockerMode.LOCK_MODE_PPICTURE:
					title = getString(R.string.label_lock_type_ppicture);
					break;
				case Constants.LockerMode.LOCK_MODE_CPATTERN:
					title = getString(R.string.label_lock_type_cpattern);
					break;
				case Constants.LockerMode.LOCK_MODE_RING:
					title = getString(R.string.label_lock_type_ring);
					break;
				default:
					title = getString(R.string.label_preview);
					break;
			}
		}
		getActionBar().setTitle(title);
	}
	
}
