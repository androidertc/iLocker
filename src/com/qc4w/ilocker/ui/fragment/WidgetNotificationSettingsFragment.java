package com.qc4w.ilocker.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.domain.NotificationApp;
import com.qc4w.ilocker.ui.adapter.NotificationAppAdapter;
import com.qc4w.ilocker.util.AccessibilityUtils;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.NotificationAppLoadTask;
import com.qc4w.ilocker.util.NotificationAppLoadTask.OnLoadListener;

public class WidgetNotificationSettingsFragment extends FragmentBase implements OnClickListener, OnLoadListener {

	private static final int MSG_LOAD_COMPLETED = 0x01;
	private View mRootLayout;
	
	private View mLoadingLayout;
	private ListView mLVNotificationApps;
	
	private NotificationAppAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new NotificationAppLoadTask(getActivity(), this).execute();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_widget_notification_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		mRootLayout.findViewById(R.id.tv_enable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_disable).setOnClickListener(this);
		mLoadingLayout = mRootLayout.findViewById(R.id.rl_loading);
		mLVNotificationApps = (ListView) mRootLayout.findViewById(R.id.lv_app_list);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_enable:
				if(AccessibilityUtils.isEnable(getActivity())) {
					getActivity().finish();
				} else {
					new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
							.setTitleText(getString(R.string.widget_label_notification))
							.setContentText(getString(R.string.widget_notification_enable_tip))
							.setCustomImage(R.drawable.ic_launcher)
							.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									sweetAlertDialog.dismiss();
									AppUtils.enterAccibility(WidgetNotificationSettingsFragment.this);
								}
							}).show();
				}
				break;
			case R.id.tv_disable:
				if(AccessibilityUtils.isEnable(getActivity())) {
	                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
	                        .setTitleText(getString(R.string.widget_label_notification))
	                        .setContentText(getString(R.string.widget_notification_disable_tip))
	                        .setCustomImage(R.drawable.ic_launcher)
	                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									sweetAlertDialog.dismiss();
									AppUtils.enterAccibility(WidgetNotificationSettingsFragment.this);
								}
							}).show();
				} else {
					getActivity().finish();
				}
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Constants.RequestCode.REQUEST_CODE_ACCESSIBILITY_SETTINGS) {
			getActivity().finish();
		}
	}

	@Override
	public void onLoadCompleted(List<NotificationApp> apps) {
		Message msg = mHandler.obtainMessage(MSG_LOAD_COMPLETED);
		msg.obj = apps;
		mHandler.sendMessage(msg);
	}
	
	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if(getActivity() != null && !getActivity().isFinishing()) {
				adapter = new NotificationAppAdapter(getActivity(), (List<NotificationApp>) msg.obj);
				mLVNotificationApps.setAdapter(adapter);
				mLoadingLayout.setVisibility(View.GONE);
				mLVNotificationApps.setVisibility(View.VISIBLE);
			}
			return false;
		}
	});
}
