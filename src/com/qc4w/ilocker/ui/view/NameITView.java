package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;

public class NameITView extends RelativeLayout {

	public NameITView(Context context) {
		super(context);
		inflate(context, R.layout.layout_name_it_view, this);

		ConfigManager config = ConfigManager.getInstance(getContext());
		TextView tvNameImageText = (TextView) findViewById(R.id.tv_name_image_text);
		tvNameImageText.setText(config.getNameImageText());
		tvNameImageText.setTextColor(config.getNameImageTextColor());
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);
		this.setGravity(Gravity.CENTER);
	}

}
 