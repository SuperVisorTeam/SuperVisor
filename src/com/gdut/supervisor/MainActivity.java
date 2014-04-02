package com.gdut.supervisor;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 主菜单界面,需导入support-v7-appcompat支持包
 */
public class MainActivity extends ActionBarActivity implements OnClickListener
{
	private RadioGroup mTabRg;
	private ActionBar mActionBar;
	private Menu mMenu;
	private EvaluateFragment evaluateFragment;
	private SupervisorFragment supervisorFragment;
	private SearchFragment searchFragment;
	private HelpFragment helpFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		InitView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.mMenu = menu;
		return true;
	}

	/**
	 * 初始化界面
	 */
	private void InitView()
	{
		//初始化四个Fragment
		evaluateFragment = EvaluateFragment.getInstance();
		supervisorFragment = SupervisorFragment.getInstance();
		searchFragment = SearchFragment.getInstance();
		helpFragment = HelpFragment.getInstance();
		// 设置ActionBar背景颜色
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_background));
		// 首次进入时先用SupervisorFragment(督导功能)来填充界面
		final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, supervisorFragment);
		fragmentTransaction.commit();

		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		// 为底部菜单按键添加监听
		mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				switch (checkedId)
				{
				// 督导功能
				case R.id.tab_rb_1:
					// 清除菜单
					mMenu.clear();
					fragmentTransaction.replace(R.id.fragment_content, supervisorFragment);
					break;
				// 查询表单
				case R.id.tab_rb_2:
					mMenu.clear();
					getMenuInflater().inflate(R.menu.actionbar_searchform, mMenu);
					fragmentTransaction.replace(R.id.fragment_content, searchFragment);
					break;
				// 评价老师
				case R.id.tab_rb_3:
					mMenu.clear();
					getMenuInflater().inflate(R.menu.actionbar_evaluate, mMenu);
					fragmentTransaction.replace(R.id.fragment_content,evaluateFragment);
					break;
				// 使用帮助
				case R.id.tab_rb_4:
					mMenu.clear();
					fragmentTransaction.replace(R.id.fragment_content, helpFragment);
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
				new AlertDialog.Builder(this).setTitle("提示").setMessage("确定退出吗？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								finish();
							}
						}).setNegativeButton("取消", null).show();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * Button响应
	 */
	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setCancelable(false);
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View view = inflater.inflate(R.layout.alertdialog_searchform_datechoose_datepicker, null);
		switch (v.getId())
		{
		// 选择开始时间的Button
		case R.id.btn_searchform_begindate:
			builder.setTitle("时间");
			builder.setView(view);
			builder.setPositiveButton("确定", null);
			builder.setNegativeButton("取消", null);
			builder.create().show();
			break;
		// 选择结束时间的Button
		case R.id.btn_searchform_enddate:
			builder.setTitle("时间");
			builder.setView(view);
			builder.setPositiveButton("确定", null);
			builder.setNegativeButton("取消", null);
			builder.create().show();
			break;
		// 其它的Button
		// ...
		default:
			break;
		}
	}

	/**
	 * 标题栏菜单按键响应
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setInverseBackgroundForced(true);
		builder.setIcon(R.drawable.icon);
		// 设置是否点击外边缘取消显示，2.3默认
		builder.setCancelable(false);
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View view = null;
		switch (item.getItemId())
		{
		// 查询表单的菜单Button
		case R.id.menu_actionbar_searchform:
			view = inflater.inflate(R.layout.alertdialog_serarchform_datechoose, null);
			builder.setTitle("请选择时间");
			builder.setPositiveButton("确定", null);
			builder.setNegativeButton("取消", null);
			Button btn1, btn2;
			btn1 = (Button) view.findViewById(R.id.btn_searchform_begindate);
			btn2 = (Button) view.findViewById(R.id.btn_searchform_enddate);
			btn1.setOnClickListener(this);
			btn2.setOnClickListener(this);
			builder.setView(view);
			builder.create().show();
			break;
		// 评价老师的菜单Button
		case R.id.menu_actionbar_evaluate_summit:
			builder.setTitle("提交表单");
			view = inflater.inflate(R.layout.alertdialog_evaluate_summit, null);
			builder.setNegativeButton("取消", null);
			builder.setView(view);
			builder.create().show();
			break;
		case android.R.id.home:
			Toast.makeText(this, "督导", 0).show();
		default:
			break;
		}
		
		return true;
	}
}
