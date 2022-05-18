package com.scripted.ado;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scripted.dataload.PropertyDriver;

public class GetTestPoint {

	static String testPoint = "";

	public static String getTstPoint(String testcaseid) {
		try {
			PropertyDriver propDriver = new PropertyDriver();
			propDriver.setPropFilePath("src/main/resources/Integrations/AzureDevOps/Properties/ADO.properties");
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
			String aurl = PropertyDriver.readProp("ADO.url");
			String auth = PropertyDriver.readProp("ADO.authorization");
			String reponame = PropertyDriver.readProp("ADO.reponame");
			String Proid = PropertyDriver.readProp("ADO.projectid");
			String planid = PropertyDriver.readProp("ADO.planid");
			String suiteid = PropertyDriver.readProp("ADO.suiteid");

			URL url = new URL(aurl + "/" + reponame + "/" + Proid + "/_apis/testplan/Plans/" + planid + "/Suites/"
					+ suiteid + "/TestPoint?testCaseId=" + testcaseid);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("Authorization", "Basic " + auth);
			conn.setRequestMethod("GET");
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
			JSONObject jobj = new JSONObject(text);
			JSONArray val = jobj.getJSONArray("value");
			for (Object object : val) {
				JSONObject obj = (JSONObject) object;

				testPoint = obj.get("id").toString();

			}
			System.out.println("Got Test Point of Test Case");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return testPoint;

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
