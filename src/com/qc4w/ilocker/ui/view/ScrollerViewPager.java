/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qc4w.ilocker.ui.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by chenupt@gmail.com on 2015/3/7. Description TODO
 */
public class ScrollerViewPager extends ViewPager {

	private static final String TAG = ScrollerViewPager.class.getSimpleName();

	private int duration = 1000;
	
	private boolean intercept = true;

	public ScrollerViewPager(Context context) {
		super(context);
	}

	public ScrollerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void fixScrollSpeed() {
		fixScrollSpeed(duration);
	}

	public void fixScrollSpeed(int duration) {
		this.duration = duration;
		setScrollSpeedUsingRefection(duration);
	}

	private void setScrollSpeedUsingRefection(int duration) {
		try {
			Field localField = ViewPager.class.getDeclaredField("mScroller");
			localField.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(),
					new DecelerateInterpolator(1.5F));
			scroller.setDuration(duration);
			localField.set(this, scroller);
			return;
		} catch (IllegalAccessException localIllegalAccessException) {
		} catch (IllegalArgumentException localIllegalArgumentException) {
		} catch (NoSuchFieldException localNoSuchFieldException) {
		}
	}

	/**
	 * 
	 * 在mViewTouchMode为true的时候，ViewPager不拦截点击事件，点击事件将由子View处理
	 */
	@Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(intercept){
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }
    }

   
    public void setTouchIntercept(boolean value){
        intercept = value;
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			return super.onTouchEvent(ev);
		} catch (Exception e) {
			return false;
		}

	}
}
