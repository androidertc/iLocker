package com.qc4w.ilocker.ui.adapter;

import java.util.List;

import android.app.Notification;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.domain.NotificationWrapper;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.PackageImageLoader;

public class NotificationAdapter extends BaseAdapter {
	
	private List<NotificationWrapper> notifications;
	private LayoutInflater inflater;
	private Context context;
	private PackageImageLoader loader;
	public NotificationAdapter(Context context, List<NotificationWrapper> notifications) {
		this.notifications = notifications;
		this.context = context;
		inflater = LayoutInflater.from(context);
		loader = PackageImageLoader.getInstance(context, R.drawable.ic_launcher);
	}
	
	@Override
	public int getCount() {
		return notifications.size();
	}

	@Override
	public Object getItem(int position) {
		return notifications.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_notification, parent, false);
			holder = new ViewHolder();
			holder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
			holder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
			holder.tv_notification_content = (TextView) convertView.findViewById(R.id.tv_notification_content);
			holder.tv_notification_time = (TextView) convertView.findViewById(R.id.tv_notification_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Notification notification = notifications.get(position).getNotification();
		loader.display(holder.iv_app_icon, notification.contentIntent.getTargetPackage());
		holder.tv_app_name.setText(AppUtils.loadAppName(context, notification.contentIntent.getTargetPackage()));
		holder.tv_notification_content.setText(notification.tickerText);
		holder.tv_notification_time.setText(DateFormat.format("k:mm", notification.when).toString());
		return convertView;
	}

	private class ViewHolder {
		ImageView iv_app_icon;
		TextView tv_app_name;
		TextView tv_notification_content;
		TextView tv_notification_time;
	}
	
}
