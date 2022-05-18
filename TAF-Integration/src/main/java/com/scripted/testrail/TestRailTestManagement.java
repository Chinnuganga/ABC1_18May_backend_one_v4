package com.scripted.testrail;

import com.scripted.dataload.PropertyDriver;
import com.scripted.testrail.APIClient;
import com.scripted.testrail.APIException;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import java.io.IOException;

public class TestRailTestManagement {

	org.json.simple.JSONObject body = new org.json.simple.JSONObject();
	org.json.simple.JSONObject response = null;
	public static Logger LOGGER = Logger.getLogger(TestRailTestManagement.class);
	public void updateTestCaseStatus(String cucumberJsonPath) throws IOException, APIException {

		PropertyDriver propDriver = new PropertyDriver();
		propDriver.setPropFilePath("src/main/resources/Integrations/Jira/TestRail/Properties/TestRail.properties");
		String url = PropertyDriver.readProp("testrail.url");
		String auth = PropertyDriver.readProp("testrail.authorization");
		String projectKey=PropertyDriver.readProp("testrail.projectkey");
		APIClient client = new APIClient(url, auth);
		CucumberJsonExtractor cucm = new CucumberJsonExtractor();
		JSONObject obj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);

		for (String feature : obj.keySet()) {
			JSONObject featureObject = obj.getJSONObject(feature);
			LOGGER.info("Updating test execution status TestRail test cases");
			for (String testCase : featureObject.keySet()) {
				String testcaseid = testCase.replace("@" + projectKey + "-", "").trim();
				if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
					body.put("status_id", "1");
					body.put("comment", "This testcase executed successfully");
					try {
						response = (org.json.simple.JSONObject) client.sendPost("add_result/" + testcaseid, body);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					body.put("status_id", "5");
					String errMsg = featureObject.getJSONObject(testCase).getString("scenarioError");
					body.put("comment", errMsg);
					try {
						response = (org.json.simple.JSONObject) client.sendPost("add_result/" + testcaseid, body);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			LOGGER.info("TestRail test cases status updation completed");
		}

	}

}
