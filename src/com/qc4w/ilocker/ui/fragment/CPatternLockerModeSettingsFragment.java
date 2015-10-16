package com.qc4w.ilocker.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fourmob.colorpicker.ContourPickerSwatch.OnContourSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.CropImageActivity;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;

public class CPatternLockerModeSettingsFragment extends FragmentBase implements OnClickListener {
	
	private static final String TAG = CPatternLockerModeSettingsFragment.class.getSimpleName();

	private View mRootLayout;
	private ConfigManager mConfig;
	private ImageView mIVNormal, mIVTouched;

	private PickerDialog mPickerDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		
		mPickerDialog = new PickerDialog();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mRootLayout != null) {
			((ViewGroup) mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_cpattern_unlock_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		mIVNormal = (ImageView) mRootLayout.findViewById(R.id.iv_normal);
		mIVTouched = (ImageView) mRootLayout.findViewById(R.id.iv_touched);
		
		mIVNormal.setImageBitmap(ImageUtils.getCPatternNormalImage(getActivity()));
		mIVTouched.setImageBitmap(ImageUtils.getCPatternTouchedImage(getActivity()));
		
		mIVNormal.setOnClickListener(this);
		mIVTouched.setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_normal_contour).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_touched_contour).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_apply:
				if(TextUtils.isEmpty(mConfig.getPatternPassword())) {
					Intent intent = new Intent(getActivity(), CreatePasswordActivity.class);
					intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_CPATTERN);
					startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CREATE_PATTERN_PASSWORD);
				} else {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_CPATTERN);
					getActivity().finish();
				}
				break;
			case R.id.tv_normal_contour:
				mPickerDialog.initialize(R.string.contour_dialog_title, ContourMapper.contourItems
						, mConfig.getCPatternNormalContour() == Constants.CommonValue.INVALID_VALUE ? 0 : ContourMapper.contourItems[mConfig.getCPatternNormalContour()], 4, 2, PickerDialog.TYPE_CONTOUR);
				mPickerDialog.setOnContourSelectedListener(new OnContourSelectedListener() {
					@Override
					public void onContourSelected(int contour) {
						int[] items = ContourMapper.contourItems;
						int length = items.length;
						for (int i = 0; i < length; i++) {
							int item = items[i];
							if(item == contour) {
								mConfig.setCPatternNormalContour(i);
								break;
							}
						}
						mIVNormal.setImageBitmap(ImageUtils.getCPatternNormalImage(getActivity()));
					}
				});
				mPickerDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.tv_touched_contour:
				mPickerDialog.initialize(R.string.contour_dialog_title, ContourMapper.contourItems
						, mConfig.getCPatternTouchedContour() == Constants.CommonValue.INVALID_VALUE ? 0 : ContourMapper.contourItems[mConfig.getCPatternTouchedContour()], 4, 2, PickerDialog.TYPE_CONTOUR);
				mPickerDialog.setOnContourSelectedListener(new OnContourSelectedListener() {
					@Override
					public void onContourSelected(int contour) {
						int[] items = ContourMapper.contourItems;
						int length = items.length;
						for (int i = 0; i < length; i++) {
							int item = items[i];
							if(item == contour) {
								mConfig.setCPatternTouchedContour(i);
								break;
							}
						}
						mIVTouched.setImageBitmap(ImageUtils.getCPatternTouchedImage(getActivity()));
					}
				});
				mPickerDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.iv_normal:
				Intent intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getCPatternNormalPicture(getActivity()));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_CPATTERN_NORMAL_IMAGE);
				break;
			case R.id.iv_touched:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getCPatternTouchedPicture(getActivity()));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_CPATTERN_TOUCHED_IMAGE);
				break;
			default:
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case Constants.RequestCode.REQUEST_CODE_PICKER_CPATTERN_NORMAL_IMAGE:
					mIVNormal.setImageBitmap(ImageUtils.getCPatternNormalImage(getActivity()));
					break;
				case Constants.RequestCode.REQUEST_CODE_PICKER_CPATTERN_TOUCHED_IMAGE:
					mIVTouched.setImageBitmap(ImageUtils.getCPatternTouchedImage(getActivity()));
					break;
				case Constants.RequestCode.REQUEST_CODE_CREATE_PATTERN_PASSWORD:
					if(!TextUtils.isEmpty(mConfig.getPatternPassword())) {
						mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_CPATTERN);
						getActivity().finish();
					}
					break;
				default:
					break;
			}
		}
	}
}
