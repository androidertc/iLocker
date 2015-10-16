package com.qc4w.ilocker.db;

public class NotificationAppConstants {

	public static final String TABLE_NAME = "notification_apps";

	public static final String ID = "_id";
	public static final String PACKAGE_NAME = "package_name";
	public static final String SHOW_NOTIFICATION = "show_notification";
	
	public static final String create_table_sql = String.format(
			"CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s BOOLEAN)"
			, TABLE_NAME, ID, PACKAGE_NAME, SHOW_NOTIFICATION);

}
