package com.qc4w.ilocker.ui.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.view.Cell;
import com.qc4w.ilocker.ui.view.LockPicturePatternView;
import com.qc4w.ilocker.util.LockPatternUtils;
import com.qc4w.ilocker.util.MD5Utils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class PPictureConfirmPasswordFragment extends FragmentBase  implements LockPicturePatternView.OnPatternListener {


	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private LockPicturePatternView mPasswordView;
	
	private TextView mTVDrawPattern;
	
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
		
		mRootLayout = inflater.inflate(R.layout.fragment_ppicture_confirm_password, container, false);
		initUI();
		return mRootLayout;
	}


	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mPasswordView = (LockPicturePatternView) mRootLayout.findViewById(R.id.lpv_lockview);
		mPasswordView.setOnPatternListener(this);
		
		mTVDrawPattern = (TextView) mRootLayout.findViewById(R.id.tv_draw_pattern);
	}

	@Override
	public void onPatternStart() {
		mPasswordView.removeCallbacks(mClearPatternRunnable);
	}


	@Override
	public void onPatternCleared() {
		mPasswordView.removeCallbacks(mClearPatternRunnable);
	}


	@Override
	public void onPatternCellAdded(List<Cell> pattern) {
		
	}


	@Override
	public void onPatternDetected(List<Cell> pattern) {
		mPasswordView.post(mClearPatternRunnable);
		if(mConfig.getPatternPassword().equals(MD5Utils.MD5(LockPatternUtils.patternToString(pattern)))) {
			if(!getActivity().isFinishing()) {
				getActivity().finish();
				Intent intent = new Intent(getActivity(), CreatePasswordActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_PPICTURE);
				startActivity(intent);
			}
		} else {
			mTVDrawPattern.setText(R.string.password_wrong);
			YoYo.with(Techniques.Shake).duration(1000).playOn(mTVDrawPattern);
			mPasswordView.postDelayed(mClearPatternRunnable, 1000);
		}
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mPasswordView.clearPattern();
			mTVDrawPattern.setText(R.string.draw_pattern_password);
		}
	};


}
