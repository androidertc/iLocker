package com.qc4w.ilocker.ui;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.qc4w.ilocker.constant.Constants;
import com.soundcloud.android.crop.Crop;

public class CropImageActivity extends ActivityBase {

    private String path;
    
    private int aspectX;
    private int aspectY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
        	path = getIntent().getStringExtra(Constants.StringConstant.PARAMS_DATA);
        	aspectX = getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA1, 1);
        	aspectY = getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA2, 1);
        } else {
        	path = savedInstanceState.getString(Constants.StringConstant.PARAMS_DATA);
        	aspectX = savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA1, 1);
        	aspectY = savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA2, 1);
        }

        Crop.pickImage(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putString(Constants.StringConstant.PARAMS_DATA, path);
    	outState.putInt(Constants.StringConstant.PARAMS_DATA1, aspectX);
    	outState.putInt(Constants.StringConstant.PARAMS_DATA2, aspectY);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK) {
        	if(resultCode == RESULT_OK) {
        		beginCrop(result.getData());
        	} else {
        		finish();
        	}
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(path));
        new Crop(source).output(outputUri).withAspect(aspectX, aspectY).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
        	setResult(RESULT_OK);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}
