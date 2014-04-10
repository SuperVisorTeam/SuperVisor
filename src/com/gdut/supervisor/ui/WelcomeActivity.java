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
import android.view.Window;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.utils.LoginHandler;

public class WelcomeActivity extends Activity {
	protected static final int START_LOGIN = 0;	//������½����� 
	protected static final int START_MAIN = 1;
	private SharedPreferences preferences = null;
	private LoginHandler loginHandler = new LoginHandler();	//��õ�¼����������
	private String account;
	private String password;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ��ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		// ����û���Ϣ
		preferences = getSharedPreferences("userdata", MODE_PRIVATE);
		account = preferences.getString("account", "");
		password = preferences.getString("password", "");
		// ��ȡ�Զ���½��־
		final boolean isAutoDebark = preferences.getBoolean("isAutoDebark",
				false);
		new Handler().postDelayed(new Runnable() {// �½�һ��handlerʵ����ת

					public void run() {
						if (isAutoDebark) { // ���Զ���½������е�¼��������¼�ɹ���ֱ�Ӵ�������
							ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
							NetworkInfo netInfo = connManager
									.getActiveNetworkInfo();
							if (netInfo == null || !netInfo.isAvailable()) {
								Toast.makeText(WelcomeActivity.this, "���粻����",
										Toast.LENGTH_LONG).show();
								handler.sendEmptyMessage(START_LOGIN);
							} else {
								login();
							}
						} else { // ����������½����
							handler.sendEmptyMessage(START_LOGIN);
						}
					}

				}, 3000);

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == START_LOGIN) {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			} else if(msg.what == START_MAIN) {
				BaseMessage.supervisor_no = account;
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}

	};

	/**
	 * ���е�¼����
	 */
	public void login() {
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Looper.prepare();
				try {
					int statusCode = loginHandler.login(account, password)
							.getStatusCode();
					switch (statusCode) {
					case 200: /* ��¼�ɹ� */
						handler.sendEmptyMessage(START_MAIN);
						break;
					// case 404: /* �û������� */
					// Toast.makeText(WelcomeActivity.this, "�û�������",
					// Toast.LENGTH_SHORT).show();
					// break;
					case 401: /* ������� */
						Toast.makeText(WelcomeActivity.this, "�������,����������",
								Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(START_LOGIN);
						break;
					case 403: /* ��ֹ���� */
						Toast.makeText(WelcomeActivity.this, "��ֹ����",
								Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(START_LOGIN);
						break;
					default:
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
//					Toast.makeText(WelcomeActivity.this, "ϵͳ��æ",	//���⣺���׳��쳣д��˾û���ã�
//							Toast.LENGTH_SHORT).show();
					handler.sendEmptyMessage(START_LOGIN);
					return;
				}
				Looper.loop();// ����loop�е�ѭ�����鿴��Ϣ����

			}

		}.start();
	}

}
