package com.gdut.supervisor.utils;

import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginDataHandler {
	
	private static String HISTORY_ACCOUNTS = "history_accounts";
	private static String HISTORY_NUM = "history_num";
	private SharedPreferences preferences = null;
	private Editor editor = null;
	private String[] history_accounts = null;
	private String[] history_passwords = null;
	

	public LoginDataHandler(Context context) {
		preferences = context.getSharedPreferences("history_userdata", Context.MODE_APPEND);
	}
	
	
	public void saveAccountMsg(String account, String password) {
		editor = preferences.edit();
		if(!isAccountExist(account)) {
			int num = preferences.getInt(HISTORY_NUM, 0);
			editor.putString(HISTORY_ACCOUNTS + num, account);
			editor.putInt(HISTORY_NUM, num + 1);
		}
		editor.putString(account, password);
		editor.commit();
		
	}
	public boolean isAccountExist(String account) {
		String accountsStr = Arrays.toString(getAccounts());
		return accountsStr.contains(account);
	}
	
	public String[] getAccounts() {
		int num = preferences.getInt(HISTORY_NUM, 0);
		history_accounts = new String[num];
		history_passwords = new String[num];
		for(int i = 0; i < num; i ++) {
			history_accounts[i] = preferences.getString(HISTORY_ACCOUNTS + i, null);
			history_passwords[i] = preferences.getString(history_accounts[i], null);
		}
		return history_accounts;
	}
	
	public String[] getPwd() {
		return history_passwords;
	}
	
}
