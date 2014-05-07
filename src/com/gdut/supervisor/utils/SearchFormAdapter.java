package com.gdut.supervisor.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gdut.supervisor.R;

public class SearchFormAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private View view;
	private Context context;
	private String[] str;
	private TextView textView;

	public SearchFormAdapter(Context context, String[] str) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		// this.str = str;
		this.str = new String[] {

		"课程表", "成绩查询", "考试安排", "教学计划", "学生请假" };
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return str.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.searchform_listviewadapter,
					null);
			textView = (TextView) convertView
					.findViewById(R.id.tv_searchform_listviewadapter);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}

		textView.setText(str[position]);
		return convertView;
	}

	static class viewHolder {
		TextView holderTextView;
	}

}
