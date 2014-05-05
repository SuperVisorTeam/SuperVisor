package com.gdut.supervisor.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.utils.LoginHandler;

public class WelcomeActivity extends Activity
{
	protected static final int START_LOGIN = 0; // 开启登陆界面的
	protected static final int START_MAIN = 1;
	private SharedPreferences preferences = null;
	private LoginHandler loginHandler = new LoginHandler(); // 获得登录处理器对象
	private String account;
	private String password;
	Intent intent;
	LinearLayout progressLayout = null;
	RelativeLayout wholeView = null;

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			progressLayout.setVisibility(View.INVISIBLE);
			if (msg.what == START_LOGIN)
			{
				Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			} else if (msg.what == START_MAIN)
			{
				BaseMessage.supervisor_no = account;
				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 除去标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		wholeView = (RelativeLayout) findViewById(R.id.rlayout_welcom);
		progressLayout = (LinearLayout) findViewById(R.id.layout_progress_welcome);
		// 获得用户信息
		preferences = getSharedPreferences("userdata", MODE_PRIVATE);
		account = preferences.getString("account", "");
		password = preferences.getString("password", "");
		// 获取自动登陆标志
		final boolean isAutoDebark = preferences.getBoolean("isAutoDebark", false);
		new Handler().postDelayed(new Runnable()
		{// 新建一个handler实现跳转

					public void run()
					{
						if (isAutoDebark)
						{ // 若自动登陆，则进行登录操作，登录成功后直接打开主界面
							ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
							NetworkInfo netInfo = connManager.getActiveNetworkInfo();
							if (netInfo == null || !netInfo.isAvailable())
							{ // 网络不可用情况
								Toast.makeText(WelcomeActivity.this, "网络不可用", Toast.LENGTH_LONG).show();
								handler.sendEmptyMessage(START_LOGIN);
							} else
							{
								login(); // 登陆联网操作
								progressLayout.setVisibility(View.VISIBLE); // 显示登陆的缓冲条
							}
						} else
						{ // 否则启动登陆界面
							handler.sendEmptyMessage(START_LOGIN);
						}

					}

				}, 1500);

	}

	/**
	 * 进行登录操作
	 */
	public void login()
	{
		new Thread()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				super.run();
				Looper.prepare();
				try
				{
					System.out.println("进入Welcome-run-try");
					int statusCode = loginHandler.login(account, password).getStatusCode();
					switch (statusCode)
					{
					case 200: /* 登录成功 */
						handler.sendEmptyMessage(START_MAIN);
						break;
					// case 404: /* 用户不存在 */
					// Toast.makeText(WelcomeActivity.this, "用户不存在",
					// Toast.LENGTH_SHORT).show();
					// break;
					case 401: /* 密码错误 */
						Toast.makeText(WelcomeActivity.this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(START_LOGIN);
						break;
					case 403: /* 禁止访问 */
						Toast.makeText(WelcomeActivity.this, "禁止访问", Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(START_LOGIN);
						break;
					default:
						break;
					}
				} catch (Exception ex)
				{
					ex.printStackTrace();
					Toast.makeText(WelcomeActivity.this, "请求超时,请稍后重试", Toast.LENGTH_SHORT).show();
					System.out.println("welcome系统繁忙");
					handler.sendEmptyMessage(START_LOGIN);
				}
				Looper.loop();// 进入loop中的循环，查看消息队列

			}

		}.start();
	}

}
