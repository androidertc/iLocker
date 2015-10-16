package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.ui.ConfirmPasswordActivity;
import com.qc4w.ilocker.util.AppUtils;

public class SettingsFragment extends FragmentBase implements OnClickListener {

	public static final String TAG = SettingsFragment.class.getSimpleName();
	
	private View mRootLayout;
	private ConfigManager mConfig;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		mRootLayout.findViewById(R.id.rl_onkey_lock).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_disable_system_lock).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_change_password).setOnClickListener(this);
		SwitchButton sbEnable = (SwitchButton) mRootLayout.findViewById(R.id.sb_service_enable);
		sbEnable.setChecked(mConfig.isServiceEnable());
		sbEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mConfig.setServiceEnable(isChecked);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_onkey_lock:
				AppUtils.createLockScreenShortCut(getActivity());
				break;
			case R.id.rl_disable_system_lock:
				Intent intent = new Intent("android.app.action.SET_NEW_PASSWORD");
				startActivity(intent);
				break;
			case R.id.rl_change_password:
				intent = new Intent(getActivity(), ConfirmPasswordActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
