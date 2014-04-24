package com.gdut.supervisor.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import com.gdut.supervisor.R;
import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_Survey;
import com.gdut.supervisor.utils.PrintlnFromData;
import com.gdut.supervisor.utils.StringHandler;
import com.gdut.supervisor.utils.SubmitHandler;
import com.gdut.supervisor.view.SupervisorFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;

public class SearchFormActivity extends Activity {
	/**
	 *获取历史数据成功的标志
	 */
	protected static final int GRTSEARCHBASESUCCESS = 0X111;
	/**
	 *请求数据的标志码
	 */
	final int SUBMIT_GETMAP2=0x11;
	/**
	 *请求数据的标志码
	 */
	final int SUBMIT_GETEdu_Survey=0x22;
	/**
	 * 督导员学号
	 */
	private static EditText supervisorEditText;
	/**
	 * 查询时的开始日期
	 */
	private Button startDate;
	/**
	 * 查询时的结束日期
	 */
	private static Button endDate;
	/**
	 * 精确查找按钮
	 */
	private Button searchButton;
	/**
	 * 显示当前是第几页
	 */
	private TextView pageTextView;
	/**
	 * 上一页按钮
	 */
	private Button lastButton;
	/**
	 * 跳转页数
	 */
	private EditText goEditText;
	/**
	 * 跳转按钮
	 */
	private Button goButton;
	/**
	 * 共多少页的标签
	 */
	private TextView pagenum;
	/**
	 * 下一页按钮
	 */
	private Button nextButton;

	/**
	 * 未提交菜单的listview
	 */
	private ListView listView;
	/**
	 * 列表按件适配器
	 */
	private ArrayAdapter<String> listViewAdapter;
	/**
	 * 上一页的最小按下次数
	 */
	public static int min = 1;
	/**
	 * 下一页的最大按下次数
	 */
	public static int max = 0;

	/**
	 * 一页要显示的条数
	 */
	public static int shownum = 3;
	/**
	 * 一共有多少页
	 */
	public static int sumnum = 0;
	/**
	 * 现在显示的页数
	 */
	public static int nownum = 1;
	/**
	 * 选择列表的ID
	 */
	public static int id = 0;
	/**
	 * goID
	 */
	private static int goID = 0;
	/**
	 * 日期窗口
	 */
	private AlertDialog dataDialog;
	/**
	 * 日期
	 */
	private String dateString = "";
	/**
	 * 年
	 */
	private int year;
	/**
	 * 月
	 */
	private int month;
	/**
	 * 日
	 */
	private int day;
	/**
	 * 精确查找窗口
	 */
	private AlertDialog searchDialog;
	/**
	 * 审核窗口
	 */
	private AlertDialog auditDialog;

	/**
	 * 所有表格名称
	 */
	private static List<String> fromName;

	private static List<String> tempfromName;
	/**
	 * 所有表格的内容
	 */
	private static List<String> fromCom;
	/**
	 * 创建是否打开了开始周的按钮
	 */
	public static boolean startIsOpen = false;
	/**
	 * 创建是否打开了结束周的按钮
	 */
	public static boolean endIsOpen = false;
	/**
	 * 查找返回的数据包
	 */
	public static Map<String, List<List>> searchMap;
	/**
	 * 该表单数据是否审核
	 */
	public static boolean[] isAudit;
	/**
	 * 所有表格的内容
	 */
	private static List<String> AuditCom;
	/**
	 * 
	 */
    Handler handler=new Handler()
    {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==SUBMIT_GETMAP2)
			{
				if (searchMap != null) {
					setDateList();
				} else {
					AuditCom = new ArrayList<String>();
					fromName = new ArrayList<String>();
					fromCom = new ArrayList<String>();
					showPage(1);
					pagenum.setText("共 0 页");
					nownum = 0;
					pageTextView.setText("查找表单" + "    " + "第(0)页");
					ShowMessageDialog.showMessage(SearchFormActivity.this,
							"对不起，没有找到查找表单！！");
				}
				
			}
			else if(msg.what==GRTSEARCHBASESUCCESS)
			{
				Intent intent = new Intent(SearchFormActivity.this,
						SupervisorActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				Toast.makeText(SearchFormActivity.this, "获取历史数据成功", 2*1000).show();
			}
			else
				if(msg.what==-GRTSEARCHBASESUCCESS)
				{
					Toast.makeText(SearchFormActivity.this, "查找历史数据失败", 2*1000).show();					
				}
		}
    	
    };
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		//
		pageTextView = (TextView) findViewById(R.id.searchPage);
		lastButton = (Button) findViewById(R.id.searchlastButton);
		goButton = (Button) findViewById(R.id.searchgoButton);
		searchButton = (Button) findViewById(R.id.searchButton);
		goEditText = (EditText) findViewById(R.id.searchgoEditText);
		pagenum = (TextView) findViewById(R.id.searchnum);
		nextButton = (Button) findViewById(R.id.searchnextButton);
		listView = (ListView) findViewById(R.id.searchfromListView);
		//
		if (searchMap == null) {
			inisearchDialogDialog();
			searchDialog.show();
		} else {
			setDateList();
		}
		//
		lastButton.setOnClickListener(new ButtonOnClickListener());
		searchButton.setOnClickListener(new ButtonOnClickListener());
		goButton.setOnClickListener(new ButtonOnClickListener());
		nextButton.setOnClickListener(new ButtonOnClickListener());
		listView.setOnItemClickListener(new ListViewOnItemSelectedListener());
		SupervisorFragment.searchIsOpen=true;
	}

	private void showPage(int pageID) {
		tempfromName = new ArrayList<String>();
		for (int i = pageID * shownum - shownum; i < pageID * shownum
				&& i < fromName.size(); i++) {

			tempfromName.add(fromName.get(i));

		}
		listViewAdapter = new ArrayAdapter<String>(SearchFormActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				StringHandler.exchangeListToStringArr(tempfromName));
		listView.setAdapter(listViewAdapter);
		pageTextView.setText("查找表单" + "    " + "第(" + nownum + ")页");
		goEditText.setText(null);
		goEditText.clearFocus();
	}

	// 系统返回事件
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			back();
			return false;
		}

		return false;
	}

	/**
	 * 返回事件
	 */
	private void back() {
		SupervisorFragment.searchIsOpen = false;
		searchMap = null;
		Intent intent = new Intent(SearchFormActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private class ButtonOnClickListener implements OnClickListener {

		public void onClick(View v) {
			if (v.getId() == R.id.searchButton) {
				inisearchDialogDialog();
				searchDialog.show();
			}
			if (v.getId() == R.id.searchlastButton) {
				nownum--;
				if (nownum < min) {
					nownum = min;
				} else {
					showPage(nownum);
				}
			}
			if (v.getId() == R.id.searchgoButton) {
				if (goEditText.getText().toString() == null
						|| goEditText.getText().toString().equals("")) {
					ShowMessageDialog.showMessage(SearchFormActivity.this,
							"请输入你要跳转的页数！！");
				} else {
					goID = Integer.valueOf(goEditText.getText().toString());
					if (goID > max || goID < min) {
						ShowMessageDialog.showMessage(SearchFormActivity.this,
								"输入的页数不存在，请重新输入！！");
						goEditText.setText(null);
						goEditText.clearFocus();
					} else {
						goPage();
					}
				}
			}
			if (v.getId() == R.id.searchnextButton) {
				nownum++;
				if (nownum > max) {
					nownum = max;
				} else {
					showPage(nownum);
				}
			}
		}

		private void goPage() {
			nownum = Integer.valueOf(goEditText.getText().toString());
			showPage(nownum);
		}
	}

	// 列表事件
	private class ListViewOnItemSelectedListener implements OnItemClickListener {
		

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			id = nownum * shownum - shownum + arg2;
			if (isAudit[id]) {
				iniauditDialog();
			} else {
				showPage(nownum);
				SupervisorFragment.searchIsOpen = true;
				System.out.println("SupervisorFragment.searchIsOpen ="+SupervisorFragment.searchIsOpen);
				
				new Thread()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						SupervisorActivity.situation=null;
						SupervisorActivity.situation = getSearchBaseMessage();
						if(SupervisorActivity.situation!=null)
						{
							
							handler.sendEmptyMessage(GRTSEARCHBASESUCCESS);
						}
						else
						{
							handler.sendEmptyMessage(-GRTSEARCHBASESUCCESS);
						}
							
					}
					
				}.start();
				
				
			
			}
		}
	}

	/**
	 * 审核窗口
	 */
	private void iniauditDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SearchFormActivity.this);
		builder.setPositiveButton("查看数据",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						showPage(nownum);
						Intent intent = new Intent(SearchFormActivity.this,
								SupervisorActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}

				}).setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		auditDialog = builder.create();
		auditDialog.setTitle("提示");
		auditDialog.setMessage("该条数据已经被审核，不能执行修改操作！！");
		auditDialog.show();
	}

	/**
	 * 日期窗口
	 */
	private void iniDateDialog() {
		// 日期窗口

		AlertDialog.Builder builder = new AlertDialog.Builder(
				SearchFormActivity.this);
		LayoutInflater mInflater2 = LayoutInflater
				.from(SearchFormActivity.this);
		View view2 = mInflater2.inflate(R.layout.date, null);
		DatePicker datePicker = (DatePicker) view2
				.findViewById(R.id.datePicker);
		Calendar c = Calendar.getInstance(Locale.CHINA);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dateString = year + "-" + (month + 1) + "-" + day;
		datePicker.init(year, month, day, new OnDateChangedListener() {

			public void onDateChanged(DatePicker arg0, int year, int month,
					int day) {
				SearchFormActivity.this.year = year;
				SearchFormActivity.this.month = month;
				SearchFormActivity.this.day = day;
				dateString = year + "-" + (month + 1) + "-" + day;
				dataDialog.setTitle("日期：" + dateString);
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if (startIsOpen) {
					startDate.setText(dateString);
				} else {
					endDate.setText(dateString);
				}
			}

		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dataDialog = builder.create();
		dataDialog.setTitle("日期：" + dateString);
		dataDialog.setView(view2);
	}

	/**
	 * 查询时弹出的窗口
	 */
	private void inisearchDialogDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SearchFormActivity.this);
		LayoutInflater mInflater = LayoutInflater.from(SearchFormActivity.this);
		View view1 = mInflater.inflate(R.layout.check, null);
		Calendar c = Calendar.getInstance(Locale.CHINA);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dateString = year + "-" + (month + 1) + "-" + day;
		// 开始日期
		startDate = (Button) view1.findViewById(R.id.startdate);
		supervisorEditText = (EditText) view1.findViewById(R.id.supervisor_no);
		if (BaseMessage.supervisor_no != null
				&& !BaseMessage.supervisor_no.equals("")) {
			supervisorEditText.setText(BaseMessage.supervisor_no);
		}
		startDate.setText(dateString);
		startDate.setTextSize(20.0f);
		startDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startIsOpen = true;
				endIsOpen = false;
				iniDateDialog();
				dataDialog.show();
			}
		});
		// 结束日期
		endDate = (Button) view1.findViewById(R.id.enddate);
		endDate.setTextSize(20.0f);
		endDate.setText(dateString);
		endDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startIsOpen = false;
				endIsOpen = true;
				iniDateDialog();
				dataDialog.show();
			}
		});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if (startDate.getText().toString() == null
						|| startDate.getText().toString().equals("")
						|| endDate.getText().toString() == null
						|| endDate.getText().toString().equals("")) {

					return;
				} else {									
						search(supervisorEditText.getText().toString(), startDate
							.getText().toString(), endDate.getText().toString());
					
				}
			}

		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				back();
			}
		});
		searchDialog = builder.create();
		searchDialog.setTitle("查询");
		searchDialog.setView(view1);
	}

	//
	private void setDateList() {
		AuditCom = new ArrayList<String>();
		fromName = new ArrayList<String>();
		fromCom = new ArrayList<String>();
		List<List> snum = (List<List>) searchMap.get("size");
		int num = Integer.valueOf((String) snum.get(0).get(0));
		System.out.println("size1: " + num);
		List<List> dudao = (List<List>) searchMap.get("dudao");
		for (int j = 0; j < num; j++) {
			int site = Integer.valueOf((String) dudao.get(j).get(3));
			String school_district = "";
			switch (site) {
			case 1: {
				school_district = "大学城";
			}
				break;
			case 2: {
				school_district = "龙洞";
			}
				break;
			case 3: {
				school_district = "东风路";
			}
				break;
			case 4: {
				school_district = "商学院";
			}
				break;
			}
			fromCom.add((String) dudao.get(j).get(0));
			fromName.add("(" + ((String) dudao.get(j).get(1)).substring(0, 10)
					+ ")" + "-" + (String) dudao.get(j).get(5) + "-"
					+ school_district + "(" + (String) dudao.get(j).get(6)
					+ ")");
			AuditCom.add((String) dudao.get(j).get(7));
		}

		sumnum = fromName.size() / shownum;

		if (isAudit == null) {
			isAudit = new boolean[AuditCom.size()];
			for (int i = 0; i < AuditCom.size(); i++) {
				if (AuditCom.get(i).equals("1")) {
					isAudit[i] = true;
				} else {
					isAudit[i] = false;
				}
			}
		}
		if ((fromName.size() % shownum) != 0) {
			sumnum++;
		}
		max = sumnum;
		pagenum.setText("共" + sumnum + "页");
		showPage(nownum);
	}

	// 查找请求
	private void search(final String supername, final String starttime, final String endtime) {
		isAudit = null;
		nownum = 1;
		new Thread()
		{

			@Override
			public void run() {
				searchMap = SubmitHandler.getMap2(supername, starttime, endtime);
				System.out.println("searchMap===" + searchMap);
			handler.sendEmptyMessage(SUBMIT_GETMAP2);					
			}
			
		}.start();
		
	}

	/**
	 * 获取具体的表单信息，有网络请求！
	 * 
	 */
	public static Edu_Survey getSearchBaseMessage() {
		Edu_Survey searchMessage = new Edu_Survey();
		String s = fromCom.get(id);
		System.out.println("get(id)" + s);
		
		searchMessage = SubmitHandler.getEdu_Survey(s);
		searchMessage.setSurvey_ID(s);
		PrintlnFromData.println(searchMessage, "查找时表格的内容:");
		return searchMessage;
	}

	/**
	 * 获得该表单的审核状态
	 */
	public static boolean getAudit() {
		return isAudit[id];
	}
}
