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
 * ���Զ����SupervisorAdapter�̳���FragmentPagerAdapter ����������������Ӧ��Fragment
 * ���г�Ա�����ĸ�ֵ���ڹ��캯��SupervisorAdapter(FragmentManager fm)�
 * ����ÿ��ҳ��ı����ṩ��List<String> titleList��ÿ��ҳ���ṩ��List<View> viewList
 */
public class SupervisorAdapter extends FragmentPagerAdapter
{

	/**
	 * �洢SupervisorActivity�������Ķ���
	 */
	Context context;

	FragmentManager myFragmentManager;
	/**
	 * ����Ҫ������ҳ����ӵ����list��
	 */
	private List<Fragment> fragmentViewList;

	/**
	 * ��List����������ı���ı���
	 */
	private List<String> titleList;
	/**
	 * ����������ͼ����
	 */
	private LayoutParams param;

	// Ĭ�ϵĹ��캯��
	public SupervisorAdapter(FragmentManager fm)
	{
		super(fm);
		// Ϊ���������ñ��⣬����titleList��
		titleList = new ArrayList<String>();// ÿ��ҳ���Title����
		titleList.add("��һ");
		titleList.add("����");
		titleList.add("����");

		// ��ȡ������Fragment��Ӧ��view,�����fragmentViewList�
		fragmentViewList = new ArrayList<Fragment>();
		//
		fragmentViewList.add(new FirstItemFragment());
		fragmentViewList.add(new SecondItemFragment());
		fragmentViewList.add(new ThirdItemFragment());
	}

	/*
	 * ���¶��Ǹ���PagerAdapter��δʵ�ַ�����ʵ��
	 */
	/**
	 * �õ�ÿ��Fragment
	 */
	@Override
	public Fragment getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return fragmentViewList.get(arg0);
	}

	/**
	 * �õ�ҳ��
	 */
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return (fragmentViewList == null) ? 0 : fragmentViewList.size();
	}

	/**
	 * ÿ��ҳ���title
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
	 * ÿ���õ�ÿ��ҳ���ID
	 */
	@Override
	public long getItemId(int position)
	{
		return super.getItemId(position);
	}
}