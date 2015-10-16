package com.qc4w.ilocker.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.view.Cell;
import com.qc4w.ilocker.ui.view.LockPatternView;
import com.qc4w.ilocker.util.MD5Utils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class PatternLockerModeSettingsFragment extends FragmentBase implements OnClickListener, OnColorSelectedListener, LockPatternView.OnPatternListener {
	
	public static final String TAG = PatternLockerModeSettingsFragment.class.getSimpleName();
	
	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	private PickerDialog mColorDialog;
	
	private LockPatternView mPatternView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());

		int[] colors = getActivity().getResources().getIntArray(R.array.colors);
		mColorDialog = new PickerDialog();
		mColorDialog.initialize(R.string.color_dialog_title, colors, mConfig.getPatternColor(), 4, 2, PickerDialog.TYPE_COLOR);
		mColorDialog.setOnColorSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_pattern_unlock_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());

		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
		
		mPatternView = (LockPatternView) mRootLayout.findViewById(R.id.lpv_lockview);

		mPatternView.setOnPatternListener(this);
		mPatternView.setTactileFeedbackEnabled(true);
	}

	@Override
	public void onColorSelected(int color) {
		mConfig.setPatternColor(color);
		mPatternView.onConfigChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_pick_color:
				mColorDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.tv_apply:
				if(TextUtils.isEmpty(mConfig.getPatternPassword())) {
					Intent intent = new Intent(getActivity(), CreatePasswordActivity.class);
					intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_PATTERN);
					startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CREATE_PATTERN_PASSWORD);
				} else {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_PATTERN);
					getActivity().finish();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case Constants.RequestCode.REQUEST_CODE_CREATE_PATTERN_PASSWORD:
				if(!TextUtils.isEmpty(mConfig.getPatternPassword())) {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_PATTERN);
					getActivity().finish();
				}
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onPatternStart() {
		mPatternView.removeCallbacks(mClearPatternRunnable);
	}

	@Override
	public void onPatternCleared() {
		mPatternView.removeCallbacks(mClearPatternRunnable);
	}

	@Override
	public void onPatternCellAdded(List<Cell> pattern) {
		
	}

	@Override
	public void onPatternDetected(List<Cell> pattern) {
		if (pattern == null)
			return;
		mPatternView.postDelayed(mClearPatternRunnable, 1000);
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mPatternView.clearPattern();
		}
	};
}
