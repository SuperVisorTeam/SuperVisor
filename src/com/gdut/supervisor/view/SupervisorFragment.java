package com.gdut.supervisor.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.ui.PreEntryActivity;
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
	 * 菜单平铺显示的GrideView
	 */
	private GridView gridView;
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

	private View parentView;
	/**
	 * 保存设置内容的sharedPreferences
	 */
	private SharedPreferences sharedPreferences;

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
		sharedPreferences = getActivity().getSharedPreferences("STYLE", getActivity().MODE_PRIVATE);
		int mode = sharedPreferences.getInt("STYLE_MODE", 0);
		switch (mode)
		{
		// 旋转
		case 0:
			parentView = inflater.inflate(R.layout.fragment_supervisor_circle, container, false);
			break;
		// 平铺
		case 1:
			parentView = inflater.inflate(R.layout.fragment_supervisor_grideview, container, false);
			break;
		default:
			break;
		}
		initView(parentView, mode);
		return parentView;
	}

	/**
	 * 初始化控件
	 * 
	 * @param parentView
	 */
	private void initView(View parentView, int STYLE_MODE)
	{
		switch (STYLE_MODE)
		{
		// 旋转菜单模式
		case 0:

			CircleLayout circleMenu = (CircleLayout) parentView.findViewById(R.id.main_circle_layout);
			circleMenu.setOnItemSelectedListener(this);
			circleMenu.setOnItemClickListener(this);

			selectedTextView = (TextView) parentView.findViewById(R.id.txt_supervisor_function_name);
			selectedTextView.setText(((CircleImageView) circleMenu.getSelectedItem()).getName());
			break;
		// 平铺菜单模式
		case 1:
			gridView = (GridView) parentView.findViewById(R.id.gV_supervisor_fragment);
			//放到资源文件
			// 功能名称
			String[] str = new String[]
			{ "教师查询", "历史查询", "预约查询", "直接录入", "督导预约", "课室查询" };
			// 图片按钮ID
			int[] imageIds = new int[]
			{ R.drawable.search_teacher_gv, R.drawable.search_history_gv, R.drawable.search_pre_entry_bv,
					R.drawable.entry_direct_gv, R.drawable.entry_pre_gv, R.drawable.search_class_gv };

			List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < str.length; i++)
			{
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("image", imageIds[i]);
				listItem.put("str", str[i]);
				listItems.add(listItem);
			}
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems, R.layout.grideview_menu,
					new String[]
					{ "image", "str" }, new int[]
					{ R.id.iv_grideview, R.id.txt_grideview });
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(new mOnItemClickListener());
			break;
		default:
			break;
		}
	}

	/**
	 * 按钮点击监听
	 */
	@Override
	public void onItemClick(View view, int position, long id, String name)
	{
		startIntent(position);
	}

	/**
	 * 旋转菜单下方文字监听
	 */
	@Override
	public void onItemSelected(View view, int position, long id, String name)
	{
		selectedTextView.setText(name);
	}

	/**
	 * 平铺菜单GrideView的监听
	 */
	private class mOnItemClickListener implements android.widget.AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			startIntent(position);
		}
	}

	/**
	 * 跳转目标
	 * 
	 * @param position
	 */
	private void startIntent(int position)
	{
		switch (position)
		{
		// 教师查询
		case 0:
			Toast.makeText(getActivity(), "教师查询暂未开放", 1).show();
			break;
		// 历史查询
		case 1:
			startActivity(new Intent(getActivity(), SearchFormActivity.class));
			SearchFormActivity.nownum = 1;
			break;
		// 预约查询
		case 2:
			startActivity(new Intent(getActivity(), ScheduleActivity.class));
			break;
		// 直接录入
		case 3:
			startActivity(new Intent(getActivity(), SupervisorActivity.class));
			break;
		// 督导预约
		case 4:
			startActivity(new Intent(getActivity(), PreEntryActivity.class));
			break;
		// 课室查询
		case 5:
			Toast.makeText(getActivity(), "课室查询暂未开放", 1).show();
			break;
		default:
			break;
		}
	}
}
