package com.gdut.supervisor.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.utils.SearchFormAdapter;

/**
 * 教务的Fragment
 */
@SuppressLint("ValidFragment")
public class EducationalFragment extends Fragment implements OnItemClickListener
{
	private ListView listView;
	private View view;

	public static EducationalFragment searchFragment;

	/**
	 * 私有化构造方法
	 */
	private EducationalFragment()
	{
	}

	/**
	 * 内部类，保证加载时时线程安全的。
	 */
	private static class SingletonHolder
	{
		private static EducationalFragment instance = new EducationalFragment();
	}

	/**
	 * 提供外部创建SearchFragment对象的方法。
	 */
	public static EducationalFragment getInstance()
	{
		return SingletonHolder.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_educational, container, false);
		listView = (ListView) view.findViewById(R.id.lv_searchform_everyday);
		listView.setOnItemClickListener(this);
		listView.setAdapter(new SearchFormAdapter(getActivity(), null));
		return view;
	}

	// Item点击监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Toast.makeText(getActivity(), "点击了第:" + (position + 1) + "项", 0).show();
	}
}
