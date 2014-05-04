package com.gdut.supervisor.ui;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.adapter.PreEntryAdapter;
import com.gdut.supervisor.dialog.ShowProgressDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_Survey_OrderInfo;
import com.gdut.supervisor.utils.SubmitHandler;
import com.gdut.supervisor.view.RefreshableView;
import com.gdut.supervisor.view.RefreshableView.PullToRefreshListener;

public class PreEntryActivity extends ActionBarActivity implements OnItemClickListener, PullToRefreshListener,
		android.view.View.OnClickListener
{
	public static Cookie cookie;
	private static final int ORDER_OBTAIN = 0x123;
	private static final int ORDER_NULL = 0x124;
	private ListView listView;
	private String getOrderPath;
	private Spinner sp_term; // 学期
	private Spinner sp_week; // 周次
	private Spinner sp_institute; // 学院
	private Spinner sp_day; // 星期
	private Spinner sp_time; // 节次
	private getOrderHandler mHandler;
	private RefreshableView refreshableView;
	private Dialog allMsg;
	private View allMsgView;

	// 展示所有信息的dialog中的控件
	private List<List> listList;
	private List<List> listSize;
	private ImageButton imgBtn_dialog;
	private TextView txt_dialog_course, txt_dialog_classroom, txt_dialog_teacher, txt_dialog_classes;

	/**
	 * 处理得到的预约信息
	 */
	private class getOrderHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			ShowProgressDialog.dismissProgress();
			switch (msg.what)
			{
			case ORDER_OBTAIN:
				Map map = (Map) msg.obj;
				if (map != null)
				{
					// setAdapter操作
					listList = (List<List>) map.get("booking_class");
					listSize = (List<List>) map.get("size");
					listView.setAdapter(new PreEntryAdapter(PreEntryActivity.this, listSize, listList));

				} else
				{
					// 测试
					listView.setAdapter(new PreEntryAdapter(PreEntryActivity.this, null, null));
					Toast.makeText(getApplicationContext(), "没有可预约信息", 1).show();
				}
				break;
			case ORDER_NULL:
				Toast.makeText(PreEntryActivity.this, "请先填写预约查询信息！", 1).show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preentry);
		initView();
		mHandler = new getOrderHandler();
	}

	/**
	 * 初始化控件
	 */
	private void initView()
	{
		listView = (ListView) findViewById(R.id.lv_preentry);
		listView.setOnItemClickListener(this);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		// 下拉刷新
		refreshableView.setOnRefreshListener(this, 0);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// 展示所有数据的Dialog
		allMsgView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.dialog_all_pre_entry_msg, null);
		allMsg = new AlertDialog.Builder(this).create();
		allMsg.setCanceledOnTouchOutside(false);

		imgBtn_dialog = (ImageButton) allMsgView.findViewById(R.id.dialog_pre_entry_close);
		imgBtn_dialog.setOnClickListener(this);

		txt_dialog_classroom = (TextView) allMsgView.findViewById(R.id.txt_pre_entry_dialog_classroom);
		txt_dialog_course = (TextView) allMsgView.findViewById(R.id.txt_pre_entry_dialog_course);
		txt_dialog_teacher = (TextView) allMsgView.findViewById(R.id.txt_pre_entry_dialog_teacher);
		txt_dialog_classes = (TextView) allMsgView.findViewById(R.id.txt_pre_entry_dialog_classes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.pre_entry_actionbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
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
		// 增加菜单
		case R.id.menu_pre_entry:
			showOrderDialog();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 下拉刷新的监听
	 */
	@Override
	public void onRefresh()
	{
		try
		{
			if (getOrderPath != null)
			{
				Map map = SubmitHandler.getOrderableList(getOrderPath);
				Message msg = mHandler.obtainMessage();
				msg.what = ORDER_OBTAIN;
				msg.obj = map;
				mHandler.sendMessage(msg);

			} else
			{
				mHandler.sendEmptyMessage(ORDER_NULL);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			Log.e("log", "-->onRefresh()-ERROR!!!");
		}
		refreshableView.finishRefreshing();
	}

	/**
	 * ListView Item的监听
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
	{
		// Toast.makeText(this, "点击了第 " + (position + 1) + " 项", 0).show();
		try
		{
			txt_dialog_classroom.setText((CharSequence) listList.get(position).get(4));
			txt_dialog_teacher.setText((CharSequence) listList.get(position).get(3));
			txt_dialog_course.setText((CharSequence) listList.get(position).get(2));
			txt_dialog_classes.setText((CharSequence) listList.get(position).get(8));
		} catch (Exception e)
		{
			Toast.makeText(this, "预约信息未获取！", 1).show();
		}

		allMsg.show();
		allMsg.getWindow().setContentView((RelativeLayout) allMsgView);
	}

	/**
	 * 按钮监听
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// dialog的图片取消button
		case R.id.dialog_pre_entry_close:
			allMsg.dismiss();
			break;
		default:
			break;
		}
	}

	/**
	 * 启动预约信息填写的对话框
	 */
	public void showOrderDialog()
	{
		LinearLayout layout = (LinearLayout) View.inflate(this, R.layout.alertdialog_order_search, null);
		sp_term = (Spinner) layout.findViewById(R.id.sp_term);
		sp_institute = (Spinner) layout.findViewById(R.id.sp_institute);
		sp_week = (Spinner) layout.findViewById(R.id.sp_week);
		sp_day = (Spinner) layout.findViewById(R.id.sp_day);
		sp_time = (Spinner) layout.findViewById(R.id.sp_time);
		Button btn_ensure = (Button) layout.findViewById(R.id.btn_dialog_order_ensure);
		Button btn_cancel = (Button) layout.findViewById(R.id.btn_dialog_order_cancel);

		// 学期选项采用动态获取当前年份的方法实现，格式：2013-2014-1
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) - 1;
		String[] terms = new String[4];
		for (int i = 0; i < terms.length; i++)
		{
			terms[i] = year + "-" + (year + 1) + "-" + ((i % 2 == 0) ? "1" : "2");
			if (i % 2 == 1)
				year++;
		}
		System.out.println(Arrays.toString(terms));
		ArrayAdapter<String> termAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				terms);

		termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_term.setAdapter(termAdapter);
		final Dialog orderSearchDialog = new AlertDialog.Builder(this).create();
		orderSearchDialog.show();
		orderSearchDialog.getWindow().setContentView(layout);
		btn_ensure.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				ShowProgressDialog.showProgress(PreEntryActivity.this, "正在查询···");
				// 另启线程处理网络操作
				new Thread()
				{
					@Override
					public void run()
					{
						super.run();
						Looper.prepare();
						obtainOrderMsg();
						orderSearchDialog.dismiss();
						Looper.loop();
					}
				}.start();
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				orderSearchDialog.dismiss();
			}
		});
	}

	/**
	 * 获取预约信息的操作
	 */
	public void obtainOrderMsg()
	{
		String term = sp_term.getSelectedItem().toString();
		String week = sp_week.getSelectedItem().toString();
		String institute = null;
		int instituteID = (int) sp_institute.getSelectedItemId();
		if (instituteID < 9)
		{
			// 加一是为了和后台数据对应
			institute = "0" + (sp_institute.getSelectedItemId() + 1);
		} else
		{
			institute = sp_institute.getSelectedItemId() + 1 + "";
		}
		String day = sp_day.getSelectedItemId() + 1 + "";
		String time = sp_time.getSelectedItem().toString();
		if (term.equals("") || week.equals("") || institute.equals("") || day.equals("") || time.equals(""))
		{
			Toast.makeText(this, "请填写必要信息", Toast.LENGTH_SHORT).show();
		} else
		{
			Edu_Survey_OrderInfo.ORDER_TERM = term;
			Edu_Survey_OrderInfo.ORDER_WEEK = week;
			Edu_Survey_OrderInfo.ORDER_INSTITUTE = institute;
			Edu_Survey_OrderInfo.ORDER_DAY = day;
			Edu_Survey_OrderInfo.ORDER_TIME = time;
			Edu_Survey_OrderInfo.dayOfWeek = sp_day.getSelectedItem().toString();// 用于获取数据后ListView的显示周几信息

			// 接下来进行网络部分的操作
			// getOrderPath = BaseMessage.baseUrl + "";
			// http://192.168.1.177:8080/dudaobooking/{institute}/{semester}/{week}/{dayOfWeek}/{section}
			getOrderPath = BaseMessage.baseUrl + "/dudaobooking/" + institute + "/" + term + "/" + week + "/"
					+ day + "/" + time;
			Map map = SubmitHandler.getOrderableList(getOrderPath);
			Message msg = mHandler.obtainMessage();
			msg.what = ORDER_OBTAIN;
			msg.obj = map;
			mHandler.sendMessage(msg);

		}
	}

}
