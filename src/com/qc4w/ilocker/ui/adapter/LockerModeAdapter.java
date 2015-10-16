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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.PreViewActivity;
import com.qc4w.ilocker.util.ViewHolder;

public class LockerModeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private ConfigManager mConfig;

	private Context mContext;

	public LockerModeAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mConfig = ConfigManager.getInstance(context);
		mContext = context;
	}

	private int[] images = new int[] { R.drawable.unlock_slide,
			R.drawable.unlock_ppicture, R.drawable.unlock_lpicture,
			R.drawable.unlock_dpicture, R.drawable.unlock_pattern,
			R.drawable.unlock_cpattern, R.drawable.unlock_digit, R.drawable.unlock_ring};

	private int[] names = new int[] { R.string.label_lock_type_none,
			R.string.label_lock_type_ppicture,
			R.string.label_lock_type_lpicture,
			R.string.label_lock_type_dpicture,
			R.string.label_lock_type_pattern,
			R.string.label_lock_type_cpattern, R.string.label_lock_type_digit, R.string.label_lock_type_ring };

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
			convertView = mInflater.inflate(R.layout.list_lock_type_item,
					parent, false);
		}
		ImageView ivLockType = ViewHolder.get(convertView, R.id.iv_lock_type);
		final ImageView current = ViewHolder.get(convertView, R.id.iv_current_flag);
		TextView tvLockType = ViewHolder.get(convertView, R.id.tv_lock_type_name);
		tvLockType.setText(names[position]);
		ivLockType.setImageResource(images[position]);
		if (mConfig.getLockerModeType() == position) {
			current.setVisibility(View.VISIBLE);
		} else {
			current.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(position == Constants.LockerMode.LOCK_MODE_NONE) {
					if(mConfig.getLockerModeType() == Constants.LockerMode.LOCK_MODE_NONE) {
						YoYo.with(Techniques.Wobble).duration(400).playOn(current);
					} else {
						mConfig.setLockerModeType(Constants.LockerMode.LOCK_MODE_NONE);
						notifyDataSetChanged();
					}
				} else {
					Intent intent = new Intent(mContext, PreViewActivity.class);
					intent.putExtra(Constants.StringConstant.PARAMS_DATA, PreViewActivity.PREVIEW_TYPE_LOCKER_MODE);
					intent.putExtra(Constants.StringConstant.PARAMS_DATA1, position);
					mContext.startActivity(intent);
				}
			}
		});
		return convertView;
	}

}
