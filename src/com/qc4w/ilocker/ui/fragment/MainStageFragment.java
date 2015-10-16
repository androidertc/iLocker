package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.view.ParallaxPagerTransformer;
import com.qc4w.ilocker.ui.view.ScrollerViewPager;
import com.qc4w.ilocker.ui.view.ViewMsgTitleIndict;

public class MainStageFragment extends FragmentBase {

	public static final String TAG = MainStageFragment.class.getSimpleName();
	
	private View mRootLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootLayout = inflater.inflate(R.layout.fragment_main_stage, container, false);
		initUI();
		return mRootLayout;
	}
	
	

	private void initUI() {
		ScrollerViewPager viewPager = (ScrollerViewPager) mRootLayout.findViewById(R.id.svp_viewpager);

        LockerPagerAdapter adapter = new LockerPagerAdapter();
        viewPager.setAdapter(adapter);

        ViewMsgTitleIndict viewPagerIndicator = (ViewMsgTitleIndict) mRootLayout.findViewById(R.id.vpti_pager_indicator);
		viewPagerIndicator.setViewPager(viewPager);
		viewPager.fixScrollSpeed(400);
		viewPager.setPageTransformer(false, new ParallaxPagerTransformer());
	}

	private class LockerPagerAdapter extends FragmentStatePagerAdapter {

		private Class[] clazzes = new Class[] {LockerScreenFragment.class, LockerModeFragment.class};
		
		private String[] titles;
		public LockerPagerAdapter() {
			super(getFragmentManager());
			titles = getResources().getStringArray(R.array.locker_screen_titles);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
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
			return 2;
		}
		
	}

}
