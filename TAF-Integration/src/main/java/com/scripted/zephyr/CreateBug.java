package com.scripted.zephyr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import com.scripted.dataload.PropertyDriver;

public class CreateBug {
	public static String key = "";

	public static void createDefect(String strTsName, String strErrMsg, String embData) throws Exception {
		trustAllHosts();
		
		PropertyDriver propDriver = new PropertyDriver();
		String errMsg = "";
		propDriver.setPropFilePath("src/main/resources/Integrations/Jira/Zephyr/Properties/Zephyr.properties");
		String BaseUri = PropertyDriver.readProp("jira.baseurl");
		String auth = PropertyDriver.readProp("jira.authorization");
		String proKey = PropertyDriver.readProp("project.key");
		if (PropertyDriver.readProp().containsKey("https.proxyHost")
				&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
			String host = PropertyDriver.readProp("https.proxyHost");
			String port = PropertyDriver.readProp("https.proxyPort");
			if (!host.isEmpty() && !port.isEmpty()) {
				System.setProperty("https.proxyHost", host);
				System.setProperty("https.proxyPort", port);
			}
		}
		
		URL burl = new URL(BaseUri + "/rest/api/2/issue");
		HttpURLConnection bcon = (HttpURLConnection) burl.openConnection();

		bcon.addRequestProperty("Authorization", "Basic " + auth);
		bcon.addRequestProperty("Content-Type", "application/json");
		bcon.setRequestMethod("POST");
		
		JSONObject jobjt = new JSONObject();
		JSONObject jobj = new JSONObject();
		JSONObject job = new JSONObject();
		job.put("key", proKey);
		jobj.put("project", job);
		jobj.put("summary", strTsName);
		jobj.put("description", strErrMsg);
		JSONObject jb = new JSONObject();
		jb.put("name", "Bug");
		jobj.put("issuetype", jb);
		jobjt.put("fields", jobj);
		byte[] btk = JSONValue.toJSONString(jobjt).getBytes("UTF-8");
		bcon.setDoOutput(true);
		OutputStream om = bcon.getOutputStream();
		om.write(btk);
		om.flush();
		InputStream ism;
		int sta = bcon.getResponseCode();
		ism = bcon.getInputStream();
		String bug = "";
		if (ism != null) {
			BufferedReader read = new BufferedReader(new InputStreamReader(ism, "UTF-8"));
			String line;
			while ((line = read.readLine()) != null) {
				bug += line;
				bug += System.getProperty("line.separator");
			}
			read.close();
		}

		JSONObject bugObj = new JSONObject(bug);
		key = bugObj.getString("key");
		System.out.println("Defect Created Successfully in JIRA");
		System.out.println("Defect Key : " + key);

		if (embData != null) {
			addAttachmentToIssue(embData);
		}
	}

	public static void addAttachmentToIssue(String embData) throws IOException {
		String url = "https://ustqualityengineering.atlassian.net/rest/api/3/issue/" + key + "/attachments";
		PropertyDriver propDriver = new PropertyDriver();
		propDriver.setPropFilePath("src/main/resources/Integrations/Jira/Zephyr/Properties/Zephyr.properties");

		String token = PropertyDriver.readProp("jira.authorization");
		if (PropertyDriver.readProp().containsKey("https.proxyHost")
				&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
			String host = PropertyDriver.readProp("https.proxyHost");
			String port = PropertyDriver.readProp("https.proxyPort");
			int portNo = Integer.parseInt(port);
			if (!host.isEmpty() && !port.isEmpty()) {
				HttpHost proxyHost = new HttpHost(host, portNo);
				RequestConfig.Builder reqconfigconbuilder = RequestConfig.custom();
				reqconfigconbuilder = reqconfigconbuilder.setProxy(proxyHost);
				RequestConfig config = reqconfigconbuilder.build();

				try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

					byte[] imageByteArray = Base64.decodeBase64(embData);

					File imgfile = new File("src/main/resources/Screenshots/failurescreenshot.png");
					OutputStream os = new FileOutputStream(imgfile);

					os.write(imageByteArray);

					File file = new File("src/main/resources/Screenshots/failurescreenshot.png");
					HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
							.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName()).build();

					HttpUriRequest request = RequestBuilder.post(url).setConfig(config)
							.setHeader("Authorization", "Basic " + token).setHeader("X-Atlassian-Token", "nocheck")
							.setEntity(data).build();

					HttpResponse response = httpclient.execute(request);
					System.out.println("Attachment Added in the Defect");
				}

			}
		} else {
			try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
				
				byte[] imageByteArray = Base64.decodeBase64(embData);

				File imgfile = new File("src/main/resources/Screenshots/failurescreenshot.png");
				OutputStream os = new FileOutputStream(imgfile);

				os.write(imageByteArray);

				File file = new File("src/main/resources/Screenshots/failurescreenshot.png");
				HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName()).build();

				HttpUriRequest request = RequestBuilder.post(url).setHeader("Authorization", "Basic " + token)
						.setHeader("X-Atlassian-Token", "nocheck").setEntity(data).build();

				HttpResponse response = httpclient.execute(request);
				System.out.println("Attachment Added in the Defect");
			}

		}

	}

	private static void trustAllHosts() {
		// TODO Auto-generated method stub
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
