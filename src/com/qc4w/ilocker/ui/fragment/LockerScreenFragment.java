package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.adapter.LockerScreenAdapter;

public class LockerScreenFragment extends FragmentBase {

	private View mRootLayout;
	
	private LockerScreenAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_locker_screen, container, false);
		GridView gvLockType = (GridView) mRootLayout.findViewById(R.id.gv_lock_type);
		mAdapter = new LockerScreenAdapter(getActivity());
		gvLockType.setAdapter(mAdapter);
		return mRootLayout;
	}
	
	

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}
}
