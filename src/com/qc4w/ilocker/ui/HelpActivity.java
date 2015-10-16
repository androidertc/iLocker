package com.qc4w.ilocker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.view.PinnedHeaderExpandableListView;
import com.qc4w.ilocker.ui.view.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.qc4w.ilocker.ui.view.StickyLayout;
import com.qc4w.ilocker.ui.view.StickyLayout.OnGiveUpTouchEventListener;

public class HelpActivity extends ActivityBase implements OnHeaderUpdateListener, OnGiveUpTouchEventListener {
	private PinnedHeaderExpandableListView expandableListView;
	private StickyLayout stickyLayout;

	private String[] questions;
	private String[] answers;
	
	private MyexpandableListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
		stickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);

		questions = getResources().getStringArray(R.array.questions);
		answers = getResources().getStringArray(R.array.answers);
		
		adapter = new MyexpandableListAdapter(this);
		expandableListView.setAdapter(adapter);

		// 展开所有group
		for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
			expandableListView.expandGroup(i);
		}

		expandableListView.setOnHeaderUpdateListener(this);
		stickyLayout.setOnGiveUpTouchEventListener(this);
		
		expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		}, false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class MyexpandableListAdapter extends BaseExpandableListAdapter {
		private Context context;
		private LayoutInflater inflater;

		public MyexpandableListAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		// 返回父列表个数
		@Override
		public int getGroupCount() {
			return questions.length;
		}

		// 返回子列表个数
		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		@Override
		public Object getGroup(int groupPosition) {

			return questions[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return "";
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {

			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupHolder groupHolder = null;
			if (convertView == null) {
				groupHolder = new GroupHolder();
				convertView = inflater.inflate(R.layout.group, null);
				groupHolder.textView = (TextView) convertView.findViewById(R.id.group);
				convertView.setTag(groupHolder);
			} else {
				groupHolder = (GroupHolder) convertView.getTag();
			}
			groupHolder.textView.setText(questions[groupPosition]);
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			ChildHolder childHolder = null;
			if (convertView == null) {
				childHolder = new ChildHolder();
				convertView = inflater.inflate(R.layout.child, null);
				childHolder.textName = (TextView) convertView.findViewById(R.id.textView);
				convertView.setTag(childHolder);
			} else {
				childHolder = (ChildHolder) convertView.getTag();
			}

			childHolder.textName.setText(answers[groupPosition]);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}

	class GroupHolder {
		TextView textView;
	}

	class ChildHolder {
		TextView textName;
	}

	@Override
	public View getPinnedHeader() {
		View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.group, null);
		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return headerView;
	}

	@Override
	public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
		String firstVisibleGroup = (String) adapter.getGroup(firstVisibleGroupPos);
		TextView textView = (TextView) headerView.findViewById(R.id.group);
		textView.setText(firstVisibleGroup);
	}

	@Override
	public boolean giveUpTouchEvent(MotionEvent event) {
		if (expandableListView.getFirstVisiblePosition() == 0) {
			View view = expandableListView.getChildAt(0);
			if (view != null && view.getTop() >= 0) {
				return true;
			}
		}
		return false;
	}

}
