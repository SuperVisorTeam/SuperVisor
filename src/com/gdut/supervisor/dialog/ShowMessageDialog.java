package com.gdut.supervisor.dialog;

import android.app.AlertDialog;
import android.content.Context;

public class ShowMessageDialog
{

	public static void showMessage(Context context, String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setPositiveButton("确定", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("提示");
		dialog.show();
	}
}
