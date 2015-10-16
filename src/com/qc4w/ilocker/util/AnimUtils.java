package com.qc4w.ilocker.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.qc4w.ilocker.ui.anim.Rotate3dAnimation;

public class AnimUtils {

	/**
	 * ÏÔÊ¾·­×ª¶¯»­
	 */
	public static void applyRotation(View view, float start, float end) {
		applyRotation(view, start, end, new AccelerateInterpolator());
	}
	
	public static void applyRotation(View view, float start, float end, Interpolator interpolator) {
		final float centerX = view.getWidth() / 2.0f;
		final float centerY = view.getHeight() / 2.0f;
		
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 0f, true, true);
		rotation.setDuration(1000);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		
		view.startAnimation(rotation);
	}
}
