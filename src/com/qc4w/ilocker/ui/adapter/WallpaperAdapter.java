package com.qc4w.ilocker.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.domain.Wallpaper;
import com.qc4w.ilocker.util.ViewHolder;
import com.qc4w.ilocker.util.WallpaperUtils;

public class WallpaperAdapter extends BaseAdapter {
	
	private Context context;
	private List<Wallpaper> wallpapers;
	private LayoutInflater mInflater;
	private WallpaperDAO mDao;
	private OnWallpaperListener listener;
	
	public WallpaperAdapter(Context context, List<Wallpaper> wallpapers, OnWallpaperListener listener) {
		this.context = context;
		this.wallpapers = wallpapers;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDao = WallpaperDAO.getInstance(context);
		this.listener = listener;
	}
	
	public void setData(List<Wallpaper> wallpapers) {
		this.wallpapers = wallpapers;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return wallpapers.size();
	}

	@Override
	public Object getItem(int position) {
		return wallpapers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.list_wallpaper_item, parent, false);
		}
		final Wallpaper wallpaper = wallpapers.get(position);
		final ImageView image = ViewHolder.get(convertView, R.id.iv_wallpaper);
		final ImageView flag = ViewHolder.get(convertView, R.id.iv_current_flag);
		ImageView delete = ViewHolder.get(convertView, R.id.iv_delete);
		WallpaperUtils.showWallpaper(context, image, wallpaper);
		if(wallpaper.isPreSet()) {
			delete.setVisibility(View.GONE);
		} else {
			delete.setVisibility(View.VISIBLE);
		}
		if(wallpaper.isCurrent()) {
			flag.setVisibility(View.VISIBLE);
		} else {
			flag.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(wallpaper.isCurrent()) {
	                YoYo.with(Techniques.Wobble).duration(400).playOn(flag);
	                return;
				}
				mDao.setCurrent(wallpaper.getId());
				listener.onWallpaperChanged();
			}
		});
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getString(R.string.confirm_delete))
                .setContentText(context.getString(R.string.confirm_delete_des))
                .setCancelText(context.getString(R.string.confirm_delete_cancel))
                .setConfirmText(context.getString(R.string.confirm_delete_ok))
                .showCancelButton(true)
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
        				mDao.delete(wallpaper.getId());
        				listener.onWallpaperDelete();
                        sDialog.setTitleText(context.getString(R.string.confirm_delete_success))
                                .setContentText(context.getString(R.string.confirm_delete_success_des))
                                .setConfirmText(context.getString(android.R.string.ok))
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
				
			}
		});
		return convertView;
	}

	public static interface OnWallpaperListener {
		public void onWallpaperDelete();
		public void onWallpaperChanged();
	}
}
