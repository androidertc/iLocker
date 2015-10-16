package com.qc4w.ilocker.util;

import java.util.Collections;
import java.util.List;

import com.qc4w.ilocker.db.NotificationAppDAO;
import com.qc4w.ilocker.domain.NotificationApp;

import android.content.Context;
import android.os.AsyncTask;

public class NotificationAppLoadTask extends AsyncTask<Void, Void, List<NotificationApp>> {

	private Context context;
	private NotificationAppDAO dao;
	private OnLoadListener listener;
	
	public NotificationAppLoadTask(Context context, OnLoadListener listener) {
		this.context = context;
		this.listener = listener;
		dao = NotificationAppDAO.getInstance(context);
	}
	
	@Override
	protected List<NotificationApp> doInBackground(Void... params) {
		List<NotificationApp> apps = AppUtils.getAllActivityApps(context);
		for (NotificationApp app : apps) {
			app.setHasActivity(true);
			app.setShowNotification(dao.isAppExists(app.getPkgName()));
		}
		Collections.sort(apps, UIUtils.NOTIFICATION_APP_COMPARATOR);
		return apps;
	}

	protected void onPostExecute(List<NotificationApp> result) {
		listener.onLoadCompleted(result);
	};
	
	public interface OnLoadListener {
		public void onLoadCompleted(List<NotificationApp> apps);
	}
}
