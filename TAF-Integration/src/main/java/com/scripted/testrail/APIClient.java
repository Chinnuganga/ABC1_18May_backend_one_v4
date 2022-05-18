package com.scripted.testrail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.scripted.dataload.PropertyDriver;
import com.scripted.generic.FileUtils;

public class APIClient {
	private String auth;
	private String m_url;
	public static Logger LOGGER = Logger.getLogger(APIClient.class);

	public APIClient(String base_url, String auth) {
		if (!base_url.endsWith("/")) {
			base_url += "/";
		}

		this.m_url = base_url + "index.php?/api/v2/";
		this.auth = auth;
	}

	public Object sendGet(String uri) throws MalformedURLException, IOException, APIException {
		return this.sendRequest("GET", uri, null);
	}

	public Object sendPost(String uri, Object data) throws MalformedURLException, IOException, APIException {
		return this.sendRequest("POST", uri, data);

	}

	private Object sendRequest(String method, String uri, Object data)
			throws MalformedURLException, IOException, APIException {

		PropertyDriver propDriver = new PropertyDriver();
		propDriver.setPropFilePath("src/main/resources/Integrations/Jira/TestRail/Properties/TestRail.properties");

		if (PropertyDriver.readProp().containsKey("https.proxyHost")
				&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
			String host = PropertyDriver.readProp("https.proxyHost");
			String port = PropertyDriver.readProp("https.proxyPort");
			if (!host.isEmpty() && !port.isEmpty()) {
				System.setProperty("https.proxyHost", host);
				System.setProperty("https.proxyPort", port);
			}
		}
		URL url = new URL(this.m_url + uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("Content-Type", "application/json");
		conn.addRequestProperty("Authorization", "Basic " + this.auth);

		if (method == "POST") {
			
			if (data != null) {
				byte[] block = JSONValue.toJSONString(data).getBytes("UTF-8");

				conn.setDoOutput(true);
				OutputStream ostream = conn.getOutputStream();
				ostream.write(block);
				ostream.flush();
			}
		}
		int status = conn.getResponseCode();

		InputStream istream;
		if (status != 200) {
			istream = conn.getErrorStream();
			if (istream == null) {
				LOGGER.error("TestRail API return HTTP " + status + " (No additional error message received)");
				throw new APIException(
						"TestRail API return HTTP " + status + " (No additional error message received)");
			}
		} else {
			istream = conn.getInputStream();
			LOGGER.info("Successfully updated TestRail test status");
		}

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

		Object result;
		if (text != "") {
			result = JSONValue.parse(text);
		} else {
			result = new JSONObject();
		}

		if (status != 200) {
			String error = "No additional error message received";
			if (result != null && result instanceof JSONObject) {
				JSONObject obj = (JSONObject) result;
				if (obj.containsKey("error")) {
					error = '"' + (String) obj.get("error") + '"';
				}
			}

			throw new APIException("TestRail API returned HTTP " + status + "(" + error + ")");
		}

		return result;
	}
}
