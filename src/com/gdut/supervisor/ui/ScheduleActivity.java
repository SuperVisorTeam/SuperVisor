package com.gdut.supervisor.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.gdut.supervisor.R;
import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_CourseClass;
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
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ScheduleActivity extends Activity {
	/**
	 * 判断请求数据是否成功的信息
	 */
	protected static final int SUCCESS = 1;
	/**
	 * 刷新按钮
	 */
	private Button scheduleButton;
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
	 * 所有表格名称
	 */
	private static List<String> fromName;

	private static List<String> tempfromName;
	/**
	 * 所有表格的内容
	 */
	private static List<Edu_Survey> fromCom;
	/**
	 * 查找返回的数据包
	 */
	public static Map<String, List<List>> scheduleMap;
	private BaseMessage baseMessage;
    /**
     *
     */
    Handler handler=new Handler()
    {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==SUCCESS)
			{
				System.out.println(scheduleMap != null);
				if (SubmitHandler.getStatuCodeSchedule()==200) 
				{
					setDateList();
					Toast.makeText(ScheduleActivity.this,"刷新成功", 2*1000).show();
				} 
				else 
				{
					
					fromName = new ArrayList<String>();
					fromCom = new ArrayList<Edu_Survey>();
					showPage(1);
					pagenum.setText("共 0 页");
					nownum = 0;
					pageTextView.setText("预定表单" + "    " + "第(0)页");
					ShowMessageDialog.showMessage(ScheduleActivity.this,
							"对不起，没有找到预定的表单！！");
				}
			}
			
		}
    	
    };
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		//
		scheduleButton = (Button) findViewById(R.id.scheduleButton);
		pageTextView = (TextView) findViewById(R.id.schedulePage);
		lastButton = (Button) findViewById(R.id.schedulelastButton);
		goButton = (Button) findViewById(R.id.schedulegoButton);
		goEditText = (EditText) findViewById(R.id.schedulegoEditText);
		pagenum = (TextView) findViewById(R.id.schedulenum);
		nextButton = (Button) findViewById(R.id.schedulenextButton);
		listView = (ListView) findViewById(R.id.schedulefromListView);
		//
		lastButton.setOnClickListener(new ButtonOnClickListener());
		goButton.setOnClickListener(new ButtonOnClickListener());
		nextButton.setOnClickListener(new ButtonOnClickListener());
		scheduleButton.setOnClickListener(new ButtonOnClickListener());
		listView.setOnItemClickListener(new ListViewOnItemSelectedListener());
	    //暂时不注册长按事件监听器，等W后台！
		//listView.setOnItemLongClickListener(new OnItemLongClickListenter());
		if (scheduleMap == null) {
			getScheduleMap(BaseMessage.supervisor_no);
		} else {
			setDateList();
		}
	}

	/**
	 * 获取预定录入的表单
	 */
	private void getScheduleMap(final String supervisor_no) {
		nownum = 1;
		new Thread(){

			@Override
			public void run() {
				Looper.prepare();
				try {
					scheduleMap = SubmitHandler.getScheduleMap(supervisor_no);
				} catch (ClientProtocolException e) {
					Toast.makeText(ScheduleActivity.this, "网络环境异常", 2*1000).show();			
					e.printStackTrace();
					return;
				} catch (IOException e) {
					Toast.makeText(ScheduleActivity.this, "网络环境异常", 2*1000).show();
					e.printStackTrace();
					return;
				}
				System.out.println("scheduleMap===" + scheduleMap);
				
					Message message=new Message();
					message.what=SUCCESS;
					handler.sendMessage(message);
			
			}
			
		}.start();
		
		
		return;
	}

	private void showPage(int pageID) {
		tempfromName = new ArrayList<String>();
		for (int i = pageID * shownum - shownum; i < pageID * shownum
				&& i < fromName.size(); i++) {

			tempfromName.add(fromName.get(i));
		}
		listViewAdapter = new ArrayAdapter<String>(ScheduleActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				StringHandler.exchangeListToStringArr(tempfromName));
		listView.setAdapter(listViewAdapter);
		pageTextView.setText("预定表单" + "\n" + "第(" + nownum + ")页");
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

	// 返回事件
	private void back() {
		SupervisorFragment.scheduleIsOpen = false;
		scheduleMap = null;
		Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private class ButtonOnClickListener implements OnClickListener {

		public void onClick(View v) {
			if (v.getId() == R.id.scheduleButton) {
				scheduleButton.startAnimation(AnimationUtils.loadAnimation(ScheduleActivity.this, R.anim.button_anim));
				scheduleMap = null;
				getScheduleMap(BaseMessage.supervisor_no);
			
			}
			if (v.getId() == R.id.schedulelastButton) {
				nownum--;
				if (nownum < min) {
					nownum = min;
				} else {
					showPage(nownum);
				}
			}
			if (v.getId() == R.id.schedulegoButton) {
				if (goEditText.getText().toString() == null
						|| goEditText.getText().toString().equals("")) {
					ShowMessageDialog.showMessage(ScheduleActivity.this,
							"请输入你要跳转的页数！！");
				} else {
					goID = Integer.valueOf(goEditText.getText().toString());
					if (goID > max || goID < min) {
						ShowMessageDialog.showMessage(ScheduleActivity.this,
								"输入的页数不存在，请重新输入！！");
						goEditText.setText(null);
						goEditText.clearFocus();
					} else {
						goPage();
					}
				}
			}
			if (v.getId() == R.id.schedulenextButton) {
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
			SupervisorFragment.scheduleIsOpen = true;
			id = nownum * shownum - shownum + arg2;
			showPage(nownum);
			Intent intent = new Intent(ScheduleActivity.this,
					SupervisorActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
   private class OnItemLongClickListenter implements OnItemLongClickListener
   {

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		id = nownum * shownum - shownum + arg2;
		showPage(nownum);
		 AlertDialog dialogDelete=showDeleteDialog(id);
		 dialogDelete.show();
		return true;
	}

	
	   
   }
	//
	private void setDateList() {
		fromName = new ArrayList<String>();
		fromCom = new ArrayList<Edu_Survey>();
		List<List> snum = (List<List>) scheduleMap.get("size");
		int num = Integer.valueOf((String) snum.get(0).get(0));
		System.out.println("size1: " + num);
		List<List> booking_class = (List<List>) scheduleMap
				.get("booking_class");
		for (int j = 0; j < num; j++) {
			Edu_Survey scheduleMessage = new Edu_Survey();
			Edu_CourseClass scheduleCourse = new Edu_CourseClass();
			//
			scheduleMessage.setSupervisor(BaseMessage.supervisor_no);
			
			// classno
			//教学班标号	
			scheduleCourse.setCourse_Class_No((String) booking_class.get(j)
					.get(0));
			BaseMessage.class_no = (String) booking_class.get(j)
					.get(0);
			System.out
					.println("cno:===" + (String) booking_class.get(j).get(0));
			// 上课时间
			scheduleMessage
					.setLesson_date((String) booking_class.get(j).get(1));
			//
			scheduleMessage.setLesson_section((String) booking_class.get(j)
					.get(5));
			//
			int site = Integer.valueOf((String) booking_class.get(j).get(3));
			scheduleCourse.setSchool_District(site + "");
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
			//
			scheduleCourse
					.setTeacher_Name((String) booking_class.get(j).get(4));
			//
			scheduleMessage.setLesson_classroom((String) booking_class.get(j)
					.get(6));
			//
			scheduleMessage.setStudent_Faculty((String) booking_class.get(j)
					.get(7));
			//
			int plan_num = Integer
					.valueOf((String) booking_class.get(j).get(8));
			scheduleCourse.setPlan_Population(plan_num);
			//
			scheduleCourse.setTeaching_Class_Group((String) booking_class
					.get(j).get(9));
			//
			fromName.add("(" + (String) booking_class.get(j).get(1) + ")" + "-"
					+ (String) booking_class.get(j).get(5) + "-"
					+ school_district + "("
					+ (String) booking_class.get(j).get(6) + ")");
			scheduleMessage.setCourse_class_no(scheduleCourse);
			fromCom.add(scheduleMessage);
		}
		sumnum = fromName.size() / shownum;
		if ((fromName.size() % shownum) != 0) {
			sumnum++;
		}
		max = sumnum;
		pagenum.setText("共" + sumnum + "页");
		showPage(nownum);
	}

	/**
	 * 获取具体的表单信息
	 */
	public static Edu_Survey getSearchBaseMessage() {
		Edu_Survey scheduleMessage = fromCom.get(id);
		System.out.println("get(id)" + id);
		PrintlnFromData.println(scheduleMessage, "预定时表格的内容:");
		return scheduleMessage;
	}
	
	/**
	 *返回一个删除预定提示框
	 *框里提示要删除的条目
	 * @param id2 
	 */
	private AlertDialog showDeleteDialog(int itemId)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
		builder.setMessage(fromName.get(itemId));
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(ScheduleActivity.this, "删除成功", 2*1000).show();
			}
			
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(ScheduleActivity.this, "删除取消", 2*1000).show();
				
			}
			
		});
		AlertDialog DeleteDialog=builder.create();
		DeleteDialog.setTitle("删除预定");
		return DeleteDialog;
	}
}
