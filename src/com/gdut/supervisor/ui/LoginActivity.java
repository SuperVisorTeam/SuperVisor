package com.gdut.supervisor.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.utils.LoginHandler;


public class LoginActivity extends Activity implements OnClickListener {
	private EditText et_account;
	private EditText et_password;
	private Button btn_debark;
	private CheckBox cb_rmpsword;
	private CheckBox cb_autodebark;
	private String account;
	private String password;
	private SharedPreferences preferences;
	private Editor editor;
	private LoginHandler loginHandler = new LoginHandler();
	int statusCode = -1;
	
	private Handler handler = new Handler() {	//用于响应处理登陆线程的结果

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == statusCode) {
				handleDebarkReulst(statusCode);
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.login);

		et_account = (EditText) findViewById(R.id.et_login_account);
		et_password = (EditText) findViewById(R.id.et_login_password);
		btn_debark = (Button) findViewById(R.id.btn_login_debark);
		cb_rmpsword = (CheckBox) findViewById(R.id.cb_login_rm_password);
		cb_autodebark = (CheckBox) findViewById(R.id.cb_login_auto_debark);

		btn_debark.setOnClickListener(this);

		// 获取默认账号和密码用于显示，如之前没记住密码，则密码处不显示
		preferences = getSharedPreferences("userdata", MODE_PRIVATE);
		editor = preferences.edit();
		et_account.setText(preferences.getString("account", null));
		et_password.setText(preferences.getString("password", null));
		
		

	}
	/**
	 * 响应登录按钮
	 */
	@Override
	public void onClick(View v) {
		// 步骤一：判断登陆是否成功，若成功则进入主界面，并保存用户信息，不成功则做出提示
		account = et_account.getText().toString().trim();
		password = et_password.getText().toString().trim();

		if (account.equals("")) {
			Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
		} else if (password.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
		} else {
			//判断联网状况
			ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connManager.getActiveNetworkInfo();
			if (netInfo == null || !netInfo.isAvailable()) {
				Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
			} else {
				
				System.out.println(account + password);
				// 进行登录操作
				new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						try {
							statusCode = loginHandler.login(account, password).getStatusCode();
							handler.sendEmptyMessage(statusCode);
						} catch (Exception ex) {
							ex.printStackTrace();
							Toast.makeText(LoginActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					
					
				}.start();
				System.out.println("statusCode" + statusCode);
				

			}
		}

	}
	/**
	 * 对登陆结果进行处理，登录成功则进入主界面
	 * @param statusCode
	 */
	public void handleDebarkReulst(int statusCode) {
		switch (statusCode) {
		case 200: /* 登录成功 */
			editor.putString("account", account);
			if(cb_rmpsword.isChecked()) {
				editor.putString("password", password);
			} else {
				editor.putString("password", "");
			}
			editor.putBoolean("isAutoDebark", cb_autodebark.isChecked());
			editor.commit();
			BaseMessage.supervisor_no = account;
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			//Toast.makeText(this, "登陆成功" + account, Toast.LENGTH_SHORT).show();
			break;
		case 404: /* 用户不存在 */
			Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
			break;
		case 401: /* 密码错误 */
			Toast.makeText(this, "密码错误,请重新输入", Toast.LENGTH_SHORT)
					.show();
			break;
		case 403: /* 禁止访问 */
			Toast.makeText(this, "禁止访问", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 返回键的响应
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				new AlertDialog.Builder(this)
						.setTitle("提示")
						.setMessage("确定退出吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										finish();
									}
								}).setNegativeButton("取消", null).show();
			}
			return true;
		}

		return super.dispatchKeyEvent(event);
	}


}
