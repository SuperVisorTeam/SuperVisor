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
 * 评价老师的Fragment
 */
@SuppressLint("ValidFragment")
public class EvaluateFragment extends Fragment
{
	public static EvaluateFragment evaluateFragment;

	/**
	 * 私有化构造方法
	 */
	private EvaluateFragment()
	{
	}

	/**
	 * 内部类，保证加载时时线程安全的。
	 */
	private static class SingletonHolder
	{
		private static EvaluateFragment instance = new EvaluateFragment();
	}

	/**
	 * 提供外部创建EvaluateFragment的方法,使用单例。
	 */
	public static EvaluateFragment getInstance()
	{
		return SingletonHolder.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_evaluate, container, false);
	}

}
