package com.scripted.qtest;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scripted.dataload.PropertyDriver;

import junit.framework.Assert;

public class CucumberJsonExtractor {
	String PASS = "passed";
	String FAIL = "failed";

	public static Logger LOGGER = Logger.getLogger(CucumberJsonExtractor.class);

	private JSONArray readData(String path) throws IOException {
		JSONArray cucumberJson = null;
		File file = new File(path);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			cucumberJson = new JSONArray(content);

		} catch (Exception e) {
			LOGGER.error("Error while trying to read file " + "Exception :" + e);
			Assert.fail("Error while trying to read file " + "Exception :" + e);
		}
		return cucumberJson;
	}

	public JSONObject getScenarioAndStepsStatus(String cucumberJsonPath) throws IOException {
		JSONArray jsonArray = readData(cucumberJsonPath);
		JSONObject result = new JSONObject();
	    int duration=0;
		try {
			PropertyDriver propDriver = new PropertyDriver();
			propDriver.setPropFilePath("src/main/resources/Integrations/Jira/qTest/Properties/qTest.properties");
			String projectKey=PropertyDriver.readProp("qTest.projectkey");
			for (Object object : jsonArray) {
				JSONObject tagObject = new JSONObject();
				JSONObject obj = (JSONObject) object;
				JSONArray scenario = obj.getJSONArray("elements");
				for (Object arr : scenario) {
					JSONObject details = new JSONObject();
					JSONObject scenarioObj = (JSONObject) arr;
					String timeStamp = scenarioObj.getString("start_timestamp");
					JSONArray before = scenarioObj.getJSONArray("before");
					for (Object bObj : before){
						JSONObject beObj = (JSONObject) bObj;
						duration = beObj.getJSONObject("result").getInt("duration");
					
					}
					
					JSONArray stepsArray = scenarioObj.getJSONArray("steps");
					JSONArray tags = scenarioObj.getJSONArray("tags");
					String tagName = "";
					for (Object tag : tags) {
						JSONObject tagList = (JSONObject) tag;
						tagName = tagList.getString("name");
						if(tagName.startsWith("@"+projectKey))
							break;
					}
					JSONArray stepStatusList = new JSONArray();
					JSONArray names = new JSONArray();
					
					details.put("scenarioStatus", PASS);
					for (Object stepObj : stepsArray) {
						JSONObject steps = (JSONObject) stepObj;
						String stepStatus = steps.getJSONObject("result").getString("status");
					
						String name = steps.getString("name");
						if (stepStatus.equals("failed")) {
							String stepErrorDetails = steps.getJSONObject("result").getString("error_message");
							stepErrorDetails = stepErrorDetails.replaceAll("[\\n\\t\\r\"]", "");
							if(stepErrorDetails.length()>700)
								stepErrorDetails = stepErrorDetails.substring(0, 700);
							details.put("scenarioStatus", FAIL);
							details.put("scenarioError", stepErrorDetails);
						}
						stepStatusList.put(stepStatus);
						names.put(name);
					}
					details.put("stepStatus", stepStatusList);
					details.put("stepNames", names);
					details.put("timestamp", timeStamp);
					details.put("duration", duration);
					tagObject.put(tagName, details);
					result.put(obj.getString("name"), tagObject);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error while trying to get status of steps and scenarios " + "Exception :" + e);
			Assert.fail("Error while trying to get status of steps and scenarios " + "Exception :" + e);
		}
		return result;
	}
	
	public String getTestCaseDetails(JSONArray Jobj,String Pid) throws IOException
	{
		String tcName = null;
		try {
			for (Object object : Jobj) {	
				JSONObject obj = (JSONObject) object;
				if(Pid.equalsIgnoreCase(obj.get("pid").toString())) {
					tcName = obj.get("name").toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("getTestCaseDetails: Error while trying to get status of steps and scenarios " + "Exception :" + e);
		}
		System.out.println("Test Case Name :"+tcName);
		return tcName;
		
	}
}
