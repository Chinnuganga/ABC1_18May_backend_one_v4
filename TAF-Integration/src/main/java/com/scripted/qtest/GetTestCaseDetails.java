package com.scripted.qtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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

public class GetTestCaseDetails {

	static String TCName = "";

	
	public static String getTestCaseName(String pid) {
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
			URL url = new URL(qurl + "/api/v3/projects/" + Proid + "/test-cases");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("Authorization", auth);
			conn.addRequestProperty("Content-Type", "application/json");
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

			JSONArray jobj = new JSONArray(text);

			System.out.println("Got Test Case Details");
			CucumberJsonExtractor obj = new CucumberJsonExtractor();
			TCName = obj.getTestCaseDetails(jobj, pid);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return TCName;

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
