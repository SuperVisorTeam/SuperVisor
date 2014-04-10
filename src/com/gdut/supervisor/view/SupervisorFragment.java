package com.gdut.supervisor.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gdut.supervisor.R;
import com.gdut.supervisor.ui.SupervisorActivity;

/**
 * 督导功能的Fragment
 */
@SuppressLint("ValidFragment")
public class SupervisorFragment extends Fragment implements OnClickListener
{
	/**
	 * 直接录入按钮
	 */
	private ImageButton btn_directwrite;
	/**
	 * 预定录入按钮
	 */
	private ImageButton btn_preparewrite;
	/**
	 * 高开窗口
	 */
	private AlertDialog leaveDialog;
	/**
	 * 判断查询功能是否打开
	 */
	public static boolean searchIsOpen = false;
	/**
	 * 判断预定功能是否打开
	 */
	public static boolean scheduleIsOpen = false;
	
	public static SupervisorFragment supervisorFragment;

	/**
	 * 私有化构造方法
	 */
	private SupervisorFragment()
	{
	}

	/**
	 * 内部类，保证加载时时线程安全的。
	 */
	private static class SingletonHolder
	{
		private static SupervisorFragment instance = new SupervisorFragment();
	}

	/**
	 * 提供外部创建SupervisorFragment对象的方法。
	 */
	public static SupervisorFragment getInstance()
	{
		return SingletonHolder.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View parentView = inflater.inflate(R.layout.fragment_supervisor, container, false);
		//
		btn_directwrite = (ImageButton) parentView.findViewById(R.id.ib_supervisor_directwrite);
		btn_preparewrite = (ImageButton) parentView.findViewById(R.id.ib_supervisor_preparewrite);
		//
		btn_directwrite.setOnClickListener(this);
		btn_preparewrite.setOnClickListener(this);
		return parentView;
	}

	// 为按钮添加监听
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// 预定录入按钮
		case R.id.ib_supervisor_preparewrite:

			break;
		// 直接录入按钮
		case R.id.ib_supervisor_directwrite:
			Intent intent = new Intent();
			intent.setClass(getActivity(), SupervisorActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}
		
}
