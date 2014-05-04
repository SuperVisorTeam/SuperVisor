package com.gdut.supervisor.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.dialog.ShowProgressDialog;
import com.gdut.supervisor.info.Edu_Survey_OrderInfo;
import com.gdut.supervisor.utils.SubmitHandler;

/**
 * 可预约信息列表List的适配器
 */
public class PreEntryAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private Context context;
	private Dialog orderDialog;
	private viewHolder holder;
	private OrderAsyncTask orderAsyncTask;
	private List<List> listSize, listList;
	private int count = 0;

	private int currentPosition = 0;
	private Button currentBtn;
	private boolean[] ORDER_SUCCESS;
//	private String[] classes;

	/**
	 * map 服务器返回的预约数据
	 */
	public PreEntryAdapter(Context context, List<List> listSize, List<List> listList)
	{
		//测试
		try
		{
			Log.v("log",  "PreEntryAdapter()--listSize-" + listSize.get(0).get(0));
			Log.v("log",  "PreEntryAdapter()--2-" + (CharSequence) listList.get(1).get(2));
			listSize.get(0).get(0);
			count = Integer.valueOf("" + listSize.get(0).get(0));
		} catch (Exception e)
		{
			//测试使用
			count = 30;
		}
		
		this.context = context;
		this.listSize = listSize;
		this.listList = listList;
		// ORDER_SUCCESS = new boolean[(Integer)listSize.get(0).get(0)];
		ORDER_SUCCESS = new boolean[count];
		initView();
	}

	private void initView()
	{
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		 return count;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
//		Log.v("log", "--->getView()-" + position);
		if (convertView == null)
		{
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.adapter_preentry, null);

			holder.btn_order = (Button) convertView.findViewById(R.id.btn_pre_entry_order);
			holder.txt_class = (TextView) convertView.findViewById(R.id.txt_pre_entry_class);
			holder.txt_time = (TextView) convertView.findViewById(R.id.txt_pre_entry_time);
			holder.txt_course = (TextView) convertView.findViewById(R.id.txt_pre_entry_course);
			holder.txt_teacher = (TextView) convertView.findViewById(R.id.txt_pre_entry_teacher);

			convertView.setTag(holder);
		} else
		{
			holder = (viewHolder) convertView.getTag();
		}
//		Log.v("log", "-->getView()--ORDER_SUCCESS[" + position + "]-" + ORDER_SUCCESS[position]);
		// 判断保存的position位置上的Butoon是否已预约成功
		if (ORDER_SUCCESS[position])
		{
			holder.btn_order.setText("已预约");
			// 这里不设的话返回来时还会可点击,因为向下拉时，Holder里的btn被设置成可点击了
			 holder.btn_order.setEnabled(false);
//			holder.btn_order.setClickable(false);
		} else
		{
			// 这里要写上默认的显示内容，要不然后面的子控件会出现显示已预订的情况。因为不设置的话btn还是Holder中的btn，而holder中的btn是已经设置了已预约的
			holder.btn_order.setText("预约");
			// 要设置为可点击，不然是Holder里的状态
			holder.btn_order.setEnabled(true);
//			holder.btn_order.setClickable(true);
			// 这个也要放这里判断
			holder.btn_order.setOnClickListener(new btnListener(position, holder.btn_order));
		}
		// listList的数据可能不完整，进行异常捕获
		try
		{
//			 课室
//			holder.txt_class.setText(classes[position]);
			
			holder.txt_class.setText((CharSequence) listList.get(position).get(4));
//			 课程名字
			holder.txt_course.setText((CharSequence) listList.get(position).get(2));
//			  老师
			holder.txt_teacher.setText((CharSequence) listList.get(position).get(3));
			//时间
			holder.txt_time.setText(Edu_Survey_OrderInfo.ORDER_WEEK + "周-" + Edu_Survey_OrderInfo.ORDER_DAY + "-" + Edu_Survey_OrderInfo.ORDER_TIME + "节");
		} catch (Exception e)
		{
			e.printStackTrace();
			if(position == 1)
			{
//				Toast.makeText(context, "数据不完整或没有数据!", 1).show();
				Log.v("log", "-->listList-数据不完整！");
			}
		}
		return convertView;
	}

	/**
	 * 要传入Button本身，使点击按钮时的currentBtn是这个
	 */
	class btnListener implements OnClickListener
	{
		private int position;
		private Button Btn;

		public btnListener(int position, Button currentBtn)
		{
			this.position = position;
			this.Btn = currentBtn;
		}

		@Override
		public void onClick(View v)
		{
//			Toast.makeText(context, "点击第 " + (position + 1) + "个Button", 0).show();
			currentPosition = position;
			currentBtn = Btn;
			orderAsyncTask = new OrderAsyncTask();
			orderAsyncTask.execute("");
			
			ShowProgressDialog.showProgress(context, "预约中···");
		}

	}

	private static class viewHolder
	{
		private Button btn_order;
		private TextView txt_class, txt_teacher, txt_course, txt_time;

	}

	/**
	 * 异步进行预约操作AsyncTask
	 */
	private class OrderAsyncTask extends AsyncTask<String, Integer, Integer>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		// 异步处理
		@Override
		protected Integer doInBackground(String... nullNow)
		{
			Log.v("log", "-->doInBackground()--params-" + nullNow[0]);
			try
			{
				Thread.sleep(1000);
				//预约操作
				return SubmitHandler.submitOrder((String) listList.get(currentPosition).get(0), (String) listList
						.get(currentPosition).get(1), (String) listList.get(currentPosition).get(5),
						Edu_Survey_OrderInfo.ORDER_DAY, Edu_Survey_OrderInfo.ORDER_WEEK);

			} catch (Exception e)
			{
				e.printStackTrace();
				Log.v("log", "-->doInBackground() ERROR!");
			}
			return 0;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Integer responseCode)
		{
			Log.v("log", "-->onPostExecute()--responseCode-" + responseCode);
			switch (responseCode)
			{
			case 200:
				ORDER_SUCCESS[currentPosition] = true;
				currentBtn.setText("已预约");
				// 这里不设的话在当前页面下currentBtn一直可点击,因为getView里设的话是要调用getView时才能起作用的。
				 currentBtn.setEnabled(false);
//				currentBtn.setClickable(false);
				break;
			case 400:
			default:
				Toast.makeText(context, "预约失败", 1).show();
				break;
			}

			Log.v("log", "-->onPostExecute()--currentPosition-" + currentPosition);
			Log.v("log", "-->onPostExecute()--ORDER_SUCCESS[" + currentPosition + "]-"
					+ ORDER_SUCCESS[currentPosition]);
			ShowProgressDialog.dismissProgress();
			super.onPostExecute(responseCode);
		}

	}

}
