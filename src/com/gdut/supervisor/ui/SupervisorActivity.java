package com.gdut.supervisor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.adapter.SupervisorAdapter;
import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_CourseClass;
import com.gdut.supervisor.info.Edu_Survey;
import com.gdut.supervisor.utils.PrintlnFromData;
import com.gdut.supervisor.utils.SubmitHandler;
import com.gdut.supervisor.view.FirstItemFragment;
import com.gdut.supervisor.view.SecondItemFragment;
import com.gdut.supervisor.view.SupervisorFragment;
import com.gdut.supervisor.view.ThirdItemFragment;
/**
 * 包含三表单的FragmentActivity
 */
public class SupervisorActivity extends FragmentActivity  {
	/**
	 * 提交成功状态码
	 */
     final	int SUBMIT_SUCCESS=1;
     
    /**
     * 修改提交成功状态码
     */
     final int MODIFY_SUCCESS=2;

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
	public static com.gdut.supervisor.info.Edu_Survey situation=null;
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
    
    /**
     *处理新线程的handler
     */
    Handler supervisorHandler=new Handler()
    {

		@Override
		public void handleMessage(Message msg) {
		
			switch(msg.what)
			{
			case 200:
				if (!SupervisorFragment.scheduleIsOpen)
				{
					// 提交成功
					submitsuccessDialog.show();
				} else 
				{
					// 预定提交成功
					schedulesuccessDialog.show();
				}
			break;
			case 400:
				ShowMessageDialog.showMessage(SupervisorActivity.this, "表单参数有误!");
				break;
			case 409:
				if (!SupervisorFragment.scheduleIsOpen) {
					// 已经被督导
					hasSaveDialog.show();
				} else {
					// 预定被督导
					schedulehasSaveDialog.show();

				}
				break;
			default :
				ShowMessageDialog.showMessage(SupervisorActivity.this, "提交失败!");
			}
			
    }
		};
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
		 
		//重置按钮
			clearButton = (Button) findViewById(R.id.clearButton);
		//提交按钮
			submitButton = (Button) findViewById(R.id.submitButton);
			//
			clearButton.setOnClickListener(new ButtonActionListener());
			submitButton.setOnClickListener(new ButtonActionListener());
			
			
		
			
		}
	
	private class ButtonActionListener implements OnClickListener {
		public void onClick(View v) {
			// 重置和删除按钮
			if (v.getId() == R.id.clearButton) {
				if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen) {
					clearDialog.show();
				} else if (SupervisorFragment.searchIsOpen) {
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
			}
			// 提交和修改按钮
			if (v.getId() == R.id.submitButton) {
				if (!SupervisorFragment.searchIsOpen && !SupervisorFragment.scheduleIsOpen) {
					showNoFinishPage();
				} else if (SupervisorFragment.searchIsOpen) {
					if (SearchFormActivity.getAudit()) {
						iniAuditdaiog();
					} else {
						System.out.println("查询之后不要再判断是否填写完整！！");

						initmodificationdaiog();
						modificationDialog.show();
					}
				} else if (SupervisorFragment.scheduleIsOpen) {
					showNoFinishPage();
				}
			}
		}
		
		

		/**
		 * 显示第几页没有填写完整
		 */
		
		private void showNoFinishPage() {
			//code的值0，1，2，3，分别表示表单填写完整，第一张表没填写完整，第三张表没填写完整
			int code = isFinish();
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
				//showFristItemActivity();
			}
				break;
			case 2: {
				// 第二张表没有填定完成
				ShowMessageDialog.showMessage(SupervisorActivity.this,
						"请填写完整第二表单！！");
				//showSecondItemActivity();
			}
				break;
			case 3: {
				// 第三张表没有填定完成
				ShowMessageDialog.showMessage(SupervisorActivity.this,
						"请填写完整第三表单！！");
				//showThirdItemActivity();
			}
				break;
			}

		}
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
								//进入查询界面
								Intent intent = new Intent(
										SupervisorActivity.this,
										SearchFormActivity.class);
								startActivity(intent);
								//干掉三个表单界面
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
				} else if
				  (SupervisorFragment.searchIsOpen) {
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
				} else if 
				  (SupervisorFragment.scheduleIsOpen) {
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
			//提示【是否修改】窗口，按【确定修改】则进行对表单的修改操作，否则不修改表单
			modificationDialog = builder.create();
			modificationDialog.setTitle("提示");
			//提示【修改成功】窗口，如果退出修改的话，则跳转到查询窗口，
			//如果继续修改的话，留在本界面
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
									/*Intent intent = new Intent(
											SupervisorActivity.this,
											SearchFormActivity.class);
									startActivity(intent);*/
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
			//提示【修改成功】窗口，如果退出修改的话，则跳转到查询窗口，
		    //如果继续修改的话，留在本界面
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
									/*Intent intent = new Intent(
											SupervisorActivity.this,
											SearchFormActivity.class);
									startActivity(intent);*/
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
		SupervisorActivity.this.finish();
	}
	
	/**
	 * 提交 表格
	 * 提交前首先获取三个表单的内容
	 */
	private void submitFrom() {
		
		/**
		 *判断网络状态 
		 */
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connManager.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isAvailable()) {
			Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
			return;
		} 
		//如果预定功能没打开，则创建一张新表
		if (!SupervisorFragment.scheduleIsOpen) {
			createSituation();
		} else {
			situation.setTerminal_Type("Android");
			String version = android.os.Build.VERSION.RELEASE;
			situation.setTerminal_Desc(version);
			FirstItemFragment.greatFristItemClassSituation();
			SecondItemFragment.greatSecondItemClassSituation();
			ThirdItemFragment.greatThirdItemClassSituation();
		}
		
		
		new Thread()
		{
			/**
			 *表示状态码
			 */
			 int StatusCode = -1;
			@Override
			public void run() {
				try {
					PrintlnFromData.println(situation, "提交时表格的内容:");
					StatusCode = SubmitHandler.submitForm(situation);
					System.out.println("提交表单的状态码：" + StatusCode);
				} catch (Exception e) {
					ShowMessageDialog.showMessage(SupervisorActivity.this,
							"系统繁忙，请稍后提交！！！");
					return;
				}
				
				supervisorHandler.sendEmptyMessage(StatusCode);
			}
			
		}.start();
	}
	
	
/**
 *判断表格是否填写完整
 */
	private int isFinish() {
		//判断第一个表单是否填写完整
		if (! FirstItemFragment.getIsFinish())
			return 1;
				
	    if (!ThirdItemFragment.getIsFinish()) {
			
				return 3;
		}
		return 0;
	}

	/**
	 * 重置函数
	 */
		private void clear() {

			if (firstOpen) {
				FirstItemFragment.clearFristItem();
			}
			if (secondOpen) {
				SecondItemFragment.clearSecondItem();
			}
			if (thirdOpen) {
				ThirdItemFragment.clearThirdItem();
			}
			//showFristItemActivity();
		}
		
		// 修改后的提交函数
		private void modification() {
			FirstItemFragment.greatFristItemClassSituation();
			if (secondOpen) {
				System.out.println("第二个页面打开了，要创建页面的数据！！");
				SecondItemFragment.greatSecondItemClassSituation();
			} else if (!secondOpen) {

			}
			if (thirdOpen) {
				System.out.println("第三个页面打开了，要创建页面的数据！！");
				ThirdItemFragment.greatThirdItemClassSituation();
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
			FirstItemFragment.greatFristItemClassSituation();
			SecondItemFragment.greatSecondItemClassSituation();
			ThirdItemFragment.greatThirdItemClassSituation();
			System.out.println("situation=======" + situation);
			situation.setCourse_class_no(edu_CourseClass);
		}
		// 启动时调用
		protected void onStart() {
			super.onStart();
			System.out.println("In SuperVisorActivity.OnStart()  SupervisorFragment.searchIsOpen ="+SupervisorFragment.searchIsOpen);
			
			if (SupervisorFragment.searchIsOpen) {
				FirstItemFragment.isGet = false;
				SecondItemFragment.isGet = false;
				ThirdItemFragment.isGet = false;
				System.out.println("In SuperVisorActivity.OnStart() FirstItemFragment.isGet = "+FirstItemFragment.isGet);
			   	
						// TODO Auto-generated method stub
						 //situation = SearchFormActivity.getSearchBaseMessage();
						 edu_CourseClass = situation.getCourse_class_no();
				
				       		
								
			}
			else if (SupervisorFragment.scheduleIsOpen) {
				situation = ScheduleActivity.getSearchBaseMessage();
				edu_CourseClass = situation.getCourse_class_no();
			}
		}
}
