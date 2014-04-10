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
 * �����±�
 */
public class ThirdItemFragment extends Fragment implements OnCheckedChangeListener, OnClickListener
{
	/**
	 * �ον�ʦ�Ƿ�ʱ��У
	 */
	private RadioGroup rdoGop_thacher_if_ontime;
	/**
	 * �ον�ʦ������Ա���ύʱ�䡢��ע
	 */
	private EditText edtTxt_teacher_name, edtTxt_supervisor, edtTxt_summit_time, edt_remark;
	/**
	 * ��ȡ��ǰʱ�䰴ť
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
	 * ��ʼ���ؼ�����Ӽ���
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
	 * RadioGroup�ον�ʦ�Ƿ�ʱ��У�ļ���
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId)
		{
		// ��
		case R.id.rdoBtn_teacher_if_ontime_yes:
			Toast.makeText(getActivity(), "��", 0).show();
			break;
		// ��
		case R.id.rdoBtn_teacher_if_ontime_no:
			Toast.makeText(getActivity(), "��", 0).show();
			break;
		default:
			break;
		}
	}
	/**
	 * �ύʱ���ļ���
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// �ον�ʦ
		case R.id.edtTxt_teacher_name:
			break;
		// ����Ա
		case R.id.edtTxt_supervisor:
			break;
		// �����ȡ��ǰʱ��
		case R.id.btn_get_submit_time:
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			String curTime = formatter.format(curDate);
			edtTxt_summit_time.setText(curTime);
			break;
		// ��ע
		case R.id.edtTxt_remark:
			break;
		default:
			break;
		}
	}

}
