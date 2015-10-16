package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.fourmob.colorpicker.ContourPickerSwatch.OnContourSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.CropImageActivity;
import com.qc4w.ilocker.ui.view.HeartPasswordView;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class HeartLockerModeSettingsFragment extends FragmentBase implements OnClickListener, OnColorSelectedListener, OnContourSelectedListener {

	public static final String TAG = DigitalLockerModeSettingsFragment.class.getSimpleName();
	
	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private PickerDialog mContourDialog;
	private PickerDialog mColorDialog;
	
	private HeartPasswordView mPasswordView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		int[] colors = getActivity().getResources().getIntArray(R.array.colors);
		
		mContourDialog = new PickerDialog();
		mContourDialog.initialize(R.string.contour_dialog_title, ContourMapper.contourItems, ContourMapper.contourItems[mConfig.getLPictureContour()], 4, 2, PickerDialog.TYPE_CONTOUR);
		mContourDialog.setOnContourSelectedListener(this);
		
		mColorDialog = new PickerDialog();
		mColorDialog.initialize(R.string.color_dialog_title, colors, mConfig.getLPictureColor(), 4, 2, PickerDialog.TYPE_COLOR);
		mColorDialog.setOnColorSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_heart_unlock_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_contour).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
		
		mPasswordView = (HeartPasswordView) mRootLayout.findViewById(R.id.hpv_password_view);
		mPasswordView.setOnClickListener(this);
		
		int width = getResources().getDisplayMetrics().widthPixels / 6;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width * 5, width * 5);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		params.addRule(RelativeLayout.BELOW, R.id.edi_indicator);
		mPasswordView.setLayoutParams(params);
	}
	
	@Override
	public void onContourSelected(int contour) {
		int[] items = ContourMapper.contourItems;
		int length = items.length;
		for (int i = 0; i < length; i++) {
			int item = items[i];
			if(item == contour) {
				mConfig.setLPictureContour(i);
				break;
			}
		}
		mPasswordView.onContourChanged();
	
	}

	@Override
	public void onColorSelected(int color) {
		mConfig.setLPictureColor(color);
		mPasswordView.onContourChanged();
	
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
					intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_LPICTURE);
					startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CREATE_DIGITAL_PASSWORD);
				} else {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_LPICTURE);
					getActivity().finish();
				}
				break;
			case R.id.hfibv_0:
				Intent intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 0));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE0);
				break;
			case R.id.hfibv_1:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 1));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE1);
				break;
			case R.id.hfibv_2:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 2));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE2);
				break;
			case R.id.hfibv_3:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 3));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE3);
				break;
			case R.id.hfibv_4:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 4));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE4);
				break;
			case R.id.hfibv_5:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 5));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE5);
				break;
			case R.id.hfibv_6:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 6));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE6);
				break;
			case R.id.hfibv_7:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 7));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE7);
				break;
			case R.id.hfibv_8:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 8));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE8);
				break;
			case R.id.hfibv_9:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getLPictureImagePath(getActivity(), 9));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE9);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE0:
			mPasswordView.loadBitmap(0);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE1:
			mPasswordView.loadBitmap(1);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE2:
			mPasswordView.loadBitmap(2);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE3:
			mPasswordView.loadBitmap(3);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE4:
			mPasswordView.loadBitmap(4);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE5:
			mPasswordView.loadBitmap(5);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE6:
			mPasswordView.loadBitmap(6);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE7:
			mPasswordView.loadBitmap(7);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE8:
			mPasswordView.loadBitmap(8);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_LPICTURE_IMAGE9:
			mPasswordView.loadBitmap(9);
			break;
		case Constants.RequestCode.REQUEST_CODE_CREATE_DIGITAL_PASSWORD:
			if(!TextUtils.isEmpty(mConfig.getDigitalPassword())) {
				mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_LPICTURE);
				getActivity().finish();
			}
			break;
		default:
			break;
		}
	}
}
