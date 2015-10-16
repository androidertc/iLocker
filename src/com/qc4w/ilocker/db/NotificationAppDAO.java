package com.qc4w.ilocker.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qc4w.ilocker.domain.NotificationApp;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.LogUtils;

public class NotificationAppDAO {
	private static final String TAG = NotificationAppDAO.class.getSimpleName();
	
	private static NotificationAppDAO instance;
	private SQLiteOpenHelper mDBHelper;
	private Context context;
	private NotificationAppDAO(Context context) {
		this.context = context;
		mDBHelper = ILockerDBHelper.getInstance(context);
	}

	public static NotificationAppDAO getInstance(Context context) {
		if(instance == null) {
			synchronized (NotificationAppDAO.class) {
				if(instance == null) {
					instance = new NotificationAppDAO(context);
				}
			}
		}
		return instance;
	}
	
	public void add(NotificationApp app) {
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(NotificationAppConstants.PACKAGE_NAME, app.getPkgName());
			values.put(NotificationAppConstants.SHOW_NOTIFICATION, 1);
			db.insert(NotificationAppConstants.TABLE_NAME, NotificationAppConstants.ID, values);
		} catch(Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
	}
	
	public void delete(int id) {
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getWritableDatabase();
			db.delete(NotificationAppConstants.TABLE_NAME, NotificationAppConstants.ID + " = ?", new String[] {String.valueOf(id)});
		} catch(Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
	}

	public void delete(String pkgName) {
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getWritableDatabase();
			db.delete(NotificationAppConstants.TABLE_NAME, NotificationAppConstants.PACKAGE_NAME + " = ?", new String[] {pkgName});
		} catch(Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
	}
	
	public void deleteAll() {
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getWritableDatabase();
			db.delete(NotificationAppConstants.TABLE_NAME, null, null);
		} catch(Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
	}
	
	public boolean isAppExists(String pkgName) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = mDBHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT * FROM " + NotificationAppConstants.TABLE_NAME + " WHERE " + NotificationAppConstants.PACKAGE_NAME + " = ?", new String[] {pkgName});
			if(cursor != null) {
				return cursor.getCount() != 0;
			}
		} catch(Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e2) {
			}
			if(db != null) {
				db.close();
			}
		}
		return false;
	}
	
	public List<NotificationApp> getAll() {
		List<NotificationApp> apps = new ArrayList<NotificationApp>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = mDBHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT * FROM " + NotificationAppConstants.TABLE_NAME, null);
			if(cursor != null) {
				while(cursor.moveToNext()) {
					NotificationApp app = new NotificationApp();
					app.setId(cursor.getInt(cursor.getColumnIndex(NotificationAppConstants.ID)));
					app.setPkgName(cursor.getString(cursor.getColumnIndex(NotificationAppConstants.PACKAGE_NAME)));
					app.setLabel(AppUtils.getAppLable(context, app.getPkgName()));
					app.setHasActivity(true);
					app.setShowNotification(true);
					apps.add(app);
				}
			}
		} catch(Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e2) {
			}
			if(db != null) {
				db.close();
			}
		}
		return apps;
	}
}
