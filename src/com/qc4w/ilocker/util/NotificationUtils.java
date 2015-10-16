package com.qc4w.ilocker.util;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.SplashActivity;

public class NotificationUtils {
	
	private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
	private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
	private static NotificationUtils instance;
	
	private NotificationManager manager;
	private Context context;
	private ConfigManager config;
	
	private NotificationUtils(Context context) {
		this.context = context;
		config = ConfigManager.getInstance(context);
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public static NotificationUtils getInstance(Context context) {
		if(instance == null) {
			synchronized (NotificationUtils.class) {
				if(instance == null) {
					instance = new NotificationUtils(context);
				}
			}
		}
		return instance;
	}
	
	public void sendMainNotification() {
		NotificationCompat.Builder builder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(context.getString(R.string.app_name))
		        .setOngoing(true)
		        .setContentText(context.getString(R.string.notification_content));
		if(!config.isNotificationIconShown()) {
			builder.setPriority(NotificationCompat.PRIORITY_MIN);
		}
		Intent resultIntent = new Intent(context, SplashActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(SplashActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);
		manager.notify(Constants.NotificationId.NOTIFICATION_ID_MAIN, builder.build());
	}
	
	public void cancelNotification(int id) {
		manager.cancel(id);
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static boolean isNotificationListenerEnabled(Context context) {
	    String pkgName = context.getPackageName();
	    final String flag = Settings.Secure.getString(context.getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
	    if (!TextUtils.isEmpty(flag)) {
	        final String[] names = flag.split(":");
	        for (int i = 0; i < names.length; i++) {
	            final ComponentName cn = ComponentName.unflattenFromString(names[i]);
	            if (cn != null) {
	                if (TextUtils.equals(pkgName, cn.getPackageName())) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static void openNotificationAccess(Context context) {
		context.startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
	}
}
