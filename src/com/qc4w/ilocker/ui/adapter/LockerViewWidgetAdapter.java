package com.qc4w.ilocker.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.ui.view.LockerViewCountdownView;
import com.qc4w.ilocker.ui.view.LockerViewSentenceView;
import com.qc4w.ilocker.ui.view.LockerViewTimeView;
import com.qc4w.ilocker.ui.view.LockerViewTimingView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class LockerViewWidgetAdapter extends PagerAdapter {
	
	private List<View> views;
	public LockerViewWidgetAdapter(Context context) {
		views = new ArrayList<View>();
		ConfigManager config = ConfigManager.getInstance(context);
		if(config.isWidgetTimeEnable()) {
			views.add(new LockerViewTimeView(context));
		}
		if(config.isWidgetSentenceEnable()) {
			views.add(new LockerViewSentenceView(context));
		}
		if(config.isWidgetCountdownEnable()) {
			views.add(new LockerViewCountdownView(context));
		}
		if(config.isWidgetTimingEnable()) {
			views.add(new LockerViewTimingView(context));
		}
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = views.get(position);
		container.addView(itemView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return itemView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
