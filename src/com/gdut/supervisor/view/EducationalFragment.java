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
 * �����Fragment
 */
@SuppressLint("ValidFragment")
public class EducationalFragment extends Fragment implements OnItemClickListener
{
	private ListView listView;
	private View view;

	public static EducationalFragment searchFragment;

	/**
	 * ˽�л����췽��
	 */
	private EducationalFragment()
	{
	}

	/**
	 * �ڲ��࣬��֤����ʱʱ�̰߳�ȫ�ġ�
	 */
	private static class SingletonHolder
	{
		private static EducationalFragment instance = new EducationalFragment();
	}

	/**
	 * �ṩ�ⲿ����SearchFragment����ķ�����
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

	// Item�������
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Toast.makeText(getActivity(), "����˵�:" + (position + 1) + "��", 0).show();
	}
}
