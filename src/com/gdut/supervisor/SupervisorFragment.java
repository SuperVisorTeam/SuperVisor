package com.gdut.supervisor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * 督导功能的Fragment
 */
public class SupervisorFragment extends Fragment implements OnClickListener
{
	private ImageButton btn_directwrite, btn_preparewrite;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View parentView = inflater.inflate(R.layout.supervisor_fragment, container, false);
		btn_directwrite = (ImageButton)parentView.findViewById(R.id.ib_supervisor_directwrite);
		btn_preparewrite = (ImageButton)parentView.findViewById(R.id.ib_supervisor_preparewrite);
		btn_directwrite.setOnClickListener(this);
		btn_preparewrite.setOnClickListener(this);
		return parentView;
	}
	//为按钮添加监听
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		//预定录入按钮
		case R.id.ib_supervisor_preparewrite:
			Toast.makeText(getActivity(), "预定录入", 0).show();
			break;
		//直接录入按钮
		case R.id.ib_supervisor_directwrite:
			Toast.makeText(getActivity(), "直接录入", 0).show();
			break;
		default:
			break;
		}
		
	}
}
