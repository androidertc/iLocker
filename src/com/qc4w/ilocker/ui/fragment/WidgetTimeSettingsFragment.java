package com.qc4w.ilocker.ui.fragment;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.util.WallpaperUtils;

public class WidgetTimeSettingsFragment extends FragmentBase implements OnClickListener, OnColorSelectedListener {
	
	private static final String TAG = WidgetTimeSettingsFragment.class.getSimpleName();
	private View mRootLayout;
	
	private ConfigManager config;
	private WallpaperDAO mDao;
	
	private TextView tvDate, tvWeek, tvClock;
	private PickerDialog mColorDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		int[] colors = getActivity().getResources().getIntArray(R.array.colors);

		mColorDialog = new PickerDialog();
		mColorDialog.initialize(R.string.color_dialog_title, colors, config.getWidgetTimeColor(), 4, 2, PickerDialog.TYPE_COLOR);
		mColorDialog.setOnColorSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_widget_time_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		tvDate = (TextView) mRootLayout.findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		tvWeek = (TextView) mRootLayout.findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
		tvClock = (TextView) mRootLayout.findViewById(R.id.sdc_clock);
		
		tvClock.setTextColor(config.getWidgetTimeColor());
		tvDate.setTextColor(config.getWidgetTimeColor());
		tvWeek.setTextColor(config.getWidgetTimeColor());
		
		mRootLayout.findViewById(R.id.tv_disable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_enable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
	}

	@Override
	public void onColorSelected(int color) {
		config.setWidgetTimeColor(color);
		tvClock.setTextColor(color);
		tvDate.setTextColor(color);
		tvWeek.setTextColor(color);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_pick_color:
				mColorDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.tv_disable:
				config.setWidgetTimeEnable(false);
				getActivity().finish();
				break;
			case R.id.tv_enable:
				config.setWidgetTimeEnable(true);
				getActivity().finish();
				break;
			default:
				break;
		}
	}
	
}
