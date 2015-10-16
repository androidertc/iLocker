package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.HelpActivity;
import com.qc4w.ilocker.util.AppUtils;

public class AboutFragment extends FragmentBase implements OnClickListener {
	
	public static final String TAG = AboutFragment.class.getSimpleName();

	private View mRootLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_about, container, false);
		initUI();
		
		return mRootLayout;
	}

	private void initUI() {
		mRootLayout.findViewById(R.id.ll_version).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_product_feedback).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_product_help).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_product_info).setOnClickListener(this);

		try {
			PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS);
			TextView tvVersion = (TextView) mRootLayout.findViewById(R.id.tv_version);
			tvVersion.setText("V" + info.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_version:
				AppUtils.openMarket(getActivity(), getActivity().getPackageName());
				break;
			case R.id.rl_product_feedback:
				AppUtils.feedback(getActivity());
				break;
			case R.id.rl_product_help:
				Intent intent = new Intent(getActivity(), HelpActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_product_info:
				AppUtils.openMarket(getActivity(), getActivity().getPackageName());
				break;
			default:
				break;
		}
	}
	
}
