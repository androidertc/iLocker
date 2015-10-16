package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.ContourMapper;
import com.qc4w.ilocker.util.ImageUtils;

public class NameImageView extends FrameLayout {
	
	
	private ImageView ivBoder;
	private ImageView ivImage;

	public NameImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.layout_name_image_view, this);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NameImageView, 0, 0);
		array.recycle();
		
		ivBoder = (ImageView) findViewById(R.id.iv_boder);
		ivImage = (ImageView) findViewById(R.id.iv_image);

		loadImage();
	}

	public void loadImage() {
		ConfigManager config = ConfigManager.getInstance(getContext());
		Bitmap boder = BitmapFactory.decodeResource(getResources(), ContourMapper.contourPresseds[config.getNameImageContour()]);
		ivBoder.setImageBitmap(BitmapUtils.getBitmap(config.getNameImageTextColor(), boder, 0.5f));
		ivImage.setImageBitmap(ImageUtils.getNameImage(getContext()));
	}
}
