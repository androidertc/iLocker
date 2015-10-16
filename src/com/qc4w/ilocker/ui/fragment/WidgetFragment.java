package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.MainStageActivity;
import com.qc4w.ilocker.ui.view.ScrollerViewPager;
import com.qc4w.ilocker.ui.view.ViewMsgTitleIndict;

public class WidgetFragment extends FragmentBase implements OnClickListener {
	
	public static final String TAG = WidgetFragment.class.getSimpleName();
	
	private View mRootLayout;
	
	private ViewMsgTitleIndict viewPagerIndicator;
	private ScrollerViewPager mViewPager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_widget, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		mViewPager = (ScrollerViewPager) mRootLayout.findViewById(R.id.view_pager);
		mViewPager.setAdapter(new WidgetAdapter(getActivity().getSupportFragmentManager()));
		viewPagerIndicator = (ViewMsgTitleIndict) mRootLayout.findViewById(R.id.vpti_pager_indicator);
		viewPagerIndicator.setViewPager(mViewPager);
		
		mViewPager.fixScrollSpeed(400);
		mRootLayout.findViewById(R.id.tv_ok).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_ok:
				((MainStageActivity)getActivity()).change2Home();
				break;
			default:
				break;
		}
	}

	private class WidgetAdapter extends FragmentStatePagerAdapter {
		
		private Class[] clazzes = new Class[] {MainScreenFragment.class, RightScreenFragment.class};

		public WidgetAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Class clazz = clazzes[position];
			Fragment fragment = null;
			try {
				fragment = (Fragment) clazz.newInstance();
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return clazzes.length;
		}
		
	}
	
}
