package com.qc4w.ilocker.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.PreViewActivity;
import com.qc4w.ilocker.util.ViewHolder;

public class LockerScreenAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private ConfigManager mConfig;

	private Context mContext;

	public LockerScreenAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mConfig = ConfigManager.getInstance(context);
		mContext = context;
	}

	private int[] images = new int[] { R.drawable.unlock_slide,
			R.drawable.unlock_mylover, R.drawable.unlock_myname/*, R.drawable.unlock_myname*/};

	private int[] names = new int[] { R.string.label_lock_type_slide,
			R.string.label_lock_type_mylover, R.string.label_lock_type_myname/*, R.string.label_lock_type_mood*/};

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return names[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_lock_type_item, parent, false);
		}
		ImageView ivLockType = ViewHolder.get(convertView, R.id.iv_lock_type);
		final ImageView current = ViewHolder.get(convertView, R.id.iv_current_flag);
		TextView tvLockType = ViewHolder.get(convertView, R.id.tv_lock_type_name);
		tvLockType.setText(names[position]);
		ivLockType.setImageResource(images[position]);
		if (mConfig.getLockerScreenType() == position) {
			current.setVisibility(View.VISIBLE);
		} else {
			current.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PreViewActivity.class);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, PreViewActivity.PREVIEW_TYPE_LOCKER_SCREEN);
				intent.putExtra(Constants.StringConstant.PARAMS_DATA1, position);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

}
