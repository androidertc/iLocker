package com.qc4w.ilocker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fourmob.colorpicker.PickerDialog;
import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;
import com.fourmob.colorpicker.ContourPickerSwatch.OnContourSelectedListener;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.util.ContourMapper;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class MenuDemoActivity extends ActivityBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_with_custom_animation);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new CustomAnimationDemoFragment())
					.commit();
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class CustomAnimationDemoFragment extends Fragment {

		public CustomAnimationDemoFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_menu_with_custom_animation, container,
					false);

			final PickerDialog contourPickerDialog = new PickerDialog();
			contourPickerDialog.initialize(R.string.color_dialog_title, ContourMapper.contourItems, R.drawable.lock_item_circle, 4, 2, PickerDialog.TYPE_CONTOUR);
			contourPickerDialog.setOnContourSelectedListener(new OnContourSelectedListener() {
				
				@Override
				public void onContourSelected(int contour) {
					Toast.makeText(getActivity(), "contour : " + contour, Toast.LENGTH_SHORT).show();
				}
			});

			rootView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					contourPickerDialog.show(getFragmentManager(), "colorpicker");
				}
			});
			
		    ShimmerTextView tv  = (ShimmerTextView) rootView.findViewById(R.id.shimmer_tv);
		    Shimmer shimmer = new Shimmer();
            shimmer.start(tv);
			return rootView;
		}
	}
}
