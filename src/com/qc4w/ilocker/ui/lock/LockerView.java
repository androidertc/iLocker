package com.qc4w.ilocker.ui.lock;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.view.ScrollerViewPager;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.BitmapUtils;
import com.qc4w.ilocker.util.WallpaperUtils;

public class LockerView implements OnClickListener {

	protected OnLockerListener mListener;
	protected Context mContext;

	private View mRootView;
	private ConfigManager mConfig;
	private WallpaperDAO mDao;
	private LayoutInflater mInflater;
	
	private ImageView bgImage;
	private ScrollerViewPager viewPager;
	private LockerViewAdapter lockerViewAdapter;
	
	private AbsLockerMode lockerMode;
	private AbsLockerScreen lockerScreen;
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public LockerView(Context context) {
		mContext = context;
		mConfig = ConfigManager.getInstance(context);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRootView = mInflater.inflate(R.layout.layout_locker_view, null, false);
		mDao = WallpaperDAO.getInstance(context);
	}
	
	public void initViews() {
		bgImage = (ImageView) mRootView.findViewById(R.id.iv_image);
		
		lockerMode = LockerModeFactory.getLockerMode(mContext, mConfig.getLockerModeType());
		lockerScreen = LockerScreenFactory.getLockerScreen(mContext, mConfig.getLockerScreenType(), this);
		
		viewPager = (ScrollerViewPager) mRootView.findViewById(R.id.vp_pager);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if(position == 1) {
					lockerMode.clearPassword();
					lockerMode.reset();
				} else {
					if(mConfig.getLockerModeType() == Constants.LockerMode.LOCK_MODE_NONE) {
						if(mListener != null) {
							mListener.onUnlock();
						}
					}
				}
				super.onPageSelected(position);
			}
		});
		lockerViewAdapter = new LockerViewAdapter();
		viewPager.setAdapter(lockerViewAdapter);
		ImageView ivCamera = (ImageView) mRootView.findViewById(R.id.iv_camera);
		if(mConfig.isWidgetCameraEnable()) {
			ivCamera.setVisibility(View.VISIBLE);
			Bitmap contour = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_camera);
			ivCamera.setImageBitmap(BitmapUtils.getBitmap(mConfig.getWidgetCameraColor(), contour, 0.8f));
		} else {
			ivCamera.setVisibility(View.GONE);
		}
		ivCamera.setOnClickListener(this);
	}
	
	public void setListener(OnLockerListener listener) {
		mListener = listener;
	}
	
	public void onLock() {
		viewPager.setCurrentItem(1, false);
		WallpaperUtils.showWallpaper(mContext, bgImage, mDao.getCurrent());
	}
	
	public void lock() {
		viewPager.setCurrentItem(1, false);
		lockerMode.reset();
	}
	
	public View getLockerView() {
		return mRootView;
	}
	
	public static interface OnLockerListener {
		public void onUnlock();
	}
	

	private class LockerViewAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View itemView = null;
			if(position == 0) {
				itemView = lockerMode.getView();
			} else {
				itemView = lockerScreen.getView();
			}
			container.addView(itemView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			return itemView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_camera:
				if(mConfig.getLockerModeType() == Constants.LockerMode.LOCK_MODE_NONE) {
					if(mListener != null) {
						mListener.onUnlock();
					}
					AppUtils.launchCamera(mContext);
				} else {
					lockerMode.launchCamera();
					viewPager.setCurrentItem(0);
				}
				break;
			default:
				break;
		}
	}
	
	public void launchNotification(Notification notification) {
		if(mConfig.getLockerModeType() == Constants.LockerMode.LOCK_MODE_NONE) {
			if(mListener != null) {
				mListener.onUnlock();
			}
			lockerMode.launchNotification(notification);
		} else {
			lockerMode.launchNotification(notification);
			viewPager.setCurrentItem(0);
		}
	}
	
}
