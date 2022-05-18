package com.scripted.qtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import com.scripted.dataload.PropertyDriver;

public class APIClient {

	public static String timeParser(String myDate, long milliseconds) throws Exception {

		int sec = (int) TimeUnit.MILLISECONDS.toSeconds(milliseconds);

		String s = myDate.replace("-", "/");
		String s1 = s.replace("T", " ");
		String s2 = s1.substring(0, s1.indexOf("."));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = sdf.parse(s2);
		System.out.println("Given date:" + date);

		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(date.getTime());
		calender.add(Calendar.SECOND, sec);
		Date changeDate = calender.getTime();
	

		String formatedDate = calender.get(Calendar.YEAR) + "-" + (calender.get(Calendar.MONTH) + 1) + "-"
				+ calender.get(Calendar.DATE) + "T"
				+ (calender.get(Calendar.HOUR_OF_DAY) + ":" + (calender.get(Calendar.MINUTE)) + ":"
						+ (calender.get(Calendar.SECOND)) + "." + (calender.get(Calendar.MILLISECOND)) + "Z");
		System.out.println("formatedDate : " + formatedDate);
		return formatedDate;
	}

	static JSONObject data = new JSONObject();

	public APIClient(String cucumberJsonPath) throws Exception {

		CucumberJsonExtractor cucm = new CucumberJsonExtractor();
		JSONObject jObj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);
		String status = "";
		JSONObject featureObject = null;
		JSONArray test_step_logs = new JSONArray();
		JSONObject des = new JSONObject();
		String TCName = "";
		PropertyDriver propDriver = new PropertyDriver();
		propDriver.setPropFilePath("src/main/resources/Integrations/Jira/qTest/Properties/qTest.properties");
		String projectKey=PropertyDriver.readProp("qTest.projectkey");
		for (String feature : jObj.keySet()) {
			featureObject = jObj.getJSONObject(feature);
			for (String testCase : featureObject.keySet()) {
				String testcaseid = testCase.replace("@" + projectKey + "-", "").trim();
				GetTestCaseDetails tc = new GetTestCaseDetails();
				TCName = tc.getTestCaseName(testcaseid);
				if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
					status = "PASS";
				} else {
					status = "FAIL";
				}

				JSONObject dataobj = new JSONObject();
				dataobj.put("name", TCName);
				dataobj.put("automation_content", TCName);
				dataobj.put("status", status);
				String timestamp = featureObject.getJSONObject(testCase).getString("timestamp").toString();
				dataobj.put("exe_start_date", timestamp);
				int duration = featureObject.getJSONObject(testCase).getInt("duration");
				long exeTime = duration;
				String endTime = timeParser(timestamp, exeTime);
				System.out.println("Ending Time: " + endTime);
				dataobj.put("exe_end_date", endTime);

				JSONArray Stpstatus = featureObject.getJSONObject(testCase).getJSONArray("stepStatus");
				JSONArray Stpname = featureObject.getJSONObject(testCase).getJSONArray("stepNames");
				String str = Stpstatus.get(0).toString();
				int stpCount = Stpname.length();
				int stpStatusCount = Stpstatus.length();
				if (stpCount == stpStatusCount) {
					for (int i = 0; i < stpCount; i++) {

						des.put("description", Stpname.get(i).toString());
						des.put("expected_result", "");
						des.put("actual_result", "");
						if (Stpstatus.get(i).toString().contains("passed"))
							des.put("status", "PASS");
						else
							des.put("status", "FAIL");
						test_step_logs.put(des);
						des = new JSONObject("{}");
					}
				}

				dataobj.put("test_step_logs", test_step_logs);
				System.out.println(dataobj);
				data = dataobj;
				statusUpdate(data);
				test_step_logs = new JSONArray("[]");

			}
		}

	}

	public static void statusUpdate(JSONObject data1) {
		try {
			PropertyDriver propDriver = new PropertyDriver();
			propDriver.setPropFilePath("src/main/resources/Integrations/Jira/qTest/Properties/qTest.properties");
			if (PropertyDriver.readProp().containsKey("https.proxyHost")
					&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
				String host = PropertyDriver.readProp("https.proxyHost");
				String port = PropertyDriver.readProp("https.proxyPort");
				if (!host.isEmpty() && !port.isEmpty()) {
					System.setProperty("https.proxyHost", host);
					System.setProperty("https.proxyPort", port);
				}
			}

			trustAllHosts();
			String qurl = PropertyDriver.readProp("qTest.url");
			String auth = PropertyDriver.readProp("qTest.authorization");
			String Proid = PropertyDriver.readProp("qTest.projectid");
			URL url = new URL(qurl + "/api/v3/projects/" + Proid + "/test-runs/0/auto-test-logs");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("Authorization", auth);
			conn.addRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			byte[] block = JSONValue.toJSONString(data1).getBytes("UTF-8");

			conn.setDoOutput(true);
			OutputStream ostream = conn.getOutputStream();
			ostream.write(block);
			ostream.flush();
			InputStream istream;
			int status = conn.getResponseCode();
			istream = conn.getInputStream();

			String text = "";
			if (istream != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));

				String line1;
				while ((line1 = reader.readLine()) != null) {
					text += line1;
					text += System.getProperty("line1.separator");
				}
				reader.close();
			}
			System.out.println("Test Case Status Updated");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void trustAllHosts() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509ExtendedTrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle)
						throws CertificateException {

				}

			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
