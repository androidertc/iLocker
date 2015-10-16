package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;

public class LockerViewSentenceView extends LinearLayout {

	public LockerViewSentenceView(Context context) {
		super(context);
		inflate(context, R.layout.layout_lockerview_sentence_view, this);
		
		ConfigManager config = ConfigManager.getInstance(context);
		TextView tvSentence = (TextView) findViewById(R.id.tv_sentence);
		tvSentence.setText(config.getSentenceContent());
		tvSentence.setTextColor(config.getWidgetSentenceColor());
	}

}
