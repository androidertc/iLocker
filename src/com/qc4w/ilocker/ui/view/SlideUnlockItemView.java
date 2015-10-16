package com.qc4w.ilocker.ui.view;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideUnlockItemView extends RelativeLayout {

	public SlideUnlockItemView(Context context) {
		super(context);
		inflate(context, R.layout.layout_simple_locker_screen, this);
		
		init();
	}

	private void init() {
		ConfigManager config = ConfigManager.getInstance(getContext());
		
		ShimmerTextView tv  = (ShimmerTextView) findViewById(R.id.shimmer_tv);
	    Shimmer shimmer = new Shimmer();
        shimmer.start(tv);
        tv.setText(config.getSlideUnlockText());
        tv.setTextColor(config.getSlideUnlockTextColor());
		
		TextView tvDate = (TextView) findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		TextView tvWeek = (TextView) findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
	
	}
	
}
