package com.qc4w.ilocker.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.db.NotificationAppDAO;
import com.qc4w.ilocker.domain.NotificationApp;
import com.qc4w.ilocker.util.PackageImageLoader;

public class NotificationAppAdapter extends BaseAdapter {
	
	private List<NotificationApp> apps;
	private LayoutInflater inflater;
	private PackageImageLoader loader;
	private NotificationAppDAO dao;
	
	public NotificationAppAdapter(Context context, List<NotificationApp> apps) {
		this.apps = apps;
		inflater = LayoutInflater.from(context);
		loader = PackageImageLoader.getInstance(context, R.drawable.ic_launcher);
		dao = NotificationAppDAO.getInstance(context);
	}

	@Override
	public int getCount() {
		return apps.size();
	}

	@Override
	public Object getItem(int position) {
		return apps.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_notification_app, parent, false);
			holder = new ViewHolder();
			holder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
			holder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
			holder.sb_notification_enable = (SwitchButton) convertView.findViewById(R.id.sb_notification_enable);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		} 
		final NotificationApp app = apps.get(position);
		loader.display(holder.iv_app_icon, app.getPkgName());
		holder.tv_app_name.setText(app.getLabel());
		holder.sb_notification_enable.setChecked(app.isShowNotification());
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				app.setShowNotification(!app.isShowNotification());
				if(app.isShowNotification()) {
					dao.add(app);
				} else {
					dao.delete(app.getPkgName());
				}
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	private class ViewHolder {
		ImageView iv_app_icon;
		TextView tv_app_name;
		SwitchButton sb_notification_enable;
	}
}
