package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.fourmob.colorpicker.ContourPickerSwatch.OnContourSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.view.DigitalPasswordView;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.WallpaperUtils;

public class DigitalLockerModeSettingsFragment extends FragmentBase implements OnClickListener, OnColorSelectedListener, OnContourSelectedListener {
	
	public static final String TAG = DigitalLockerModeSettingsFragment.class.getSimpleName();
	
	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private PickerDialog mContourDialog;
	private PickerDialog mColorDialog;
	
	private DigitalPasswordView mPasswordView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		int[] colors = getActivity().getResources().getIntArray(R.array.colors);
		
		mContourDialog = new PickerDialog();
		mContourDialog.initialize(R.string.contour_dialog_title, ContourMapper.contourItems, ContourMapper.contourItems[mConfig.getDigitalContour()], 4, 2, PickerDialog.TYPE_CONTOUR);
		mContourDialog.setOnContourSelectedListener(this);
		
		mColorDialog = new PickerDialog();
		mColorDialog.initialize(R.string.color_dialog_title, colors, mConfig.getDigitalColor(), 4, 2, PickerDialog.TYPE_COLOR);
		mColorDialog.setOnColorSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_digital_unlock_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_contour).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
		
		mPasswordView = (DigitalPasswordView) mRootLayout.findViewById(R.id.dpv_password_view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_pick_color:
				mColorDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.tv_pick_contour:
				mContourDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.tv_apply:
				if(TextUtils.isEmpty(mConfig.getDigitalPassword())) {
					Intent intent = new Intent(getActivity(), CreatePasswordActivity.class);
					intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_DIGIT);
					startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CREATE_DIGITAL_PASSWORD);
				} else {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_DIGIT);
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
			case Constants.RequestCode.REQUEST_CODE_CREATE_DIGITAL_PASSWORD:
				if(!TextUtils.isEmpty(mConfig.getDigitalPassword())) {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_DIGIT);
					getActivity().finish();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onContourSelected(int contour) {
		int[] items = ContourMapper.contourItems;
		int length = items.length;
		for (int i = 0; i < length; i++) {
			int item = items[i];
			if(item == contour) {
				mConfig.setDigitalContour(i);
				break;
			}
		}
		mPasswordView.onContourChanged();
	}

	@Override
	public void onColorSelected(int color) {
		mConfig.setDigitalColor(color);
		mPasswordView.onContourChanged();
	}

}
