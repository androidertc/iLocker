package com.qc4w.ilocker.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.fragment.CPatternCreatePasswordFragment;
import com.qc4w.ilocker.ui.fragment.DPictureCreatePasswordFragment;
import com.qc4w.ilocker.ui.fragment.DigitalCreatePasswordFragment;
import com.qc4w.ilocker.ui.fragment.FragmentBase;
import com.qc4w.ilocker.ui.fragment.LPictureCreatePasswordFragment;
import com.qc4w.ilocker.ui.fragment.PPictureCreatePasswordFragment;
import com.qc4w.ilocker.ui.fragment.PatternCreatePasswordFragment;
import com.qc4w.ilocker.ui.fragment.RingCreatePasswordFragement;

public class CreatePasswordActivity extends ActivityBase {

	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_password);
		
		if(savedInstanceState == null) {
			type= getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA, 0);
		} else {
			type= savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA);
		}
		initType();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Constants.StringConstant.PARAMS_DATA, type);
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
				fragment = new CPatternCreatePasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_DIGIT:
				fragment = new DigitalCreatePasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_DPICTURE:
				fragment = new DPictureCreatePasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_LPICTURE:
				fragment = new LPictureCreatePasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_PATTERN:
				fragment = new PatternCreatePasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_PPICTURE:
				fragment = new PPictureCreatePasswordFragment();
				break;
			case Constants.LockerMode.LOCK_MODE_RING:
				fragment = new RingCreatePasswordFragement();
				break;
			default:
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
