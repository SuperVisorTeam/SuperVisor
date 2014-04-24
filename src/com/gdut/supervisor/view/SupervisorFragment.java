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
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.ui.ScheduleActivity;
import com.gdut.supervisor.ui.SearchFormActivity;
import com.gdut.supervisor.ui.SupervisorActivity;
import com.gdut.supervisor.view.CircleLayout.OnItemClickListener;
import com.gdut.supervisor.view.CircleLayout.OnItemSelectedListener;

/**
 * 督导功能的Fragment
 */
@SuppressLint("ValidFragment")
public class SupervisorFragment extends Fragment implements OnItemSelectedListener, OnItemClickListener
{
	/**
	 * 转轮菜单底部文字
	 */
	TextView selectedTextView;
	/**
	 * 转轮菜单图片按钮
	 */
	ImageButton btn_preparewrite;
	/**
	 * 离开窗口
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
		initView(parentView);
		return parentView;
	}

	/**
	 * 初始化控件
	 * 
	 * @param parentView
	 */
	private void initView(View parentView)
	{

		CircleLayout circleMenu = (CircleLayout) parentView.findViewById(R.id.main_circle_layout);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);

		selectedTextView = (TextView) parentView.findViewById(R.id.txt_supervisor_function_name);
		selectedTextView.setText(((CircleImageView) circleMenu.getSelectedItem()).getName());
	}

	/**
	 * 转轮按钮点击监听
	 */
	@Override
	public void onItemClick(View view, int position, long id, String name)
	{
		switch (position)
		{
		// 教师查询
		case 0:
			Toast.makeText(getActivity(), "你点击了 " + name, Toast.LENGTH_SHORT).show();
			break;
		// 历史查询
		case 1:
			Intent intent = new Intent(getActivity(), SearchFormActivity.class);
			startActivity(intent);
			SearchFormActivity.nownum = 1;
			break;
		// 预约查询
		case 2:
			Intent intentToSchedule = new Intent(getActivity(), ScheduleActivity.class);
			startActivity(intentToSchedule);
			break;
		// 直接录入
		case 3:
			Intent intentToSearch = new Intent();
			intentToSearch.setClass(getActivity(), SupervisorActivity.class);
			startActivity(intentToSearch);
			break;
		case 4:
			// 督导预约
			Toast.makeText(getActivity(), "你点击了 " + name, Toast.LENGTH_SHORT).show();
			break;
		// 课室查询
		case 5:
			Toast.makeText(getActivity(), "你点击了 " + name, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemSelected(View view, int position, long id, String name)
	{
		selectedTextView.setText(name);
	}
}
