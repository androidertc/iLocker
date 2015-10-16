package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;

public class HeartPasswordView extends RelativeLayout {
	
	private int width;

	private Bitmap bitmapBoder;
	private Bitmap bitmapButton;

	private ConfigManager config;
	
	private HeartFadeImageButtonView[] btns = new HeartFadeImageButtonView[10];

	private ImageView btnDel;
	
	public HeartPasswordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.layout_heart_password_view, this);
		
		width = (int) (context.getResources().getDisplayMetrics().widthPixels / 6f);
		
		config = ConfigManager.getInstance(context);
		init(context);
		loadBitmap();
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		for (HeartFadeImageButtonView button : btns) {
			button.setOnClickListener(l);
		}
		findViewById(R.id.btn_del).setOnClickListener(l);
	}
	
	private void loadBitmap() {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getLPictureContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getLPictureColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getLPictureContour()]);
		for (HeartFadeImageButtonView buttonView : btns) {
			buttonView.onConfigChanged(FadeImageButtonView.TYPE_LPICTURE, bitmapBoder, bitmapButton);
		}
		btnDel.setImageDrawable(ImageUtils.getHeartBackspace(getContext()));
	}

	public void onContourChanged() {
		loadBitmap();
	}
	
	public void loadBitmap(int index) {
		bitmapBoder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getLPictureContour()]);
		bitmapBoder = BitmapUtils.getBitmap(config.getLPictureColor(), bitmapBoder);
		
		bitmapButton = BitmapFactory.decodeResource(getResources(), ContourMapper.contourNormals[config.getLPictureContour()]);
		btns[index].onConfigChanged(FadeImageButtonView.TYPE_LPICTURE, bitmapBoder, bitmapButton);
	}

	private void init(Context context) {
		btnDel = (ImageView) findViewById(R.id.btn_del);
		int radius = (int) Math.max(100, config.getScreenWidth() / 6f);
		View v0 = findViewById(R.id.ccv_0);
		LayoutParams params0 = (LayoutParams) v0.getLayoutParams();
		params0.topMargin = (int) (0.75f * width);
		params0.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params0.width = radius;
		params0.height = radius;
		v0.setLayoutParams(params0);
		
		View v1 = findViewById(R.id.ccv_1);
		LayoutParams params1 = (LayoutParams) v1.getLayoutParams();
		params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params1.rightMargin = (int) (1.05f * width);
		params1.width = radius;
		params1.height = radius;
		v1.setLayoutParams(params1);
		
		View v2 = findViewById(R.id.ccv_2);
		LayoutParams params2 = (LayoutParams) v2.getLayoutParams();
		params2.topMargin = (int) (0.75f * width);
		params2.rightMargin = (int) (0.1f * width);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params2.width = radius;
		params2.height = radius;
		v2.setLayoutParams(params2);
		
		View v3 = findViewById(R.id.ccv_3);
		LayoutParams params3 = (LayoutParams) v3.getLayoutParams();
		params3.topMargin = (int) (width * 1.8f);
		params3.rightMargin = (int) (width * 0.45f);
		params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params3.width = radius;
		params3.height = radius;
		v3.setLayoutParams(params3);
		
		View v4 = findViewById(R.id.ccv_4);
		LayoutParams params4 = (LayoutParams) v4.getLayoutParams();
		params4.topMargin = (int) (width * 2.8f);
		params4.rightMargin = (int) (width * 1.15f);
		params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params4.width = radius;
		params4.height = radius;
		v4.setLayoutParams(params4);
		
		View v5 = findViewById(R.id.ccv_5);
		LayoutParams params5 = (LayoutParams) v5.getLayoutParams();
		params5.topMargin = (int) (width * 3.75f);
		params5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params5.width = radius;
		params5.height = radius;
		v5.setLayoutParams(params5);
		
		View v6 = findViewById(R.id.ccv_6);
		LayoutParams params6 = (LayoutParams) v6.getLayoutParams();
		params6.topMargin = (int) (width * 2.8f);
		params6.leftMargin = (int) (width * 1.15f);
		params6.width = radius;
		params6.height = radius;
		v6.setLayoutParams(params6);
		
		View v7 = findViewById(R.id.ccv_7);
		LayoutParams params7 = (LayoutParams) v7.getLayoutParams();
		params7.topMargin = (int) (width * 1.8f);
		params7.leftMargin = (int) (width * 0.45f);
		params7.width = radius;
		params7.height = radius;
		v7.setLayoutParams(params7);

		View v8 = findViewById(R.id.ccv_8);
		LayoutParams params8 = (LayoutParams) v8.getLayoutParams();
		params8.topMargin = (int) (0.75f * width);
		params8. leftMargin = (int) (0.1f * width);
		params8.width = radius;
		params8.height = radius;
		v8.setLayoutParams(params8);
		
		View v9 = findViewById(R.id.ccv_9);
		LayoutParams params9 = (LayoutParams) v9.getLayoutParams();
		params9.leftMargin = (int) (1.05f * width);
		params9.width = radius;
		params9.height = radius;
		v9.setLayoutParams(params9);

		btns[0] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_0);
		btns[1] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_1);
		btns[2] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_2);
		btns[3] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_3);
		btns[4] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_4);
		btns[5] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_5);
		btns[6] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_6);
		btns[7] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_7);
		btns[8] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_8);
		btns[9] = (HeartFadeImageButtonView) findViewById(R.id.hfibv_9);
	}

}
