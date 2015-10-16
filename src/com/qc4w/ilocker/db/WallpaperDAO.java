package com.qc4w.ilocker.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qc4w.ilocker.domain.Wallpaper;
import com.qc4w.ilocker.util.LogUtils;

public class WallpaperDAO {
	
	public static final String TAG = WallpaperDAO.class.getSimpleName();
	
	private static WallpaperDAO instance;

	private ILockerDBHelper mDBHelper;
	
	private WallpaperDAO(Context context) {
		mDBHelper = ILockerDBHelper.getInstance(context);
	}
	
	public static WallpaperDAO getInstance(Context context) {
		if(instance == null) {
			synchronized (WallpaperDAO.class) {
				if(instance == null) {
					instance = new WallpaperDAO(context);
				}
			}
		}
		return instance;
	}
	
	public Wallpaper getById(int id) {
		Wallpaper wallpaper = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try{
			db = mDBHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT * FROM " + WallpaperConstants.TABLE_NAME + " WHERE " + WallpaperConstants.ID + " = ?" , new String[] {String.valueOf(id)});
			if(cursor != null) {
				if(cursor.moveToFirst()) {
					wallpaper = new Wallpaper();
					wallpaper.setId(cursor.getInt(cursor.getColumnIndexOrThrow(WallpaperConstants.ID)));
					wallpaper.setPath(cursor.getString(cursor.getColumnIndexOrThrow(WallpaperConstants.PATH)));
					wallpaper.setPreSet(cursor.getInt(cursor.getColumnIndex(WallpaperConstants.IS_PRE_SET)) == 1 ? true : false);
					wallpaper.setCurrent(cursor.getInt(cursor.getColumnIndex(WallpaperConstants.IS_CURRENT)) == 1 ? true : false);
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
				LogUtils.e(TAG, e2);
			}
			if(db != null) {
				db.close();
			}
		}
		return wallpaper;
	}
	
	public Wallpaper getCurrent() {
		Wallpaper wallpaper = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try{
			db = mDBHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT * FROM " + WallpaperConstants.TABLE_NAME + " WHERE " + WallpaperConstants.IS_CURRENT + " = ?" , new String[] {String.valueOf(1)});
			if(cursor != null) {
				if(cursor.moveToFirst()) {
					wallpaper = new Wallpaper();
					wallpaper.setId(cursor.getInt(cursor.getColumnIndexOrThrow(WallpaperConstants.ID)));
					wallpaper.setPath(cursor.getString(cursor.getColumnIndexOrThrow(WallpaperConstants.PATH)));
					wallpaper.setPreSet(cursor.getInt(cursor.getColumnIndex(WallpaperConstants.IS_PRE_SET)) == 1 ? true : false);
					wallpaper.setCurrent(cursor.getInt(cursor.getColumnIndex(WallpaperConstants.IS_CURRENT)) == 1 ? true : false);
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
				LogUtils.e(TAG, e2);
			}
			if(db != null) {
				db.close();
			}
		}
		return wallpaper;
	
	}
	
	public long add(Wallpaper wallpaper) {
		SQLiteDatabase db = null;
		long rowId = -1;
		try {
			db = mDBHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(WallpaperConstants.PATH, wallpaper.getPath());
			values.put(WallpaperConstants.IS_PRE_SET, wallpaper.isPreSet());
			values.put(WallpaperConstants.IS_CURRENT, wallpaper.isCurrent());
			rowId = db.insert(WallpaperConstants.TABLE_NAME, WallpaperConstants.ID, values);
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
		return rowId;
	}
	
	public List<Wallpaper> getAll() {
		List<Wallpaper> wallpapers = new ArrayList<Wallpaper>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try{
			db = mDBHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT * FROM " + WallpaperConstants.TABLE_NAME, null);
			if(cursor != null) {
				while(cursor.moveToNext()) {
					Wallpaper paper = new Wallpaper();
					paper.setId(cursor.getInt(cursor.getColumnIndexOrThrow(WallpaperConstants.ID)));
					paper.setPath(cursor.getString(cursor.getColumnIndexOrThrow(WallpaperConstants.PATH)));
					paper.setPreSet(cursor.getInt(cursor.getColumnIndex(WallpaperConstants.IS_PRE_SET)) == 1 ? true : false);
					paper.setCurrent(cursor.getInt(cursor.getColumnIndex(WallpaperConstants.IS_CURRENT)) == 1 ? true : false);
					wallpapers.add(paper);
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
				LogUtils.e(TAG, e2);
			}
			if(db != null) {
				db.close();
			}
		}
		return wallpapers;
	}
	
	public int delete(int id) {
		SQLiteDatabase db = null;
		int num = 0;
		try {
			db = mDBHelper.getWritableDatabase();
			num = db.delete(WallpaperConstants.TABLE_NAME, WallpaperConstants.ID + " = ?", new String[] {String.valueOf(id)});
			
			if(getCurrent() == null) {
				setCurrent(1);
			}
			
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
		return num;
	}
	
	public int update(Wallpaper paper) {
		SQLiteDatabase db = null;
		int num = 0;
		try{
			db = mDBHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(WallpaperConstants.PATH, paper.getPath());
			values.put(WallpaperConstants.IS_PRE_SET, paper.isPreSet());
			values.put(WallpaperConstants.IS_CURRENT, paper.isCurrent());
			num = db.update(WallpaperConstants.TABLE_NAME, values, WallpaperConstants.ID + " = ?", new String[] {String.valueOf(paper.getId())});
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		} finally {
			if(db != null) {
				db.close();
			}
		}
		return num;
	}
	
	public void cancelCurrent() {
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getWritableDatabase();
            db.execSQL("UPDATE " + WallpaperConstants.TABLE_NAME + " SET " + WallpaperConstants.IS_CURRENT + " = ?", new String[]{String.valueOf(0)});
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    
	}
	
    public synchronized void setCurrent(int id) {
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getWritableDatabase();
            db.execSQL("UPDATE " + WallpaperConstants.TABLE_NAME + " SET " + WallpaperConstants.IS_CURRENT + " = ?", new String[]{String.valueOf(0)});
            db.execSQL("UPDATE " + WallpaperConstants.TABLE_NAME + " SET " + WallpaperConstants.IS_CURRENT + " = ? WHERE " + WallpaperConstants.ID + " = " + id, new String[]{String.valueOf(1)});
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

	public void initWallpaper(Context context) {
		try {
			String[] list = context.getAssets().list("wallpapers");
			for (int i = 0; i < list.length; i++) {
				Wallpaper paper = new Wallpaper();
				paper.setPath("file:///android_asset/wallpapers/" + list[i]);
				paper.setPreSet(true);
				paper.setCurrent(i == 0);
				add(paper);
			}
		} catch (IOException e) {
			LogUtils.e(TAG, e);
		}
	}
}
