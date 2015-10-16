package com.qc4w.ilocker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ILockerDBHelper extends SQLiteOpenHelper {
	
	private static final int DB_VERSION = 2;
	private static final String DB_NAME = "ilocker.db";
	
	private static ILockerDBHelper instance;
	
	
	public static ILockerDBHelper getInstance(Context context) {
		if(instance == null) {
			synchronized (ILockerDBHelper.class) {
				if(instance == null) {
					instance = new ILockerDBHelper(context);
				}
			}
		}
		return instance;
	}

	private ILockerDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(WallpaperConstants.create_table_sql);
		db.execSQL(NotificationAppConstants.create_table_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion == 1) {
			db.execSQL(NotificationAppConstants.create_table_sql);
		}
	}

}
