package com.qc4w.ilocker.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class IconUtils {

	public static Drawable getIcon(Context context, String pkgName, int id) {
		try {
			Context targetContext = context.createPackageContext(pkgName, Context.CONTEXT_IGNORE_SECURITY);
			if(targetContext != null) {
				
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
