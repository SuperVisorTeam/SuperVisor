package com.gdut.supervisor.view;

import com.gdut.supervisor.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ���ߵ�Fragment
 */
@SuppressLint("ValidFragment")
public class ToolsFragment extends Fragment
{
	public static ToolsFragment helpFragment;

	/**
	 * ˽�л����췽��
	 */
	private ToolsFragment()
	{
		Log.v("log", "-->HelpFragment-HelpFragment()");
	}

	/**
	 * �ڲ��࣬��֤����ʱʱ�̰߳�ȫ�ġ�
	 */
	private static class SingletonHolder
	{
		private static ToolsFragment instance = new ToolsFragment();
	}

	/**
	 * �ṩ�ⲿ����HelpFragment����ķ�����
	 */
	public static ToolsFragment getInstance()
	{
		return SingletonHolder.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_tools, container, false);
	}
}
