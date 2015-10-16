package com.qc4w.ilocker.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourmob.colorpicker.ContourPickerSwatch.OnContourSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CommentActivity;
import com.qc4w.ilocker.ui.CropImageActivity;
import com.qc4w.ilocker.ui.view.NameImageView;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class NameLockerScreenSettingsFragment extends FragmentBase implements OnClickListener, OnContourSelectedListener {
	
	private static final String TAG = NameLockerScreenSettingsFragment.class.getSimpleName();

	private View mRootLayout;
	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	private TextView mETText, mETNameImageText;

	private PickerDialog mContourDialog;
	
	private NameImageView mNameImageView;
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
		mRootLayout = inflater.inflate(R.layout.fragment_name_locker_screen_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mNameImageView = (NameImageView) mRootLayout.findViewById(R.id.niv_name_image);
		mNameImageView.setOnClickListener(this);
		
		mETText = (TextView) mRootLayout.findViewById(R.id.et_slide_text);
		mETText.setText(mConfig.getNameUnlockText());
		mETText.setTextColor(mConfig.getNameUnlockTextColor());
		
		mETNameImageText = (TextView) mRootLayout.findViewById(R.id.et_name_image_text);
		mETNameImageText.setText(mConfig.getNameImageText());
		mETNameImageText.setTextColor(mConfig.getNameImageTextColor());
		
		mETText.setOnClickListener(this);
		mETNameImageText.setOnClickListener(this);

		TextView tvDate = (TextView) mRootLayout.findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		TextView tvWeek = (TextView) mRootLayout.findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
		
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_contour).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.niv_name_image:
				Intent intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getNameImagePath(getActivity()));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_NAME_IMAGE);
				break;
			case R.id.tv_apply:
				mConfig.setNameImageText(mETNameImageText.getText().toString());
				mConfig.setNameUnlockText(mETText.getText().toString());
				mConfig.setLockerScreenType(Constants.LockerScreen.LOCK_SCREEN_MYNAME);
				getActivity().finish();
				break;
			case R.id.tv_pick_contour:
				mContourDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.et_slide_text:
				intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, mConfig.getNameUnlockText());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, mConfig.getNameUnlockTextColor());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE);
				break;
			case R.id.et_name_image_text:
				intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, mConfig.getNameImageText());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, mConfig.getNameImageTextColor());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_SUBTITLE);
				break;
			default:
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case Constants.RequestCode.REQUEST_CODE_PICKER_NAME_IMAGE:
				mNameImageView.loadImage();
				break;
			case Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE:
				if(resultCode == Activity.RESULT_OK && data != null) {
					String content = data.getStringExtra(Constants.StringConstant.PARAMS_DATA);
					int color = data.getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
					mConfig.setNameUnlockText(content);
					mConfig.setNameUnlockTextColor(color);
					mETText.setText(content);
					mETText.setTextColor(color);
				}
				break;
			case Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_SUBTITLE:
				if(resultCode == Activity.RESULT_OK && data != null) {
					String content = data.getStringExtra(Constants.StringConstant.PARAMS_DATA);
					int color = data.getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
					mConfig.setNameImageText(content);
					mConfig.setNameImageTextColor(color);
					mETNameImageText.setText(content);
					mETNameImageText.setTextColor(color);
					mNameImageView.loadImage();
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
				mConfig.setNameImageContour(i);
				break;
			}
		}
		mNameImageView.loadImage();
	}

}
