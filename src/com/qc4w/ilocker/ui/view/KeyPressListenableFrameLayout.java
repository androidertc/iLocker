package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class KeyPressListenableFrameLayout extends FrameLayout {
	private KeyClickListener keyListener;
	
	public KeyPressListenableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setKeyPressListener(KeyClickListener keyListener) {
		this.keyListener = keyListener;
	}

	@Override 
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if (null != keyListener) {
				keyListener.onKeyPreIme(event);
			}
			return false;
		}
		return super.dispatchKeyEvent(event);
	}
	
	public interface KeyClickListener {
		public boolean onKeyPreIme(KeyEvent event);
	}
}
