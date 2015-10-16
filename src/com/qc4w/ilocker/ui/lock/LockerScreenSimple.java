package com.qc4w.ilocker.ui.lock;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.ui.adapter.LockerViewWidgetAdapter;
import com.qc4w.ilocker.ui.adapter.NotificationAdapter;
import com.qc4w.ilocker.ui.view.SwipeDismissListViewTouchListener;
import com.qc4w.ilocker.util.NotificationContainerUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class LockerScreenSimple extends AbsLockerScreen {

	private View mRootView;
	private ListView mLVNotifications;
	
	private NotificationAdapter adapter;
	
	public LockerScreenSimple(Context context, LockerView lockerView) {
		super(context, lockerView);
	}

	@Override
	protected void initViews() {
		mRootView = getInflater().inflate(R.layout.layout_simple_locker_screen, null, false);

		ConfigManager config = ConfigManager.getInstance(getContext());
		
		ShimmerTextView tv  = (ShimmerTextView) mRootView.findViewById(R.id.shimmer_tv);
	    Shimmer shimmer = new Shimmer();
        shimmer.start(tv);
        tv.setText(config.getSlideUnlockText());
        tv.setTextColor(config.getSlideUnlockTextColor());

        ViewPager pager = (ViewPager) mRootView.findViewById(R.id.vp_widget_pager);
        pager.setAdapter(new LockerViewWidgetAdapter(getContext()));
        
		mLVNotifications = (ListView) mRootView.findViewById(R.id.lv_notifications);
		mLVNotifications.setAdapter(getNotificationAdapter());

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(mLVNotifications, new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismissRight(ListView listView, int[] reverseSortedPositions) {
                    	NotificationContainerUtils utils = NotificationContainerUtils.getInstance(getContext());
                    	for (int position : reverseSortedPositions) {
                    		getLockerView().launchNotification(utils.getAllNotifications().get(position).getNotification());
                    		utils.onNotificationHandleed();
						}
                    }
                    
                    @Override
                    public void onDismissLeft(ListView listView, int[] reverseSortedPositions) {
                    	NotificationContainerUtils utils = NotificationContainerUtils.getInstance(getContext());
                    	for (int position : reverseSortedPositions) {
                    		utils.onNotificationHandleed(utils.getAllNotifications().get(position));
						}
                    }
                });
        mLVNotifications.setOnTouchListener(touchListener);
        mLVNotifications.setOnScrollListener(touchListener.makeScrollListener());
	}

	@Override
	public View getView() {
		return mRootView;
	}

	@Override
	public NotificationAdapter getNotificationAdapter() {
		if(adapter == null) {
			adapter = new NotificationAdapter(getContext(), NotificationContainerUtils.getInstance(getContext()).getAllNotifications());
		}
		return adapter;
	}

}
