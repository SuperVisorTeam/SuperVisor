package com.gdut.supervisor.ui;

import java.util.ArrayList;
import java.util.Map;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.utils.LoginDataHandler;
import com.gdut.supervisor.utils.LoginHandler;


public class LoginActivity extends Activity implements OnClickListener {
	protected static final int LOGIN_EXCEPTION = 0;
	private AutoCompleteTextView et_account;
	private EditText et_password;
	private Button btn_debark;
	private CheckBox cb_rmpsword;
	private CheckBox cb_autodebark;
	private LinearLayout progressLayout = null;	//用于登陆缓冲条的显示
	private String account;
	private String password;
	private SharedPreferences preferences;
	private Editor editor;
	private LoginHandler loginHandler = new LoginHandler();
	private LoginDataHandler loginDataHandler = null;
	private String[] accountsAndPwds = null;
	int statusCode = -1;
	
	private Handler handler = new Handler() {	//用于响应处理登陆线程的结果

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			progressLayout.setVisibility(View.INVISIBLE);
			if(msg.what == statusCode) {
				handleDebarkReulst(statusCode);
			} else if(msg.what == LOGIN_EXCEPTION) {
				Toast.makeText(LoginActivity.this, "请求超时,请稍后重试", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.login);
		progressLayout = (LinearLayout) findViewById(R.id.layout_progress_login);
		
		et_account = (AutoCompleteTextView) findViewById(R.id.et_login_account);
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
		
		loginDataHandler = new LoginDataHandler(this);
		accountsAndPwds = loginDataHandler.getAccounts();
		//如果历史记录不为空
		if(accountsAndPwds != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1
					, accountsAndPwds);
			et_account.setAdapter(adapter);
			et_account.setOnItemClickListener(new AutoItemSelectListener());
		}

	}
	/**
	 * 响应登录按钮
	 */
	@Override
	public void onClick(View v) {
		// 步骤一：判断登陆是否成功，若成功则进入主界面，并保存用户信息，不成功则做出提示
		account = et_account.getText().toString();
		password = et_password.getText().toString();

		if (account.equals("")) {
			Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
		} else if (password.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
		} else {
			//判断联网状况
			ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connManager.getActiveNetworkInfo();
			if (netInfo == null || !netInfo.isAvailable()||!netInfo.isConnectedOrConnecting()) {
				Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
			} else {
				
				progressLayout.setVisibility(View.VISIBLE);
				// 进行登录操作
				new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						try {
							System.out.println("进入Login-run-try");
							statusCode = loginHandler.login(account, password).getStatusCode();
							handler.sendEmptyMessage(statusCode);
							System.out.println("statusCode" + statusCode);
						} catch (Exception ex) {
							ex.printStackTrace();
							handler.sendEmptyMessage(LOGIN_EXCEPTION);
							//return;
						}
					}
					
				}.start();
				

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
				editor.putString("password", password);	//保存默认的登录账户和密码
				loginDataHandler.saveAccountMsg(account, password);	//将账号密码保存在记录中
			} else {
				editor.putString("password", "");
				loginDataHandler.saveAccountMsg(account, "");
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
	 * 为自动补全文本框提供监听器，选中账号则密码处显示对应密码
	 *
	 */
	public class AutoItemSelectListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String[] passwords = loginDataHandler.getPwd();
			et_password.setText(passwords[position]);
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
										android.os.Process.killProcess(android.os.Process.myPid());
									}
								}).setNegativeButton("取消", null).show();
			}
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

	public class AutoCompleteAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
