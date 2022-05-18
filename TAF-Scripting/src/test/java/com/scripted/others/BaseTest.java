package com.scripted.others;

import java.io.File;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.scripted.Allure.AllureReport;
import com.scripted.generic.FileUtils;
import com.scripted.web.BrowserDriver;

public class BaseTest {
	@BeforeSuite
	public void setup() {
		File dirPath = new File(FileUtils.getCurrentDir() + File.separator + "allure-results");
		FileUtils.deleteDirectory(dirPath);
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("beforetest");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("aftertest");
		//LaunchBrowsers.closeBrowser();
	}
	
	

	@AfterSuite
	public void teardown() {
		try {
			BrowserDriver.closeBrowser();
			AllureReport.customizeReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
