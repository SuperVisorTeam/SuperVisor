package com.gdut.supervisor.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;

public class LoginHandler {

	private DefaultHttpClient httpclient = new DefaultHttpClient();
	/** ��¼URL */
	// private String loginUrl =
	// "http://cscw.gdut.edu.cn:8888/edusupervisor/j_spring_security_check";
	/** �˳�URL */
	private String logoutUrl;

	/** ����ύ��URL */
	private String submitTableUrl;


	public LoginHandler() {
	}
	
	/**
	 * ��ñ���ύ��URL
	 * 
	 * @return
	 */
	public String getSubmitTableUrl() {
		return submitTableUrl;
	}

	/**
	 * �趨����ύ��URL
	 * 
	 * @param submitTableUrl
	 */
	public void setSubmitTableUrl(String submitTableUrl) {
		this.submitTableUrl = submitTableUrl;
	}

	/**
	 * ����˳�URL
	 * 
	 * @return
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * �趨�˳�URL
	 * 
	 * @param logoutUrl
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public DefaultHttpClient getHttpclient() {
		return this.httpclient;
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public StatusLine login(String username, String password) {

		StatusLine statusLine = null;
		// http://psy.gdut.edu.cn:8080
		BaseMessage.baseUrl = "http://psy.gdut.edu.cn:8080";
		String loginUrl = BaseMessage.baseUrl + "/j_spring_security_check";
		System.out.println("loginUrl:" + loginUrl);
		try {
			HttpPost httpost = new HttpPost(loginUrl);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// 输入督导员的用户�?密码
			params.add(new BasicNameValuePair("j_username", username));
			params.add(new BasicNameValuePair("j_password", password));

			httpost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);

			statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();

		} catch (IOException e) {

			ShowMessageDialog.showMessage(null, e.toString());
			e.printStackTrace();
		}

		return statusLine;
	}

	/**
	 * �?��登录
	 * 
	 * @return
	 */
	public boolean loginOut() {

		StatusLine statusLine = null;

		try {

			HttpDelete httpDelete = new HttpDelete(logoutUrl);

			HttpResponse response = httpclient.execute(httpDelete);

			statusLine = response.getStatusLine();

			int statusCode = statusLine.getStatusCode();
			// �?��成功
			if (statusCode == 200) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}