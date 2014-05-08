package com.gdut.supervisor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdut.supervisor.R;

/*
 * 用于请求网络数据过程中显示缓冲条
 *
 */
public class ShowProgressDialog {
	private static Dialog pDialog;
	/**
	 * 显示缓冲条对话框
	 * @param context	Activity环境
	 * @param message   要显示的信息
	 */
	public static void showProgress(Context context, String message) {
		pDialog = new AlertDialog.Builder(context).create();
		pDialog.setCanceledOnTouchOutside(false);
		LinearLayout pLayout = (LinearLayout) View.inflate(context, R.layout.dialog_evaluate_order_preentry, null);
		TextView alertMsg = (TextView) pLayout.findViewById(R.id.tv_progress_dialog);
		alertMsg.setText(message);
		pDialog.show();
		pDialog.setContentView(pLayout);
		
	}
	/**
	 * 缓冲条的消失
	 */
	public static void dismissProgress() {
		pDialog.dismiss();
	}
}
