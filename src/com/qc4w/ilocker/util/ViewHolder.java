package com.qc4w.ilocker.util;

import android.util.SparseArray;
import android.view.View;

/**
 * @author tianc
 * @Override public View getView(int position, View convertView, ViewGroup
 *           parent) {
 * 
 *           if (convertView == null) { convertView =
 *           LayoutInflater.from(context) .inflate(R.layout.banana_phone,
 *           parent, false); }
 * 
 *           ImageView iconView = ViewHolder.get(convertView, R.id.icon);
 *           TextView nameView = ViewHolder.get(convertView, R.id.name);
 * 
 *           AppEntry entry = getItem(position);
 *           nameView.setText(entry.getName());
 *           iconView.setImageResource(entry.getIcon());
 * 
 *           return convertView; }
 */
public class ViewHolder {

	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
