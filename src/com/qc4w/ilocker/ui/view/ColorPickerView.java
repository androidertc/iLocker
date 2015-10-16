package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.adapter.ColorPickerAdapter;
import com.qc4w.ilocker.ui.adapter.ColorPickerAdapter.OnColorSelectedListener;

public class ColorPickerView extends RelativeLayout implements OnColorSelectedListener {

	private ColorPickerAdapter adapter;

	private int[] colors;
	private OnColorSelectedListener listener;
	public ColorPickerView(Context context, int defColor, OnColorSelectedListener listener) {
		super(context);
		inflate(context, R.layout.layout_color_picker_view, this);

		colors = context.getResources().getIntArray(R.array.colors);
		GridView gridView = (GridView) findViewById(R.id.grid_view);
		adapter = new ColorPickerAdapter(colors, context, defColor, this);
		gridView.setAdapter(adapter);
		
		this.listener = listener;
	}
	

	@Override
	public void onColorSelected(int color) {
		if(listener != null) {
			listener.onColorSelected(color);
		}
	}
}
