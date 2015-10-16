package com.qc4w.ilocker.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.domain.NotificationApp;
import com.qc4w.ilocker.ui.LockScreenActivity;
import com.qc4w.ilocker.ui.fragment.FragmentBase;

public class AppUtils {
	
	private static final String TAG = AppUtils.class.getSimpleName();
	
	public static void enterAccibility(FragmentBase context) {
		try {
			Intent intent =  new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);  
			context.startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_ACCESSIBILITY_SETTINGS);  
		} catch (Exception e) {
		}
	}
	
	public static String loadAppName(Context context, String pkgName) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
			return info.loadLabel(pm).toString();
		} catch (Exception e) {
		}
		return "";
	}
	
	// 启动短信应用
	public static void launchSms(Context context) {
		try {
			Uri smsToUri = Uri.parse("smsto:");  
			Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			context.startActivity(intent);
		} catch(Exception e) {
			LogUtils.e(TAG, e);
			Toast.makeText(context, R.string.message_not_found, Toast.LENGTH_LONG).show();
		}
	}

	// 启动拨号应用
	public static void launchDial(Context context) {
		try {
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			context.startActivity(intent);
		} catch(Exception e) {
			LogUtils.e(TAG, e);
			Toast.makeText(context, R.string.phone_not_found, Toast.LENGTH_LONG).show();
		}
	}

	// 启动相机应用
	public static void launchCamera(Context context) {
		try {
			Intent intent = new Intent();
			intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			context.startActivity(intent);
		} catch(Exception e) {
			LogUtils.e(TAG, e);
			Toast.makeText(context, R.string.camera_not_found, Toast.LENGTH_LONG).show();
		}
	}
	
	public static void feedback(Context context) {
		try {
			Intent data=new Intent(Intent.ACTION_SENDTO); 
			data.setData(Uri.parse("mailto:qc4wlocker@gmail.com")); 
			data.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.feedback)); 
//			data.putExtra(Intent.EXTRA_TEXT, "这是内容");
			context.startActivity(data); 
		} catch(Exception e) {
			LogUtils.e(TAG, e);
			Toast.makeText(context, R.string.email_not_found, Toast.LENGTH_LONG).show();
		}
	}

	private static final Object iHardwareService;
	private static final Method setFlashEnabledMethod;
	static {
		iHardwareService = getHardwareService();
		setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);
		if (iHardwareService == null) {
			LogUtils.v(TAG, "This device does supports control of a flashlight");
		} else {
			LogUtils.v(TAG, "This device does not support control of a flashlight");
		}
	}
	
	public static void openMarket(Context context, String packageName) {
		try {
			Uri localUri = Uri.parse("market://details?id=" + packageName);
			context.startActivity(new Intent(Intent.ACTION_VIEW, localUri));
		} catch (Exception e1) {
			Toast.makeText(context, R.string.no_market_founded, Toast.LENGTH_LONG).show();
		}
	}

	public static void enableFlashlight() {
		setFlashlight(true);
	}

	public static void disableFlashlight() {
		setFlashlight(false);
	}

	private static Object getHardwareService() {
		Class<?> serviceManagerClass = maybeForName("android.os.ServiceManager");
		if (serviceManagerClass == null) {
			return null;
		}

		Method getServiceMethod = maybeGetMethod(serviceManagerClass,
				"getService", String.class);
		if (getServiceMethod == null) {
			return null;
		}

		Object hardwareService = invoke(getServiceMethod, null, "hardware");
		if (hardwareService == null) {
			return null;
		}

		Class<?> iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub");
		if (iHardwareServiceStubClass == null) {
			return null;
		}

		Method asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass,
				"asInterface", IBinder.class);
		if (asInterfaceMethod == null) {
			return null;
		}

		return invoke(asInterfaceMethod, null, hardwareService);
	}

	private static Method getSetFlashEnabledMethod(Object iHardwareService) {
		if (iHardwareService == null) {
			return null;
		}
		Class<?> proxyClass = iHardwareService.getClass();
		return maybeGetMethod(proxyClass, "setFlashlightEnabled", boolean.class);
	}

	private static Class<?> maybeForName(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException cnfe) {
			// OK
			return null;
		} catch (RuntimeException re) {
			LogUtils.e(TAG, "Unexpected error while finding class " + name, re);
			return null;
		}
	}

	private static Method maybeGetMethod(Class<?> clazz, String name,
			Class<?>... argClasses) {
		try {
			return clazz.getMethod(name, argClasses);
		} catch (NoSuchMethodException nsme) {
			// OK
			return null;
		} catch (RuntimeException re) {
			LogUtils.e(TAG, "Unexpected error while finding method " + name, re);
			return null;
		}
	}

	private static Object invoke(Method method, Object instance, Object... args) {
		try {
			return method.invoke(instance, args);
		} catch (IllegalAccessException e) {
			LogUtils.e(TAG, "Unexpected error while invoking " + method, e);
			return null;
		} catch (InvocationTargetException e) {
			LogUtils.e(TAG, "Unexpected error while invoking " + method, e.getCause());
			return null;
		} catch (RuntimeException re) {
			LogUtils.e(TAG, "Unexpected error while invoking " + method, re);
			return null;
		}
	}
	
	private static void setFlashlight(boolean active) {
		if (iHardwareService != null) {
			invoke(setFlashEnabledMethod, iHardwareService, active);
		}
	}

    public static void createLockScreenShortCut(Context context) {
      Intent shortcutIntent = new Intent();
      //设置点击快捷方式时启动的Activity,因为是从Lanucher中启动，所以包名类名要写全。
      shortcutIntent.setComponent(new ComponentName(context.getPackageName(), LockScreenActivity.class.getName()));
      //设置启动的模式
      shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

      // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除
      shortcutIntent.setAction("com.qc4w.ilocker.action.LOCK_SCREEN");
      shortcutIntent.addCategory(Intent.CATEGORY_DEFAULT);
      Intent resultIntent = new Intent();
      //设置快捷方式图标
      resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher));
      resultIntent.putExtra("duplicate", false);
      //启动的Intent
      resultIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
      //设置快捷方式的名称
      resultIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.one_key_lock_screen));

      resultIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
      context.sendBroadcast(resultIntent);
  }
    
    public static List<NotificationApp> getAllInstallApps(Context context) {
    	List<NotificationApp> apps = new ArrayList<NotificationApp>();
		PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> infos = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo info : infos) {
			NotificationApp app = new NotificationApp();
			app.setLabel(info.loadLabel(pm).toString());
			app.setPkgName(info.packageName);
			apps.add(app);
		}
		return apps;
    }
    
    public static List<NotificationApp> getAllActivityApps(Context context) {
    	List<NotificationApp> apps = new ArrayList<NotificationApp>();
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
		for (ResolveInfo info : infos) {
			ApplicationInfo appInfo = info.activityInfo.applicationInfo;
			NotificationApp app = new NotificationApp();
			app.setLabel(appInfo.loadLabel(pm).toString());
			app.setPkgName(appInfo.packageName);
			apps.add(app);
		}
		return apps;
    }
    
    public static String getAppLable(Context context, String pkgName) {
    	try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
			return info.loadLabel(pm).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
