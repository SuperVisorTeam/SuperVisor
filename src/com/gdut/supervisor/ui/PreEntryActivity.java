package com.gdut.supervisor.ui;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.adapter.PreEntryAdapter;
import com.gdut.supervisor.info.Edu_Survey_OrderInfo;
import com.gdut.supervisor.utils.SubmitHandler;

public class PreEntryActivity extends ActionBarActivity implements
		OnItemClickListener {
	private static final int ORDER_OBTAIN = 0;
	private ListView listView;
	private String summitUrl;
	private Spinner sp_term; // 学期
	private Spinner sp_week; // 周次
	private Spinner sp_institute; // 学院
	private Spinner sp_day; // 星期
	private Spinner sp_time; // 节次
	private Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_preentry);
		initView();
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case ORDER_OBTAIN:
					Map info = (Map) msg.obj;
					if (info != null) {
						// setAdapter操作
						
					} else {
						Toast.makeText(getApplicationContext(), "没有可预约信息",
								Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}
			}
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pre_entry_actionbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 菜单监听
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// 返回菜单
		case android.R.id.home:
			finish();
			break;
		// 增加菜单
		case R.id.menu_pre_entry:
			// Toast.makeText(this, "预约", 0).show();
			// String path = "http://www.baidu.com";
			// PreEntryAsyncTask asyncTask = new PreEntryAsyncTask();
			// asyncTask.execute(path);
			showOrderDialog();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		listView = (ListView) findViewById(R.id.lv_preentry);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * ListView Item的监听
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		Toast.makeText(this, "点击了第 " + (position + 1) + " 项", 0).show();
	}

	/**
	 * 异步进行预约操作
	 */
	private class PreEntryAsyncTask extends
			AsyncTask<String, Integer, String[]> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// 异步处理
		@Override
		protected String[] doInBackground(String... params) {
			Log.v("log", "-->params:" + params[0]);
			summitUrl = "http://10.21.32.123:8080/dudaoSaveBooking/"
					+ Edu_Survey_OrderInfo.course_Class_No + "/"
					+ Edu_Survey_OrderInfo.schedule_id + "/"
					+ Edu_Survey_OrderInfo.semester + "/"
					+ Edu_Survey_OrderInfo.dayOfWeek;
			try {
				// Gson gson = new Gson();
				// String json = gson.toJson(params[0]);
				// DefaultHttpClient client = new DefaultHttpClient();
				// HttpPost post = new HttpPost(summitUrl);
				// HttpEntity postEntity = new StringEntity(json, "UTF-8");
				// post.setEntity(postEntity);
				// post.setHeader("Content-Type",
				// "application/json;charset=UTF-8");
				//
				// HttpResponse response = client.execute(post);
				// HttpEntity responseEntity = response.getEntity();
				// String responseText = EntityUtils.toString(responseEntity,
				// HTTP.UTF_8);
				// Log.v("log", "-->预约返回状态码："
				// + response.getStatusLine().getStatusCode());
				// Map<String, List<List>> map = gson.fromJson(responseText,
				// HashMap.class);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("log", "ERROR!!!");
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String[] result) {
			listView.setAdapter(new PreEntryAdapter(PreEntryActivity.this, 10));
			listView.setOnItemClickListener(PreEntryActivity.this);
			super.onPostExecute(result);
		}

	}

	// ======================================================================================
	/**
	 * 启动预约信息填写的对话框
	 */
	public void showOrderDialog() {
		LinearLayout layout = (LinearLayout) View.inflate(this,
				R.layout.alertdialog_order_search, null);
		sp_term = (Spinner) layout.findViewById(R.id.sp_term);
		sp_institute = (Spinner) layout.findViewById(R.id.sp_institute);
		sp_week = (Spinner) layout.findViewById(R.id.sp_week);
		sp_day = (Spinner) layout.findViewById(R.id.sp_day);
		sp_time = (Spinner) layout.findViewById(R.id.sp_time);
		
		//学期选项采用动态获取当前年份的方法实现，格式：2013-2014-1
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) - 1;
		String[] terms = new String[4];
		for (int i = 0; i < terms.length; i ++) {
				terms[i] = year + "-" + (year + 1) + "-" + ((i % 2 == 0)? "1": "2");
				if(i % 2 == 1) year ++; 
		}
		System.out.println(Arrays.toString(terms));
		ArrayAdapter<String> termAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, terms);
		
		termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_term.setAdapter(termAdapter);
		new AlertDialog.Builder(this).setTitle("预约查询信息：").setView(layout)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 另启线程处理网络操作
						new Thread() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								super.run();
								setProgressBarIndeterminate(true);
								obtainOrderMsg();
							}
						}.start();
					}
				}).setNegativeButton("取消", null).show();

	}

	/**
	 * 获取预约信息的操作
	 */
	public void obtainOrderMsg() {
		String term = sp_term.getSelectedItem().toString();
		String week = sp_week.getSelectedItem().toString();
		String institute = null;
		int instituteID = (int) sp_institute.getSelectedItemId();
		if (instituteID < 9) {
			institute = "0" + (sp_institute.getSelectedItemId() + 1); // 加一是为了和后台数据对应
		} else {
			institute = sp_institute.getSelectedItemId() + 1 + "";
		}
		String day = sp_day.getSelectedItemId() + 1 + "";
		String time = sp_time.getSelectedItem().toString();
		if (term.equals("") || week.equals("") || institute.equals("")
				|| day.equals("") || time.equals("")) {
			Toast.makeText(this, "请填写必要信息", Toast.LENGTH_SHORT).show();
		} else {
			Edu_Survey_OrderInfo.ORDER_TERM = term;
			Edu_Survey_OrderInfo.ORDER_WEEK = week;
			Edu_Survey_OrderInfo.ORDER_INSTITUTE = institute;
			Edu_Survey_OrderInfo.ORDER_DAY = day;
			Edu_Survey_OrderInfo.ORDER_TIME = time;

			// 接下来进行网络部分的操作
			Map map = SubmitHandler.getOrderableList(term, institute, week,
					day, time);
			Message msg = handler.obtainMessage();
			msg.what = ORDER_OBTAIN;
			msg.obj = map;
			handler.sendMessage(msg);

		}
	}

}
