package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.view.EnterDigitalIndicator;
import com.qc4w.ilocker.ui.view.RingPasswordView;
import com.qc4w.ilocker.util.MD5Utils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class RingConfirmPasswordFragment extends FragmentBase implements OnClickListener {

	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private RingPasswordView mPasswordView;
	private EnterDigitalIndicator indicator;
	private String password;

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
		
		mRootLayout = inflater.inflate(R.layout.fragment_ring_confirm_password, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mPasswordView = (RingPasswordView) mRootLayout.findViewById(R.id.rpv_password_view);
		mPasswordView.setOnClickListener(this);
		indicator = (EnterDigitalIndicator) mRootLayout.findViewById(R.id.edi_indicator);

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
			default:
				break;
		}
		
		indicator.passwordChanged(password);
		mHandler.postDelayed(r, 200);
	}

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			if(password.length() > 4) {
				password = password.substring(0, 4);
			}
			if(password.length() == 4) {
				if(mConfig.getDigitalPassword().equals(MD5Utils.MD5(password))) {
					if(!getActivity().isFinishing()) {
						getActivity().finish();
						Intent intent = new Intent(getActivity(), CreatePasswordActivity.class);
						intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_RING);
						startActivity(intent);
					}
				} else {
					indicator.passwordWrong();
					password = "";
				}
			}
		}
	};
	
	private Handler mHandler = new Handler();
}
