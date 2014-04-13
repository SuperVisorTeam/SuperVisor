package com.gdut.supervisor.view;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.gdut.supervisor.R;
import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.ui.SupervisorActivity;
import com.gdut.supervisor.utils.ClassGroupXMLHandler;
import com.gdut.supervisor.utils.ClassNameHandler;
import com.gdut.supervisor.utils.ClassRoomGroupXMLHandler;
import com.gdut.supervisor.utils.SubmitHandler;

/**
 * 实现第一个表单功能的Fragment。
 * 该Fragmentl里面的控件按功能可分为三类，一类是让用户输入查询条件的控件包括
 * ：【时间日期】按钮，【学校校区】spinner选择器,【检查节次】spinner选择器，上课地点 

*按钮；一类是显示查询结果的控件：【学院名称】文本框，专业班级按钮，应到人数文本框；
 *一类是要提交的数据的数据框，如【实到人数】输入框以及后两个Fragment中的控件。
 * @author 涂臻宇

 */
public class FirstItemFragment extends Fragment implements OnClickListener
{
	/**
	 * 上课时间
	 */
	private static Spinner checkclassSpinner;
	/**
	 * 学校校区
	 */
	private static Spinner schoollocationSpinner;
	/**
	 * 实例化学校校区适配器
	 */
	private static ArrayAdapter<CharSequence> schoollocationSpinnerAdapter;
	/**
	 * 实例化上课时间的适配器
	 */
	private static ArrayAdapter<CharSequence> checkclassSpinnerAdapter;
	/**
	 * 实例化上课教学楼的适配器
	 */
	ArrayAdapter<String> classAdapter;
	/**
	 * 实例化上课教室的适配器
	 */
	private ArrayAdapter<String> classroomAdapter;
	/**
	 * 专业班级的数组
	 */
	private static String classname[];
	/**
	 * 专业班级
	 */
	private static String classGroup;
	/**
	 * 学院名称
	 */
	private static EditText schoolnameEditText;
	/**
	 * 专业班级
	 */
	private static Button classnameEditText;

	/**
     * 应到的学生人数输入框
	 */
	private static EditText studentNumber_editText ;
	/**
     * 实际的学生人数输入框
	 */
	private static EditText realNumber_editText ;
	/**
	 * 缺课人数
	 */
	public static int absentnum = 0;
	/**
	 * 日期文本框
	 */
	private static Button dateEditText;
	/**
	 * 上课地点
	 */
	private static Button schoollationEditText;
	/**
	 * 日期窗口
	 */
	private AlertDialog dataDialog;
	/**
	 * 已经预定窗口
	 */
	private AlertDialog hasBookDialog;
	/**
	 * 专业班级窗口
	 */
	private AlertDialog classnameDialog;
	/**
	 * 上课地点
	 */
	private AlertDialog schoollationDialog;
	/**
	 * 上课地点的字符串
	 */
	private String schoollationString = "";
	/**
	 * 日期
	 */
	private String dateString = "";
	/**
	 * 日期
	 */
	private static String defString = "";
	/**
	 * 年
	 */
	private static int year;
	/**
	 * 月
	 */
	private static int month;
	/**
	 * 日
	 */
	private static int day;
	Map<String, Object> utilMap;
	/**
	 * 校区ID
	 */
	private static int schoolID;
	/**
	 * 校区文件ID
	 */
	private static int schoolXMLID;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.frist, container, false);
       /*************************初始化各种控件****************************/
		// 学校校区的spinner
		schoollocationSpinner = (Spinner) rootView.findViewById(R.id.schoollocationSpinner);
		//学校校区的spinner的adapter,内容资源来xml文件的字符数组资源
		schoollocationSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.shoolsite,
				android.R.layout.simple_dropdown_item_1line);
		schoollocationSpinner.setAdapter(schoollocationSpinnerAdapter);
		//上课地点
		schoollationEditText = (Button) rootView.findViewById(R.id.schoollationEditText);
		schoollationEditText.setOnClickListener(this);
		schoollationEditText.setTextSize(20.0f);
		// 学院名称
		schoolnameEditText = (EditText) rootView.findViewById(R.id.schoolnameEditText);
		// 专业班级
		classnameEditText = (Button) rootView.findViewById(R.id.classnameEditText);
		classnameEditText.setTextSize(20.0f);
		classnameEditText.setOnClickListener(this);
		classnameEditText.setText("");
		//获取时间日期的按钮，点击可选择日期
		dateEditText = (Button) rootView.findViewById(R.id.dateEditText);
		dateEditText.setTextSize(20.0f);
		//获取当前日期
		Calendar c = Calendar.getInstance(Locale.CHINA);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dateString = year + "-" + (month + 1) + "-" + day;
		defString = year + "-" + (month + 1) + "-" + day;
		dateEditText.setText(dateString);

		dateEditText.setOnClickListener(this);
		//得到上课时间（节次）的spinner,内容资源来xml文件的字符数组资源
		checkclassSpinner = (Spinner) rootView.findViewById(R.id.checkclassSpinner);
		checkclassSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.time,
				android.R.layout.simple_dropdown_item_1line);
		checkclassSpinner.setAdapter(checkclassSpinnerAdapter);
        //应到学生人数输入框（不能修改）
		studentNumber_editText= (EditText) rootView.findViewById(R.id.peoplenumEditText);
		studentNumber_editText.setText("0");
       //实到学生人数输入框
		realNumber_editText = (EditText) rootView.findViewById(R.id.reallynumEditText);
		realNumber_editText.setText("0");
		//
		//checkclassSpinner.setOnItemSelectedListener(new autoCompleteTextViewListener());
	    schoollocationSpinner.setOnItemSelectedListener(new schoollocationListener());
		return rootView;
	}

	@Override
	public void onClick(View v) {
    //获取日期的按钮点击监听事件
   // 点击该按钮将会弹出一个日期选择窗口
		if(v.getId()==R.id.dateEditText)
		{
			iniDateDialog();
			dataDialog.show();
			//将显示查询结果的控件的字段（【学院名称】，【专业班级】清除
			schoolnameEditText.setText("");
			classnameEditText.setText("");
		}
		/*
		 * 获取上课地点的按钮的点击监听事件
		 * 点击该按钮会弹出一个窗口，该窗口可以进行教学楼的选择和教室的选择
		 * 教学楼的选择列表则应根据之前选择校区来得到
		 */
		else	if(v.getId()==R.id.schoollationEditText)
		{
			//如果校区文件ID不是有效的,显示警告后返回
			if(schoolXMLID==0) 
			{
				Toast.makeText(getActivity(), "请选择校区！",2*1000).show();
				return;
			}
			else{
			//如果校区文件ID有效,就弹出选择窗口
			iniSchoollationDialog();
			schoollationDialog.show();
			}
		}
		//获取专业班级的按钮
		else	if(v.getId()==R.id.classnameEditText)
		{
			
		}
	}
	/**
	 * 学校校区选项的选择监听事件
	 * 基本思路：根据被点击选项来得到相应校区文件，
	 * 然后清除"学院名称"和"上课地点"和"专业班级"三个控件的上的字体
	 * 选项编号从一开始
	 */
	private class schoollocationListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			switch (position) {
			case 1: {
				schoolXMLID = R.xml.site1;
			}
				break;
			case 2: {
				schoolXMLID = R.xml.site2;
			}
				break;
			case 3: {
				schoolXMLID = R.xml.site3;
			}
				break;
			case 4: {
				schoolXMLID = R.xml.site4;
			}
				break;
			default :schoolXMLID=0;
			}
			//将显示查询结果的控件的字段清除
			schoollationEditText.setText("");
			schoolnameEditText.setText("");
			classnameEditText.setText("");
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub			
		}		
	}
	/**
	 *初始化选择上课地点的窗口
	 */
	private void iniSchoollationDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater myInflater = LayoutInflater.from(getActivity());
		 //该view用于存放选择教室的对话框的布局 
		View view = myInflater.inflate(R.layout.schoollation, null);
		//通过view找到该view里的spinner,进行教学楼的选择
		final Spinner classBuildingSpinner = (Spinner) view.findViewById(R.id.classSpinner);
		//该字符串数组str用于存放解析得到教学楼列表
		String str[] = null;
		//解析校区文件，得到里面的教学楼列表
		try {
			str = ClassGroupXMLHandler.exchangeXmlToStringarr(
					getActivity(), schoolXMLID);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//为控件加载教学楼列表Adapter
		classAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, str);
		classBuildingSpinner.setAdapter(classAdapter);
		

		//通过选择教学楼后由view中得到另一个spinner,该spinner进行课室的选择
		//该课室的列表资源来自xml文件
		final Spinner class_roomSpinner = (Spinner) view.findViewById(R.id.class_roomSpinner);
				
		//为选择教学楼spinner的选项点击设置点击监听事件
		//每点击一个选项，就根据该选项设置选择教室的spinner的选项列表
				classBuildingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String strForClassroom[]=null ;
						//如果教学楼列表选项的不合法，则将教师列表的选项设置为空
						if (classBuildingSpinner.getSelectedItem().toString() != null
								&&!classBuildingSpinner.getSelectedItem().toString().equals("")) {
						//如果教学楼列表选项的合法，则通过教学楼名称解析相应学校校区文件得到该教学楼的教师列表，以字符串数组返回
						//strForClassroom[]接受教室列表					
							try {
								
								strForClassroom = ClassRoomGroupXMLHandler.exchangeXmlToStringarr(
										getActivity(), 
										schoolXMLID,
										classBuildingSpinner.getSelectedItem().toString());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}			
							//设置教室列表spinner的适配器，教室列表资源有str[]提供
							classroomAdapter = new ArrayAdapter<String>(
									getActivity(),
									android.R.layout.simple_dropdown_item_1line, strForClassroom);
							class_roomSpinner.setAdapter(classroomAdapter);
						}									
					}
					
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		
				
		//
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	         
			public void onClick(DialogInterface dialog, int which) {
				//点击"确定"，则：判断两个spinner是否有有效选择，如果选择无效，则直接返回，
				if (classBuildingSpinner.getSelectedItem().toString() == null
						|| classBuildingSpinner.getSelectedItem().toString().equals("")
						|| class_roomSpinner.getSelectedItem().toString() == null
						|| class_roomSpinner.getSelectedItem().toString()
								.equals("")) {
					return;
				}
				//如果选择有效，则将第二个spinner的选项，即教室的【选项】设置为“上课地点”的按钮的文本内容
				else {
					schoollationString = class_roomSpinner.getSelectedItem().toString();
					schoollationEditText.setText(schoollationString);
					}
				}		
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
          //点击取消按钮，对话框消失
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		schoollationDialog = builder.create();
		schoollationDialog.setTitle("上课地点");
		schoollationDialog.setView(view);
	}
	
	/**
	 * 日期窗口
	 */
	private void iniDateDialog() {
		// 日期窗口
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		LayoutInflater mInflater2 = LayoutInflater.from(getActivity());
		View view2 = mInflater2.inflate(R.layout.date, null);
		DatePicker datePicker1 = (DatePicker) view2
				.findViewById(R.id.datePicker);
		datePicker1.init(year, month, day, new OnDateChangedListener() {

			public void onDateChanged(DatePicker arg0, int year, int month,
					int day) {
				FirstItemFragment.year = year;
				FirstItemFragment.month = month;
				FirstItemFragment.day = day;
				dateString = year + "-" + (month + 1) + "-" + day;
				System.out.println("dateString==" + dateString);
				dataDialog.setTitle("日期：" + dateString);
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// 显示当前日期
				dateEditText.setText(dateString);
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
}
