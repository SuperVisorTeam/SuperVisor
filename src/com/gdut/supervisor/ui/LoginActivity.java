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
	
	private Handler handler = new Handler() {	//������Ӧ�����½�̵߳Ľ��

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
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.login);

		et_account = (EditText) findViewById(R.id.et_login_account);
		et_password = (EditText) findViewById(R.id.et_login_password);
		btn_debark = (Button) findViewById(R.id.btn_login_debark);
		cb_rmpsword = (CheckBox) findViewById(R.id.cb_login_rm_password);
		cb_autodebark = (CheckBox) findViewById(R.id.cb_login_auto_debark);

		btn_debark.setOnClickListener(this);

		// ��ȡĬ���˺ź�����������ʾ����֮ǰû��ס���룬�����봦����ʾ
		preferences = getSharedPreferences("userdata", MODE_PRIVATE);
		editor = preferences.edit();
		et_account.setText(preferences.getString("account", null));
		et_password.setText(preferences.getString("password", null));
		
		

	}
	/**
	 * ��Ӧ��¼��ť
	 */
	@Override
	public void onClick(View v) {
		// ����һ���жϵ�½�Ƿ�ɹ������ɹ�����������棬�������û���Ϣ�����ɹ���������ʾ
		account = et_account.getText().toString().trim();
		password = et_password.getText().toString().trim();

		if (account.equals("")) {
			Toast.makeText(this, "�������˺�", Toast.LENGTH_SHORT).show();
		} else if (password.equals("")) {
			Toast.makeText(this, "����������", Toast.LENGTH_SHORT).show();
		} else {
			//�ж�����״��
			ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = connManager.getActiveNetworkInfo();
			if (netInfo == null || !netInfo.isAvailable()) {
				Toast.makeText(this, "���粻����", Toast.LENGTH_SHORT).show();
			} else {
				
				System.out.println(account + password);
				// ���е�¼����
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
							Toast.makeText(LoginActivity.this, "ϵͳ��æ", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					
					
				}.start();
				System.out.println("statusCode" + statusCode);
				

			}
		}

	}
	/**
	 * �Ե�½������д�����¼�ɹ������������
	 * @param statusCode
	 */
	public void handleDebarkReulst(int statusCode) {
		switch (statusCode) {
		case 200: /* ��¼�ɹ� */
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
			//Toast.makeText(this, "��½�ɹ�" + account, Toast.LENGTH_SHORT).show();
			break;
		case 404: /* �û������� */
			Toast.makeText(this, "�û�������", Toast.LENGTH_SHORT).show();
			break;
		case 401: /* ������� */
			Toast.makeText(this, "�������,����������", Toast.LENGTH_SHORT)
					.show();
			break;
		case 403: /* ��ֹ���� */
			Toast.makeText(this, "��ֹ����", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
	
	/**
	 * ���ؼ�����Ӧ
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				new AlertDialog.Builder(this)
						.setTitle("��ʾ")
						.setMessage("ȷ���˳���")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										finish();
									}
								}).setNegativeButton("ȡ��", null).show();
			}
			return true;
		}

		return super.dispatchKeyEvent(event);
	}


}
