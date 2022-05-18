package com.scripted.zephyr;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import junit.framework.Assert;

public class JiraAPI extends zephyrConfig{

	public static Logger LOGGER = Logger.getLogger("JiraAPI");
	
	public static String createVersion() throws Exception 
	{
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		String versionId = "";
		try
		{
		
		String payload = "{\"description\": \"New Version Created through Automation\",\"name\":\"" + RELEASE_VERSION
				+ "\",\"archived\": false,\"released\": " + config.getBoolean("released") + ",\"releaseDate\": \""
				+ config.getString("release.endDate") + "\",\"startDate\": \""+config.getString("release.startDate")+"\",\"project\":\"" + PROJECT_KEY + "\",\"projectId\": \"" + PROJECT_ID + "\"}";
		String responseString = tcm.doPost(JIRA_BASEURL + JIRA_API_CONTEXT + "version", payload);
		JSONObject obj = new JSONObject(responseString);
		versionId = obj.getString("id");
		}
		catch(Exception e)
		{
			LOGGER.error("Error while creating version in Zephyr "+"Exception :"+e);
			Assert.fail("Error while creating version in Zephyr "+"Exception :"+e);
		}
		LOGGER.info("Version Created : " + versionId);
		return versionId;
	}
	
	public static String getVersionId() throws Exception {
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		String versionId = "";
		String responseString = tcm.doGet(JIRA_BASEURL + JIRA_API_CONTEXT + "project/" + PROJECT_KEY + "/versions");
		JSONArray arr = new JSONArray(responseString);
		for (Object object : arr) {
			JSONObject obj = (JSONObject) object;
			if (RELEASE_VERSION.equals(obj.getString("name"))) {
				versionId = obj.getString("id");
			}
		}
		if (versionId.isEmpty()) {
			try {
				versionId = createVersion();
			} catch (Exception e) {
				LOGGER.error("Unable to find the release version "+ RELEASE_VERSION+" Exception :"+e);
				Assert.fail("Unable to find the release version "+ RELEASE_VERSION+" Exception :"+e);
				throw new Exception("Unable to find the release version " + RELEASE_VERSION);
			}
		}
		LOGGER.info("Version Id Retrieved : " + versionId);
		return versionId;
	}
	
	public int getIssueId(String jiraKey) throws Exception {
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		int issueId = 0;
		try
		{
		String responseString = tcm.doGet(JIRA_BASEURL + JIRA_API_CONTEXT + "issue/" + jiraKey + "?fields=id");
		JSONObject obj = new JSONObject(responseString);
		issueId = Integer.valueOf(obj.getString("id"));
		LOGGER.info("Issue Id Retrived : " + issueId);
		}
		catch (Exception e)
		{
			LOGGER.error("Error while trying to retrieve IssueID "+"Exception :"+e);
		}
		return issueId;
	}	
}
