package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.fourmob.colorpicker.PickerDialog;
import com.fourmob.colorpicker.ContourPickerSwatch.OnContourSelectedListener;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.util.ContourMapper;

public class MoodLockerScreenSettingsFragment extends FragmentBase implements OnClickListener, OnContourSelectedListener {

	private static final String TAG = NameLockerScreenSettingsFragment.class.getSimpleName();

	private View mRootLayout;
	private ConfigManager mConfig;
	private WallpaperDAO mDao;

	private PickerDialog mContourDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		
		mContourDialog = new PickerDialog();
		mContourDialog.initialize(R.string.contour_dialog_title, ContourMapper.contourItems, ContourMapper.contourItems[mConfig.getNameImageContour()], 4, 2, PickerDialog.TYPE_CONTOUR);
		mContourDialog.setOnContourSelectedListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_mood_locker_screen_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		
	}

	@Override
	public void onContourSelected(int contour) {
		
	}

	@Override
	public void onClick(View v) {
		
	}

}
