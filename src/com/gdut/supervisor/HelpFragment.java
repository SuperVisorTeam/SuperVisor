package com.gdut.supervisor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 使用帮助的Fragment
 */
@SuppressLint("ValidFragment")
public class HelpFragment extends Fragment
{
	public static HelpFragment helpFragment;
	
	/**
	 * 私有化构造方法
	 */
	private HelpFragment()
	{
		Log.v("log", "-->HelpFragment-HelpFragment()");
	}
	/**
	 * 创建HelpFragment对象，使用同步。
	 */
	private static synchronized void syscInt()
	{
		if(helpFragment == null)
		{
			Log.v("log", "-->HelpFragment-syscInt()");
			helpFragment = new HelpFragment();
		}
	}
	/**
	 *提供外部创建HelpFragment对象的方法。 
	 */
	public static  HelpFragment getInstance()
	{
		if(helpFragment == null)
		{
			Log.v("log", "-->HelpFragment-getInstance()");
			syscInt();
		}
		return helpFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.help_fragment, container, false);
	}
}
