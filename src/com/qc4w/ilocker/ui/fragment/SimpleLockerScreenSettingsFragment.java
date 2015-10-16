package com.qc4w.ilocker.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourmob.colorpicker.PickerDialog;
import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CommentActivity;
import com.qc4w.ilocker.util.WallpaperUtils;

public class SimpleLockerScreenSettingsFragment extends FragmentBase implements OnClickListener {
	
	private static final String TAG = SimpleLockerScreenSettingsFragment.class.getSimpleName();
	
	private View mRootLayout;
	private ConfigManager mConfig;
	private TextView mETText;
	private WallpaperDAO mDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfig = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		
		mRootLayout = inflater.inflate(R.layout.fragment_simple_locker_screen_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mETText = (TextView) mRootLayout.findViewById(R.id.et_slide_text);
		mETText.setText(mConfig.getSlideUnlockText());
		mETText.setTextColor(mConfig.getSlideUnlockTextColor());
		mETText.setOnClickListener(this);

		TextView tvDate = (TextView) mRootLayout.findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		TextView tvWeek = (TextView) mRootLayout.findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
		
		mRootLayout.findViewById(R.id.tv_apply).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_apply:
				mConfig.setSlideUnlockText(mETText.getText().toString());
				mConfig.setLockerScreenType(Constants.LockerScreen.LOCK_SCREEN_SIMPLE);
				getActivity().finish();
				break;
			case R.id.et_slide_text:
				Intent intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, mConfig.getSlideUnlockText());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, mConfig.getSlideUnlockTextColor());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE);
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
			case Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE:
				if(data != null) {
					String content = data.getStringExtra(Constants.StringConstant.PARAMS_DATA);
					int color = data.getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
					mConfig.setSlideUnlockText(content);
					mConfig.setSlideUnlockTextColor(color);
					mETText.setText(content);
					mETText.setTextColor(color);
				}
				break;
			default:
				break;
			}
		}
	}

}
