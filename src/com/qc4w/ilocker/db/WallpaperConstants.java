package com.qc4w.ilocker.db;

public interface WallpaperConstants {

	public static final String TABLE_NAME = "wallpaper";

	public static final String ID = "_id";
	public static final String PATH = "path";
	public static final String IS_PRE_SET = "is_pre_set";
	public static final String IS_CURRENT = "is_current";
	
	public static final String create_table_sql = String.format(
			"CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s BOOLEAN, %s BOOLEAN)"
			, TABLE_NAME, ID, PATH, IS_PRE_SET, IS_CURRENT);
}
