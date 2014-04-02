package com.gdut.supervisor;

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

import com.gdut.supervisor.utils.SearchFormAdapter;

/**
 * 表单查询的Fragment
 * 
 * @author Mr.Zheng
 * 
 */
@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements OnItemClickListener
{
	private ListView listView;
	private View view;

	public static SearchFragment searchFragment;

	/**
	 * 私有化构造方法
	 */
	private SearchFragment()
	{
		Log.v("log", "-->SearchFragment-SearchFragment()");
	}
	/**
	 * 创建SearchFragment对象，使用同步。
	 */
	private static synchronized void syscInt()
	{
		if (searchFragment == null)
		{
			Log.v("log", "-->SearchFragment-syscInt()");
			searchFragment = new SearchFragment();
		}
	}
	/**
	 *提供外部创建SearchFragment对象的方法。 
	 */
	public static SearchFragment getInstance()
	{
		if (searchFragment == null)
		{
			Log.v("log", "-->SearchFragment-getInstance()");
			syscInt();
		}
		return searchFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.search_fragment, container, false);
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
