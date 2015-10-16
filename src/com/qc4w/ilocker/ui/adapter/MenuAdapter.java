package com.qc4w.ilocker.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.util.ViewHolder;

public class MenuAdapter extends BaseAdapter {

	private int[] mImagesNormal = new int[] { R.drawable.ic_theme_normal, R.drawable.ic_wallpaper_normal, R.drawable.ic_remind_normal, R.drawable.ic_switch_normal, R.drawable.ic_about_normal };
	private int[] mImagesSelect = new int[] { R.drawable.ic_theme_select, R.drawable.ic_wallpaper_select, R.drawable.ic_remind_select, R.drawable.ic_switch_select, R.drawable.ic_about_select };;
	private String[] mMenus;
	private int mDefaultColor;
	private int mSelectColor;
	private int mCurrentPosition;
	private LayoutInflater mInflater;
	
	public MenuAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resources resources = context.getResources();
		mMenus = resources.getStringArray(R.array.main_menus);
		mDefaultColor = resources.getColor(R.color.drak_gray);
		mSelectColor = resources.getColor(R.color.app_main_color);
	}
	
	public void setSelectPosition(int position) {
		mCurrentPosition = position;
		notifyDataSetChanged();
	}
	
	public int getSelectPosition() {
		return mCurrentPosition;
	}
	
	@Override
	public int getCount() {
		return mMenus.length;
	}

	@Override
	public Object getItem(int position) {
		return mMenus[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_main_menu_item, null);
		}
		ImageView ivMenuItem = ViewHolder.get(convertView, R.id.iv_menu_item);
		TextView tvMenuItem = ViewHolder.get(convertView, R.id.tv_menu_item);
		if(position != mCurrentPosition) {
			ivMenuItem.setImageResource(mImagesNormal[position]);
			tvMenuItem.setText(mMenus[position]);
			tvMenuItem.setTextColor(mDefaultColor);
		} else {
			ivMenuItem.setImageResource(mImagesSelect[position]);
			tvMenuItem.setText(mMenus[position]);
			tvMenuItem.setTextColor(mSelectColor);
		}
		return convertView;
	}

}
