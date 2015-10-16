package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CreatePasswordActivity;
import com.qc4w.ilocker.ui.view.DigitalPasswordView;
import com.qc4w.ilocker.ui.view.EnterDigitalIndicator;
import com.qc4w.ilocker.util.MD5Utils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class DigitalConfirmPasswordFragment extends FragmentBase  implements OnClickListener {

	private View mRootLayout;

	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	
	private DigitalPasswordView mPasswordView;
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
		
		mRootLayout = inflater.inflate(R.layout.fragment_digital_confirm_password, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		mPasswordView = (DigitalPasswordView) mRootLayout.findViewById(R.id.dpv_password_view);
		mPasswordView.setOnClickListener(this);
		indicator = (EnterDigitalIndicator) mRootLayout.findViewById(R.id.edi_indicator);
	}


	@Override
	public void onClick(View v) {
		if(password == null) {
			password = "";
		}
		switch (v.getId()) {
			case R.id.fbv_0:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_0)).getText().toString();
				break;
			case R.id.fbv_1:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_1)).getText().toString();
				break;
			case R.id.fbv_2:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_2)).getText().toString();
				break;
			case R.id.fbv_3:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_3)).getText().toString();
				break;
			case R.id.fbv_4:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_4)).getText().toString();
				break;
			case R.id.fbv_5:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_5)).getText().toString();
				break;
			case R.id.fbv_6:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_6)).getText().toString();
				break;
			case R.id.fbv_7:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_7)).getText().toString();
				break;
			case R.id.fbv_8:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_8)).getText().toString();
				break;
			case R.id.fbv_9:
				password += ((TextView)mPasswordView.findViewById(R.id.tv_9)).getText().toString();
				break;
			case R.id.fbv_del:
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
						intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.LockerMode.LOCK_MODE_DIGIT);
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
