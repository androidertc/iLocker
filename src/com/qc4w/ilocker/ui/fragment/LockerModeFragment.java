package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.adapter.LockerModeAdapter;

public class LockerModeFragment extends FragmentBase {

	private View mRootLayout;
	
	private LockerModeAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_locker_mode, container, false);
		GridView gvLockType = (GridView) mRootLayout.findViewById(R.id.gv_lock_type);
		mAdapter = new LockerModeAdapter(getActivity());
		gvLockType.setAdapter(mAdapter);
		return mRootLayout;
	}
	
	

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}
}
