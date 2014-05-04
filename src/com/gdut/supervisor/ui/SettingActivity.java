package com.gdut.supervisor.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import com.gdut.supervisor.R;

public class SettingActivity extends ActionBarActivity implements OnClickListener
{
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private CheckBox chk_cirle, chk_tile;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		// 初始化
		init();
	}

	private void init()
	{
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		sharedPreferences = getSharedPreferences("STYLE", MODE_PRIVATE);
		
		chk_cirle = (CheckBox) findViewById(R.id.chk_setting_style_circle);
		chk_tile = (CheckBox) findViewById(R.id.chk_setting_style_tile);
		chk_cirle.setOnClickListener(this);
		chk_tile.setOnClickListener(this);

		switch (sharedPreferences.getInt("STYLE_MODE", 0))
		{
		case 0:
			chk_cirle.setChecked(true);
			break;
		case 1:
			chk_tile.setChecked(true);
			break;
		default:
			break;
		}
	}

	/**
	 * 菜单监听
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		// 返回菜单
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v)
	{
		editor = sharedPreferences.edit();
		switch (v.getId())
		{
		// 旋转菜单
		case R.id.chk_setting_style_circle:
			chk_cirle.setChecked(true);
			chk_tile.setChecked(false);

			
			editor.putInt("STYLE_MODE", 0);
			break;
		// 平铺菜单
		case R.id.chk_setting_style_tile:
			chk_tile.setChecked(true);
			chk_cirle.setChecked(false);
			
			editor.putInt("STYLE_MODE", 1);
			break;

		default:
			break;
		}
//		editor.putBoolean("IS_UPDATE", true);
		editor.commit();
	}

}
