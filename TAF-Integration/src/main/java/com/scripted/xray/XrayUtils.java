package com.scripted.xray;

import java.io.File;

import org.apache.log4j.Logger;

import com.scripted.generic.FileUtils;

import junit.framework.Assert;

public class XrayUtils {

	public static Logger LOGGER = Logger.getLogger(XrayUtils.class);

	public static void uploadResultsFromCucumberJson(String jsonReportLocation) {
		try {
			File cucumberJsonfilePath = new File(FileUtils.getFilePath(jsonReportLocation));
			XrayTestManagement Xmanage = new XrayTestManagement();
			Xmanage.updateTestCaseStatus(cucumberJsonfilePath.toString());
			LOGGER.info("Updated test case status successfully");
		} catch (Exception e) {
			System.out.println(e);
			LOGGER.error("Error while trying to upload results from CucumberJson" + " Exception :" + e);
			Assert.fail("Error while trying to upload results from CucumberJson" + " Exception :" + e);
		}
	}
}
