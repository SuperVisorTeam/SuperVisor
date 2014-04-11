package com.gdut.supervisor.adapter;

import java.util.ArrayList;
import java.util.List;

import com.gdut.supervisor.view.FirstItemFragment;
import com.gdut.supervisor.view.SecondItemFragment;
import com.gdut.supervisor.view.ThirdItemFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * 该自定义的SupervisorAdapter继承于FragmentPagerAdapter 加载了三个表单所对应的Fragment
 * 所有成员变量的赋值都在构造函数SupervisorAdapter(FragmentManager fm)里，
 * 包括每个页面的标题提供者List<String> titleList，每个页卡提供者List<View> viewList
 */
public class SupervisorAdapter extends FragmentPagerAdapter
{

	/**
	 * 存储SupervisorActivity的上下文对象，
	 */
	Context context;

	FragmentManager myFragmentManager;
	/**
	 * 把需要滑动的页卡添加到这个list中
	 */
	private List<Fragment> fragmentViewList;

	/**
	 * 该List存放三个表单的标题的标题
	 */
	private List<String> titleList;
	/**
	 * 三个表单的视图参数
	 */
	private LayoutParams param;

	// 默认的构造函数
	public SupervisorAdapter(FragmentManager fm)
	{
		super(fm);
		// 为三个表单设置标题，存于titleList中
		titleList = new ArrayList<String>();// 每个页面的Title数据
		titleList.add("表单一");
		titleList.add("表单二");
		titleList.add("表单三");

		// 获取三个表单Fragment对应的view,存放于fragmentViewList里，
		fragmentViewList = new ArrayList<Fragment>();
		//
		fragmentViewList.add(new FirstItemFragment());
		fragmentViewList.add(new SecondItemFragment());
		fragmentViewList.add(new ThirdItemFragment());
	}

	/*
	 * 以下都是父类PagerAdapter的未实现方法的实现
	 */
	/**
	 * 得到每个Fragment
	 */
	@Override
	public Fragment getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return fragmentViewList.get(arg0);
	}

	/**
	 * 得到页数
	 */
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return (fragmentViewList == null) ? 0 : fragmentViewList.size();
	}

	/**
	 * 每个页面的title
	 */
	@Override
	public CharSequence getPageTitle(int position)
	{
		return (titleList.size() > position) ? titleList.get(position) : "";
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		super.destroyItem(container, position, object);
	}

	/**
	 * 每个得到每个页面的ID
	 */
	@Override
	public long getItemId(int position)
	{
		return super.getItemId(position);
	}
}