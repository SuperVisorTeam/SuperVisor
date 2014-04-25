package com.gdut.supervisor.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.ui.SupervisorActivity;


/**
 * 第三章表单
 */
public class ThirdItemFragment extends Fragment 
{
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
	private static EditText submitTime;
	/**
	 * 显示时间
	 */
	public static String temp_time;
	/**
	 * 提交时间按钮
	 */
	private static Button submit_time_bt;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.third, container, false);
		initView(rootView);
		return rootView;

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}

	/**
	 * 初始化控件、添加监听
	 * 
	 * @param view
	 */
	private void initView(View view)
	{
		// 任课老师
				teachernameEditText = (EditText) view.findViewById(R.id.teachernameEditText);
				//
				int i = 0;
				editText[i++] = (EditText) view.findViewById(R.id.othercomEditText);
				supervisorEditText = (EditText) view.findViewById(R.id.supervisorEditText);
				//
				yesRadioButton = (RadioButton) view.findViewById(R.id.yes);
				noRadioButton = (RadioButton) view.findViewById(R.id.no);
				// 提交时间
				submitTime = (EditText) view.findViewById(R.id.submitTime);
				submit_time_bt = (Button) view.findViewById(R.id.submit_time_bt);
				submit_time_bt.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (v.getId() == R.id.submit_time_bt) {
							temp_time = gainTime();
							BaseMessage.temp_time  = temp_time;
		
							submitTime.setText(temp_time);
						}

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
			teachernameEditText.setText(null);
		}

		// 获得该表格是否填写完整
		public static boolean getIsFinish() {
			if (supervisorEditText.getText().toString().equals("")
					|| supervisorEditText.getText().toString() == null) {
				return false;
			} else if (teachernameEditText.getText().toString().equals("")
					|| teachernameEditText.getText().toString() == null) {
				return false;
			}
			return true;
		}

		// 获得填写表格的内容
		public static void greatThirdItemClassSituation() {
			int i = 0;
			//获取老师上课及时到达情况
			if (yesRadioButton.isChecked()) {
				byte a = 1;
				SupervisorActivity.situation.setTeacher_Ontime(a);
			} else {
				byte b = 0;
				SupervisorActivity.situation.setTeacher_Ontime(b);
			}
			//获取上课老师的名字
			SupervisorActivity.edu_CourseClass.setTeacher_Name(teachernameEditText
					.getText().toString());
            //获取检查时间
			SupervisorActivity.situation.setSurvey_Time(submitTime.getText().toString());
			//获取督导员学号
			SupervisorActivity.situation.setSupervisor(supervisorEditText.getText()
					.toString());
			//获取“备注”
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
			temp_time = "";
			submitTime.setText(null);
			submit_time_bt.setFocusable(false);
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
		}

		// 获取当前时间
		private String gainTime() {
			Date date = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 将date对象转换成字符串
			String string_date = sdf2.format(date);
			return string_date;
		}

	
}
