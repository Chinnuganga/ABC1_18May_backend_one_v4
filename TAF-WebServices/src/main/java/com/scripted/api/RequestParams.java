package com.scripted.api;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

public class RequestParams {

	public String uri;
	public String contenttype;
	public String proxy;
	public int port;
	public JSONObject jsonBody;
	public JSONArray jsonArrBody;
	public String RestMethodType;
	public String genJsonResponsePath = "src/main/resources/GenRocket/Output/";
	public String apiJsonRequestPath = "src/main/resources/WebServices/APIJsonRequest/";
    public static Logger LOGGER = Logger.getLogger(RequestParams.class);

	public String getRestMethodType() {
		return RestMethodType;
	}

	public void setRestMethodType(String restMethodType) {
		RestMethodType = restMethodType;
	}

	public JSONObject getJsonbody() {
		return jsonBody;
	}

	public void setJsonbody(String fileName) {
		try {
			String requestFilePath = GetProp.getFilePath(apiJsonRequestPath + fileName + ".txt");
			String content = new String(Files.readAllBytes(Paths.get(requestFilePath)));
			jsonBody = new JSONObject(content);
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
			Assert.fail("Error while set the Json body : "+e);
			e.printStackTrace();
		}
	}

	
	public void setGenJsonbody(String fileName) {
		try {
			String requestFilePath = GetProp.getFilePath(genJsonResponsePath + fileName + ".json");
			String content = new String(Files.readAllBytes(Paths.get(requestFilePath)));
			jsonArrBody = new JSONArray(content);
			jsonBody = jsonArrBody.getJSONObject(0);
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * @param uri
	 * @param contentType
	 * @param mediaType
	 * @param basePath
	 * @param baseURI
	 * @param body
	 * @param header
	 * @param proxy
	 * @param host
	 * @param port
	 * @param scheme
	 * @param proxy_URI
	 * @param iSrelaxedHTTPSValidation
	 * @param protocol
	 * @param file
	 * @param password
	 * @param path
	 * @param keystore
	 * @param headersMap
	 */
	public RequestParams(String uri, String contentType, String proxy, int port, String body, String restMethodType) {
		super();
		this.uri = uri;
		this.contenttype = contentType;
		this.proxy = proxy;
		this.port = port;
		//this.body = body;
		this.RestMethodType = restMethodType;
	}

	public String getproxy() {
		return proxy;
	}

	public void setproxyAndPort(String proxy, int port) {
		this.proxy = proxy;
		this.port = port;
	}

	public int getport() {
		return port;
	}

	public void setport(int port) {
		this.port = port;
	}

	public String geturi() {
		return uri;
	}

	public void seturi(String uri) {
		this.uri = uri;
	}

	public String getcontenttype() {
		return contenttype;
	}

	public void setcontenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public RequestParams() {
	}

}
