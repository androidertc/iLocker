package com.qc4w.ilocker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.adapter.ColorPickerAdapter.OnColorSelectedListener;
import com.qc4w.ilocker.ui.view.ColorPickerView;
import com.qc4w.ilocker.ui.view.KeyPressListenableFrameLayout;
import com.qc4w.ilocker.ui.view.NoSizeCircleColorView;

public class CommentActivity extends Activity implements OnClickListener, KeyPressListenableFrameLayout.KeyClickListener, OnColorSelectedListener {
	
	private String content;
	
	private PopupWindow popupWindow;
	private KeyPressListenableFrameLayout mLayoutComment;
	private LinearLayout mLayoutFooterPlaceHolder;
	private EditText mEditTextComment;
	private ImageView mImageClose, mImageSubmit;
	
	private LayoutInflater inflater;
	private NoSizeCircleColorView mIVColorButton;
	
	private boolean isKeyBoardVisible;
	private int previousParentHeight;
	private int keyboardHeight;
	private int width;
	
	private ColorPickerView colorPickerView;
	
	private int color;
	
	public interface OnCommentChangedListener {
		public void doCommentChanged(String content);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null) {
			content = getIntent().getStringExtra(Constants.StringConstant.PARAMS_DATA);
			color = getIntent().getIntExtra(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
		} else {
			content = savedInstanceState.getString(Constants.StringConstant.PARAMS_DATA);
			color = savedInstanceState.getInt(Constants.StringConstant.PARAMS_DATA1, Color.WHITE);
		}
		
		inflater = getLayoutInflater();
		mLayoutComment = (KeyPressListenableFrameLayout) inflater.inflate(R.layout.activity_comment, null);
		mLayoutComment.setKeyPressListener(this);
		setContentView(mLayoutComment);
		previousParentHeight = 0;
		keyboardHeight = 0;
		mLayoutComment.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Rect r = new Rect();
				mLayoutComment.getWindowVisibleDisplayFrame(r);
				int screenHeight = mLayoutComment.getRootView().getHeight();
				int heightDifference = screenHeight - (r.bottom);
				if (previousParentHeight - heightDifference > 50) {			
					if (null != popupWindow) {
						popupWindow.dismiss();
					}
				}
				previousParentHeight = heightDifference;
				if (heightDifference > 100) {
					isKeyBoardVisible = true;
					changeKeyboardHeight(heightDifference);
				} else {
					isKeyBoardVisible = false;
				}
			}
		});
		width = getResources().getDisplayMetrics().widthPixels;
		colorPickerView = new ColorPickerView(this, color, this);
		initElements();
		changeKeyboardHeight((int) getPixelByResId(R.dimen.emoticon_keyboard_height));
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(Constants.StringConstant.PARAMS_DATA, content);
		outState.putInt(Constants.StringConstant.PARAMS_DATA1, color);
	}
	
	public float getPixelByResId(int resId) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				getResources().getDimension(resId),
				getResources().getDisplayMetrics());
	}

	private void changeKeyboardHeight(int height) {
		if (height > 100) {
			keyboardHeight = height;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, keyboardHeight);
			mLayoutFooterPlaceHolder.setLayoutParams(params);
		}
	}

	private void initElements() {
		mLayoutFooterPlaceHolder = (LinearLayout) mLayoutComment.findViewById(R.id.fragment_comment_footer_placeholder);
		mImageClose = (ImageView) mLayoutComment.findViewById(R.id.fragment_comment_close);
		mImageSubmit = (ImageView) mLayoutComment.findViewById(R.id.fragment_comment_submit);
		mEditTextComment = (EditText) mLayoutComment.findViewById(R.id.fragment_comment_content);
		mEditTextComment.setOnClickListener(this);
		
		mEditTextComment.setText(content);
		mEditTextComment.setTextColor(color);

		mEditTextComment.setText(content);
		mEditTextComment.setSelection(mEditTextComment.getText().length());
		mIVColorButton = (NoSizeCircleColorView) mLayoutComment.findViewById(R.id.iv_color_btn);
		mIVColorButton.setColor(color);
		
		popupWindow = new PopupWindow(colorPickerView, LayoutParams.MATCH_PARENT, keyboardHeight, false);
		popupWindow.setWidth(width);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mLayoutFooterPlaceHolder.setVisibility(LinearLayout.GONE);
			}
		});
		findViewById(R.id.fragment_comment_layout_main).setOnClickListener(this);
		mLayoutComment.setOnClickListener(this);
		mIVColorButton.setOnClickListener(this);
		mImageClose.setOnClickListener(this);
		mImageSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.iv_color_btn: 
			if(null == popupWindow){
				break;
			}
			if (!popupWindow.isShowing()) {
				popupWindow.setHeight((int) keyboardHeight);
				if (isKeyBoardVisible) {
					mLayoutFooterPlaceHolder.setVisibility(LinearLayout.GONE);
				} else {
					mLayoutFooterPlaceHolder.setVisibility(LinearLayout.VISIBLE);
				}
				popupWindow.showAtLocation(mLayoutComment, Gravity.BOTTOM, 0, 0);
			} else {
				popupWindow.dismiss();
			}
			break;
		case R.id.fragment_comment_content:
			if (null != popupWindow && popupWindow.isShowing()) {
				popupWindow.dismiss();
				if (!isKeyBoardVisible) {
					openKeyboard();
				} else {
					if (popupWindow != null) popupWindow.dismiss();
				}
			}
			break;
		case R.id.fragment_comment_close:
			finishComment(false);
			break;
		case R.id.fragment_comment_submit:
			finishComment(true);
			break;
		case R.id.fragment_comment_layout_main:
			finishComment(false);
			break;
		default:
			finishComment(false);
			break;
		} 
	}

	public void openKeyboard() {
		InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.toggleSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
	}
	
	private void finishComment(boolean save) {
		if (isKeyBoardVisible) {
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditTextComment.getWindowToken(), 0);
		}
		if(save) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString(Constants.StringConstant.PARAMS_DATA, mEditTextComment.getText().toString());
			bundle.putInt(Constants.StringConstant.PARAMS_DATA1, color);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
		}

		if (null != popupWindow) {
			popupWindow.dismiss();
			popupWindow = null;
		}
		this.finish();
		overridePendingTransition(R.anim.default_anim_in, R.anim.default_anim_out);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishComment(false);
			return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyPreIme(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			finishComment(false);
			return true;
		}
		return false;
	}

	@Override
	public void onColorSelected(int color) {
		this.color = color;
		mEditTextComment.setTextColor(color);
		mIVColorButton.setColor(color);
	}
	
}
