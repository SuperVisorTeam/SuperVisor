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
 * ������ʦ��Fragment
 */
@SuppressLint("ValidFragment")
public class EvaluateFragment extends Fragment
{
	public static EvaluateFragment evaluateFragment;

	/**
	 * ˽�л����췽��
	 */
	private EvaluateFragment()
	{
	}

	/**
	 * �ڲ��࣬��֤����ʱʱ�̰߳�ȫ�ġ�
	 */
	private static class SingletonHolder
	{
		private static EvaluateFragment instance = new EvaluateFragment();
	}

	/**
	 * �ṩ�ⲿ����EvaluateFragment�ķ���,ʹ�õ�����
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
