package com.scripted.integration;

import java.io.File;

import org.apache.log4j.Logger;

import com.scripted.generic.FileUtils;

import com.scripted.zephyr.ZephyrTestCaseManagementCloud;
import com.scripted.zephyr.zephyrConfig;

import junit.framework.Assert;

public class JiraUtils {
	public static Logger LOGGER = Logger.getLogger(JiraUtils.class);
	
	public static void uploadResultsFromCucumberJson(String jsonReportLocation) {
		try
		{
		File cucumberJsonfilePath = new File(FileUtils.getFilePath(jsonReportLocation));
		zephyrConfig.propSet(true);
		ZephyrTestCaseManagementCloud tcm = new ZephyrTestCaseManagementCloud();
		tcm.updateTestCaseAndStepStatus(cucumberJsonfilePath.toString());
		LOGGER.info("Updated testcase and teststep status successfully");
		}
		catch(Exception e)
		{
		LOGGER.error("Error while trying to upload results from CucumberJson"+" Exception :"+e);
		Assert.fail("Error while trying to upload results from CucumberJson"+" Exception :"+e);
		}
	}
}
