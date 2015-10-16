package com.qc4w.ilocker.ui.fragment;

import java.util.Calendar;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.CommentActivity;
import com.qc4w.ilocker.ui.view.CountdownTextView;
import com.qc4w.ilocker.util.WallpaperUtils;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

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

public class WidgetCountdownSettingsFragment extends FragmentBase implements OnClickListener, OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	
	private static final String TAG = WidgetCountdownSettingsFragment.class.getSimpleName();

	private View mRootLayout;

	private ConfigManager config;
	private WallpaperDAO mDao;
	private TextView tvCountdownContent;
	private CountdownTextView tvCountdown;
	
	private DatePickerDialog dpd;
	private TimePickerDialog tpd;
	private Calendar calendar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		
		calendar = Calendar.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_widget_countdown_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		tvCountdownContent = (TextView) mRootLayout.findViewById(R.id.tv_countdown_content);
		tvCountdown = (CountdownTextView) mRootLayout.findViewById(R.id.tv_countdown_time);
		
		tvCountdownContent.setText(config.getCountdownContent());
		tvCountdown.setTime(config.getCountdownTimestamp());
		tvCountdownContent.setTextColor(config.getWidgetCountdownColor());
		tvCountdown.setTextColor(config.getWidgetCountdownColor());
		tvCountdown.setOnClickListener(this);
		tvCountdownContent.setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_enable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_disable).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_enable:
				config.setWidgetCountdownEnable(true);
				getActivity().finish();
				break;
			case R.id.tv_disable:
				config.setWidgetCountdownEnable(false);
				getActivity().finish();
				break;
			case R.id.tv_countdown_content:
				Intent intent = new Intent(getActivity(), CommentActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, config.getCountdownContent());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, config.getWidgetCountdownColor());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_CHAGE_TEXT_TITLE);
				break;
			case R.id.tv_countdown_time:
				calendar.setTimeInMillis(config.getCountdownTimestamp());
				dpd = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
		        tpd = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

		        dpd.setYearRange(Constants.CommonValue.FIRST_YEAR, Constants.CommonValue.LAST_YEAR);
		        dpd.setCloseOnSingleTapDay(false);
		        dpd.show(getFragmentManager(), TAG);
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
					config.setCountdownContent(content);
					config.setWidgetCountdownColor(color);
					tvCountdownContent.setText(content);
					tvCountdownContent.setTextColor(color);
					tvCountdown.setTextColor(color);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		config.setCountdownTimestamp(calendar.getTimeInMillis());
		tvCountdown.setTime(config.getCountdownTimestamp());
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		config.setCountdownTimestamp(calendar.getTimeInMillis());
        tpd.setCloseOnSingleTapMinute(false);
        tpd.show(getFragmentManager(), TAG);
		tvCountdown.setTime(config.getCountdownTimestamp());
	}
}
