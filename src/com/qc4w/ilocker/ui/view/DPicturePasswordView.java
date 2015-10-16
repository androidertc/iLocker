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

public class DPicturePasswordView extends RelativeLayout {

	private Bitmap bitmapBoder;
	private Bitmap bitmapButton;
	
	private FadeImageButtonView[] fibvs = new FadeImageButtonView[10];
	private ConfigManager config;
	private ImageView fibvDel;
	
	public DPicturePasswordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.layout_dpicture_password_view, this);
		
		config = ConfigManager.getInstance(context);
		init();
		loadBitmap();
		
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		for (FadeImageButtonView fbv : fibvs) {
			fbv.setOnClickListener(l);
		}
		findViewById(R.id.fibv_del).setOnClickListener(l);
	}

	private void init() {
		fibvs[0] = (FadeImageButtonView) findViewById(R.id.fibv_1);
		fibvs[1] = (FadeImageButtonView) findViewById(R.id.fibv_2);
		fibvs[2] = (FadeImageButtonView) findViewById(R.id.fibv_3);
		fibvs[3] = (FadeImageButtonView) findViewById(R.id.fibv_4);
		fibvs[4] = (FadeImageButtonView) findViewById(R.id.fibv_5);
		fibvs[5] = (FadeImageButtonView) findViewById(R.id.fibv_6);
		fibvs[6] = (FadeImageButtonView) findViewById(R.id.fibv_7);
		fibvs[7] = (FadeImageButtonView) findViewById(R.id.fibv_8);
		fibvs[8] = (FadeImageButtonView) findViewById(R.id.fibv_9);
		fibvs[9] = (FadeImageButtonView) findViewById(R.id.fibv_0);
		
		fibvDel = (ImageView) findViewById(R.id.fibv_del);
	}

	private void loadBitmap() {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getDPictureContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getDPictureColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getDPictureContour()]);
		for (FadeImageButtonView buttonView : fibvs) {
			buttonView.onConfigChanged(FadeImageButtonView.TYPE_DPICTURE, bitmapBoder, bitmapButton);
		}
		
		fibvDel.setImageDrawable(ImageUtils.getDPictureBackspace(getContext()));
	}

	public void onContourChanged() {
		loadBitmap();
	}
	
	public void loadBitmap(int index) {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getDPictureContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getDPictureColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getDPictureContour()]);
		fibvs[index].onConfigChanged(FadeImageButtonView.TYPE_DPICTURE, bitmapBoder, bitmapButton);
	}
}
