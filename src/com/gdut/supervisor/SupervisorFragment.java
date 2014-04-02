package com.gdut.supervisor;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
/**
 * 督导功能的Fragment
 */
@SuppressLint("ValidFragment")
public class SupervisorFragment extends Fragment implements OnClickListener
{
	private ImageButton btn_directwrite, btn_preparewrite;
	public static SupervisorFragment supervisorFragment;

	/**
	 * 私有化构造方法
	 */
	private SupervisorFragment()
	{
		Log.v("log", "-->SupervisorFragment-SupervisorFragment()");
	}
	/**
	 * 创建SupervisorFragment对象，使用同步。
	 */
	private static synchronized void syscInt()
	{
		if (supervisorFragment == null)
		{
			Log.v("log", "-->SupervisorFragment-syscInt()");
			supervisorFragment = new SupervisorFragment();
		}
	}
	/**
	 *提供外部创建SupervisorFragment对象的方法。 
	 */
	public static SupervisorFragment getInstance()
	{
		if (supervisorFragment == null)
		{
			Log.v("log", "-->SupervisorFragment-getInstance()");
			syscInt();
		}
		return supervisorFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View parentView = inflater.inflate(R.layout.supervisor_fragment, container, false);
		btn_directwrite = (ImageButton)parentView.findViewById(R.id.ib_supervisor_directwrite);
		btn_preparewrite = (ImageButton)parentView.findViewById(R.id.ib_supervisor_preparewrite);
		btn_directwrite.setOnClickListener(this);
		btn_preparewrite.setOnClickListener(this);
		return parentView;
	}
	//为按钮添加监听
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		//预定录入按钮
		case R.id.ib_supervisor_preparewrite:
			
			break;
		//直接录入按钮
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
