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
 * 工具的Fragment
 */
@SuppressLint("ValidFragment")
public class ToolsFragment extends Fragment {
	/**
	 * 按钮数组
	 */
	private static Button[] tool_button = new Button[6];
	/**
	 * 按钮的个数
	 */
	private static int toolnum = 6;

	public static ToolsFragment helpFragment;

	/**
	 * 私有化构造方法
	 */
	private ToolsFragment() {
		Log.v("log", "-->HelpFragment-HelpFragment()");
	}

	/**
	 * 内部类，保证加载时时线程安全的。
	 */
	private static class SingletonHolder {
		private static ToolsFragment instance = new ToolsFragment();
	}

	/**
	 * 提供外部创建HelpFragment对象的方法。
	 */
	public static ToolsFragment getInstance() {
		return SingletonHolder.instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View toolView = inflater.inflate(R.layout.fragment_tools, container,
				false);
		// 初始化按钮
		int i = 0;
		/**
		 * 校园新闻按钮
		 */
		tool_button[i++] = (Button) toolView.findViewById(R.id.news);
		/**
		 * 校内通知按钮
		 */

		tool_button[i++] = (Button) toolView.findViewById(R.id.notices);
		/**
		 * 图书查询按钮
		 */

		tool_button[i++] = (Button) toolView.findViewById(R.id.book);

		/**
		 * 地图导航按钮
		 */

		tool_button[i++] = (Button) toolView.findViewById(R.id.map);
		/**
		 * 快递查询按钮
		 */

		tool_button[i++] = (Button) toolView.findViewById(R.id.post);
		/**
		 * 实时公交按钮
		 */

		tool_button[i++] = (Button) toolView.findViewById(R.id.bus);
		/**
		 * 按钮监听
		 */
		for (int k = 0; k < toolnum; k++) {
			tool_button[k]
					.setOnClickListener((android.view.View.OnClickListener) new ButtonListener());
		}
		return toolView;
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			// 新闻事件
			case (R.id.news): {
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();

			}
				break;
			// 通知事件
			case (R.id.notices): {
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();
			}
				break;

			// 图书事件
			case (R.id.book): {
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();
			}
				break;
			// 地图事件
			case (R.id.map): {
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();
			}
				break;

			// 快递事件
			case (R.id.post): {
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();
			}
				break;

			// 公交事件
			case (R.id.bus): {
				Toast.makeText(getActivity(), "功能暂未实现，后续版本将推出", 0).show();
			}
				break;
			default:
				break;
			}
		}

	}
}