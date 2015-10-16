package com.qc4w.ilocker.ui.view;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LockerViewTimeView extends LinearLayout {

	public LockerViewTimeView(Context context) {
		super(context);
		inflate(context, R.layout.layout_lockerview_time_view, this);
		ConfigManager config = ConfigManager.getInstance(context);
		
		TextView tvDate = (TextView) findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		TextView tvWeek = (TextView) findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
		TextView tvClock = (TextView) findViewById(R.id.sdc_clock);
		
		tvClock.setTextColor(config.getWidgetTimeColor());
		tvDate.setTextColor(config.getWidgetTimeColor());
		tvWeek.setTextColor(config.getWidgetTimeColor());
	}

}
