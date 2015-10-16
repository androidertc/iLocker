package com.qc4w.ilocker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.view.CircleColorView;

public class ColorPickerAdapter extends BaseAdapter {
	
	private int[] colors;
	private LayoutInflater inflater;
	
	private int defColor;

	private OnColorSelectedListener listener;
	public ColorPickerAdapter(int[] colors, Context context, int defColor, OnColorSelectedListener listener) {
		this.listener = listener;
		this.colors = colors;
		inflater = LayoutInflater.from(context);
		this.defColor = defColor;
	}
	
	public void setDefColor(int color) {
		this.defColor = color;
		notifyDataSetChanged();
	}
	
	public int getDefColor() {
		return defColor;
	}

	@Override
	public int getCount() {
		return colors.length;
	}

	@Override
	public Object getItem(int position) {
		return colors[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_color_picker, parent, false);
			holder = new ViewHolder();
			holder.ccv = (CircleColorView) convertView.findViewById(R.id.circle_color_view);
			holder.ivCurrentFlag = (ImageView) convertView.findViewById(R.id.iv_current_flag);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ccv.setColor(colors[position]);
		if(defColor == colors[position]) {
			holder.ivCurrentFlag.setVisibility(View.VISIBLE);
		} else {
			holder.ivCurrentFlag.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				defColor = colors[position];
				notifyDataSetChanged();
				if(listener != null) {
					listener.onColorSelected(defColor);
				}
			}
		});
		return convertView;
	}

	private class ViewHolder {
		CircleColorView ccv;
		ImageView ivCurrentFlag;
	}
	
	public interface OnColorSelectedListener {
		public void onColorSelected(int color);
	}
}
