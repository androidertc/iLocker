package com.qc4w.ilocker.ui.view;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.util.ImageUtils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoversITView extends RelativeLayout {

	public LoversITView(Context context) {
		super(context);
		inflate(context, R.layout.layout_lovers_it_view, this);

		ConfigManager config = ConfigManager.getInstance(getContext());
		TextView tvImagesText = (TextView) findViewById(R.id.tv_images_text);
		tvImagesText.setText(config.getLoversImagesText());
		tvImagesText.setTextColor(config.getLoversImagesTextColor());
		ImageView ivMale = (ImageView) findViewById(R.id.iv_image_male);
		ImageView ivFemale = (ImageView) findViewById(R.id.iv_image_female);
		
		ivMale.setImageBitmap(ImageUtils.getMaleImage(getContext()));
		ivFemale.setImageBitmap(ImageUtils.getFemaleImage(getContext()));

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);
		this.setGravity(Gravity.CENTER);
	}

}
