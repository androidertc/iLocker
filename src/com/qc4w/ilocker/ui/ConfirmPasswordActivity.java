package com.qc4w.ilocker.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.fragment.CPatternConfirmPasswordFragment;
import com.qc4w.ilocker.ui.fragment.DPictureConfirmPasswordFragment;
import com.qc4w.ilocker.ui.fragment.DigitalConfirmPasswordFragment;
import com.qc4w.ilocker.ui.fragment.FragmentBase;
import com.qc4w.ilocker.ui.fragment.LPictureConfirmPasswordFragment;
import com.qc4w.ilocker.ui.fragment.PPictureConfirmPasswordFragment;
import com.qc4w.ilocker.ui.fragment.PatternConfirmPasswordFragment;
import com.qc4w.ilocker.ui.fragment.RingConfirmPasswordFragment;

public class ConfirmPasswordActivity extends ActivityBase {

	private int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_password);
		
		type = ConfigManager.getInstance(this).getLockerModeType();
		initType();
	}
	
	private void initType() {
		FragmentBase fragment = getFragment(type);
		if(fragment != null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.fl_fragment_container, fragment, fragment.getTAG());
			ft.commit();
		}
	}

	private FragmentBase getFragment(int type) {
		FragmentBase fragment = null;
		switch (type) {
			case Constants.LockerMode.LOCK_MODE_CPATTERN:
				fragment = new CPatternConfirmPasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_DIGIT:
				fragment = new DigitalConfirmPasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_DPICTURE:
				fragment = new DPictureConfirmPasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_LPICTURE:
				fragment = new LPictureConfirmPasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_PATTERN:
				fragment = new PatternConfirmPasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_PPICTURE:
				fragment = new PPictureConfirmPasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_RING:
				fragment = new RingConfirmPasswordFragment();
				break;
			default:
				showToast(R.string.current_locker_mode_not_need_password);
				finish();
				break;
		}
		return fragment;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
