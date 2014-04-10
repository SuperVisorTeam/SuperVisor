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
 * �������ܵ�Fragment
 */
@SuppressLint("ValidFragment")
public class SupervisorFragment extends Fragment implements OnClickListener
{
	/**
	 * ֱ��¼�밴ť
	 */
	private ImageButton btn_directwrite;
	/**
	 * Ԥ��¼�밴ť
	 */
	private ImageButton btn_preparewrite;
	/**
	 * �߿�����
	 */
	private AlertDialog leaveDialog;
	/**
	 * �жϲ�ѯ�����Ƿ��
	 */
	public static boolean searchIsOpen = false;
	/**
	 * �ж�Ԥ�������Ƿ��
	 */
	public static boolean scheduleIsOpen = false;
	
	public static SupervisorFragment supervisorFragment;

	/**
	 * ˽�л����췽��
	 */
	private SupervisorFragment()
	{
	}

	/**
	 * �ڲ��࣬��֤����ʱʱ�̰߳�ȫ�ġ�
	 */
	private static class SingletonHolder
	{
		private static SupervisorFragment instance = new SupervisorFragment();
	}

	/**
	 * �ṩ�ⲿ����SupervisorFragment����ķ�����
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

	// Ϊ��ť��Ӽ���
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// Ԥ��¼�밴ť
		case R.id.ib_supervisor_preparewrite:

			break;
		// ֱ��¼�밴ť
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
