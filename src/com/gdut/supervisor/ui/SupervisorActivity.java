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
 * 包含三表单的FragmentActivity
 */
public class SupervisorActivity extends FragmentActivity  {
	/**
	 * 清空按钮
	 */
	private Button clearButton;
	/**
	 * 提交按钮
	 */
	private Button submitButton;
	/**
	 * 高开窗口
	 */
	private AlertDialog leaveDialog;
	/**
	 * 已经督导窗口
	 */
	private AlertDialog hasSaveDialog;
	/**
	 * 已经督导窗口
	 */
	private AlertDialog schedulehasSaveDialog;
	/**
	 * 提交窗口
	 */
	private AlertDialog submitDialog;
	/**
	 * 提交成功窗口
	 */
	private AlertDialog submitsuccessDialog;
	/**
	 * 审核窗口
	 */
	private AlertDialog auditDialog;
	/**
	 * 预定提交成功窗口
	 */
	private AlertDialog schedulesuccessDialog;

	/**
	 * 修改窗口
	 */
	private AlertDialog modificationDialog;
	/**
	 * 修改成功窗口
	 */
	private AlertDialog modificationsuccessDialog;
	/**
	 * 修改失败窗口
	 */
	private AlertDialog modificationfaileDialog;
	/**
	 * 重置窗口
	 */
	private AlertDialog clearDialog;

	/**
	 * 用来判断第一个卡片是否被打开
	 */
	private boolean firstOpen = true;
	/**
	 * 用来判断第二个卡片是否被打开
	 */
	public static boolean secondOpen = false;

	/**
	 * 用来判断第三个卡片是否被打开
	 */
	public static boolean thirdOpen = false;

	/**
	 * 创建一张表，存储该次督导的情况
	 */
	public static com.gdut.supervisor.info.Edu_Survey situation;
	/**
	 * 班级基本信息
	 */
	public static com.gdut.supervisor.info.Edu_CourseClass edu_CourseClass;
    /**
     * 该viewPager 用于加载三个表单
     */
    private ViewPager viewPager; 
    /**
     * 一个viewpager的指示器，效果就是一个横的粗的下划线
     */
    private PagerTabStrip pagerTabStrip;
    //
    private PagerTitleStrip pagerTitleStrip;
    /**
     * 装载在viewpager中的Adpter
     */
    SupervisorAdapter  pagerAdpter;
    /**
     * 装控
     */
    FragmentManager  myFragmentManager;
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.form);
        //实例化控件
		 viewPager=(ViewPager)findViewById(R.id.viewpager);		 
		 pagerTabStrip=(PagerTabStrip)findViewById(R.id.pagertab);
		 //实例化按钮控件
		 clearButton = (Button) findViewById(R.id.clearButton);
		 submitButton = (Button) findViewById(R.id.submitButton);
		 //
		 myFragmentManager=getSupportFragmentManager();
		 //为viewpager加载adapter	
		 SupervisorAdapter mySupervisorAdapter=new SupervisorAdapter(myFragmentManager);
		 viewPager.setAdapter(mySupervisorAdapter);
		 
		 iniDialog();
		 //
		 if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen) {
				submitButton.setText("提交");
				clearButton.setText("重置");
			} else if (SupervisorFragment.searchIsOpen) {
				submitButton.setText("修改");
				clearButton.setText("退出");
			} else if (SupervisorFragment.scheduleIsOpen) {
				submitButton.setText("提交");
				clearButton.setText("退出");
			}
		}
	// 初始化各种窗口
	private void iniDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("退出之后，窗口的数据将会失去!!")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		leaveDialog = builder.create();
		leaveDialog.setTitle("退出");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("您确定要 提交 吗？")
				.setPositiveButton("提交", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//submitFrom();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		submitDialog = builder.create();
		submitDialog.setTitle("提交");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("是否重置？重置之后窗口所有的内容将不保存！")
				.setPositiveButton("重置", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
					//	clear();
					}

				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		clearDialog = builder.create();
		clearDialog.setTitle("重置");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("提交成功")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("再写一张",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								//clear();
							}
						});
		submitsuccessDialog = builder.create();
		submitsuccessDialog.setTitle("提示");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("提交成功")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}
				})
				.setNegativeButton("返回预定",
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
		schedulesuccessDialog.setTitle("提示");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("该班级已经被督导！！")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("再写一张",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							//	clear();
							}
						});
		hasSaveDialog = builder.create();
		hasSaveDialog.setTitle("提示");
		builder = new AlertDialog.Builder(this);
		builder.setMessage("该预定班级已经被督导！！")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						exit();
					}

				})
				.setNegativeButton("返回预定",
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
		schedulehasSaveDialog.setTitle("提示");
	}
	//返回按钮的点击事件监听器
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen) {
					leaveDialog.show();
				} else if (SupervisorFragment.searchIsOpen) {
					System.out.println("查询之后的退出事件！！");
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
					System.out.println("预约之后的退出事件！！");
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
	// 退出按钮事件
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
