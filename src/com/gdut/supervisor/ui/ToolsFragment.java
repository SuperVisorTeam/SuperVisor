package com.gdut.supervisor.ui;

import com.gdut.supervisor.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 工具的Fragment
 */
@SuppressLint("ValidFragment")
public class ToolsFragment extends Fragment
{
	public static ToolsFragment helpFragment;

	/**
	 * 私有化构造方法
	 */
	private ToolsFragment()
	{
		Log.v("log", "-->HelpFragment-HelpFragment()");
	}

	/**
	 * 内部类，保证加载时时线程安全的。
	 */
	private static class SingletonHolder
	{
		private static ToolsFragment instance = new ToolsFragment();
	}

	/**
	 * 提供外部创建HelpFragment对象的方法。
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
