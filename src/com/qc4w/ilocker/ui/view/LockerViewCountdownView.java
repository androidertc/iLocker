package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;

public class LockerViewCountdownView extends LinearLayout {

	public LockerViewCountdownView(Context context) {
		super(context);
		inflate(context, R.layout.layout_lockerview_countdown_view, this);
		
		ConfigManager config = ConfigManager.getInstance(context);
		TextView tvCountdownContent = (TextView) findViewById(R.id.tv_countdown_content);
		CountdownTextView tvCountdown = (CountdownTextView) findViewById(R.id.tv_countdown_time);
		
		tvCountdownContent.setText(config.getCountdownContent());
		tvCountdown.setTime(config.getCountdownTimestamp());
		tvCountdownContent.setTextColor(config.getWidgetCountdownColor());
		tvCountdown.setTextColor(config.getWidgetCountdownColor());
	}

}
