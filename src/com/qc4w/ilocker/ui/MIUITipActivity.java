package com.qc4w.ilocker.ui;

import cn.pedant.SweetAlert.SweetAlertDialog;
import android.os.Bundle;
import android.view.WindowManager;

public class MIUITipActivity extends ActivityBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
	        .setTitleText("Are you sure?")
	        .setContentText("Won't be able to recover this file!")
	        .setCancelText("No,cancel plx!")
	        .setConfirmText("Yes,delete it!")
	        .showCancelButton(true)
	        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
				
				@Override
				public void onClick(SweetAlertDialog sweetAlertDialog) {
					
				}
			}).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
	            @Override
	            public void onClick(SweetAlertDialog sDialog) {

	            }
	        });
		sad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		sad.show();
		
	
	}
}
