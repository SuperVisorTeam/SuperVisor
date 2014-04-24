package com.gdut.supervisor.view;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.ui.SupervisorActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * 第二个表单的窗口
 * 
 */
public class SecondItemFragment extends Fragment {
	/**
	 * 文本框的数组
	 */
	private static EditText[] editText = new EditText[7];
	/**
	 * 文本框的个数
	 */
	private static int editTextnum = 7;
	/**
	 * 按钮数组
	 */
	private static Button[] button = new Button[14];
	/**
	 * 按钮的个数
	 */
	private static int buttonnum = 14;
	/**
	 * 最多人数
	 */

	 private static int numMax = 0;
	 

	/**
	 * 判断是否已经获得数据
	 */

	

	 /**
		 * 判断是否已经获得数据
		 */
		public static boolean isGet = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.second, container, false);
		// 初始化文本编辑框
		int i = 0;
		//1.学生上课迟到早退情况
		editText[i++] = (EditText) rootView.findViewById(R.id.latenumEditText);
		//2.学生旷课情况
		editText[i++] = (EditText) rootView
				.findViewById(R.id.truantnumEditText);
		//3.学生请假情况
		editText[i++] = (EditText) rootView.findViewById(R.id.leavenumEditText);
		 //4.学生带食品进入教室在课堂\n上吃东西情况
		editText[i++] = (EditText) rootView.findViewById(R.id.eatnumEditText);
		//5.学生课堂上翻看手机情况
		editText[i++] = (EditText) rootView.findViewById(R.id.phonenumEditText);
		 //6.学生上课睡觉或讲话情况
		editText[i++] = (EditText) rootView.findViewById(R.id.sleepnumEditText);
		//7.学生穿拖鞋短裤进教室情况
		editText[i++] = (EditText) rootView.findViewById(R.id.shonenumEditText);

		// 初始化按钮
		int j = 0;
		button[j++] = (Button) rootView.findViewById(R.id.latereduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.lateaddButton);

		button[j++] = (Button) rootView
				.findViewById(R.id.truantnumreduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.truantnumaddButton);

		button[j++] = (Button) rootView.findViewById(R.id.leavereduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.leaveaddButton);

		button[j++] = (Button) rootView.findViewById(R.id.eatreduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.eataddButton);

		button[j++] = (Button) rootView.findViewById(R.id.phonereduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.phoneaddButton);

		button[j++] = (Button) rootView.findViewById(R.id.sleepreduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.sleepaddButton);

		button[j++] = (Button) rootView.findViewById(R.id.shonereduceButton);
		button[j++] = (Button) rootView.findViewById(R.id.shoneaddButton);
		//获取最大人数
		numMax = BaseMessage.num;
		// 添加监听
		for (int k = 0; k < buttonnum; k++) {
			button[k]
					.setOnClickListener((android.view.View.OnClickListener) new ButtonActionListener());
		}
		for (int k = 0; k < editTextnum; k++) {
			editText[k].addTextChangedListener(new TextChangedListener());
		}

		return rootView;

	}

	// 文本框内容改变事件
	private class TextChangedListener implements TextWatcher {

		public void afterTextChanged(Editable s) {
			for (int k = 0; k < editTextnum; k++) {
				if (editText[k].getText().toString() != null
						&& !editText[k].getText().toString().equals("")) {
					int num = Integer.valueOf(editText[k].getText().toString());
					if (num > numMax) {
						Toast.makeText(getActivity(), "不能超过总人数", 0).show();
						editText[k].setText(numMax + "");
						System.out.println("你输入的值已经超过了应到的人数！！");
					}
				}
			}
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	int num = 0;

	// 按钮事件
	private class ButtonActionListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			// 迟到事件
			case (R.id.latereduceButton): {
				if (editText[0].getText().toString() == null
						|| editText[0].getText().toString().equals("")) {
					editText[0].setText("0");
				}
				num = Integer.valueOf(editText[0].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[0].setText(num + "");
			}
				break;
			case (R.id.lateaddButton): {
				if (editText[0].getText().toString() == null
						|| editText[0].getText().toString().equals("")) {
					editText[0].setText("0");
				}
				num = Integer.valueOf(editText[0].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[0].setText(num + "");
			}
				break;

			// 旷课
			case (R.id.truantnumreduceButton): {
				if (editText[1].getText().toString() == null
						|| editText[1].getText().toString().equals("")) {
					editText[1].setText("0");
				}
				num = Integer.valueOf(editText[1].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[1].setText(num + "");
			}
				break;
			case (R.id.truantnumaddButton): {
				if (editText[1].getText().toString() == null
						|| editText[1].getText().toString().equals("")) {
					editText[1].setText("0");
				}
				num = Integer.valueOf(editText[1].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[1].setText(num + "");
			}
				break;

			// 请假
			case (R.id.leavereduceButton): {
				if (editText[2].getText().toString() == null
						|| editText[2].getText().toString().equals("")) {
					editText[2].setText("0");
				}
				num = Integer.valueOf(editText[2].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[2].setText(num + "");
			}
				break;

			case (R.id.leaveaddButton): {
				if (editText[2].getText().toString() == null
						|| editText[2].getText().toString().equals("")) {
					editText[2].setText("0");
				}
				num = Integer.valueOf(editText[2].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[2].setText(num + "");
			}
				break;

			// 吃东西
			case (R.id.eatreduceButton): {
				if (editText[3].getText().toString() == null
						|| editText[3].getText().toString().equals("")) {
					editText[3].setText("0");
				}
				num = Integer.valueOf(editText[3].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[3].setText(num + "");
			}
				break;
			case (R.id.eataddButton): {
				if (editText[3].getText().toString() == null
						|| editText[3].getText().toString().equals("")) {
					editText[3].setText("0");
				}
				num = Integer.valueOf(editText[3].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[3].setText(num + "");
			}
				break;

			// 玩手机
			case (R.id.phonereduceButton): {
				if (editText[4].getText().toString() == null
						|| editText[4].getText().toString().equals("")) {
					editText[4].setText("0");
				}
				num = Integer.valueOf(editText[4].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[4].setText(num + "");
			}
				break;
			case (R.id.phoneaddButton): {
				if (editText[4].getText().toString() == null
						|| editText[4].getText().toString().equals("")) {
					editText[4].setText("0");
				}
				num = Integer.valueOf(editText[4].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[4].setText(num + "");
			}
				break;

			// 睡觉
			case (R.id.sleepreduceButton): {
				if (editText[5].getText().toString() == null
						|| editText[5].getText().toString().equals("")) {
					editText[5].setText("0");
				}
				num = Integer.valueOf(editText[5].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[5].setText(num + "");
			}
				break;
			case (R.id.sleepaddButton): {
				if (editText[5].getText().toString() == null
						|| editText[5].getText().toString().equals("")) {
					editText[5].setText("0");
				}
				num = Integer.valueOf(editText[5].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[5].setText(num + "");
			}
				break;

			// 穿拖鞋
			case (R.id.shonereduceButton): {
				if (editText[6].getText().toString() == null
						|| editText[6].getText().toString().equals("")) {
					editText[6].setText("0");
				}
				num = Integer.valueOf(editText[6].getText().toString());
				if (num <= 0) {
				} else {
					num--;
				}
				editText[6].setText(num + "");
			}
				break;
			case (R.id.shoneaddButton): {
				if (editText[6].getText().toString() == null
						|| editText[6].getText().toString().equals("")) {
					editText[6].setText("0");
				}
				num = Integer.valueOf(editText[6].getText().toString());
				if (num >= numMax) {
				} else {
					num++;
				}
				editText[6].setText(num + "");
			}
				break;
			default:
				break;
			}
		}
	}

	// 重置第二个菜单的数据列表
	public static void clearSecondItem() {
		for (int i = 0; i < editTextnum; i++) {
			editText[i].setText("0");
			editText[i].clearFocus();
		}
	}

	// 获得填写表格的内容
	public static void greatSecondItemClassSituation() {
		int i = 0;
		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setLate_LeaveEarly_Num(Integer
				.valueOf(editText[i++].getText().toString()));

		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setTruant_Num(Short.valueOf(editText[i++]
				.getText().toString()));

		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setVacate_Num(Integer
				.valueOf(editText[i++].getText().toString()));

		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setFood_Eat_Num(Integer
				.valueOf(editText[i++].getText().toString()));

		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setPlay_Mobil_Num(Integer
				.valueOf(editText[i++].getText().toString()));

		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setSleep_Speak_Num(Integer
				.valueOf(editText[i++].getText().toString()));

		//
		if (editText[i].getText().toString() == null
				|| editText[i].getText().toString().equals("")) {
			editText[i].setText("0");
		}
		SupervisorActivity.situation.setSlipper_Shorts_Num(Integer
				.valueOf(editText[i++].getText().toString()));
	}

	// 设置填写表格的内容
	public static void setSecondItemClassSituation() {
		int i = 0;
		//
		editText[i++].setText(SupervisorActivity.situation
				.getLate_LeaveEarly_Num() + "");

		//
		editText[i++]
				.setText(SupervisorActivity.situation.getTruant_Num() + "");

		//
		editText[i++]
				.setText(SupervisorActivity.situation.getVacate_Num() + "");

		//
		editText[i++].setText(SupervisorActivity.situation.getFood_Eat_Num()
				+ "");

		//
		editText[i++].setText(SupervisorActivity.situation.getPlay_Mobil_Num()
				+ "");

		//
		editText[i++].setText(SupervisorActivity.situation.getSleep_Speak_Num()
				+ "");

		//
		editText[i++].setText(SupervisorActivity.situation
				.getSlipper_Shorts_Num() + "");
	}
	
	
	// 恢复时调用
		public void onResume() {
			super.onResume();
			numMax = BaseMessage.num;
			System.out.println("Second-----SupervisorFragment.searchIsOpen="+SupervisorFragment.searchIsOpen);
			System.out.println("Second-----isGet="+isGet);
			if (!SupervisorFragment.searchIsOpen) {
              
			} else {
				if (!isGet) {
					isGet = true;
					setSecondItemClassSituation();
				}
			}
			for (int i = 0; i < editTextnum; i++) {
				editText[i].clearFocus();
			}
		}
		
	
}
