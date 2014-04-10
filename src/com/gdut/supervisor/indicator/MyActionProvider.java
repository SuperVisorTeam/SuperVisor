package com.gdut.supervisor.indicator;

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
 * PopupMenu�˵�
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
	 * ���ص�view��Ϊ��ʾ��actionbar�Ͽ��Ե����ͼ�ꡣ
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
	 * �˵��������
	 */
	@Override
	public boolean onMenuItemClick(MenuItem arg0)
	{
		AlertDialog.Builder builder = new Builder(context);
		builder.setInverseBackgroundForced(true);
		builder.setIcon(R.drawable.icon);
		// �����Ƿ������Եȡ����ʾ��2.3Ĭ��
		builder.setCancelable(false);
		switch (arg0.getItemId())
		{
		// �����˵�
		case R.id.menu_help:
			view = layoutInflater.inflate(R.layout.alertdialog_menu_help, null);
			builder.setTitle("ʹ�ð���");
			builder.setPositiveButton("ȷ��", null);
			builder.setView(view);
			builder.create().show();
			break;
		// ���ڲ˵�
		case R.id.menu_about:
			Toast.makeText(context, "����", 0).show();
			break;
		case R.id.menu_setting:
			Toast.makeText(context, "����", 0).show();
			break;
		//ע��
		case R.id.menu_logout:
			Toast.makeText(context, "ע��", 0).show();
			break;
		// ���ò˵�
		default:
			return false;
		}
		return true;
	}
}
