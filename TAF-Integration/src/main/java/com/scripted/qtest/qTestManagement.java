package com.scripted.qtest;

import org.apache.log4j.Logger;

public class qTestManagement {
	org.json.simple.JSONObject body = new org.json.simple.JSONObject();
	org.json.simple.JSONObject response = null;
	public static Logger LOGGER = Logger.getLogger(qTestManagement.class);
	public void updateTestCaseStatus(String cucumberJsonPath) throws Exception {

		APIClient obj=new APIClient(cucumberJsonPath);
		LOGGER.info("qTest test cases status updation completed");
		
	}

}
