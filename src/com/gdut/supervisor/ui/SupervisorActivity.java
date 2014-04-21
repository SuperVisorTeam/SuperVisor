package com.gdut.supervisor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_CourseClass;
import com.gdut.supervisor.info.Edu_Survey;
import com.gdut.supervisor.utils.PrintlnFromData;
import com.gdut.supervisor.utils.SubmitHandler;
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
		  //初始化各种窗口控件
		  iniDialog();
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
		 
		
		}
	/**
	 * 返回键的监听时间处理器
	 * /(non-Javadoc)
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
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
					Intent intent = new Intent(SupervisorActivity.this,
							SearchFormActivity.class);
					startActivity(intent);
					SupervisorActivity.this.finish();
				} else if (SupervisorFragment.scheduleIsOpen) {
					System.out.println("预约之后的退出事件！！");
					BaseMessage.num = 0;
					BaseMessage.teacherName = "";
					SupervisorFragment.scheduleIsOpen = false;
					secondOpen = false;
					thirdOpen = false;
					Intent intent = new Intent(SupervisorActivity.this,
							ScheduleActivity.class);
					startActivity(intent);
					SupervisorActivity.this.finish();
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 审核窗口
	 */
	private void iniAuditdaiog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("该数据已经审核，不能执行修改操作！！")
				.setPositiveButton("返回查询",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								BaseMessage.num = 0;
								BaseMessage.teacherName = "";
								SupervisorFragment.searchIsOpen = false;
								secondOpen = false;
								thirdOpen = false;
								Intent intent = new Intent(
										SupervisorActivity.this,
										SearchFormActivity.class);
								startActivity(intent);
								SupervisorActivity.this.finish();
							}

						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		auditDialog = builder.create();
		auditDialog.setTitle("提示");
		auditDialog.show();
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
		//提交窗口的初始化
		builder.setMessage("您确定要 提交 吗？")
				.setPositiveButton("提交", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						submitFrom();
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
						clear();
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
								clear();
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
								BaseMessage.num = 0;
								BaseMessage.teacherName = "";
								SupervisorFragment.scheduleIsOpen = false;
								secondOpen = false;
								thirdOpen = false;
								Intent intent = new Intent(
										SupervisorActivity.this,
										ScheduleActivity.class);
								startActivity(intent);
								SupervisorActivity.this.finish();
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
								clear();
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
								BaseMessage.num = 0;
								BaseMessage.teacherName = "";
								SupervisorFragment.scheduleIsOpen = false;
								secondOpen = false;
								thirdOpen = false;
								Intent intent = new Intent(
										SupervisorActivity.this,
										ScheduleActivity.class);
								startActivity(intent);
								SupervisorActivity.this.finish();
							}
						});
		schedulehasSaveDialog = builder.create();
		schedulehasSaveDialog.setTitle("提示");
	}

	/**
	 * 退出按钮事件
	 */
	private void exit() {
		BaseMessage.num = 0;
		BaseMessage.teacherName = "";
		SupervisorFragment.searchIsOpen = false;
		SupervisorFragment.scheduleIsOpen = false;
		secondOpen = false;
		thirdOpen = false;
		Intent intent = new Intent(SupervisorActivity.this, MainActivity.class);
		startActivity(intent);
		SupervisorActivity.this.finish();
	}
	
	/**
	 * 提交 表格
	 */
	private void submitFrom() {
		if (!SupervisorFragment.scheduleIsOpen) {
			createSituation();
		} else {
			situation.setTerminal_Type("Android");
			String version = android.os.Build.VERSION.RELEASE;
			situation.setTerminal_Desc(version);
			/*FristItemActivity.greatFristItemClassSituation();
			SecondItemActivity.greatSecondItemClassSituation();
			ThirdItemActivity.greatThirdItemClassSituation();*/
		}
		int i = -1;
		try {
			PrintlnFromData.println(situation, "提交时表格的内容:");
			i = SubmitHandler.submitForm(situation);
			System.out.println("提交表单的状态码：" + i);
		} catch (Exception e) {
			ShowMessageDialog.showMessage(SupervisorActivity.this,
					"系统繁忙，请稍后提交！！！");
			return;
		}
		if (i == 200) {
			if (!SupervisorFragment.scheduleIsOpen) {
				// 提交成功
				submitsuccessDialog.show();
			} else {
				// 预定提交成功
				schedulesuccessDialog.show();

			}
		} else if (i == 400) {
			ShowMessageDialog.showMessage(this, "表单参数有误!");
		} else if (i == 409) {
			if (!SupervisorFragment.scheduleIsOpen) {
				// 已经被督导
				hasSaveDialog.show();
			} else {
				// 预定被督导
				schedulehasSaveDialog.show();

			}

		} else {
			ShowMessageDialog.showMessage(this, "提交失败!");

		}
	}
	/**
	 * 重置函数
	 */
		private void clear() {
/*
			if (firstOpen) {
				FristItemActivity.clearFristItem();
			}
			if (secondOpen) {
				SecondItemActivity.clearSecondItem();
			}
			if (thirdOpen) {
				ThirdItemActivity.clearThirdItem();
			}
			showFristItemActivity();*/
		}
		
		// 修改之后的各种窗口
		private void initmodificationdaiog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("是否提交修改")
					.setPositiveButton("确定修改",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									modification();
								}

							})
					.setNegativeButton("取消修改",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			modificationDialog = builder.create();
			modificationDialog.setTitle("提示");
			builder.setMessage("修改成功！！")
					.setPositiveButton("退出修改",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									BaseMessage.num = 0;
									BaseMessage.teacherName = "";
									SupervisorFragment.searchIsOpen = false;
									secondOpen = false;
									thirdOpen = false;
									Intent intent = new Intent(
											SupervisorActivity.this,
											SearchFormActivity.class);
									startActivity(intent);
									SupervisorActivity.this.finish();
								}

							})
					.setNegativeButton("继续修改",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			modificationsuccessDialog = builder.create();
			modificationsuccessDialog.setTitle("提示");
			builder.setMessage("修改失败！！")
					.setPositiveButton("退出修改",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									BaseMessage.num = 0;
									BaseMessage.teacherName = "";
									SupervisorFragment.searchIsOpen = false;
									secondOpen = false;
									thirdOpen = false;
									Intent intent = new Intent(
											SupervisorActivity.this,
											SearchFormActivity.class);
									startActivity(intent);
									SupervisorActivity.this.finish();
								}

							})
					.setNegativeButton("继续修改",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			modificationfaileDialog = builder.create();
			modificationfaileDialog.setTitle("提示");
		}

		// 修改后的提交函数
		private void modification() {
			/*FristItemActivity.greatFristItemClassSituation();
			if (secondOpen) {
				System.out.println("第二个页面打开了，要创建页面的数据！！");
				SecondItemActivity.greatSecondItemClassSituation();
			} else if (!secondOpen) {

			}
			if (thirdOpen) {
				System.out.println("第三个页面打开了，要创建页面的数据！！");
				ThirdItemActivity.greatThirdItemClassSituation();
			} else if (!thirdOpen) {

			}
			situation.setCourse_class_no(edu_CourseClass);
			System.out.println("修改后的situation：" + situation);
			int i = -1;
			try {
				PrintlnFromData.println(situation, "修改时表格的内容:");
				i = SubmitHandler.modificationForm(situation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("修改提交状态码：" + i);
			if (i == 200)// 修改成功
			{
				modificationsuccessDialog.show();
			} else {
				modificationfaileDialog.show();
			}
       */
		}
		
		/**
		 * 创建一个表单
		 */
		private void createSituation() {
			situation = new Edu_Survey();
			edu_CourseClass = new Edu_CourseClass();
			situation.setTerminal_Type("Android");
			String version = android.os.Build.VERSION.RELEASE;
			situation.setTerminal_Desc(version);
		/*	FristItemActivity.greatFristItemClassSituation();
			SecondItemActivity.greatSecondItemClassSituation();
			ThirdItemActivity.greatThirdItemClassSituation();*/
			System.out.println("situation=======" + situation);
			situation.setCourse_class_no(edu_CourseClass);
		}
		/**
		 * 显示第几页没有填写完整
		 */
		private void showNoFinishPage() {
			/*int code = isFinish();
			switch (code) {
			case 0: {
				// 填定完成

				submitDialog.show();

			}
				break;
			case 1: {
				// 第一张表没有填定完成
				ShowMessageDialog.showMessage(SupervisorActivity.this,
						"请填写完整第一表单！！");
				showFristItemActivity();
			}
				break;
			case 2: {
				// 第二张表没有填定完成
				ShowMessageDialog.showMessage(SupervisorActivity.this,
						"请填写完整第二表单！！");
				showSecondItemActivity();
			}
				break;
			case 3: {
				// 第三张表没有填定完成
				ShowMessageDialog.showMessage(SupervisorActivity.this,
						"请填写完整第三表单！！");
				showThirdItemActivity();
			}
				break;
			}

		}*/
	}
		// 判断表格是否填写完整
		private int isFinish() {
			/*boolean fristIsFinish = false;
			boolean thirdIsFinish = false;
			fristIsFinish = FristItemActivity.getIsFinish();
			if (!fristIsFinish) {
				return 1;
			} else if (!secondOpen) {
				return 2;
			}
			if (thirdOpen) {
				thirdIsFinish = ThirdItemActivity.getIsFinish();
				if (!thirdIsFinish) {
					return 3;
				}
			} else if (!thirdOpen) {
				return 3;
			}*/
			return 0;
		}
		// 启动时调用
		protected void onStart() {
			super.onStart();
			if (SupervisorFragment.searchIsOpen) {
			/*	FristItemActivity.isGet = false;
				SecondItemActivity.isGet = false;
				ThirdItemActivity.isGet = false;*/
				situation = SearchFormActivity.getSearchBaseMessage();
				edu_CourseClass = situation.getCourse_class_no();
			}
			if (SupervisorFragment.scheduleIsOpen) {
				situation = ScheduleActivity.getSearchBaseMessage();
				edu_CourseClass = situation.getCourse_class_no();
			}
		}
}
