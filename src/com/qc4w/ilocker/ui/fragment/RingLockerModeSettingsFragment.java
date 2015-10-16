package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
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
import com.qc4w.ilocker.ui.view.RingPasswordView;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class RingLockerModeSettingsFragment extends FragmentBase implements OnClickListener, OnColorSelectedListener, OnContourSelectedListener {
	private static final String TAG = RingLockerModeSettingsFragment.class.getSimpleName();

	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private PickerDialog mContourDialog;
	private PickerDialog mColorDialog;
	
	private RingPasswordView mPasswordView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		int[] colors = getActivity().getResources().getIntArray(R.array.colors);
		
		mContourDialog = new PickerDialog();
		mContourDialog.initialize(R.string.contour_dialog_title, ContourMapper.contourItems, ContourMapper.contourItems[mConfig.getRingContour()], 4, 2, PickerDialog.TYPE_CONTOUR);
		mContourDialog.setOnContourSelectedListener(this);
		
		mColorDialog = new PickerDialog();
		mColorDialog.initialize(R.string.color_dialog_title, colors, mConfig.getRingColor(), 4, 2, PickerDialog.TYPE_COLOR);
		mColorDialog.setOnColorSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_ring_unlock_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());

		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_contour).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
		
		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_contour).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
	
		mPasswordView = (RingPasswordView) mRootLayout.findViewById(R.id.rpv_password_view);
		mPasswordView.setOnClickListener(this);
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mPasswordView.getLayoutParams();
		params.height = mConfig.getScreenWidth();
		params.width = mConfig.getScreenWidth();
		mPasswordView.setLayoutParams(params);
	}

	@Override
	public void onContourSelected(int contour) {
		int[] items = ContourMapper.contourItems;
		int length = items.length;
		for (int i = 0; i < length; i++) {
			int item = items[i];
			if(item == contour) {
				mConfig.setRingContour(i);
				break;
			}
		}
		mPasswordView.onContourChanged();
	}

	@Override
	public void onColorSelected(int color) {
		mConfig.setRingColor(color);
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
					intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_RING);
					startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CREATE_DIGITAL_PASSWORD);
				} else {
					mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_RING);
					getActivity().finish();
				}
				break;
			case R.id.fibv_0:
				Intent intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 0));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE0);
				break;
			case R.id.fibv_1:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 1));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE1);
				break;
			case R.id.fibv_2:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 2));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE2);
				break;
			case R.id.fibv_3:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 3));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE3);
				break;
			case R.id.fibv_4:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 4));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE4);
				break;
			case R.id.fibv_5:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 5));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE5);
				break;
			case R.id.fibv_6:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 6));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE6);
				break;
			case R.id.fibv_7:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 7));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE7);
				break;
			case R.id.fibv_8:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 8));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE8);
				break;
			case R.id.fibv_9:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getRingImagePath(getActivity(), 9));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE9);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE0:
			mPasswordView.loadBitmap(0);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE1:
			mPasswordView.loadBitmap(1);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE2:
			mPasswordView.loadBitmap(2);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE3:
			mPasswordView.loadBitmap(3);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE4:
			mPasswordView.loadBitmap(4);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE5:
			mPasswordView.loadBitmap(5);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE6:
			mPasswordView.loadBitmap(6);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE7:
			mPasswordView.loadBitmap(7);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE8:
			mPasswordView.loadBitmap(8);
			break;
		case Constants.RequestCode.REQUEST_CODE_PICKER_RING_IMAGE9:
			mPasswordView.loadBitmap(9);
			break;
		case Constants.RequestCode.REQUEST_CODE_CREATE_DIGITAL_PASSWORD:
			if(!TextUtils.isEmpty(mConfig.getDigitalPassword())) {
				mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_RING);
				getActivity().finish();
			}
			break;
		default:
			break;
		}
	}
}
