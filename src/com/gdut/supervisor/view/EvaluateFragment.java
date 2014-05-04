package com.gdut.supervisor.view;

import com.gdut.supervisor.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 评价老师的Fragment
 */
@SuppressLint("ValidFragment")
public class EvaluateFragment extends Fragment {
	private static Button push_button;

	public static EvaluateFragment evaluateFragment;

	/**
	 * 私有化构造方法
	 */
	private EvaluateFragment() {
	}

	/**
	 * 内部类，保证加载时时线程安全的。
	 */
	private static class SingletonHolder {
		private static EvaluateFragment instance = new EvaluateFragment();
	}

	/**
	 * 提供外部创建EvaluateFragment的方法,使用单例。
	 */
	public static EvaluateFragment getInstance() {
		return SingletonHolder.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View EvaluateView = inflater.inflate(R.layout.fragment_evaluate,
				container, false);

		/**
		 * 提交按钮初始化
		 */
		push_button = (Button) EvaluateView.findViewById(R.id.push_button);
		/**
		 * 按钮监听
		 */

		push_button
				.setOnClickListener((android.view.View.OnClickListener) new pushListener());

		return EvaluateView;
	}

	private class pushListener implements OnClickListener {
		public void onClick(View v) {
			if (v.getId() == R.id.push_button)
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();
		}
	}
}