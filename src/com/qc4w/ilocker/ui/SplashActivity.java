package com.qc4w.ilocker.ui;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.db.WallpaperDAO;
import com.qc4w.ilocker.ui.view.ClipRevealFrame;
import com.qc4w.ilocker.util.LogUtils;

public class SplashActivity extends Activity {
	
	private static final String TAG = SplashActivity.class.getSimpleName();

	private static final int MSG_ID_ENTER_APP = 0x01;
	
	private Button fab;
    private View mRootLayout;
    private ClipRevealFrame mMenuLayout;
    
    private TextView mTVWelcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		fab = (Button) findViewById(R.id.fab);
		mTVWelcome = (TextView) findViewById(R.id.tv_welcome);
        mRootLayout = findViewById(R.id.root_layout);
        mMenuLayout = (ClipRevealFrame) findViewById(R.id.menu_layout);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		ConfigManager config = ConfigManager.getInstance(this);
		config.setScreenHeight(dm.heightPixels);
		config.setScreenWidth(dm.widthPixels);
		if (!config.isWallpaperInited()) {
			checkWallpaper(config);
		}
		mHandler.sendEmptyMessageDelayed(MSG_ID_ENTER_APP, 400);
	}

    private void checkWallpaper(ConfigManager config) {
    	try {
	        WallpaperDAO themeDao = WallpaperDAO.getInstance(this);
	        if (themeDao.getAll().size() == 0) {
	        	themeDao.initWallpaper(this);
	        }
	        config.setWallpaperInited(true);
    	} catch (Exception e) {
    		LogUtils.e(TAG, e);
    	}
    }
    
    private void startAnim(View v) {
        int x = (v.getLeft() + v.getRight()) / 2;
        int y = (v.getTop() + v.getBottom()) / 2;
        float radiusOfFab = 1f * v.getWidth() / 2f;
        float radiusFromFabToRoot = (float) Math.hypot(
                Math.max(x, mRootLayout.getWidth() - x),
                Math.max(y, mRootLayout.getHeight() - y));

        startArcAnim(x, y, radiusOfFab, radiusFromFabToRoot);
    }
    

    private void startArcAnim(int cx, int cy, float startRadius, float endRadius) {
        mMenuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<Animator>();

        Animator revealAnim = createCircularReveal(mMenuLayout, cx, cy, startRadius, endRadius);
        revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        revealAnim.setDuration(300);

        animList.add(revealAnim);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animList);
        animSet.start();
        
        mTVWelcome.setTextColor(Color.WHITE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private Animator createCircularReveal(final ClipRevealFrame view, int x, int y, float startRadius, float endRadius) {
        final Animator reveal;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            reveal = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
        } else {
            view.setClipOutLines(true);
            view.setClipCenter(x, y);
            reveal = ObjectAnimator.ofFloat(view, "ClipRadius", startRadius, endRadius);
            reveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setClipOutLines(false);
					Intent intent = new Intent(SplashActivity.this, MainStageActivity.class);
					startActivity(intent);
					finish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return reveal;
    }
    
    private Handler mHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_ID_ENTER_APP:
					startAnim(fab);
					break;
				default:
					break;
			}
			return false;
		}
	});
}
