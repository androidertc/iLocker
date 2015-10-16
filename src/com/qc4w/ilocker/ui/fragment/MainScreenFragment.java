package com.qc4w.ilocker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.config.ConfigManager;
import com.qc4w.ilocker.constant.Constants;
import com.qc4w.ilocker.ui.WidgetSettingsActivity;
import com.qc4w.ilocker.ui.view.CountdownTextView;
import com.qc4w.ilocker.ui.view.TimingTextView;
import com.qc4w.ilocker.util.AccessibilityUtils;

public class MainScreenFragment extends FragmentBase implements OnClickListener {
	
	public static final String TAG = MainScreenFragment.class.getSimpleName();
	
	private View mRootLayout;
	private ConfigManager config;
	private ImageView ivTimeFlag, ivSentenceFlag, ivTimingFlag, ivCountdownFlag, ivNotificationFlag, ivCameraFlag;
	
	private TextView tvSentenceContent, tvCountdownContent, tvTimingContent;
	private CountdownTextView tvCountdown;
	private TimingTextView tvTiming;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ConfigManager.getInstance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mRootLayout != null) {
			((ViewGroup)mRootLayout.getParent()).removeAllViews();
			return mRootLayout;
		}
		mRootLayout = inflater.inflate(R.layout.fragment_main_screen, container, false);
		initUI();
		return mRootLayout;
	}

	private void initUI() {
		ivCameraFlag = (ImageView) mRootLayout.findViewById(R.id.iv_camera_flag);
		ivCountdownFlag = (ImageView) mRootLayout.findViewById(R.id.iv_countdown_flag);
		ivNotificationFlag = (ImageView) mRootLayout.findViewById(R.id.iv_notification_flag);
		ivSentenceFlag = (ImageView) mRootLayout.findViewById(R.id.iv_sentence_flag);
		ivTimeFlag = (ImageView) mRootLayout.findViewById(R.id.iv_time_flag);
		ivTimingFlag = (ImageView) mRootLayout.findViewById(R.id.iv_timing_flag);
		
		mRootLayout.findViewById(R.id.rl_camera_container).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_countdown_container).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_time_container).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_timing_container).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_sentence_container).setOnClickListener(this);
		mRootLayout.findViewById(R.id.rl_notification_container).setOnClickListener(this);
		// 日期和时间部件
		TextView tvDate = (TextView) mRootLayout.findViewById(R.id.tv_date);
		tvDate.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
		TextView tvWeek = (TextView) mRootLayout.findViewById(R.id.tv_week);
		tvWeek.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_WEEKDAY));
		
		// 文字短语部件
		tvSentenceContent = (TextView) mRootLayout.findViewById(R.id.tv_sentence_content);
		
		// 倒计时部件
		tvCountdownContent = (TextView) mRootLayout.findViewById(R.id.tv_countdown_content);
		tvCountdown = (CountdownTextView) mRootLayout.findViewById(R.id.tv_countdown_time);
		
		// 计时部件
		tvTimingContent = (TextView) mRootLayout.findViewById(R.id.tv_timing_content);
		tvTiming = (TimingTextView) mRootLayout.findViewById(R.id.tv_timing_time);
	}

	@Override
	public void onResume() {
		super.onResume();
		ivCameraFlag.setImageResource(config.isWidgetCameraEnable() ? R.drawable.ic_selected : R.drawable.ic_unselect);
		ivCountdownFlag.setImageResource(config.isWidgetCountdownEnable() ? R.drawable.ic_selected : R.drawable.ic_unselect);
		ivNotificationFlag.setImageResource(AccessibilityUtils.isEnable(getActivity()) ? R.drawable.ic_selected : R.drawable.ic_unselect);
		ivSentenceFlag.setImageResource(config.isWidgetSentenceEnable() ? R.drawable.ic_selected : R.drawable.ic_unselect);
		ivTimeFlag.setImageResource(config.isWidgetTimeEnable() ? R.drawable.ic_selected : R.drawable.ic_unselect);
		ivTimingFlag.setImageResource(config.isWidgetTimingEnable() ? R.drawable.ic_selected : R.drawable.ic_unselect);
		
		tvSentenceContent.setText(config.getSentenceContent());
		tvCountdownContent.setText(config.getCountdownContent());
		tvTimingContent.setText(config.getTimingContent());
		tvCountdown.setTime(config.getCountdownTimestamp(), true);
		tvTiming.setTime(config.getTimingTimestamp(), true);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), WidgetSettingsActivity.class);
		switch (v.getId()) {
			case R.id.rl_camera_container:
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_CAMERA);
				getActivity().startActivity(intent);
				break;
			case R.id.rl_countdown_container:
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_COUNTDOWN);
				getActivity().startActivity(intent);
				break;
			case R.id.rl_time_container:
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_TIME);
				getActivity().startActivity(intent);
				break;
			case R.id.rl_timing_container:
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_TIMING);
				getActivity().startActivity(intent);
				break;
			case R.id.rl_sentence_container:
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_SENTENCE);
				getActivity().startActivity(intent);
				break;
			case R.id.rl_notification_container:
				intent.putExtra(Constants.StringConstant.PARAMS_DATA, Constants.WidgetType.WIDGET_TYPE_NOTIFICATION);
				getActivity().startActivity(intent);
				break;
			default:
				break;
		}
	}
}
