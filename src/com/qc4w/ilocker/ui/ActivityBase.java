package com.qc4w.ilocker.ui;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.app.ActionBar;
import android.os.Bundle;
import android.widget.Toast;

import com.qc4w.ilocker.util.UIUtils;

public class ActivityBase extends SwipeBackActivity {

	protected void showToast(String content) {
		Toast.makeText(this, content, Toast.LENGTH_LONG).show();
	}
	
	protected void showToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		UIUtils.changeActionBarHomeUpDrawable(this);
		
	}

}
