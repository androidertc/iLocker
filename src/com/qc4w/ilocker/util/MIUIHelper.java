package com.qc4w.ilocker.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;

public class MIUIHelper {

	private static final String TAG = MIUIHelper.class.getSimpleName();

	public static final int AutoRunFlag = 0x40000000;

	public static final int FLAG_SHOW_FLOATING_WINDOW = 1 << 27;

	public static final String ADD_WINDOWMANAGER_VER = "3.3.29";

	/**
	 * 判断是不是自启动被 MIUI V5 干掉了
	 * 
	 * @param info
	 * @return 返回 true 是被干掉了 返回 false 没有被干掉
	 */
	public static boolean disableAutoStart(Context context) {
		if (!isMiuiV5()) {
			return false;
		}

		ApplicationInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo;
			if (hasAddToSystem(info)) {
				return false;
			}
			if (((AutoRunFlag & info.flags) != 0)) {
				return true;
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
		return false;
	}

	/**
	 * 判断是不是弹出 WINDOW 被干掉了
	 * 
	 * @param info
	 * @return
	 */
	public static boolean disableWindowAlter(Context context) {
		if (!isMiuiV5()) {
			return false;
		}
		ApplicationInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).applicationInfo;
			if (((FLAG_SHOW_FLOATING_WINDOW & info.flags) == 0)) {
				return true;
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
		return false;
	}

	/**
	 * 判断是否是被加到系统中了
	 * 
	 * @param info
	 * @return
	 */
	public static boolean hasAddToSystem(ApplicationInfo info) {
		boolean i = false;
		if ((0x1 & info.flags) != 0) {
			i = true;
		}
		return i;
	}

	public static boolean isMiuiV5() {
		boolean result = false;
		String ver = SystemProperties.get("ro.miui.ui.version.name", "unkonw");
		if (ver.equals("V5") || ver.equals("V6")) {
			if (hasAddWindowManager()) {
				result = true;
			}
		}
		return result;
	}

	public static boolean isMiui() {

		try {

			String s = Build.DISPLAY;
			if (s != null) {
				if (s.toUpperCase().contains("MIUI")) {
					return true;
				}
			}

			s = Build.MODEL; // 小米
			if (s != null) {
				if (s.contains("MI-ONE")) {
					return true;
				}
			}

			s = Build.DEVICE;
			if (s != null) {
				if (s.contains("mione")) {
					return true;
				}
			}

			s = Build.MANUFACTURER;
			if (s != null) {
				if (s.equalsIgnoreCase("Xiaomi")) {
					return true;
				}
			}

			s = Build.PRODUCT;
			if (s != null) {
				if (s.contains("mione")) {
					return true;
				}
			}

			String ver = SystemProperties.get("ro.miui.ui.version.name",
					"unkonw");
			if ("V5".equals(ver) || "V6".equals(ver)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 是否添加了浮动框管理
	 * 
	 * @return
	 */
	public static boolean hasAddWindowManager() {
		boolean result = true;
		try {
			String ver = SystemProperties.get("ro.build.version.incremental", "unkonw");
			if (ver.startsWith("JLB")) {
				if (Float.valueOf(ver.substring(3, ver.length())) < 22.0F) {
					result = false;
				}
			} else if (ver.startsWith("JX")) {
				result = true;

			} else if (ver.startsWith("JH")) {
				result = true;
			} else if (ver.startsWith("JMA")) {
				if (Float.valueOf(ver.substring(7, ver.length())) < 1.0F) {
					result = false;
				}
			} else if (ver.startsWith("HBJ")) {
				result = true;
			} else if (ver.startsWith("KX")) {
				result = true;
			} else if (!ver.equals(ADD_WINDOWMANAGER_VER)) {
				String[] nVer = ver.split("\\.");
				String[] aVer = ADD_WINDOWMANAGER_VER.split("\\.");
				if (nVer.length > aVer.length) {
					result = true;
				} else if (nVer.length > aVer.length) {
					result = false;
				} else {
					for (int i = 0; i < nVer.length; i++) {
						int n = Integer.valueOf(nVer[i]);
						int a = Integer.valueOf(aVer[i]);
						if (n > a) {
							result = true;
							break;
						} else if (n < a) {
							result = false;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			result = false;
			LogUtils.e(TAG, e);
		}
		return result;

	}

	private static final String SCHEME = "package";

	public static void showInstalledAppDetails(Context context,
			String packageName) {
		try {
			Intent intent = new Intent();
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
			context.startActivity(intent);
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
	}

	public static void openAutoStart(Context context) {
		try {
			Intent intent = new Intent(
					"android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
			intent.addCategory("android.intent.category.DEFAULT");
			context.startActivity(intent);
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
	}
	
	/**
	   * 打开MIUI权限管理界面(MIUI v5, v6)
	   * @param context
	   */
	  public static void openMiuiPermissionActivity(Context context) {
		    Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
			String rom = SystemProperties.get("ro.miui.ui.version.name", "unkonw");
		    
		    if ("V5".equals(rom)) {
		    	openAppDetailActivity(context, context.getPackageName());
		    } else if ("V6".equals(rom)) {
		        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
		        intent.putExtra("extra_pkgname", context.getPackageName());
		    }
		    
		    if (isIntentAvailable(context, intent)) {
			    if (context instanceof Activity) {
			    	Activity a = (Activity) context;
			        a.startActivityForResult(intent, 2);
			    }
		    } else {

		    }
	  }
	  
	  public static void openMiuiAutostartActivity(Context context) {
		  Intent intent = new Intent();
		  String rom = SystemProperties.get("ro.miui.ui.version.name", "unkonw");
		  if("V5".equals(rom)) {
			  // TODO 王天成自启动管理界面 V5
		  } else if("V6".equals(rom)) {
			  intent.setClassName("com.miui.permcenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
			  if(isIntentAvailable(context, intent)) {
				  context.startActivity(intent);
			  }
		  }
	  }
	  
	  public static void openAppDetailActivity(Context context, String packageName) {
	    Intent intent = null;
	      intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
	      Uri uri = Uri.fromParts("package:", packageName, null);
	      intent.setData(uri);
	    if (isIntentAvailable(context, intent)) {
	      context.startActivity(intent);
	    } else {
	    }
	  }
	  
     public static boolean isIntentAvailable(Context context, Intent intent) {
	      if (intent == null) return false;
	      return context.getPackageManager().queryIntentActivities(intent, PackageManager.GET_ACTIVITIES).size() > 0;
     }
}
