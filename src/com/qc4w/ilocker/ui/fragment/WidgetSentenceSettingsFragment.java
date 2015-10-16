package com.qc4w.ilocker.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.qc4w.ilocker.ui.CommentActivity;
import com.qc4w.ilocker.util.WallpaperUtils;

public class WidgetSentenceSettingsFragment extends FragmentBase implements OnClickListener {
	
	private View mRootLayout;

	private ConfigManager config;
	private WallpaperDAO mDao;
	private TextView tvSentence;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_widget_sentence_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		tvSentence = (TextView) mRootLayout.findViewById(R.id.tv_sentence);
		tvSentence.setText(config.getSentenceContent());
		tvSentence.setTextColor(config.getWidgetSentenceColor());
		tvSentence.setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_enable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_disable).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_sentence:
				Intent intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, config.getSentenceContent());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, config.getWidgetSentenceColor());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE);
				break;
			case R.id.tv_enable:
				config.setWidgetSentenceEnable(true);
				getActivity().finish();
				break;
			case R.id.tv_disable:
				config.setWidgetSentenceEnable(false);
				getActivity().finish();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE:
				if(resultCode == Activity.RESULT_OK && data != null) {
					String content = data.getStringExtra(Constants.StringConstant.PARAMS_DATA);
					int color = data.getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
					config.setSentenceContnet(content);
					config.setWidgetSentenceColor(color);
					tvSentence.setText(content);
					tvSentence.setTextColor(color);
				}
				break;
			default:
				break;
		}
	}
}
