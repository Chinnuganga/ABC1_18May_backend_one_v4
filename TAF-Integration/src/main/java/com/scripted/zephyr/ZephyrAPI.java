package com.scripted.zephyr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class ZephyrAPI extends zephyrConfig {

	static Logger log = Logger.getLogger("ZephyrAPI");
	static String stepResponse;
	static ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
	static String cycleId;

	public static String getCycleId() throws Exception {
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		cycleId = "";
		String responseString = tcm.doGet(
				ZAPI_BASEURL + ZAPI_URI_CONTEXT + "cycles/search?projectId=" + PROJECT_ID + "&versionId=" + VERSION_ID);
		JSONArray obj = new JSONArray(responseString);
		for (int i = 0; i < obj.length(); i++) {
			JSONObject jsonObject = obj.getJSONObject(i);
			if (TESTCYCLE_NAME.equals(jsonObject.getString("name"))) {
				cycleId = jsonObject.getString("id");
			}
		}
		if (cycleId.isEmpty()) {
			cycleId = createTestCycle(VERSION_ID);
		}
		log.info("Cycle Id Retrived : " + cycleId);
		return cycleId;
	}

	private static String createTestCycle(String versionId) throws Exception {
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		String cycleId = "";
		String payload = "{\"clonedCycleId\": \"\", \"name\": \"" + TESTCYCLE_NAME
				+ "\", \"build\": \"\",\"environment\": \"\",\"description\": \"Test Cycle created for Automation Framework Test Execution on"
				+ new Date() + "\",\"startDate\": \"\",\"endDate\": \"\",\"projectId\": \"" + PROJECT_ID
				+ "\",\"versionId\": \"" + versionId + "\"}";

		String responseString = tcm.doPost(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "cycle", payload);
		JSONObject obj = new JSONObject(responseString);
		cycleId = obj.getString("id");
		log.info("Test Cycle Created : " + cycleId);
		return cycleId;
	}

	public String createExecution(int issueId) throws Exception {
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		String payload = "{\"cycleId\": \"" + CYCLE_ID + "\",\"issueId\": \"" + issueId + "\",\"projectId\": \""
				+ PROJECT_ID + "\",\"versionId\": \"" + VERSION_ID + "\",\"assigneeType\": \"currentUser\"}";
		String responseString = tcm.doPost(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "execution", payload);
		JSONObject obj = new JSONObject(responseString);
		String executionId = obj.getJSONObject("execution").getString("id");
		log.info("Execution Created : " + executionId);
		return executionId;
	}

	public String updateTestStepStatus(String status, String errorDetails, int issueId, String stepId,
			String executionId, String resultId) throws Exception {
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		String changeStatus = "";
		int statusId = -1;
		String payload = "";
		
		if (status.equals("passed")) {
			statusId = 1;
			payload = "{\"status\":{\"id\":\"" + statusId + "\"},\"issueId\":" + issueId + ",\"comment\": \""
					+ "Executed Successfully " + "\",\"stepId\":\"" + stepId + "\",\"executionId\":\"" + executionId
					+ "\"}";
		} else if (status.equals("failed")) {
			statusId = 2;
			payload = "{\"status\":{\"id\":\"" + statusId + "\"},\"issueId\":" + issueId + ",\"comment\": \""
					+ errorDetails + "\",\"stepId\":\"" + stepId + "\",\"executionId\":\"" + executionId + "\"}";
		} else {
			statusId = -1;
			payload = "{\"status\":{\"id\":\"" + statusId + "\"},\"issueId\":" + issueId + ",\"comment\": \"" + " "
					+ "\",\"stepId\":\"" + stepId + "\",\"executionId\":\"" + executionId + "\"}";
		}
		
		stepResponse = tcm.doPut(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "stepresult/" + resultId, payload);
		
		log.info("Test Step Status Updated Successfully");
		return changeStatus;
	}

	private JSONArray getTestSteps(int issueId) throws Exception {
		String response = tcm
				.doGet(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "teststep/" + issueId + "?projectId=" + PROJECT_ID);
		JSONArray obj = new JSONArray(response);
		return obj;
	}

	public void updateExecutionStatus(int issueId, String status, String errorDetails, String executionId)
			throws Exception {
		String payload = null;
		int statusId = -1;

		switch (status.toLowerCase()) {
		case "passed":
			statusId = 1;
			payload = "{\"status\":{\"id\":" + statusId + "},\"issueId\":" + issueId + ",\"comment\": \""
					+ "Test Case Executed Successfully" + "\",\"projectId\":" + PROJECT_ID + ",\"id\":\"" + executionId
					+ "\"}";
			break;
		case "failed":
			statusId = 2;
			payload = "{\"status\":{\"id\":" + statusId + "},\"issueId\":" + issueId + ",\"comment\": \"" + errorDetails
					+ "\",\"projectId\":" + PROJECT_ID + ",\"id\":\"" + executionId + "\"}";
			break;

		case "skipped":
			statusId = -1;
			payload = "{\"status\":{\"id\":" + statusId + "},\"issueId\":" + issueId + ",\"comment\": \""
					+ "Test Case Skipped" + "\",\"projectId\":" + PROJECT_ID + ",\"id\":\"" + executionId + "\"}";
			break;

		default:
			payload = "{\"status\":{\"id\":" + statusId + "},\"issueId\":" + issueId + ",\"projectId\":" + PROJECT_ID
					+ ",\"id\":\"" + executionId + "\"}";
			break;
		}
		tcm.doPut(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "execution/" + executionId, payload);
		log.info("Execution status updated");
	}

	public JSONObject getTestStepIds(String jiraKey, String executionId, int issueId) throws Exception {
		JSONArray steps = getTestSteps(issueId);
		JSONObject stepIds = new JSONObject();
		List<String> testStepId = new ArrayList<String>();
		List<String> testResultIds = new ArrayList<String>();
		if (steps.length() > 0) {
			String response = tcm.doGet(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "stepresult/search?issueId=" + issueId
					+ "&executionId=" + executionId + "&isOrdered=true");
			JSONObject obje = new JSONObject(response);
				JSONArray arr = obje.getJSONArray("stepResults");
				for (int i = 0; i < steps.length(); i++) {
					JSONObject stepObj = (JSONObject) steps.get(i);
					String testResultId = "";
					for (int j = 0; j < stepObj.length(); j++) {
						if (arr.getJSONObject(j).getString("stepId").equals(stepObj.getString("id"))) {
							arr.getJSONObject(j).getString("stepId");
							testResultId = arr.getJSONObject(j).getString("id");
							testResultIds.add(testResultId);
							break;
						}
					}
					String testStep = stepObj.getString("id");
					testStepId.add(testStep);
				}
			
			stepIds.put("testStepId", testStepId);
			stepIds.put("testResultId", testResultIds);
		} 
		
		return stepIds;
	}

	public static String addStepsScreenshots(int issueId, String executionId, String embedData) {

		String versionId = "-1";
		String entityName = "stepResult";
		try {
			byte[] imageByteArray = Base64.decodeBase64(embedData);

			File imgfile = new File("src/main/resources/Screenshots/stepscreenshot.png");
			OutputStream os = new FileOutputStream(imgfile);

			os.write(imageByteArray);
			String response = tcm.doAttachPost(ZAPI_BASEURL + ZAPI_URI_CONTEXT + "attachment?issueId=" + issueId
					+ "&versionId=" + versionId + "&entityName=" + entityName + "&cycleId=" + cycleId + "&entityId="
					+ executionId + "&projectId=" + PROJECT_ID + "&comment=comment", imgfile);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
