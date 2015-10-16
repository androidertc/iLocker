package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;

public class RingPasswordView extends RelativeLayout {

	private Bitmap bitmapBoder;
	private Bitmap bitmapButton;
	
	private FadeImageButtonView[] fibvs = new FadeImageButtonView[10];
	private ConfigManager config;
	private ImageView btnDel;
	
	public RingPasswordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.layout_ring_password_view, this);
		
		config = ConfigManager.getInstance(context);
		init();
		loadBitmap();
		
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		for (FadeImageButtonView fbv : fibvs) {
			fbv.setOnClickListener(l);
		}
		findViewById(R.id.btn_del).setOnClickListener(l);
	}

	private void init() {
		fibvs[0] = (FadeImageButtonView) findViewById(R.id.fibv_0);
		fibvs[1] = (FadeImageButtonView) findViewById(R.id.fibv_1);
		fibvs[2] = (FadeImageButtonView) findViewById(R.id.fibv_2);
		fibvs[3] = (FadeImageButtonView) findViewById(R.id.fibv_3);
		fibvs[4] = (FadeImageButtonView) findViewById(R.id.fibv_4);
		fibvs[5] = (FadeImageButtonView) findViewById(R.id.fibv_5);
		fibvs[6] = (FadeImageButtonView) findViewById(R.id.fibv_6);
		fibvs[7] = (FadeImageButtonView) findViewById(R.id.fibv_7);
		fibvs[8] = (FadeImageButtonView) findViewById(R.id.fibv_8);
		fibvs[9] = (FadeImageButtonView) findViewById(R.id.fibv_9);
		
		btnDel = (ImageView) findViewById(R.id.btn_del);
	}

	private void loadBitmap() {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getRingContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getRingColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getRingContour()]);
		for (FadeImageButtonView buttonView : fibvs) {
			buttonView.onConfigChanged(FadeImageButtonView.TYPE_RING, bitmapBoder, bitmapButton);
		}
		btnDel.setImageDrawable(ImageUtils.getRingBackspace(getContext()));
	}

	public void onContourChanged() {
		loadBitmap();
	}
	
	public void loadBitmap(int index) {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getRingContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getRingColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getRingContour()]);
		fibvs[index].onConfigChanged(FadeImageButtonView.TYPE_RING, bitmapBoder, bitmapButton);
	}
}
