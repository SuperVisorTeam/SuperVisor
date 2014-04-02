package com.gdut.supervisor;

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
		Log.v("log", "-->EvaluateFragment-EvaluateFragment()");
	}

	/**
	 * 创建EvaluateFragment对象
	 */
	private static synchronized void syscInt()
	{
		if (evaluateFragment == null)
		{
			Log.v("log", "-->EvaluateFragment-syscInt()");
			evaluateFragment = new EvaluateFragment();
		}
	}

	/**
	 * 提供外部创建EvaluateFragment的方法,使用单例。
	 */
	public static EvaluateFragment getInstance()
	{
		if (evaluateFragment == null)
		{
			Log.v("log", "-->EvaluateFragment-getInstance()");
			syscInt();
		}
		return evaluateFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.evaluate_fragment, container, false);
	}

}
