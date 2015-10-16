package com.qc4w.ilocker.ui.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.view.Cell;
import com.qc4w.ilocker.ui.view.LockPatternView;
import com.qc4w.ilocker.util.LockPatternUtils;
import com.qc4w.ilocker.util.MD5Utils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class PatternCreatePasswordFragment extends FragmentBase implements OnClickListener, LockPatternView.OnPatternListener {

	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private LockPatternView mPasswordView;
	
	private String password;
	private TextView mTVDrawPattern;

	private TextView mBtnCancel, mBtnDone;
	
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
		
		mRootLayout = inflater.inflate(R.layout.fragment_pattern_create_password, container, false);
		initUI();
		return mRootLayout;
	}


	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mPasswordView = (LockPatternView) mRootLayout.findViewById(R.id.lpv_lockview);
		mPasswordView.setOnPatternListener(this);
		
		mTVDrawPattern = (TextView) mRootLayout.findViewById(R.id.tv_draw_pattern);
		
		mBtnCancel = (TextView) mRootLayout.findViewById(R.id.btn_reset);
		mBtnDone = (TextView) mRootLayout.findViewById(R.id.btn_done);
		mBtnCancel.setOnClickListener(this);
		mBtnDone.setOnClickListener(this);
		mBtnDone.setEnabled(false);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_reset:
				password = "";
				mBtnDone.setEnabled(false);
				break;
			case R.id.btn_done:
				mConfig.setPatternPassword(MD5Utils.MD5(password));
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				break;
			default:
				break;
		}
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
		if(TextUtils.isEmpty(password)) {
			password = LockPatternUtils.patternToString(pattern);
			mTVDrawPattern.setText(R.string.try_again);
		} else if(TextUtils.equals(password, LockPatternUtils.patternToString(pattern))) {
			mBtnDone.setEnabled(true);
		} else {
			mTVDrawPattern.setText(R.string.password_not_equal);
			YoYo.with(Techniques.Shake).duration(1000).playOn(mTVDrawPattern);
		}
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mPasswordView.clearPattern();
		}
	};

}
