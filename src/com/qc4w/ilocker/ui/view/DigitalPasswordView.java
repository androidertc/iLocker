package com.qc4w.ilocker.ui.view;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DigitalPasswordView extends RelativeLayout {

	private Bitmap bitmapBoder;
	private Bitmap bitmapButton;
	
	private FadeButtonView[] fbvs = new FadeButtonView[10];
	private ConfigManager config;
	private ImageView fbvDel;
	
	public DigitalPasswordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.layout_digital_password_view, this);
		
		config = ConfigManager.getInstance(context);
		init();
		loadBitmap();
		
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		for (FadeButtonView fbv : fbvs) {
			fbv.setOnClickListener(l);
		}
		fbvDel.setOnClickListener(l);
	}

	private void init() {
		fbvs[0] = (FadeButtonView) findViewById(R.id.fbv_0);
		fbvs[1] = (FadeButtonView) findViewById(R.id.fbv_1);
		fbvs[2] = (FadeButtonView) findViewById(R.id.fbv_2);
		fbvs[3] = (FadeButtonView) findViewById(R.id.fbv_3);
		fbvs[4] = (FadeButtonView) findViewById(R.id.fbv_4);
		fbvs[5] = (FadeButtonView) findViewById(R.id.fbv_5);
		fbvs[6] = (FadeButtonView) findViewById(R.id.fbv_6);
		fbvs[7] = (FadeButtonView) findViewById(R.id.fbv_7);
		fbvs[8] = (FadeButtonView) findViewById(R.id.fbv_8);
		fbvs[9] = (FadeButtonView) findViewById(R.id.fbv_9);
		
		fbvDel = (ImageView) findViewById(R.id.fbv_del);
		fbvDel.setImageDrawable(ImageUtils.getDigitalBackspace(getContext()));
	}

	private void loadBitmap() {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getDigitalContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getDigitalColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getDigitalContour()]);
		bitmapButton = BitmapUtils.getBitmap(config.getDigitalColor(), bitmapButton);
		for (FadeButtonView buttonView : fbvs) {
			buttonView.onConfigChanged(bitmapBoder, bitmapButton);
		}
		fbvDel.setImageDrawable(ImageUtils.getDigitalBackspace(getContext()));
	}

	public void onContourChanged() {
		loadBitmap();
	}
}
