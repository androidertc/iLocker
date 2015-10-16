package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;

public class LockerViewTimingView extends LinearLayout {

	public LockerViewTimingView(Context context) {
		super(context);
		inflate(context, R.layout.layout_lockerview_timing_view, this);
		
		ConfigManager config = ConfigManager.getInstance(context);
		TextView tvTimingContent = (TextView) findViewById(R.id.tv_timing_content);
		TimingTextView tvTiming = (TimingTextView) findViewById(R.id.tv_timing_time);
		
		tvTimingContent.setText(config.getTimingContent());
		tvTiming.setTime(config.getTimingTimestamp());
		tvTimingContent.setTextColor(config.getWidgetTimingColor());
		tvTiming.setTextColor(config.getWidgetTimingColor());
	}

}
