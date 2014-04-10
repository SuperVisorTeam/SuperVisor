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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.gdut.supervisor.R;

/**
 * 第三章表单
 */
public class ThirdItemFragment extends Fragment implements OnCheckedChangeListener, OnClickListener
{
	/**
	 * 任课教师是否按时到校
	 */
	private RadioGroup rdoGop_thacher_if_ontime;
	/**
	 * 任课教师、督导员、提交时间、备注
	 */
	private EditText edtTxt_teacher_name, edtTxt_supervisor, edtTxt_summit_time, edt_remark;
	/**
	 * 获取当前时间按钮
	 */
	private Button btn_get_summit_time;

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
		rdoGop_thacher_if_ontime = (RadioGroup) view.findViewById(R.id.rdoGop_teacher_if_ontime);
		rdoGop_thacher_if_ontime.setOnCheckedChangeListener(this);
		
		edtTxt_teacher_name = (EditText)view.findViewById(R.id.edtTxt_teacher_name);
		edtTxt_teacher_name.setOnClickListener(this);
		edtTxt_supervisor = (EditText)view.findViewById(R.id.edtTxt_supervisor);
		edtTxt_supervisor.setOnClickListener(this);
		edtTxt_summit_time = (EditText)view.findViewById(R.id.edtTxt_submit_time);
		edtTxt_summit_time.setOnClickListener(this);
		edt_remark = (EditText)view.findViewById(R.id.edtTxt_remark);
		edt_remark.setOnClickListener(this);
		
		btn_get_summit_time = (Button)view.findViewById(R.id.btn_get_submit_time);
		btn_get_summit_time.setOnClickListener(this);
	}

	/**
	 * RadioGroup任课教师是否按时到校的监听
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId)
		{
		// 是
		case R.id.rdoBtn_teacher_if_ontime_yes:
			Toast.makeText(getActivity(), "是", 0).show();
			break;
		// 否
		case R.id.rdoBtn_teacher_if_ontime_no:
			Toast.makeText(getActivity(), "否", 0).show();
			break;
		default:
			break;
		}
	}
	/**
	 * 提交时间多的监听
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// 任课教师
		case R.id.edtTxt_teacher_name:
			break;
		// 督导员
		case R.id.edtTxt_supervisor:
			break;
		// 点击获取当前时间
		case R.id.btn_get_submit_time:
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			String curTime = formatter.format(curDate);
			edtTxt_summit_time.setText(curTime);
			break;
		// 备注
		case R.id.edtTxt_remark:
			break;
		default:
			break;
		}
	}

}
