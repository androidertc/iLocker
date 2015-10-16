package com.qc4w.ilocker.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.qc4w.ilocker.domain.NotificationApp;

public class UIUtils {

    public static final Comparator<NotificationApp> NOTIFICATION_APP_COMPARATOR = new Comparator<NotificationApp>() {
        private final Collator sCollator = Collator.getInstance(Locale.getDefault());

        @Override
        public int compare(NotificationApp object1, NotificationApp object2) {
            String label1 = null == object1.getLabel() ? "" : object1.getLabel();
            String label2 = null == object2.getLabel() ? "" : object2.getLabel();
            if (!label1.equals(label2)) {
                return sCollator.compare(label1, label2);
            } else {
                return sCollator.compare(object1.getPkgName(), object2.getPkgName());
            }
        }
    };

	public static boolean hasNavBar (Resources resources) {
	    int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
	    if (id > 0)
	        return resources.getBoolean(id);
	    else
	        return false;
	}
	
	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
	 */
	public static int sp2px(Context context, float spValue) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources()
				.getDisplayMetrics()) + 0.5f);
	}
	
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources()
				.getDisplayMetrics()) + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 px 的单位 转成为 dip(像素)
	 */
	 public static int convertPxOrDip(Context context, int px) { 
	     float scale = context.getResources().getDisplayMetrics().density; 
	     return (int)(px/scale + 0.5f*(px>=0?1:-1)); 
	 }
	 
	public static Spanned setTextColorSpan(Context context, int formatText, int color, String text){
		String textColorSpan = "<font color='" + color + "'>" + text + "</font>";
		return Html.fromHtml(String.format(context.getResources().getString(formatText), textColorSpan));
	}
	

	@SuppressLint("NewApi")
	public static void changeActionBarHomeUpDrawable(Activity activity, int rid) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			activity.getActionBar().setHomeAsUpIndicator(rid);
		} else {
			Drawable homeUp = activity.getResources().getDrawable(rid);
			final View home = activity.findViewById(android.R.id.home);
			if (home == null) {
				return;
			}
			
			final ViewGroup parent = (ViewGroup) home.getParent();
			final int childCount = parent.getChildCount();
			if (childCount != 2) {
				return;
			}
			
			final View first = parent.getChildAt(0);
			final View second = parent.getChildAt(1);
			final View up = first.getId() == android.R.id.home ? second : first;
			
			if (up instanceof ImageView) {
				ImageView upIndicatorView = (ImageView) up;
				upIndicatorView.setImageDrawable(homeUp);
			}
		}
	}
	
	@SuppressLint("NewApi")
	public static void changeActionBarHomeUpDrawable(Activity activity) {
		DrawerArrowDrawable drawerArrow = new DrawerArrowDrawable(activity) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        drawerArrow.setProgress(1f);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			activity.getActionBar().setHomeAsUpIndicator(drawerArrow);
		} else {
			final View home = activity.findViewById(android.R.id.home);
			if (home == null) {
				return;
			}
			
			final ViewGroup parent = (ViewGroup) home.getParent();
			final int childCount = parent.getChildCount();
			if (childCount != 2) {
				return;
			}
			
			final View first = parent.getChildAt(0);
			final View second = parent.getChildAt(1);
			final View up = first.getId() == android.R.id.home ? second : first;
			
			if (up instanceof ImageView) {
				ImageView upIndicatorView = (ImageView) up;
				upIndicatorView.setImageDrawable(drawerArrow);
			}
		}
	}
	

	public static Drawable getGeneralActionBarBackground(Context context) {
		Drawable drawable = new ColorDrawable(0xFF0A7E07);
		return drawable;
		
	}
}
