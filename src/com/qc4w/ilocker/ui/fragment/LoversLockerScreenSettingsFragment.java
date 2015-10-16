package com.qc4w.ilocker.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CommentActivity;
import com.qc4w.ilocker.ui.CropImageActivity;
import com.qc4w.ilocker.util.ImageUtils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class LoversLockerScreenSettingsFragment extends FragmentBase implements OnClickListener {
	
	private View mRootLayout;
	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	private TextView mETText, mEtImagesText;
	
	private ImageView mIVMale, mIVFemale;
	
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
	};
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_lovers_locker_screen_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mETText = (TextView) mRootLayout.findViewById(R.id.et_slide_text);
		mETText.setText(mConfig.getLoversUnlockText());
		mETText.setTextColor(mConfig.getLoversUnlockTextColor());

		mEtImagesText = (TextView) mRootLayout.findViewById(R.id.et_images_text);
		mEtImagesText.setText(mConfig.getLoversImagesText());
		mEtImagesText.setTextColor(mConfig.getLoversImagesTextColor());
		
		mEtImagesText.setOnClickListener(this);
		mETText.setOnClickListener(this);
		
		TextView tvDate = (TextView) mRootLayout.findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		TextView tvWeek = (TextView) mRootLayout.findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
		
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
		
		mIVMale = (ImageView) mRootLayout.findViewById(R.id.iv_image_male);
		mIVFemale = (ImageView) mRootLayout.findViewById(R.id.iv_image_female);
		
		mIVMale.setImageBitmap(ImageUtils.getMaleImage(getActivity()));
		mIVFemale.setImageBitmap(ImageUtils.getFemaleImage(getActivity()));
		
		mIVMale.setOnClickListener(this);
		mIVFemale.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_image_male:
				Intent intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getMaleImagePath(getActivity()));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_MALE_IMAGE);
				break;
			case R.id.iv_image_female:
				intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, ImageUtils.getFemaleImagePath(getActivity()));
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, 1);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, 1);
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_FEMALE_IMAGE);
				break;
			case R.id.tv_apply:
				mConfig.setLoversUnlockText(mETText.getText().toString());
				mConfig.setLoversImageText(mEtImagesText.getText().toString());
				mConfig.setLockerScreenType(Constants.LockerScreen.LOCK_SCREEN_MYLOVER);
				getActivity().finish();
				break;
			case R.id.et_slide_text:
				intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, mConfig.getLoversUnlockText());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, mConfig.getLoversUnlockTextColor());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE);
				break;
			case R.id.et_images_text:
				intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, mConfig.getLoversImagesText());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, mConfig.getLoversImagesTextColor());
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
			case Constants.RequestCode.REQUEST_CODE_PICKER_MALE_IMAGE:
				mIVMale.setImageBitmap(ImageUtils.getMaleImage(getActivity()));
				break;
			case Constants.RequestCode.REQUEST_CODE_PICKER_FEMALE_IMAGE:
				mIVFemale.setImageBitmap(ImageUtils.getFemaleImage(getActivity()));
				break;
			case Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE:
				if(resultCode == Activity.RESULT_OK && data != null) {
					String content = data.getStringExtra(Constants.StringConstant.PARAMS_DATA);
					int color = data.getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
					mConfig.setLoversUnlockText(content);
					mConfig.setLoversUnlockTextColor(color);
					mETText.setText(content);
					mETText.setTextColor(color);
				}
				break;
			case Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_SUBTITLE:
				if(resultCode == Activity.RESULT_OK && data != null) {
					String content = data.getStringExtra(Constants.StringConstant.PARAMS_DATA);
					int color = data.getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
					mConfig.setLoversImageText(content);
					mConfig.setLoversImagesTextColor(color);
					mEtImagesText.setText(content);
					mEtImagesText.setTextColor(color);
				}
				break;
			default:
				break;
		}
	}

}
