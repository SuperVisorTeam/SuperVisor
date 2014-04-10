package com.gdut.supervisor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Button;

import com.gdut.supervisor.R;
import com.gdut.supervisor.adapter.SupervisorAdapter;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.view.SupervisorFragment;
/**
 * ����������FragmentActivity
 */
public class SupervisorActivity extends FragmentActivity  {
	/**
	 * ��հ�ť
	 */
	private Button clearButton;
	/**
	 * �ύ��ť
	 */
	private Button submitButton;
	/**
	 * �߿�����
	 */
	private AlertDialog leaveDialog;
	/**
	 * �Ѿ���������
	 */
	private AlertDialog hasSaveDialog;
	/**
	 * �Ѿ���������
	 */
	private AlertDialog schedulehasSaveDialog;
	/**
	 * �ύ����
	 */
	private AlertDialog submitDialog;
	/**
	 * �ύ�ɹ�����
	 */
	private AlertDialog submitsuccessDialog;
	/**
	 * ��˴���
	 */
	private AlertDialog auditDialog;
	/**
	 * Ԥ���ύ�ɹ�����
	 */
	private AlertDialog schedulesuccessDialog;

	/**
	 * �޸Ĵ���
	 */
	private AlertDialog modificationDialog;
	/**
	 * �޸ĳɹ�����
	 */
	private AlertDialog modificationsuccessDialog;
	/**
	 * �޸�ʧ�ܴ���
	 */
	private AlertDialog modificationfaileDialog;
	/**
	 * ���ô���
	 */
	private AlertDialog clearDialog;

	/**
	 * �����жϵ�һ����Ƭ�Ƿ񱻴�
	 */
	private boolean firstOpen = true;
	/**
	 * �����жϵڶ�����Ƭ�Ƿ񱻴�
	 */
	public static boolean secondOpen = false;

	/**
	 * �����жϵ�������Ƭ�Ƿ񱻴�
	 */
	public static boolean thirdOpen = false;

	/**
	 * ����һ�ű��洢�ôζ��������
	 */
	public static com.gdut.supervisor.info.Edu_Survey situation;
	/**
	 * �༶������Ϣ
	 */
	public static com.gdut.supervisor.info.Edu_CourseClass edu_CourseClass;
    /**
     * ��viewPager ���ڼ���������
     */
    private ViewPager viewPager; 
    /**
     * һ��viewpager��ָʾ����Ч������һ����Ĵֵ��»���
     */
    private PagerTabStrip pagerTabStrip;
    //
    private PagerTitleStrip pagerTitleStrip;
    /**
     * װ����viewpager�е�Adpter
     */
    SupervisorAdapter  pagerAdpter;
    /**
     * װ��
     */
    FragmentManager  myFragmentManager;
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.form);
        //ʵ�����ؼ�
		 viewPager=(ViewPager)findViewById(R.id.viewpager);		 
		 pagerTabStrip=(PagerTabStrip)findViewById(R.id.pagertab);
		 //ʵ������ť�ؼ�
		 clearButton = (Button) findViewById(R.id.clearButton);
		 submitButton = (Button) findViewById(R.id.submitButton);
		 //
		 myFragmentManager=getSupportFragmentManager();
		 //Ϊviewpager����adapter	
		 SupervisorAdapter mySupervisorAdapter=new SupervisorAdapter(myFragmentManager);
		 viewPager.setAdapter(mySupervisorAdapter);
		 
		 iniDialog();
		 //
		 if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen) {
				submitButton.setText("�ύ");
				clearButton.setText("����");
			} else if (SupervisorFragment.searchIsOpen) {
				submitButton.setText("�޸�");
				clearButton.setText("�˳�");
			} else if (SupervisorFragment.scheduleIsOpen) {
				submitButton.setText("�ύ");
				clearButton.setText("�˳�");
			}
		}
	// ��ʼ�����ִ���
	private void iniDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("�˳�֮�󣬴��ڵ����ݽ���ʧȥ!!")
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		leaveDialog = builder.create();
		leaveDialog.setTitle("�˳�");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("��ȷ��Ҫ �ύ ��")
				.setPositiveButton("�ύ", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//submitFrom();
					}
				})
				.setNegativeButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		submitDialog = builder.create();
		submitDialog.setTitle("�ύ");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("�Ƿ����ã�����֮�󴰿����е����ݽ������棡")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
					//	clear();
					}

				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		clearDialog = builder.create();
		clearDialog.setTitle("����");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("�ύ�ɹ�")
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("��дһ��",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								//clear();
							}
						});
		submitsuccessDialog = builder.create();
		submitsuccessDialog.setTitle("��ʾ");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("�ύ�ɹ�")
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}
				})
				.setNegativeButton("����Ԥ��",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
						/*		BaseMessage.num = 0;
								BaseMessage.teacherName = "";
								MenuActivity.scheduleIsOpen = false;
								secondOpen = false;
								thirdOpen = false;
								Intent intent = new Intent(
										SupervisorActivity.this,
										ScheduleActivity.class);
								startActivity(intent);
								SupervisorActivity.this.finish();*/
							}
						});
		schedulesuccessDialog = builder.create();
		schedulesuccessDialog.setTitle("��ʾ");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("�ð༶�Ѿ�����������")
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("��дһ��",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							//	clear();
							}
						});
		hasSaveDialog = builder.create();
		hasSaveDialog.setTitle("��ʾ");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("��Ԥ���༶�Ѿ�����������")
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("����Ԥ��",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
/*								BaseMessage.num = 0;
								BaseMessage.teacherName = "";
								MenuActivity.scheduleIsOpen = false;
								secondOpen = false;
								thirdOpen = false;
								Intent intent = new Intent(
										SupervisorActivity.this,
										ScheduleActivity.class);
								startActivity(intent);
								SupervisorActivity.this.finish();*/
							}
						});
		schedulehasSaveDialog = builder.create();
		schedulehasSaveDialog.setTitle("��ʾ");
	}
	//���ذ�ť�ĵ���¼�������
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen) {
					leaveDialog.show();
				} else if (SupervisorFragment.searchIsOpen) {
					System.out.println("��ѯ֮����˳��¼�����");
					BaseMessage.num = 0;
					BaseMessage.teacherName = "";
					SupervisorFragment.searchIsOpen = false;
					secondOpen = false;
					thirdOpen = false;
					/*Intent intent = new Intent(SupervisorActivity.this,
							SearchFormActivity.class);
					startActivity(intent);
					SupervisorActivity.this.finish();*/
				} else if (SupervisorFragment.scheduleIsOpen) {
					System.out.println("ԤԼ֮����˳��¼�����");
					BaseMessage.num = 0;
					BaseMessage.teacherName = "";
					SupervisorFragment.scheduleIsOpen = false;
					secondOpen = false;
					thirdOpen = false;
				/*	Intent intent = new Intent(SupervisorActivity.this,
							ScheduleActivity.class);
					startActivity(intent);
					SupervisorActivity.this.finish();*/
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}
	// �˳���ť�¼�
	private void exit() {
		BaseMessage.num = 0;
		BaseMessage.teacherName = "";
		SupervisorFragment.searchIsOpen = false;
		SupervisorFragment.scheduleIsOpen = false;
		secondOpen = false;
		thirdOpen = false;
		//Intent intent = new Intent(SupervisorActivity.this, MenuActivity.class);
		//startActivity(intent);
		SupervisorActivity.this.finish();
	}
}
