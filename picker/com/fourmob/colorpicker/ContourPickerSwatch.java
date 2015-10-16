package com.fourmob.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.util.BitmapUtils;

public class ContourPickerSwatch extends FrameLayout implements View.OnClickListener {
	private ImageView mCheckmarkImage;
	private int mContour;
	private OnContourSelectedListener mOnContourSelectedListener;
	private ImageView mSwatchImage;

	public ContourPickerSwatch(Context paramContext, int contour, boolean checked, OnContourSelectedListener onContourSelectedListener) {
		super(paramContext);
		this.mContour = contour;
		this.mOnContourSelectedListener = onContourSelectedListener;
		LayoutInflater.from(paramContext).inflate(R.layout.layout_color_picker_swatch, this);
		this.mSwatchImage = ((ImageView) findViewById(R.id.color_picker_swatch));
		this.mCheckmarkImage = ((ImageView) findViewById(R.id.color_picker_checkmark));
		setContour(contour);
		setChecked(checked);
		setOnClickListener(this);
	}

	private void setChecked(boolean checked) {
		if (checked) {
			this.mCheckmarkImage.setVisibility(View.VISIBLE);
			return;
		}
		this.mCheckmarkImage.setVisibility(View.GONE);
	}

	public void onClick(View view) {
		if (this.mOnContourSelectedListener != null)
			this.mOnContourSelectedListener.onContourSelected(this.mContour);
	}

	protected void setContour(int contour) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), contour);
		mSwatchImage.setImageBitmap(BitmapUtils.getBitmap(getResources().getColor(R.color.app_main_color), bitmap, 0.6f));
	}

	public static abstract interface OnContourSelectedListener {
		public abstract void onContourSelected(int contour);
	}
}