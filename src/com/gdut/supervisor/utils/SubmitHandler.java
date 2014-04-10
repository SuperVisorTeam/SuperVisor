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

			// ���붽��Ա���û���/����
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
	 *             �ύ��
	 */
	public static int submitForm(Edu_Survey classSituation) throws Exception {

		/**
		 * ����ύ��URL
		 */
		String submitTableUrl = BaseMessage.baseUrl + "/Edu_Survey/save";
		Edu_SurveyToIphone es = ChangeData.changePhone(classSituation);
		PrintlnPhoneFromData.println(es, "�ύ���������ʾ");
		String json = gson.toJson(es);

		
		/*String json = "";
		if (es.getCourse_class_no() == null)// Ԥ��
		{
			json = gson.toJson(classSituation);
		} else {
			PrintlnPhoneFromData.println(es, "�ύ���������ʾ");
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
		System.out.println("�ύ����״̬�룺"
				+ response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("�ύ�ɹ�");
		} else if (response.getStatusLine().getStatusCode() == 400) {
			System.out.println("����������!");
		} else if (response.getStatusLine().getStatusCode() == 409) {
			System.out.println("��ѧ���Ѿ�����������!");
		} else {
			System.out.println("�ύʧ��!");
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
	 *             ͨ��У�������ڣ��Ͽεص㣬�Ͽνڴ� ����ѧԺ���ƣ�רҵ�༶��Ӧ������
	 */
	public static int getMap(String school_district, String date,
			String study_place, String section, String user_no)
			throws ClientProtocolException, IOException {
		// ȡ��
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl + "/dudao/"
				+ school_district + "/" + date + "/" + study_place + "/"
				+ section + "/" + user_no);
		System.out.println("=========" + BaseMessage.baseUrl + "/dudao/"
				+ school_district + "/" + date + "/" + study_place + "/"
				+ section + "/" + user_no);
		HttpResponse response = httpclient.execute(httpGet);
		System.out.println("��ȡ�Ͽεص��״̬�룺"
				+ response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			System.out.println("�������ݴ����˵ص�û�ж�Ӧ�Ŀ�Ҫ��");
		} else if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("������ȷ");

			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

			getmap = gson.fromJson(responseText, HashMap.class);
		} else if (response.getStatusLine().getStatusCode() == 402) {
			System.out.println("�ð༶�Ѿ���Ԥ��");
		} else if (response.getStatusLine().getStatusCode() == 409) {
			System.out.println("�ð༶�Ѿ�������");
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
	 *             ͨ������Ա��ѧ�ţ���ʼʱ�䣬����ʱ�� ����ȡ�Ͽεص㣬У����רҵ�༶�ȱ�����
	 */
	public static Map<String, List<List>> getMap2(String user_no,
			String start_date, String end_date) throws ClientProtocolException,
			IOException {
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl + "/dudao/check/"
				+ user_no + "/" + start_date + "/" + end_date);

		HttpResponse response = httpclient.execute(httpGet);
		System.out
				.println("���ҵ�״̬�룺" + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			System.out.println("�������ݴ����˵ص�û�ж�Ӧ�Ŀ�Ҫ��");
		} else if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("������ȷ");

			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

			Map<String, List<List>> map = gson.fromJson(responseText,
					HashMap.class);
			return map;
		}

		return null;

	}

	/**
	 * ���ҹ��ܵ�ʵ��
	 */
	public static Edu_Survey getEdu_Survey(String survey_id)
			throws ClientProtocolException, IOException {

		// ȡ��
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl
				+ "/dudao/checkForUpdate/" + survey_id);

		HttpResponse response = httpclient.execute(httpGet);

		HttpEntity entity = response.getEntity();

		String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

		System.out.println("responseText:" + responseText);

		Edu_Survey edu_survey = gson.fromJson(responseText, Edu_Survey.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// ��������
		String modifys = edu_survey.getModify_Time();
		long modifytime = Long.valueOf(modifys);
		Date modifydt = new Date(modifytime);
		String modifydate = sdf.format(modifydt);
		edu_survey.setModify_Time(modifydate);

		// ��������
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
	 *             �޸ı�
	 */
	public static int modificationForm(Edu_Survey modificationSituation)
			throws Exception {

		/**
		 * �޸ĵ�URL
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
		System.out.println("�޸ı���״̬�룺"
				+ response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("�޸ĳɹ�");
		} else {
			System.out.println("�޸��ύʧ��!");
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
	 *             Ԥ������ ͨ��ѧ�� �������ص���Ϣ
	 */
	public static Map<String, List<List>> getScheduleMap(String user_no)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(BaseMessage.baseUrl + "/bookingQuery/"
				+ user_no);

		HttpResponse response = httpclient.execute(httpGet);
		System.out
				.println("Ԥ����״̬�룺" + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			System.out.println("û���ҵ���Ӧ��Ԥ������");
		} else if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("������ȷ");

			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity, HTTP.UTF_8);

			Map<String, List<List>> map = gson.fromJson(responseText,
					HashMap.class);
			return map;
		}

		return null;

	}
}
