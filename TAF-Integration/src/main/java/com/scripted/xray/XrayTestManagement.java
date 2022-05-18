package com.scripted.xray;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scripted.dataload.PropertyDriver;

public class XrayTestManagement {
	org.json.simple.JSONObject body = new org.json.simple.JSONObject();
	org.json.simple.JSONObject response = null;
	public static Logger LOGGER = Logger.getLogger(XrayTestManagement.class);
	public void updateTestCaseStatus(String cucumberJsonPath) throws IOException, APIException {
		
		XrayAPIClient client = new XrayAPIClient();
		CucumberJsonExtractor cucm = new CucumberJsonExtractor();
		JSONArray data=cucm.readData(cucumberJsonPath);
		client.createTestExecutionAndStatusUpdate(data);
	
		}
}
