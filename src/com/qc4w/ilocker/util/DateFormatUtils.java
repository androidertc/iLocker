package com.qc4w.ilocker.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.content.Context;

import com.qc4w.ilocker.R;

public class DateFormatUtils {

	private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	
	public static String getTimeString(Context context, long timeDiff) {
		long days = 0;
		String timeString = null;
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		if(timeDiff > AlarmManager.INTERVAL_DAY){
			days = timeDiff / AlarmManager.INTERVAL_DAY;
			timeString = formatter.format(timeDiff - days * AlarmManager.INTERVAL_DAY);
			return String.format(context.getString(R.string.time_format), days, timeString);
		}else{
			return formatter.format(timeDiff);
		}
	}
	
	public static long getNextDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTimeInMillis();
	}
	
	public static long getToday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTimeInMillis();
	}
	
}
