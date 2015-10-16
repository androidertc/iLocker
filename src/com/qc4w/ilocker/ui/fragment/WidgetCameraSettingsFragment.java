package com.qc4w.ilocker.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.fourmob.colorpicker.PickerDialog;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class WidgetCameraSettingsFragment extends FragmentBase implements OnClickListener, OnColorSelectedListener {

	private static final String TAG = WidgetCameraSettingsFragment.class.getSimpleName();
	private View mRootLayout;
	
	private ConfigManager config;
	private WallpaperDAO mDao;
	
	private ImageView ivCamera;
	private PickerDialog mColorDialog;
	private Bitmap contour;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ConfigManager.getInstance(getActivity());
		mDao = WallpaperDAO.getInstance(getActivity());
		int[] colors = getActivity().getResources().getIntArray(R.array.colors);
		
		contour = BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera);
		mColorDialog = new PickerDialog();
		mColorDialog.initialize(R.string.color_dialog_title, colors, config.getWidgetCameraColor(), 4, 2, PickerDialog.TYPE_COLOR);
		mColorDialog.setOnColorSelectedListener(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		contour.recycle();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_widget_camera_settings, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ImageView ivBackground = (ImageView) mRootLayout.findViewById(R.id.iv_background);
		WallpaperUtils.showWallpaper(getActivity(), ivBackground, mDao.getCurrent());
		
		ivCamera = (ImageView) mRootLayout.findViewById(R.id.iv_camera);
		mRootLayout.findViewById(R.id.tv_disable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_enable).setOnClickListener(this);
		mRootLayout.findViewById(R.id.tv_pick_color).setOnClickListener(this);
		ivCamera.setImageBitmap(BitmapUtils.getBitmap(config.getWidgetCameraColor(), contour, 0.8f));
	}
	
	@Override
	public void onColorSelected(int color) {
		config.setWidgetCameraColor(color);
		ivCamera.setImageBitmap(BitmapUtils.getBitmap(color, contour, 0.8f));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_pick_color:
				mColorDialog.show(getFragmentManager(), TAG);
				break;
			case R.id.tv_disable:
				config.setWidgetCameraEnable(false);
				getActivity().finish();
				break;
			case R.id.tv_enable:
				config.setWidgetCameraEnable(true);
				getActivity().finish();
				break;
			default:
				break;
		}
	}
}
