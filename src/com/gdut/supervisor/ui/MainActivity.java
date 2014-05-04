package com.gdut.supervisor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.gdut.supervisor.R;
import com.gdut.supervisor.view.EducationalFragment;
import com.gdut.supervisor.view.EvaluateFragment;
import com.gdut.supervisor.view.SupervisorFragment;
import com.gdut.supervisor.view.ToolsFragment;

/**
 * 主菜单界面,需导入support-v7-appcompat支持包
 */
public class MainActivity extends ActionBarActivity
{
	/**
	 * 底部菜单组
	 */
	private RadioGroup mTabRg;
	/**
	 * 评价功能的Fragment
	 */
	private EvaluateFragment evaluateFragment;
	/**
	 * 督导功能的Fragment
	 */
	private SupervisorFragment supervisorFragment;
	/**
	 * 教务功能的Fragment
	 */
	private EducationalFragment educationalFragment;
	/**
	 * 工具功能的Fragment
	 */
	private ToolsFragment toolsFragment;
	/**
	 * 离开窗口
	 */
	private AlertDialog leaveDialog;

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	/**
	 * 设置保存的sharedPreferences
	 */
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		init();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.detach(supervisorFragment);
		fragmentTransaction.attach(supervisorFragment);
		fragmentTransaction.commit();
	}

	@Override
	protected void onStart()
	{
		super.onStart();

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.popup_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 初始化界面
	 */
	private void init()
	{
		// 初始化各种窗口
		iniDialog();
		// 初始化四个Fragment

		evaluateFragment = EvaluateFragment.getInstance();
		supervisorFragment = SupervisorFragment.getInstance();
		educationalFragment = EducationalFragment.getInstance();
		toolsFragment = ToolsFragment.getInstance();

		fragmentManager = getSupportFragmentManager();

		// 首次进入时先用SupervisorFragment(督导功能)来填充界面

		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, supervisorFragment);
		fragmentTransaction.commit();

		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		// 为底部菜单按键添加监听
		mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				fragmentTransaction = fragmentManager.beginTransaction();
				switch (checkedId)
				{
				// 督导
				case R.id.tab_rb_1:
					fragmentTransaction.replace(R.id.fragment_content, supervisorFragment);
					break;
				// 教务
				case R.id.tab_rb_2:
					fragmentTransaction.replace(R.id.fragment_content, educationalFragment);
					break;
				// 评价
				case R.id.tab_rb_3:
					fragmentTransaction.replace(R.id.fragment_content, evaluateFragment);
					break;
				// 工具
				case R.id.tab_rb_4:
					fragmentTransaction.replace(R.id.fragment_content, toolsFragment);
					break;
				default:
					break;
				}
				fragmentTransaction.commit();
			}
		});
	}
	/**
	 * 返回键的响应
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
		{
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0)
			{
				leaveDialog.show();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	// 初始化各种窗口
	private void iniDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("您确定要退出督导系统 吗？").setPositiveButton("后台运行", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				runInBG();
			}
		}).setNegativeButton("退出", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				// exit();
			}
		}).setNeutralButton("取消", null);
		leaveDialog = builder.create();
		leaveDialog.setTitle("退出");
	}

	/**
	 * 后台运行
	 */
	private void runInBG()
	{
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	/**
	 * 退出
	 */
	private void exit()
	{
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
