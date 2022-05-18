package com.scripted.api;

import java.io.BufferedReader;
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

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import junit.framework.Assert;

public class RestAPIClient {
	public static JSONObject jResponse;
	public static Logger LOGGER = Logger.getLogger(RestAPIClient.class);

	public static void restApiGetReqRes(String getUri) {
		try {
			trustAllHosts();
			System.setProperty("https.proxyHost", "165.225.106.32");
			System.setProperty("https.proxyPort", "10223");

			URL url = new URL(getUri);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.addRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setDoOutput(true);
			int responseCode = conn.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				LOGGER.info("Response :" + response.toString());
				jResponse = new JSONObject(response.toString());
			} else {
				LOGGER.info("No response for get request.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Failed to retrieve response from get request"+e);
			Assert.fail("Failed to retrieve response from get request"+e);
		}
	}

	public static void valJsonResponseVal(String jsonPath, String jKey, Object expValue) {

		try {
			String strVal = null;
			Object actVal = fetchJsonPathValue(jResponse, jsonPath, jKey);

			Assert.assertEquals("Mismatch in the response - JsonPath : " + jsonPath, actVal, expValue);
			LOGGER.info("Expected value " + expValue + " is matching with the actual : " + actVal);
			System.out.println("Expected value " + expValue + " is matching with the actual : " + actVal);
		} catch (Exception e) {
			LOGGER.error("Error in validating the response, Exception :" + e);
			Assert.fail("Error in validating the response, Exception :" + e);
		}
	}

	public static Object fetchJsonPathValue(JSONObject jsonObject, String jsonPath, String jsonKey) {
		int jsonPathLen;
		Object strObjValue = null;
		try {
			JSONArray jsonArr = new JSONArray();
			if (jsonPath.contains(".")) {
				String[] splitJsonObj = jsonPath.split("\\.");
				jsonPathLen = splitJsonObj.length;
				for (int i = 0; i < jsonPathLen; i++) {
					if (splitJsonObj[i].contains("[")) {
						String[] splitJsonArr = splitJsonObj[i].split("\\[");
						jsonArr = jsonObject.getJSONArray(splitJsonArr[0]);
						String jsonarrCount = splitJsonArr[1].replace("]", "");
						int arrCount = Integer.parseInt(jsonarrCount);
						jsonObject = jsonArr.getJSONObject(arrCount);
					} else {
						jsonObject = jsonObject.getJSONObject(splitJsonObj[i]);
					}
				}
				strObjValue = jsonObject.get(jsonKey);

			} else {
				if (jsonPath.contains("[")) {
					String[] splitJsonArr = jsonPath.split("\\[");
					jsonArr = jsonObject.getJSONArray(splitJsonArr[0]);
					String jsonarrCount = splitJsonArr[1].replace("]", "");
					int arrCount = Integer.parseInt(jsonarrCount);
					jsonObject = jsonArr.getJSONObject(arrCount);
				} else {
					jsonObject = jsonObject.getJSONObject(jsonPath);
				}
				strObjValue = jsonObject.get(jsonKey);
			}

		} catch (Exception e) {
			LOGGER.error("Error in fetching the jsonpath value, Exception :" + e);
			Assert.fail("Error in fetching the jsonpath value, Exception :" + e);
		}
		return strObjValue;
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
