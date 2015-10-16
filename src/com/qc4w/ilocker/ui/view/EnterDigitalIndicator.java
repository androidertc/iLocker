package com.qc4w.ilocker.ui.view;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qc4w.ilocker.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class EnterDigitalIndicator extends LinearLayout {

	private EnterDigitalIndicatorItemView[] ediis = new EnterDigitalIndicatorItemView[4];
	
	public EnterDigitalIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.view_enter_digital_indicator, this);
		
		ediis[0] = (EnterDigitalIndicatorItemView) findViewById(R.id.edii_1);
		ediis[1] = (EnterDigitalIndicatorItemView) findViewById(R.id.edii_2);
		ediis[2] = (EnterDigitalIndicatorItemView) findViewById(R.id.edii_3);
		ediis[3] = (EnterDigitalIndicatorItemView) findViewById(R.id.edii_4);
	}
	
	public void passwordChanged(String password) {
		int len = password.length();
		if(len > 4) {
			len = 4;
		}
		for (int i = 0; i < ediis.length; i++) {
			ediis[i].setSelected(false);
		}
		for (int i = 0; i < len; i++) {
			ediis[i].setSelected(true);
		}
	}

	public void passwordWrong() {
		for (int i = 0; i < ediis.length; i++) {
			ediis[i].setSelected(false);
		}
		YoYo.with(Techniques.Shake).duration(600).playOn(this);
	}
}
