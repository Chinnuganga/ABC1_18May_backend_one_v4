package com.scripted.xray;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
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

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.scripted.dataload.PropertyDriver;

public class XrayAPIClient {

	
	static JSONObject body = new JSONObject();
	static JSONObject data = new JSONObject();
	static Object token;
	org.json.simple.JSONObject response = null;
	public static HttpResponse httpsResponse = null;
	public static Logger LOGGER = Logger.getLogger(XrayAPIClient.class);
	private static String cdir = System.getProperty("user.dir");
	public XrayAPIClient() throws IOException
	{
		trustAllHosts();
		URL url = new URL("https://xray.cloud.xpand-it.com/api/v1/authenticate");
		PropertyDriver propDriver = new PropertyDriver();
		propDriver.setPropFilePath("src/main/resources/Integrations/Jira/Xray/Properties/Xray.properties");
		if (PropertyDriver.readProp().containsKey("https.proxyHost")
				&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
			String host = PropertyDriver.readProp("https.proxyHost");
			String port = PropertyDriver.readProp("https.proxyPort");
			if (!host.isEmpty() && !port.isEmpty()) {
				System.setProperty("https.proxyHost", host);
				System.setProperty("https.proxyPort", port);
			}
		}
		String client_id = PropertyDriver.readProp("ClientId");
		String client_secret = PropertyDriver.readProp("ClientSecret");
		body.put("client_id",client_id);
		body.put("client_secret",client_secret);
		data = body;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("Content-Type", "application/json");

		byte[] block = JSONValue.toJSONString(data).getBytes("UTF-8");
		conn.setDoOutput(true);
		java.io.OutputStream ostream = conn.getOutputStream();
		ostream.write(block);
		ostream.flush();
		InputStream istream;
		int status = conn.getResponseCode();
		istream = conn.getInputStream();

		String text = "";
		if (istream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));

			String line;
			while ((line = reader.readLine()) != null) {
				text += line;
				text += System.getProperty("line.separator");
			}

			reader.close();
		}

		if (text != "") {
			token = JSONValue.parse(text);
		} else {
			token = new JSONObject();
		}
		System.out.println("The Authorization Token is : "+token);
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
	public static void getScenario(String key) throws IOException
	{
		
		URL url = new URL("https://xray.cloud.xpand-it.com/api/v1/export/cucumber?keys="+key);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("Content-Type", "application/json");
		conn.addRequestProperty("Authorization", "Bearer "+token);
		InputStream istream;
		int status = conn.getResponseCode();
		istream = conn.getInputStream();

		String text = "";
		if (istream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));

			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Feature:"))
				{
					String[] splitline=line.split("Feature:");
					text += "Feature: "+splitline[1];
					text += System.getProperty("line.separator");
				}
				if(line.contains("|")||line.contains("Scenario Outline:")||line.contains("@")||line.contains("Scenario:")||line.contains("Given")||line.contains("And")||line.contains("When")||line.contains("Then"))
				{
				text += line;
				text += System.getProperty("line.separator");
				}
						
			}
			reader.close();
			FileWriter fw=new FileWriter(cdir + "/Features" + "/XrayFeatures/"+key+".feature");    
	           fw.write(text);    
	           fw.close();  
	           System.out.println("The "+key+" Test Case Scenario Has Extracted Successfully");
			
		}		
	}
	public static void createTestExecutionAndStatusUpdate(Object data) throws IOException
	{
		URL url = new URL("https://xray.cloud.xpand-it.com/api/v1/import/execution/cucumber");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("Content-Type", "application/json");
		conn.addRequestProperty("Authorization", "Bearer "+token);
		byte[] block = JSONValue.toJSONString(data).getBytes("UTF-8");
		conn.setDoOutput(true);
		java.io.OutputStream ostream = conn.getOutputStream();
		ostream.write(block);
		ostream.flush();
		InputStream istream;
		int status = conn.getResponseCode();                                                                                  
		istream = conn.getInputStream();

		String text = "";
		if (istream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));

			String line;
			while ((line = reader.readLine()) != null) {
				text += line;
				text += System.getProperty("line.separator");
			}
			reader.close();
		}		
	}
	
}
