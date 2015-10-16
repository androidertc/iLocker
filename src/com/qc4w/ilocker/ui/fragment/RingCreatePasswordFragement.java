package com.qc4w.ilocker.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.view.EnterDigitalIndicator;
import com.qc4w.ilocker.ui.view.RingPasswordView;
import com.qc4w.ilocker.util.MD5Utils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class RingCreatePasswordFragement extends FragmentBase implements OnClickListener {

	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private RingPasswordView mPasswordView;
	private EnterDigitalIndicator indicator;
	
	private String password, password1;
	private TextView mTVEnterDigital;

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
		
		mRootLayout = inflater.inflate(R.layout.fragment_ring_create_password, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mPasswordView = (RingPasswordView) mRootLayout.findViewById(R.id.rpv_password_view);
		mPasswordView.setOnClickListener(this);
		indicator = (EnterDigitalIndicator) mRootLayout.findViewById(R.id.edi_indicator);
		mTVEnterDigital = (TextView) mRootLayout.findViewById(R.id.tv_enter_digital);
		
		mBtnCancel = (TextView) mRootLayout.findViewById(R.id.btn_reset);
		mBtnDone = (TextView) mRootLayout.findViewById(R.id.btn_done);
		mBtnCancel.setOnClickListener(this);
		mBtnDone.setOnClickListener(this);
		mBtnDone.setEnabled(false);
		
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mPasswordView.getLayoutParams();
		params.height = mConfig.getScreenWidth();
		params.width = mConfig.getScreenWidth();
		mPasswordView.setLayoutParams(params);
	}


	@Override
	public void onClick(View v) {
		if(password == null) {
			password = "";
		}
		switch (v.getId()) {
			case R.id.fibv_0:
				password += "0";
				break;
			case R.id.fibv_1:
				password += "1";
				break;
			case R.id.fibv_2:
				password += "2";
				break;
			case R.id.fibv_3:
				password += "3";
				break;
			case R.id.fibv_4:
				password += "4";
				break;
			case R.id.fibv_5:
				password += "5";
				break;
			case R.id.fibv_6:
				password += "6";
				break;
			case R.id.fibv_7:
				password += "7";
				break;
			case R.id.fibv_8:
				password += "8";
				break;
			case R.id.fibv_9:
				password += "9";
				break;
			case R.id.btn_del:
				if(password.length() > 0) {
					password = password.substring(0, password.length() - 1);
				}
				break;
			case R.id.btn_reset:
				password = "";
				password1 = "";
				indicator.passwordChanged(password);
				mBtnDone.setEnabled(false);
				break;
			case R.id.btn_done:
				mConfig.setDigitalPassword(MD5Utils.MD5(password));
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				break;
			default:
				break;
		}
		
		indicator.passwordChanged(password);
		mHandler.postDelayed(r, 200);
	}
	
	public void clearPassword() {
		password = "";
		indicator.passwordChanged(password);
	}

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			if(password.length() > 4) {
				password = password.substring(0, 4);
			}
			if(password.length() == 4) {
				if(TextUtils.isEmpty(password1)) {
					password1 = password;
					password = "";
					indicator.passwordChanged(password);
					mTVEnterDigital.setText(R.string.try_again);
				} else if(TextUtils.equals(password, password1)) {
					mBtnDone.setEnabled(true);
				} else {
					indicator.passwordWrong();
					password = "";
					mTVEnterDigital.setText(R.string.password_not_equal);
				}
			}
		}
	};
	
	private Handler mHandler = new Handler();


}
