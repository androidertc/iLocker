package com.qc4w.ilocker.constant;

public class Constants {
	
	public static class Path {
		public static final String WALLPAPER_NAME = "wallpaper.png";
		
		public static final String MALE_IMAGE_NAME = "male_image.png";
		public static final String FEMALE_IMAGE_NAME = "female_image.png";
		
		public static final String NAME_IMAGE_NAME = "name_image.png";
		
		public static final String DPICTURE_IMAGE_SUFFIX = "dpicture_image.png";
		public static final String PPICTURE_IMAGE_SUFFIX = "ppicture_image.png";
		public static final String LPICTURE_IMAGE_SUFFIX = "lpicture_image.png";
		public static final String RING_IMAGE_SUFFIX = "ring_image.png";
		public static final String WALLPAPER_IMAGE_SUFFIX = "wallpaper.png";
		
		public static final String CPATTERN_NORMAL_IMAGE_NAME = "cpattern_normal.png";
		public static final String CPATTERN_TOUCHED_IMAGE_NAME = "cpattern_touched.png";
	}

	public static class StringConstant {
		public static final String PARAMS_DATA = "params_data";
		public static final String PARAMS_DATA1 = "params_data1";
		public static final String PARAMS_DATA2 = "params_data2";
	}

	public static class LockerScreen {
		public static final int LOCK_SCREEN_SIMPLE = 0;
		public static final int LOCK_SCREEN_MYLOVER = 1;
		public static final int LOCK_SCREEN_MYNAME = 2;
		public static final int LOCK_SCREEN_MOOD = 3;
	}
	
	public static class LockerMode {
		public static final int LOCK_MODE_NONE = 0; // ÎÞ
		public static final int LOCK_MODE_PPICTURE = 1; // ¾ÅÍ¼Ëø 
		public static final int LOCK_MODE_LPICTURE = 2; // °®ÐÄËø
		public static final int LOCK_MODE_DPICTURE = 3; // ÕÕÆ¬Ëø
		public static final int LOCK_MODE_PATTERN = 4; // ÊÖÊÆËø
		public static final int LOCK_MODE_CPATTERN = 5; // ×Ô¶¨Òå
		public static final int LOCK_MODE_DIGIT = 6; // Êý×ÖÃÜÂëËø
		public static final int LOCK_MODE_RING = 7; // Êý×ÖÃÜÂëËø
	}
	
	public static class WidgetType {
		public static final int WIDGET_TYPE_TIME = 0x01;
		public static final int WIDGET_TYPE_SENTENCE = 0x02;
		public static final int WIDGET_TYPE_COUNTDOWN = 0x03;
		public static final int WIDGET_TYPE_TIMING = 0x04;
		public static final int WIDGET_TYPE_NOTIFICATION = 0x05;
		public static final int WIDGET_TYPE_CAMERA = 0x06;
	}
	
	public static class RequestCode {
		public static final int REQUEST_CODE_PICKER_CAMERA = 0x01;
		public static final int REQUEST_CODE_PICKER_ALBUM = 0x02;
		
		public static final int REQUEST_CODE_PICKER_MALE_IMAGE = 0x03;
		public static final int REQUEST_CODE_PICKER_FEMALE_IMAGE = 0x04;

		public static final int REQUEST_CODE_PICKER_NAME_IMAGE = 0x05;
		
		public static final int REQUEST_CODE_CHAGE_TEXT_TITLE = 0x06;
		public static final int REQUEST_CODE_CHAGE_TEXT_SUBTITLE = 0x07;
		
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE1 = 0x08;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE2 = 0x09;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE3 = 0x0A;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE4 = 0x0B;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE5 = 0x0C;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE6 = 0x0D;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE7 = 0x0E;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE8 = 0x0F;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE9 = 0x10;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE_CLEAR = 0x11;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE0 = 0x12;
		public static final int REQUEST_CODE_PICKER_DPICTURE_IMAGE_DEL = 0x13;

		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE1 = 0x14;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE2 = 0x15;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE3 = 0x16;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE4 = 0x17;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE5 = 0x18;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE6 = 0x19;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE7 = 0x1A;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE8 = 0x1B;
		public static final int REQUEST_CODE_PICKER_PPICTURE_IMAGE9 = 0x1C;
		
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE0 = 0x1D;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE1 = 0x1E;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE2 = 0x1F;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE3 = 0x20;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE4 = 0x21;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE5 = 0x22;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE6 = 0x23;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE7 = 0x24;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE8 = 0x25;
		public static final int REQUEST_CODE_PICKER_LPICTURE_IMAGE9 = 0x26;
		
		public static final int REQUEST_CODE_PICKER_WALLPAPER = 0x27;
		
		public static final int REQUEST_CODE_CREATE_DIGITAL_PASSWORD = 0x28;
		public static final int REQUEST_CODE_CREATE_PATTERN_PASSWORD = 0x29;
		
		public static final int REQUEST_CODE_PICKER_CPATTERN_NORMAL_IMAGE = 0x30;
		public static final int REQUEST_CODE_PICKER_CPATTERN_TOUCHED_IMAGE = 0x31;
		
		public static final int REQUEST_CODE_PICKER_RING_IMAGE0 = 0x32;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE1 = 0x33;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE2 = 0x34;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE3 = 0x35;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE4 = 0x36;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE5 = 0x37;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE6 = 0x38;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE7 = 0x39;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE8 = 0x40;
		public static final int REQUEST_CODE_PICKER_RING_IMAGE9 = 0x41;
		
		public static final int REQUEST_CODE_ACCESSIBILITY_SETTINGS = 0x42;
		
	}

	public static class NotificationId {
		public static final int NOTIFICATION_ID_MAIN = 0x01;
	}
	
	public static class CommonValue {
		public static final int INVALID_VALUE = -1;
		
		public static final int FIRST_YEAR = 1970;
		public static final int LAST_YEAR = 2037;
	}
	
}
