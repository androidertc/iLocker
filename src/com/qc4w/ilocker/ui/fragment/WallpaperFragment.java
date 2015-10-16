package com.qc4w.ilocker.ui.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.domain.Wallpaper;
import com.qc4w.ilocker.ui.CropImageActivity;
import com.qc4w.ilocker.ui.adapter.WallpaperAdapter;
import com.qc4w.ilocker.util.ImageUtils;

public class WallpaperFragment extends FragmentBase implements WallpaperAdapter.OnWallpaperListener, OnClickListener {
	
	public static final String TAG = WallpaperFragment.class.getSimpleName();
	
	private WallpaperDAO mDao;
	
	private List<Wallpaper> wallpapers;
	
	private WallpaperAdapter mAdapter;
	
	private ConfigManager config;
	
	private String path;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wallpaper, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.gv_wallpaper);
		mAdapter = new WallpaperAdapter(getActivity(), wallpapers, this);
		gridView.setAdapter(mAdapter);
		view.findViewById(R.id.rl_pick_wallpaper_from_gallery).setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDao = WallpaperDAO.getInstance(getActivity());
		wallpapers = mDao.getAll();
		config = ConfigManager.getInstance(getActivity());
	}

	@Override
	public void onWallpaperDelete() {
		wallpapers = mDao.getAll();
		mAdapter.setData(wallpapers);
	}

	@Override
	public void onWallpaperChanged() {
		wallpapers = mDao.getAll();
		mAdapter.setData(wallpapers);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_pick_wallpaper_from_gallery:
				path = ImageUtils.getWallpaperPath(getActivity());
				Intent intent = new Intent(getActivity(), CropImageActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, path);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, config.getScreenWidth());
				intent.putExtra(Constants.StringConstant.PARAMS_DATA2, config.getScreenHeight());
				startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_WALLPAPER);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case Constants.RequestCode.REQUEST_CODE_PICKER_WALLPAPER:
				if(path != null) {
					Wallpaper paper = new Wallpaper();
					paper.setCurrent(true);
					paper.setPath(path);
					paper.setPreSet(false);
					mDao.cancelCurrent();
					mDao.add(paper);
					onWallpaperChanged();
				}
				break;
			default:
				break;
			}
		}
	}
}
