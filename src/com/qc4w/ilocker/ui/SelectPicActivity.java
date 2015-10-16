package com.qc4w.ilocker.ui;


import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.qc4w.ilocker.constant.Constants;

public class SelectPicActivity extends ActivityBase {

	private Uri photoUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	private void takePicFromAlbum() {
		
	}
	
	private void takePicFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		ContentValues values = new ContentValues();  
		photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);  
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
		startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_PICKER_CAMERA);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.RequestCode.REQUEST_CODE_PICKER_ALBUM) {
			if(resultCode == Activity.RESULT_OK && data != null) {
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		} else if (requestCode == Constants.RequestCode.REQUEST_CODE_PICKER_CAMERA) { 
			//when i put extras in intent when do ACTION_CAMERA, intent return is null, otherwise intent return a thumbnail
			if(resultCode == Activity.RESULT_OK) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if (null == photoUri) {
					return;
				}
				String[] projection = {
						MediaStore.Images.Media._ID,
						MediaStore.Images.Media.DISPLAY_NAME,
						MediaStore.Images.Media.DATA,
						MediaStore.Images.Media.BUCKET_ID,
						MediaStore.Images.Media.BUCKET_DISPLAY_NAME
					};
				Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), photoUri, projection);
				ArrayList<String> imageList = new ArrayList<String>();
				if (cursor.moveToNext()) {
					// imageList.add(PickerImage.getPickerImage(cursor));
				}
				bundle.putSerializable(Constants.StringConstant.PARAMS_DATA, imageList);
				intent.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
	}
}
