package com.gdut.supervisor.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gdut.supervisor.R;

/**
 * PopupMenu菜单
 */
public class MyActionProvider extends ActionProvider implements OnMenuItemClickListener
{
	private Context context;
	private LayoutInflater layoutInflater;
	private PopupMenu popupMenu;
	private View view;

	public MyActionProvider(Context context)
	{
		super(context);
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 返回的view即为显示在actionbar上可以点击的图标。
	 */
	@Override
	public View onCreateActionView()
	{
		view = layoutInflater.inflate(R.layout.action_provider, null);
		ImageView popupView = (ImageView) view.findViewById(R.id.popup_view);
		popupView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				popupMenu = new PopupMenu(context, v);
				popupMenu.setOnMenuItemClickListener(MyActionProvider.this);
				MenuInflater menuInflater = popupMenu.getMenuInflater();
				menuInflater.inflate(R.menu.main_menu, popupMenu.getMenu());
				popupMenu.show();
			}
		});
		return view;
	}

	/**
	 * 菜单点击监听
	 */
	@Override
	public boolean onMenuItemClick(MenuItem arg0)
	{
		AlertDialog.Builder builder = new Builder(context);
		builder.setInverseBackgroundForced(true);
		builder.setIcon(R.drawable.icon);
		// 设置是否点击外边缘取消显示，2.3默认
		builder.setCancelable(false);
		switch (arg0.getItemId())
		{
		// 帮助菜单
		case R.id.menu_help:
			view = layoutInflater.inflate(R.layout.alertdialog_menu_help, null);
			builder.setTitle("使用帮助");
			builder.setPositiveButton("确定", null);
			builder.setView(view);
			builder.create().show();
			break;
		// 关于菜单
		case R.id.menu_about:
			Toast.makeText(context, "关于", 0).show();
			break;
		case R.id.menu_setting:
			Toast.makeText(context, "设置", 0).show();
			break;
		// 设置菜单
		default:
			return false;
		}
		return true;
	}
}
