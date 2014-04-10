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
 *��һ�ű�
 */
public class FirstItemFragment extends Fragment
{
	/**
	 * �Ͽ�ʱ��
	 */
	private static Spinner checkclassSpinner;
	/**
	 * ѧУУ��
	 */
	private static Spinner schoollocationSpinner;
	/**
	 * ʵ����ѧУУ��������
	 */
	private static ArrayAdapter<CharSequence> schoollocationSpinnerAdapter;
	/**
	 * ʵ�����Ͽ�ʱ���������
	 */
	private static ArrayAdapter<CharSequence> checkclassSpinnerAdapter;
	/**
	 * ʵ�����Ͽν�ѧ¥��������
	 */
	ArrayAdapter<String> classAdapter;
	/**
	 * ʵ�����Ͽν��ҵ�������
	 */
	private ArrayAdapter<String> classroomAdapter;
	/**
	 * רҵ�༶������
	 */
	private static String classname[];
	/**
	 * רҵ�༶
	 */
	private static String classGroup;
	/**
	 * ѧԺ����
	 */
	private static EditText schoolnameEditText;
	/**
	 * רҵ�༶
	 */
	private static Button classnameEditText;

	/**
     * 
	 */
	private static EditText[] editText = new EditText[2];
	/**
	 * ȱ������
	 */
	public static int absentnum = 0;
	/**
	 * �����ı���
	 */
	private static Button dateEditText;
	/**
	 * �Ͽεص�
	 */
	private static Button schoollationEditText;
	/**
	 * ���ڴ���
	 */
	private AlertDialog dataDialog;
	/**
	 * �Ѿ�Ԥ������
	 */
	private AlertDialog hasBookDialog;
	/**
	 * רҵ�༶����
	 */
	private AlertDialog classnameDialog;
	/**
	 * �Ͽεص�
	 */
	private AlertDialog schoollationDialog;
	/**
	 * �Ͽεص�
	 */
	private String schoollationString = "";
	/**
	 * ����
	 */
	private String dateString = "";
	/**
	 * ����
	 */
	private static String defString = "";
	/**
	 * ��
	 */
	private int year;
	/**
	 * ��
	 */
	private int month;
	/**
	 * ��
	 */
	private int day;
	Map<String, Object> utilMap;
	/**
	 * У��ID
	 */
	private static int schoolID;
	/**
	 * У���ļ�ID
	 */
	private static int schoolXMLID;
	/**
	 * ����һ����ǩ�ж��Ƿ��˻�����ݰ�ť
	 */
	private boolean isGeDatat = false;
	/**
	 * �ж��Ƿ��Ѿ��������
	 */
	public static boolean isGet = false;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.frist, container, false);

		// ѧУУ��
		schoollocationSpinner = (Spinner) rootView.findViewById(R.id.schoollocationSpinner);
		schoollocationSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.shoolsite,
				android.R.layout.simple_dropdown_item_1line);
		schoollocationSpinner.setAdapter(schoollocationSpinnerAdapter);
		// �Ͽεص�
		schoollationEditText = (Button) rootView.findViewById(R.id.schoollationEditText);
		schoollationEditText.setOnClickListener(new ButtonActionListener());
		schoollationEditText.setTextSize(20.0f);
		// ѧԺ����
		schoolnameEditText = (EditText) rootView.findViewById(R.id.schoolnameEditText);
		// רҵ�༶
		classnameEditText = (Button) rootView.findViewById(R.id.classnameEditText);
		classnameEditText.setTextSize(20.0f);
		classnameEditText.setOnClickListener(new ButtonActionListener());
		classnameEditText.setText("");
		// ����
		dateEditText = (Button) rootView.findViewById(R.id.dateEditText);
		dateEditText.setTextSize(20.0f);
		Calendar c = Calendar.getInstance(Locale.CHINA);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dateString = year + "-" + (month + 1) + "-" + day;
		defString = year + "-" + (month + 1) + "-" + day;
		dateEditText.setText(dateString);

		dateEditText.setOnClickListener(new ButtonActionListener());
		// �Ͽ�ʱ��
		checkclassSpinner = (Spinner) rootView.findViewById(R.id.checkclassSpinner);
		checkclassSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.time,
				android.R.layout.simple_dropdown_item_1line);
		checkclassSpinner.setAdapter(checkclassSpinnerAdapter);

		//
		int i = 0;

		editText[i++] = (EditText) rootView.findViewById(R.id.peoplenumEditText);

		editText[0].setText("0");

		editText[i++] = (EditText) rootView.findViewById(R.id.reallynumEditText);

		editText[1].setText("0");
		//
		checkclassSpinner.setOnItemSelectedListener(new autoCompleteTextViewListener());
		schoollocationSpinner.setOnItemSelectedListener(new schoollocationListener());
		return rootView;

	}

	// У���¼�
	private class schoollocationListener implements OnItemSelectedListener
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen)
			{
				schoollationEditText.setText("");
			}
			schoolID = arg2;
			switch (schoolID)
			{
			case 1:
			{
				schoolXMLID = R.xml.site1;
			}
				break;
			case 2:
			{
				schoolXMLID = R.xml.site2;
			}
				break;
			case 3:
			{
				schoolXMLID = R.xml.site3;
			}
				break;
			case 4:
			{
				schoolXMLID = R.xml.site4;
			}
				break;
			}
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{

		}
	}

	// ���ڰ�ť
	private class ButtonActionListener implements OnClickListener
	{
		public void onClick(View v)
		{
			if (v.getId() == R.id.dateEditText)
			{
				iniDateDialog();
				dataDialog.show();
			}
			if (v.getId() == R.id.schoollationEditText)
			{
				iniSchoollationDialog();
				schoollationDialog.show();
			}
			if (v.getId() == R.id.classnameEditText)
			{
				if (classnameEditText.getText().toString() != null
						&& !classnameEditText.getText().toString().equals(""))
				{
					iniClassnameDialog();
					classnameDialog.show();
				}
			}
		}

	}

	/**
	 * ���ڴ���
	 */
	private void iniDateDialog()
	{
		// ���ڴ���
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater mInflater2 = LayoutInflater.from(getActivity());
		View view2 = mInflater2.inflate(R.layout.date, null);
		DatePicker datePicker1 = (DatePicker) view2.findViewById(R.id.datePicker);
		datePicker1.init(year, month, day, new OnDateChangedListener()
		{

			public void onDateChanged(DatePicker arg0, int year, int month, int day)
			{
				FirstItemFragment.this.year = year;
				FirstItemFragment.this.month = month;
				FirstItemFragment.this.day = day;
				dateString = year + "-" + (month + 1) + "-" + day;
				System.out.println("dateString==" + dateString);
				dataDialog.setTitle("���ڣ�" + dateString);

			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				// ��ʾ��ǰ����
				dateEditText.setText(dateString);
				if (!SupervisorFragment.searchIsOpen)
				{
					getData();
				}
				isGeDatat = true;
			}

		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		dataDialog = builder.create();
		dataDialog.setTitle("���ڣ�" + dateString);
		dataDialog.setView(view2);
	}

	/**
	 * �Ͽεص㴰��
	 */
	private void iniSchoollationDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater mInflater2 = LayoutInflater.from(getActivity());
		View view = mInflater2.inflate(R.layout.schoollation, null);
		final Spinner classSpinner = (Spinner) view.findViewById(R.id.classSpinner);
		if (schoollocationSpinner.getSelectedItem().toString() != null
				&& !schoollocationSpinner.getSelectedItem().toString().equals(""))
		{
			String str[] = null;
			try
			{
				str = ClassGroupXMLHandler.exchangeXmlToStringarr(getActivity(), schoolXMLID);
			} catch (XmlPullParserException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			classAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,
					str);
			classSpinner.setAdapter(classAdapter);
		} else
		{
			String str[] =
			{ "", "", "" };
			classAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,
					str);
			classSpinner.setAdapter(classAdapter);
		}
		final Spinner class_roomSpinner = (Spinner) view.findViewById(R.id.class_roomSpinner);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				if (classSpinner.getSelectedItem().toString() == null
						|| classSpinner.getSelectedItem().toString().equals("")
						|| class_roomSpinner.getSelectedItem().toString() == null
						|| class_roomSpinner.getSelectedItem().toString().equals(""))
				{
					return;
				} else
				{
					schoollationString = class_roomSpinner.getSelectedItem().toString();
					schoollationEditText.setText(schoollationString);
					if (!SupervisorFragment.searchIsOpen)
					{
						getData();
					}
					isGeDatat = true;
				}
			}

		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		schoollationDialog = builder.create();
		schoollationDialog.setTitle("�Ͽεص�");
		schoollationDialog.setView(view);
		classSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				if (classSpinner.getSelectedItem().toString() == null
						|| classSpinner.getSelectedItem().toString().equals(""))
				{
					String str[] =
					{ "", "", "" };
					classroomAdapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_dropdown_item_1line, str);
					class_roomSpinner.setAdapter(classroomAdapter);
				} else
				{
					String str[] = null;
					try
					{
						str = ClassRoomGroupXMLHandler.exchangeXmlToStringarr(getActivity(), schoolXMLID,
								classSpinner.getSelectedItem().toString());
					} catch (XmlPullParserException e)
					{
						e.printStackTrace();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					classroomAdapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_dropdown_item_1line, str);
					class_roomSpinner.setAdapter(classroomAdapter);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
	}

	/**
	 * רҵ�༶����
	 */
	private void iniClassnameDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String classcontext = "\n";
		for (int i = 0; i < classname.length; i++)
		{
			classcontext = classcontext + classname[i] + "\n";
		}
		classnameDialog = builder.create();
		classnameDialog.setMessage(classcontext);
		classnameDialog.setTitle("רҵ�༶");
	}

	// ���¼������
	private void getData()
	{
		if (schoollocationSpinner.getSelectedItem().toString() == null
				|| schoollocationSpinner.getSelectedItem().toString().equals("")
				|| schoollationEditText.getText().toString() == null
				|| schoollationEditText.getText().toString().equals("")
				|| dateEditText.getText().toString() == null || dateEditText.getText().toString().equals("")
				|| checkclassSpinner.getSelectedItem().toString() == null
				|| checkclassSpinner.getSelectedItem().toString().equals(""))
		{
		} else
		{
			if (SupervisorActivity.secondOpen)
			{
				// SecondItemActivity.clearSecondItem();
			}
			if (SupervisorActivity.thirdOpen)
			{
				// ThirdItemActivity.clearThirdItem();
			}
			try
			{
				int code = -1;
				code = SubmitHandler.getMap(schoolID + "", dateEditText.getText().toString(), schoollationEditText
						.getText().toString(), checkclassSpinner.getSelectedItem().toString(),
						BaseMessage.supervisor_no);
				if (code == 409)
				{
					inihasBookDialog(schoollationEditText.getText().toString() + "\n�༶�Ѿ�����������ѡ��������ѧ����ж�����");
				} else if (code == 200)
				{
					utilMap = SubmitHandler.getmap;
					if (SupervisorActivity.secondOpen)
					{
						// SecondItemActivity.clearSecondItem();
					}
					if (SupervisorActivity.thirdOpen)
					{
						// ThirdItemActivity.clearThirdItem();
					}
					BaseMessage.class_no = (String) utilMap.get("course_class_no");
					schoolnameEditText.setText((String) utilMap.get("student_faculty"));
					int i = 0;
					classname = ClassNameHandler.exchangeStringToArray((String) utilMap
							.get("teaching_class_group"));
					classGroup = (String) utilMap.get("teaching_class_group");
					double num1 = (Double) utilMap.get("plan_population");
					int num = (int) num1;
					BaseMessage.num = num;
					editText[i++].setText(num + "");
					BaseMessage.teacherName = (String) utilMap.get("teacher_name");
					classnameEditText.setText("��ʾȫ��רҵ�༶");
					editText[i++].setText("0");
				} else if (code == 402)
				{
					inihasBookDialog(schoollationEditText.getText().toString() + "\n�����ѱ�����Ԥ���ˣ���ѡ��������ѧ����ж�����");
				} else if (code == 404)
				{
					inihasBookDialog(schoollationEditText.getText().toString() + "\nû�ж�Ӧ�Ŀ�Ҫ�ϣ���ѡ��������ѧ����ж�����");
				} else if (code == 400)
				{
					inihasBookDialog(schoollationEditText.getText().toString() + "\n����Ĳ����д�!");
				} else
				{
					inihasBookDialog("�뱣���������ӣ�");
				}
			} catch (ClientProtocolException e)
			{
				ShowMessageDialog.showMessage(getActivity(), "�뱣���������ӣ�");
				return;
			} catch (IOException e)
			{
				ShowMessageDialog.showMessage(getActivity(), "�뱣���������ӣ�");
				return;
			}
		}
	}

	/**
	 * �Ѿ�Ԥ������
	 */
	private void inihasBookDialog(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				hasGetNull();
			}

		});
		hasBookDialog = builder.create();
		hasBookDialog.setTitle("��ʾ");
		hasBookDialog.setMessage(message);
		hasBookDialog.show();
	}

	/**
	 * ���ú���
	 */
	private void hasGetNull()
	{
		int i = 0;
		BaseMessage.num = 0;
		editText[i++].setText("0");
		editText[i++].setText("0");
		schoolnameEditText.setText("");
		classnameEditText.setText("");
		schoollationEditText.setText("");
		BaseMessage.teacherName = "";
	}

	// ���ڴε��¼�
	private class autoCompleteTextViewListener implements OnItemSelectedListener
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			if (isGeDatat)
			{
				if (!SupervisorFragment.searchIsOpen)
				{
					// getData();
				}
			}
			isGeDatat = true;
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
		}
	}
	// �ָ�ʱ����
	// �����ò����ˣ���Ϊʹ����ViewPager+Fragment
	/*
	 * public void onResume() { super.onResume(); for (int i = 0; i < 2; i++) {
	 * editText[i].clearFocus(); } if (SupervisorFragment.searchIsOpen) { if
	 * (!isGet) { isGet = true; setFristItemClassSituation(); } } else if
	 * (SupervisorFragment.scheduleIsOpen) { setFristItemClassSituation(); } }
	 */
}
