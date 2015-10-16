package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qc4w.ilocker.R;

public class RightScreenFragment extends FragmentBase {

	private View mRootLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mRootLayout != null) {
			((ViewGroup) mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_right_screen, container, false);
		return mRootLayout;
	}
}
