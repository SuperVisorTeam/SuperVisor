package com.gdut.supervisor.adapter;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdut.supervisor.R;
import com.gdut.supervisor.info.Edu_Survey_OrderInfo;
import com.google.gson.Gson;

public class PreEntryAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private View view;
	private Context context;
	private Map map;
	private String summitUrl;
	private Handler orderHandler;
	Button btn_order;
	// private Button button;
	private int num;
	TextView txtClass, teacherName, className, courseName;

	/**
	 * map 服务器返回的预约数据
	 */
	public PreEntryAdapter(Context context, int num)
	{
		this.context = context;
		this.map = map;
		inflater = LayoutInflater.from(context);
		orderHandler = new OrderHandler();
		this.num = num;
	}

	@Override
	public int getCount()
	{
		return num;
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
		Log.v("log", "--->getView()");
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.adapter_preentry, null);
			btn_order = (Button) convertView.findViewById(R.id.btn_pre_entry_pre);

			convertView.setTag(btn_order);
		} else
		{
			btn_order = (Button) convertView.getTag();
		}

		btn_order.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(context, "点击第 " + (position + 1) + "个Button", 0).show();
				OrderAsyncTask orderAsyncTask = new OrderAsyncTask();
				orderAsyncTask.execute(summitUrl);
				btn_order.setText("已预约");
				// new OrderThread().start();

			}
		});
		
		
		return convertView;
	}


	/**
	 * Handler
	 */
	private class OrderHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			btn_order.setText("已预约");
			super.handleMessage(msg);
		}
	}

	/**
	 * 线程操作
	 */
	private class OrderThread extends Thread
	{

		Message msg = new Message();

		public void run()
		{
			try
			{

			} catch (Exception e)
			{
			}
			msg.arg1 = 888;
			orderHandler.sendMessage(msg);
		}
	}

	/**
	 * 异步进行预约操作AsyncTask
	 */
	private class OrderAsyncTask extends AsyncTask<String, Integer, String[]>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		// 异步处理
		@Override
		protected String[] doInBackground(String... params)
		{
			Log.v("log", "-->params:" + params[0]);
			// /dudaoSaveBooking/{course_Class_No}/{schedule_id}/{semester}/{dayOfWeek}
			summitUrl = "http://10.21.32.123:8080/dudaoSaveBooking/" + "(2013-2014-2)-02153939-00005985-1" + "/"
					+ "41283" + "/" + "2013-2014-2" + "/2"
					+ Edu_Survey_OrderInfo.dayOfWeek;
			try
			{
				Gson gson = new Gson();
				String json = gson.toJson(params[0]);
				DefaultHttpClient client = new DefaultHttpClient();
//				HttpPost post = new HttpPost(summitUrl);
//				HttpEntity postEntity = new StringEntity(json, "UTF-8");
//				post.setEntity(postEntity);
//				post.setHeader("Content-Type", "application/json;charset=UTF-8");
//
//				HttpResponse response = client.execute(post);
//				HttpEntity responseEntity = response.getEntity();
//				String responseText = EntityUtils.toString(responseEntity, HTTP.UTF_8);
//				Log.v("log", "-->预约返回状态码：" + response.getStatusLine().getStatusCode());
//				Map<String, List<List>> map = gson.fromJson(responseText, HashMap.class);
				String[][] str = new String[1][1];
				
				HttpGet httpGet = new HttpGet(summitUrl);
				HttpResponse response = client.execute(httpGet);
				Log.v("log", "---" + response.getStatusLine().getStatusCode());
				switch (response.getStatusLine().getStatusCode())
				{
				case 200:
					Toast.makeText(context, "预约成功！", 1).show();
					break;

				default:
					Toast.makeText(context, "预约失败！", 1).show();
					break;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				Log.e("log", "ERROR!!!");
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			btn_order.setText("已预约");
			super.onPostExecute(result);
		}

	}
	
}
