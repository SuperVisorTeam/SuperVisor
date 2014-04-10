package com.gdut.supervisor.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.gdut.supervisor.dialog.ShowMessageDialog;
import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_Survey;
import com.gdut.supervisor.info.Edu_SurveyToIphone;
import com.google.gson.Gson;

public class SubmitHandler {
	private static Gson gson = new Gson();
	public static DefaultHttpClient httpclient = new DefaultHttpClient();
	public static Map<String, Object> getmap = new HashMap<String, Object>();

	public static StatusLine login(String username, String password) {

		StatusLine statusLine = null;
		String loginUrl = BaseMessage.baseUrl + "/j_spring_security_check";
		try {
			System.out.println("loginUrl:" + loginUrl);
			HttpPost httpost = new HttpPost(loginUrl);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// 输入督导员的用户名/密码
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
	 * 
	 * @param classSituation
	 * @return
	 * @throws Exception
	 *             提交表单
	 */
	public static int submitForm(Edu_Survey classSituation) throws Exception {

		/**
		 * 表格提交的URL
		 */
		String submitTableUrl = BaseMessage.baseUrl + "/Edu_Survey/save";
		Edu_SurveyToIphone es = ChangeData.changePhone(classSituation);
		PrintlnPhoneFromData.println(es, "提交后的数据显示");
		String json = gson.toJson(es);

		
		/*String json = "";
		if (es.getCourse_class_no() == null)// 预定
		{
			json = gson.toJson(classSituation);
		} else {
			PrintlnPhoneFromData.println(es, "提交后的数据显示");
			json = gson.toJson(es);
		}
*/
		HttpPost httpost = new HttpPost(submitTableUrl);

		HttpEntity gsonEntity = new StringEntity(json, "UTF-8");

		httpost.setEntity(gsonEntity);

		httpost.setHeader("Content-Type", "application/json;charset=UTF-8");

		HttpResponse response = httpclient.execute(httpost);

		HttpEntity entity = response.getEntity();

		String responseText = EntityUtils.toString(entity, HTTP.UTF_8);
		System.out.println("提交表单的状态码："
				+ response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("提交成功");
		} else if (response.getStatusLine().getStatusCode() == 400) {
			System.out.println("表单参数有误!");
		} else if (response.getStatusLine().getStatusCode() == 409) {
			System.out.println("教学班已经被督导！！!");
		} else {
			System.out.println("提交失败!");
		}

		return response.getStatusLine().getStatusCode();
	}

	/**
	 * 
	 * @param school_district
	 * @param date
	 * @param study_place
	 * @param section
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             通过校区，日期，上课地点，上课节次 返回学院名称，专业班级，应到人数
	 */
	public static int getMap(String school_district, String date,
			String study_place, String section, String user_no)
			throws ClientProtocolException, IOException {
		// 取得
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl + "/dudao/"
				+ school_district + "/" + date + "/" + study_place + "/"
				+ section + "/" + user_no);
		System.out.println("=========" + BaseMessage.baseUrl + "/dudao/"
				+ school_district + "/" + date + "/" + study_place + "/"
				+ section + "/" + user_no);
		HttpResponse response = httpclient.execute(httpGet);
		System.out.println("获取上课地点的状态码："
				+ response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			System.out.println("输入数据错误或此地点没有对应的课要上");
		} else if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("输入正确");

			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

			getmap = gson.fromJson(responseText, HashMap.class);
		} else if (response.getStatusLine().getStatusCode() == 402) {
			System.out.println("该班级已经被预定");
		} else if (response.getStatusLine().getStatusCode() == 409) {
			System.out.println("该班级已经被督导");
		}

		return response.getStatusLine().getStatusCode();
	}

	/**
	 * 
	 * @param user_no
	 * @param start_date
	 * @param end_date
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             通过督导员的学号，开始时间，结束时间 来获取上课地点，校区，专业班级等表单数据
	 */
	public static Map<String, List<List>> getMap2(String user_no,
			String start_date, String end_date) throws ClientProtocolException,
			IOException {
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl + "/dudao/check/"
				+ user_no + "/" + start_date + "/" + end_date);

		HttpResponse response = httpclient.execute(httpGet);
		System.out
				.println("查找的状态码：" + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			System.out.println("输入数据错误或此地点没有对应的课要上");
		} else if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("输入正确");

			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

			Map<String, List<List>> map = gson.fromJson(responseText,
					HashMap.class);
			return map;
		}

		return null;

	}

	/**
	 * 查找功能的实现
	 */
	public static Edu_Survey getEdu_Survey(String survey_id)
			throws ClientProtocolException, IOException {

		// 取得
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl
				+ "/dudao/checkForUpdate/" + survey_id);

		HttpResponse response = httpclient.execute(httpGet);

		HttpEntity entity = response.getEntity();

		String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

		System.out.println("responseText:" + responseText);

		Edu_Survey edu_survey = gson.fromJson(responseText, Edu_Survey.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 创建日期
		String modifys = edu_survey.getModify_Time();
		long modifytime = Long.valueOf(modifys);
		Date modifydt = new Date(modifytime);
		String modifydate = sdf.format(modifydt);
		edu_survey.setModify_Time(modifydate);

		// 创建日期
		String adds = edu_survey.getAdd_Time();
		long addtime = Long.valueOf(adds);
		Date addtimedt = new Date(addtime);
		String add_Time = sdf.format(addtimedt);
		edu_survey.setAdd_Time(add_Time);

		return edu_survey;
	}

	/**
	 * 
	 * @param classSituation
	 * @return
	 * @throws Exception
	 *             修改表单
	 */
	public static int modificationForm(Edu_Survey modificationSituation)
			throws Exception {

		/**
		 * 修改的URL
		 */
		String modificationUrl = BaseMessage.baseUrl + "/Edu_Survey/update";

		String json = gson.toJson(modificationSituation);

		HttpPost httpost = new HttpPost(modificationUrl);

		HttpEntity gsonEntity = new StringEntity(json, "UTF-8");

		httpost.setEntity(gsonEntity);

		httpost.setHeader("Content-Type", "application/json;charset=UTF-8");

		HttpResponse response = httpclient.execute(httpost);

		HttpEntity entity = response.getEntity();

		String responseText = EntityUtils.toString(entity, HTTP.UTF_8);
		System.out.println("修改表单的状态码："
				+ response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("修改成功");
		} else {
			System.out.println("修改提交失败!");
		}

		return response.getStatusLine().getStatusCode();
	}

	/**
	 * 
	 * @param user_no
	 * @param start_date
	 * @param end_date
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             预定功能 通过学号 来获得相关的信息
	 */
	public static Map<String, List<List>> getScheduleMap(String user_no)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl + "/bookingQuery/"
				+ user_no);

		HttpResponse response = httpclient.execute(httpGet);
		System.out
				.println("预定的状态码：" + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			System.out.println("没有找到相应的预定教室");
		} else if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("输入正确");

			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

			Map<String, List<List>> map = gson.fromJson(responseText,
					HashMap.class);
			return map;
		}

		return null;

	}
}
