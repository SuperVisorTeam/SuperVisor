package com.gdut.supervisor.view;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.ui.SupervisorActivity;

/**
 * 第三章表单
 */
public class ThirdItemFragment extends Fragment {
	/**
	 * 是否按时到达按钮
	 */
	private static RadioButton yesRadioButton;

	/**
	 * 是否按时到达按钮
	 */
	private static RadioButton noRadioButton;

	private static EditText[] editText = new EditText[1];
	/**
	 * 文本框的个数
	 */
	private static int editTextnum = 1;
	/**
	 * 督导员签名
	 */
	private static EditText supervisorEditText;
	/**
	 * 任课老师
	 */
	private static EditText teachernameEditText;
	/**
	 * 判断是否已经获得数据
	 */
	public static boolean isGet = false;
	/**
	 * 提交时间
	 */
	private static Button submitTime;
	/**
	 * 显示时间
	 */
	public static String temp_time;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	   
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.third, container, false);
		initView(rootView);
		return rootView;

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/**
	 * 初始化控件、添加监听
	 * 
	 * @param view
	 */
	private void initView(View view) {
		// 任课老师
		teachernameEditText = (EditText) view
				.findViewById(R.id.teachernameEditText);
		//
		int i = 0;
		editText[i++] = (EditText) view.findViewById(R.id.othercomEditText);
		supervisorEditText = (EditText) view
				.findViewById(R.id.supervisorEditText);
		//
		yesRadioButton = (RadioButton) view.findViewById(R.id.yes);
		noRadioButton = (RadioButton) view.findViewById(R.id.no);
		// 提交时间
		submitTime = (Button) view.findViewById(R.id.submitTime);
		submitTime.setClickable(true);
		submitTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				int mHour = c.get(Calendar.HOUR_OF_DAY);
				int mMinute = c.get(Calendar.MINUTE);
				LinearLayout layout = (LinearLayout) View.inflate(getActivity(), R.layout.timepicker, null);
				final TimePicker timePicker = (TimePicker) layout.findViewById(R.id.timepicker);
				timePicker.setCurrentHour(mHour);
				timePicker.setCurrentMinute(mMinute);
				
				AlertDialog.Builder timebuilder = new AlertDialog.Builder(getActivity());
				timebuilder.setTitle("检查时间：");
				timebuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int hour = timePicker.getCurrentHour();
						int mimute = timePicker.getCurrentMinute();
						String hourstr = hour < 10 ? "0" + hour: hour + "";
						String mimutestr = mimute < 10 ? "0" + mimute : mimute + "";
						temp_time = FirstItemFragment.dateString + " " + hourstr + ":" + mimutestr + ":00";
						BaseMessage.temp_time = temp_time;
						submitTime.setText(temp_time);
					}
				});
				timebuilder.setNegativeButton("取消", null);
				AlertDialog timeDialog = timebuilder.create();
				timeDialog.setCanceledOnTouchOutside(false);
				timeDialog.setView(layout);
				timeDialog.show();		
			}
		});
		
	}

	// 重置第三个菜单的数据列表
	public static void clearThirdItem() {
		yesRadioButton.setChecked(true);
		noRadioButton.setChecked(false);
		for (int i = 0; i < editTextnum; i++) {
			editText[i].setText(null);
			editText[i].clearFocus();
		}
		submitTime.setText(null);
		temp_time = "";
		supervisorEditText.setText(null);
		BaseMessage.teacherName = "";
		BaseMessage.temp_time = "";
		teachernameEditText.setText(null);
	}

	// 获得该表格是否填写完整
	public static boolean getIsFinish() {
		//如果打开表单的时候没划到第二个表单的话，这个表单是不会被初始化的，即控件都是null的,所以要判断下
		if(supervisorEditText==null||teachernameEditText==null||submitTime==null)
			return false;
		if (supervisorEditText.getText().toString().equals("")
				|| supervisorEditText.getText().toString() == null) {
			return false;
		} else if (teachernameEditText.getText().toString().equals("")
				|| teachernameEditText.getText().toString() == null) {
			return false;
		}
			else if(submitTime.getText().toString().equals("")||submitTime.getText().toString()==null){
				return false;
		}
		return true;
	}

	// 获得填写表格的内容
	public static void greatThirdItemClassSituation() {
		int i = 0;
		// 获取老师上课及时到达情况
		if (yesRadioButton.isChecked()) {
			byte a = 1;
			SupervisorActivity.situation.setTeacher_Ontime(a);
		} else {
			byte b = 0;
			SupervisorActivity.situation.setTeacher_Ontime(b);
		}
		// 获取上课老师的名字
		SupervisorActivity.edu_CourseClass.setTeacher_Name(teachernameEditText
				.getText().toString());
		// 获取检查时间
		SupervisorActivity.situation.setSurvey_Time(submitTime.getText()
				.toString());
		// 获取督导员学号
		SupervisorActivity.situation.setSupervisor(supervisorEditText.getText()
				.toString());
		// 获取“备注”
		SupervisorActivity.situation.setOther_Situation(editText[i++].getText()
				.toString());
	}

	// 设置填写表格的内容
	public static void setThirdItemClassSituation() {
		// 教师
		teachernameEditText.setText(SupervisorActivity.edu_CourseClass
				.getTeacher_Name());
		if (SupervisorFragment.searchIsOpen) {
			// 老师是否到时到达
			byte a = SupervisorActivity.situation.getTeacher_Ontime();
			if (a == 1) {
				yesRadioButton.setChecked(true);
				noRadioButton.setChecked(false);
			} else {
				yesRadioButton.setChecked(false);
				noRadioButton.setChecked(true);
			}
			// 其它情况
			int i = 0;
			editText[i++].setText(SupervisorActivity.situation
					.getOther_Situation());
		}

		// 督导员签名
		supervisorEditText
				.setText(SupervisorActivity.situation.getSupervisor());
		 
		
		
	}

	// 恢复时调用
	public void onResume() {
		super.onResume();
		if (!SupervisorFragment.searchIsOpen) {
			supervisorEditText.setText(BaseMessage.supervisor_no);
			teachernameEditText.setText(BaseMessage.teacherName);
		} else if (SupervisorFragment.searchIsOpen) {
			if (!isGet) {
				isGet = true;
				setThirdItemClassSituation();
			}
		}
		for (int i = 0; i < editTextnum; i++) {
			editText[i].clearFocus();
		}
		if (SupervisorFragment.scheduleIsOpen) {
			setThirdItemClassSituation();
		}
		submitTime.setText(temp_time);
	}



}
