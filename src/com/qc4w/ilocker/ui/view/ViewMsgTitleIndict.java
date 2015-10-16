package com.qc4w.ilocker.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.view.ViewPagerTab.OnPageListener;
import com.qc4w.ilocker.util.UIUtils;

public class ViewMsgTitleIndict extends RelativeLayout implements OnClickListener, OnPageListener{
	private Context mContext;
	private LinearLayout mLinearText, mLineIndicator;

	private RelativeLayout[] mLinClick;
	private TextView[] mText;

	private ViewPagerTab mTab;

	private String[] mTitles;
	private int mTabCount;
	private int mUnselectColor;
	private int mSeletedColor;
	private int mNumColor;
	private float mIndicatorHeight;
	private float mTextSize;
	private float mNumSize;

	private ViewPager mViewPager;

	public ViewMsgTitleIndict(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewMsgTitleIndict(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// load basic layout
		inflate(context, R.layout.view_message_indicator, this);
		mContext = context;

		// load defaults from resource
		final Resources res = getResources();
		final int defaultColor = res.getColor(R.color.default_defalult_color);
		final int defaultSelectColor = res.getColor(R.color.default_select_color);
		final int defaultNumColor = res.getColor(R.color.num_color);
		final int defalutTabCount = res.getInteger(R.integer.default_indicator_count);
		final float defaultIndiHight = res.getDimension(R.dimen.default_indicator_height);
		final float defaultSize = res.getDimension(R.dimen.default_text_size);
		final float defaultNumSize = res.getDimension(R.dimen.default_num_size);

		// Retrieve styles attributes
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewMsgTitleIndict, 0, 0);

		mUnselectColor = a.getColor(R.styleable.ViewMsgTitleIndict_textColor_default, defaultColor);
		mSeletedColor = a.getColor(R.styleable.ViewMsgTitleIndict_textColor_selected, defaultSelectColor);
		mNumColor = a.getColor(R.styleable.ViewMsgTitleIndict_num_color, defaultNumColor);
		mTabCount = a.getInt(R.styleable.ViewMsgTitleIndict_tabCount, defalutTabCount);
		mIndicatorHeight = a.getDimension(R.styleable.ViewMsgTitleIndict_indicatorHeight, defaultIndiHight);
		mTextSize = a.getDimension(R.styleable.ViewMsgTitleIndict_textSize, defaultSize);
		mNumSize = a.getDimension(R.styleable.ViewMsgTitleIndict_numSize, defaultNumSize);
		mTitles = getResources().getStringArray(a.getResourceId(R.styleable.ViewMsgTitleIndict_textArray, R.array.widget_type));
		a.recycle();
		
		if (mTitles != null && mTabCount != mTitles.length) {
			new Throwable("tilte count must match");
		}
		mLinearText = (LinearLayout) findViewById(R.id.view_message_title);
		mLineIndicator = (LinearLayout) findViewById(R.id.view_message_title_indicator);
		initTitle();
		mTab = new ViewPagerTab(mContext, mTabCount);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int) mIndicatorHeight);
		mLineIndicator.addView(mTab, params);
	}

	private void initTitle() {
		mLinClick = new RelativeLayout[mTitles.length];
		mText = new TextView[mTitles.length];
		LinearLayout.LayoutParams valParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);

		LinearLayout.LayoutParams paramsLine = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);

		for (int i = 0; i < mTitles.length; i++) {

			mLinClick[i] = new RelativeLayout(mContext);

			mText[i] = new TextView(mContext);
			mText[i].setText(mTitles[i]);
			mText[i].setTextColor(mUnselectColor);
			mText[i].setTextSize(UIUtils.px2sp(mContext, mTextSize));

			View view = new View(mContext);
			view.setBackgroundColor(0xFFE0E0DE);

			mText[i].setId(10 + i);
			RelativeLayout.LayoutParams paramsNumber = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsNumber.topMargin = 5;
			paramsNumber.leftMargin = -1;
			paramsNumber.addRule(RelativeLayout.RIGHT_OF, mText[i].getId());

			RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsText.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			mLinClick[i].addView(mText[i], paramsText);

			mLinearText.addView(mLinClick[i], valParams);
			mLinearText.addView(view, paramsLine);

			mLinClick[i].setTag(i);
			mLinClick[i].setOnClickListener(this);
		}
		mText[0].setTextColor(mSeletedColor);
	}

	public void setViewPager(ViewPager viewpager) {
		this.mViewPager = viewpager;
		mTab.setViewPager(mViewPager);
		mTab.setOnPageListener(this);
		mViewPager.setPageMargin(10);
	}

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		mViewPager.setCurrentItem(tag);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		for (int i = 0; i < mTitles.length; i++) {
			mText[i].setTextColor(mUnselectColor);
			if (i == position) {
				mText[i].setTextColor(mSeletedColor);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int position) {
	
	}
}
