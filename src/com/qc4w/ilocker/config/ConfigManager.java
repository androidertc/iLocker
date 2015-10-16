package com.qc4w.ilocker.config;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.lock.LockerManager;
import com.qc4w.ilocker.util.DateFormatUtils;

public class ConfigManager {
	
	private static ConfigManager instance;
	
	private SharedPreferences mPreferences;
	
	private Context mContext;
	
	private ConfigManager(Context context) {
		mPreferences = context.getSharedPreferences(ConfigConstant.CONFIG_FILE_NAME, getModel());
		mContext = context;
	}
	
	public static ConfigManager getInstance(Context context) {
		if(instance == null) {
			synchronized (ConfigManager.class) {
				if(instance == null) {
					instance = new ConfigManager(context);
				}
			}
		}
		return instance;
	}
	
	public boolean isMIUISettingsTip() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_MIUI_SETTINGS_TIP, false);
	}
	
	public void setMIUISettingsTip(boolean tip) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_MIUI_SETTINGS_TIP, tip).commit();
	}
	
	public boolean isServiceEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_SERVICE_ENABLE, true);
	}
	
	public void setServiceEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_SERVICE_ENABLE, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isWallpaperInited() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WALLPAPER_INITED, false);
	}
	
	public void setWallpaperInited(boolean isInited) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WALLPAPER_INITED, isInited).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getLockerScreenType() {
		return mPreferences.getInt(ConfigConstant.CONFIG_LOCKER_SCREEN_TYPE, Constants.LockerScreen.LOCK_SCREEN_SIMPLE);
	}
	
	public void setLockerScreenType(int type) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_LOCKER_SCREEN_TYPE, type).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getLockerModeType() {
		return mPreferences.getInt(ConfigConstant.CONFIG_LOCKER_MODE_TYPE, Constants.LockerMode.LOCK_MODE_NONE);
	}
	
	public void setLockerModeType(int type) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_LOCKER_MODE_TYPE, type).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getSlideUnlockText() {
		return mPreferences.getString(ConfigConstant.CONFIG_SLIDE_UNLOCK_TEXT, mContext.getString(R.string.slide_to_unlock_string));
	}
	
	public void setSlideUnlockText(String text) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_SLIDE_UNLOCK_TEXT, text).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getSlideUnlockTextColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_SLIDE_UNLOCK_TEXT_COLOR, Color.WHITE);
	}
	
	public void setSlideUnlockTextColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_SLIDE_UNLOCK_TEXT_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getLoversUnlockText() {
		return mPreferences.getString(ConfigConstant.CONFIG_LOVERS_UNLOCK_TEXT, mContext.getString(R.string.slide_to_unlock_string));
	}
	
	public void setLoversUnlockText(String text) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_LOVERS_UNLOCK_TEXT, text).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getLoversImagesText() {
		return mPreferences.getString(ConfigConstant.CONFIG_LOVERS_IMAGES_TEXT, mContext.getString(R.string.label_lock_type_mylover));
	}
	
	public void setLoversImageText(String text) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_LOVERS_IMAGES_TEXT, text).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getLoversUnlockTextColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_LOVERS_UNLOCK_TEXT_COLOR, Color.WHITE);
	}
	
	public void setLoversUnlockTextColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_LOVERS_UNLOCK_TEXT_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getLoversImagesTextColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_LOVERS_IMAGES_TEXT_COLOR, Color.WHITE);
	}
	
	public void setLoversImagesTextColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_LOVERS_IMAGES_TEXT_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isNotificationIconShown() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_SHOW_NOTIFICATION_ICON, false);
	}
	
	public void setNotificationIconShown(boolean shown) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_SHOW_NOTIFICATION_ICON, shown).commit();
	}
	
	public void setScreenHeight(int height) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_SCREEN_HEIGHT, height).commit();
	}
	
	public int getScreenHeight() {
		return mPreferences.getInt(ConfigConstant.CONFIG_SCREEN_HEIGHT, Constants.CommonValue.INVALID_VALUE);
	}
	
	public void setScreenWidth(int width) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_SCREEN_WIDTH, width).commit();
	}
	
	public int getScreenWidth() {
		return mPreferences.getInt(ConfigConstant.CONFIG_SCREEN_WIDTH, Constants.CommonValue.INVALID_VALUE);
	}
	
	public String getDigitalPassword() {
		return mPreferences.getString(ConfigConstant.CONFIG_DIGITAL_PASSWORD, null);
	}
	
	public void setDigitalPassword(String password) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_DIGITAL_PASSWORD, password).commit();
	}
	
	public String getPatternPassword() {
		return mPreferences.getString(ConfigConstant.CONFIG_PATTERN_PASSWORD, null);
	}
	
	public void setPatternPassword(String password) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_PATTERN_PASSWORD, password).commit();
	}
	
	public void setDigitalColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_DIGITAL_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getDigitalColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_DIGITAL_COLOR, Color.WHITE);
	}
	
	public void setDigitalContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_DIGITAL_CONTOUR, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getDigitalContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_DIGITAL_CONTOUR, 0);
	}
	
	public void setNameUnlockText(String text) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_NAME_UNLOCK_TEXT, text).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getNameUnlockText() {
		return mPreferences.getString(ConfigConstant.CONFIG_NAME_UNLOCK_TEXT, mContext.getString(R.string.slide_to_unlock_string));
	}
	
	public void setNameImageText(String text) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_NAME_IMAGE_TEXT, text).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getNameImageText() {
		return mPreferences.getString(ConfigConstant.CONFIG_NAME_IMAGE_TEXT, mContext.getString(R.string.label_lock_type_myname));
	}
	
	public void setNameUnlockTextColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_NAME_UNLOCK_TEXT_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getNameUnlockTextColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_NAME_UNLOCK_TEXT_COLOR, Color.WHITE);
	}
	
	public void setNameImageTextColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_NAME_IMAGE_TEXT_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getNameImageTextColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_NAME_IMAGE_TEXT_COLOR, Color.WHITE);
	}
	
	public void setNameImageContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_NAME_IMAGE_CONTOUR, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getNameImageContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_NAME_IMAGE_CONTOUR, 0);
	}
	
	public void setPatternColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_PATTERN_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getDPictureContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_DPICTURE_CONTOUR, 0);
	}
	
	public void setDPictureContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_DPICTURE_CONTOUR, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getDPictureColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_DPICTURE_COLOR, Color.WHITE);
	}
	
	public void setDPictureColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_DPICTURE_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getPPictureContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_PPICTURE_CONTOUR, 0);
	}
	
	public void setPPictureContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_PPICTURE_CONTOUR, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getPPictureColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_PPICTURE_COLOR, Color.WHITE);
	}
	
	public void setPPictureColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_PPICTURE_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getLPictureContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_LPICTURE_CONTOUR, 0);
	}
	
	public void setLPictureContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_LPICTURE_CONTOUR, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getRingColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_RING_COLOR, Color.WHITE);
	}
	
	public void setRingColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_RING_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getRingContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_RING_CONTOUR, 0);
	}
	
	public void setRingContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_RING_CONTOUR, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getLPictureColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_LPICTURE_COLOR, Color.WHITE);
	}
	
	public void setLPictureColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_LPICTURE_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getPatternColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_PATTERN_COLOR, Color.WHITE);
	}
	
	public int getCPatternNormalContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_CPATTERN_CONTOUR_NORMAL, Constants.CommonValue.INVALID_VALUE);
	}
	
	public void setCPatternNormalContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_CPATTERN_CONTOUR_NORMAL, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getCPatternTouchedContour() {
		return mPreferences.getInt(ConfigConstant.CONFIG_CPATTERN_CONTOUR_TOUCHED, Constants.CommonValue.INVALID_VALUE);
	}
	
	public void setCPatternTouchedContour(int index) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_CPATTERN_CONTOUR_TOUCHED, index).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isWidgetTimeEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WIDGET_TIME, true);
	}
	
	public void setWidgetTimeEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WIDGET_TIME, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isWidgetSentenceEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WIDGET_SENTENCE, true);
	}

	public void setWidgetSentenceEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WIDGET_SENTENCE, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}

	public boolean isWidgetCountdownEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WIDGET_COUNTDOWN, true);
	}
	
	public void setWidgetCountdownEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WIDGET_COUNTDOWN, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isWidgetTimingEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WIDGET_TIMING, true);
	}
	
	public void setWidgetTimingEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WIDGET_TIMING, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isWidgetNotificationEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WIDGET_NOTIFICATION, true);
	}
	
	public void setWidgetNotificationEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WIDGET_NOTIFICATION, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public boolean isWidgetCameraEnable() {
		return mPreferences.getBoolean(ConfigConstant.CONFIG_WIDGET_CAMERA, true);
	}
	
	public void setWidgetCameraEnable(boolean enable) {
		mPreferences.edit().putBoolean(ConfigConstant.CONFIG_WIDGET_CAMERA, enable).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getSentenceContent() {
		return mPreferences.getString(ConfigConstant.CONFIG_WIDGET_SENTENCE_CONTENT, mContext.getString(R.string.widget_sentence_content_default));
	}
	
	public void setSentenceContnet(String content) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_WIDGET_SENTENCE_CONTENT, content).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getCountdownContent() {
		return mPreferences.getString(ConfigConstant.CONFIG_WIDGET_COUNTDOWN_CONTENT, mContext.getString(R.string.widget_countdown_content_default));
	}
	
	public void setCountdownContent(String content) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_WIDGET_COUNTDOWN_CONTENT, content).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public long getCountdownTimestamp() {
		return mPreferences.getLong(ConfigConstant.CONFIG_WIDGET_COUNTDOWN_TIMESTAMP, DateFormatUtils.getNextDay());
	}
	
	public void setCountdownTimestamp(long timestamp) {
		mPreferences.edit().putLong(ConfigConstant.CONFIG_WIDGET_COUNTDOWN_TIMESTAMP, timestamp).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public String getTimingContent() {
		return mPreferences.getString(ConfigConstant.CONFIG_WIDGET_TIMING_CONTENT, mContext.getString(R.string.widget_timing_content_default));
	}
	
	public void setTimingContent(String content) {
		mPreferences.edit().putString(ConfigConstant.CONFIG_WIDGET_TIMING_CONTENT, content).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public long getTimingTimestamp() {
		return mPreferences.getLong(ConfigConstant.CONFIG_WIDGET_TIMING_TIMESTAMP, DateFormatUtils.getToday());
	}
	
	public void setTimingTimestamp(long timestamp) {
		mPreferences.edit().putLong(ConfigConstant.CONFIG_WIDGET_TIMING_TIMESTAMP, timestamp).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getWidgetTimeColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_WIDGET_TIME_COLOR, Color.WHITE);
	}
	
	public void setWidgetTimeColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_WIDGET_TIME_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getWidgetSentenceColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_WIDGET_SENTENCE_COLOR, Color.WHITE);
	}
	
	public void setWidgetSentenceColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_WIDGET_SENTENCE_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getWidgetCountdownColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_WIDGET_COUNTDOWN_COLOR, Color.WHITE);
	}
	
	public void setWidgetCountdownColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_WIDGET_COUNTDOWN_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getWidgetTimingColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_WIDGET_TIMING_COLOR, Color.WHITE);
	}
	
	public void setWidgetTimingColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_WIDGET_TIMING_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public int getWidgetCameraColor() {
		return mPreferences.getInt(ConfigConstant.CONFIG_WIDGET_CAMERA_COLOR, Color.WHITE);
	}
	
	public void setWidgetCameraColor(int color) {
		mPreferences.edit().putInt(ConfigConstant.CONFIG_WIDGET_CAMERA_COLOR, color).commit();
		LockerManager.getInstance(mContext).onConfigChanged();
	}
	
	public static int getModel() {//after 2.3 use MODE_MULTI_PROCESS(0x0004)
		return Build.VERSION_CODES.FROYO < Build.VERSION.SDK_INT ? 4 : Context.MODE_PRIVATE;
	}
	
}
