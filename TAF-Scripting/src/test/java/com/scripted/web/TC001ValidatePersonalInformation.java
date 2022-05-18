package com.scripted.web;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.scripted.AutoPracPageobjects.APHomepage;
import com.scripted.AutoPracPageobjects.APMyAccountPage;
import com.scripted.AutoPracPageobjects.APPersonalInformationPage;
import com.scripted.AutoPracPageobjects.APSignInpage;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.web.BrowserDriver;

public class TC001ValidatePersonalInformation {
	public static WebDriver driver = BrowserDriver.funcGetWebdriver();

	public static void main(String[] args) throws InterruptedException {
		BrowserDriver.launchWebURL("http://automationpractice.com/");

		APHomepage homePage = PageFactory.initElements(driver, APHomepage.class);
		APSignInpage signInPage = PageFactory.initElements(driver, APSignInpage.class);
		APMyAccountPage myaccountPage = PageFactory.initElements(driver, APMyAccountPage.class);
		APPersonalInformationPage personalInformationPage = PageFactory.initElements(driver, APPersonalInformationPage.class);
		
		PropertyDriver propDriver = new PropertyDriver();
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configAutomationPrac.properties");
		String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		String sheetname = "TC001";
		String key = "";
		
		
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);

		// Get the whole data
		LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();

		// Get specific data
		Map<String, String> fRow = (getAllData.get("1"));
		
		homePage.clickSignin();
		signInPage.login(fRow.get("UserName"), fRow.get("Password"));
		myaccountPage.clickMyPersonalInformation();
		
		personalInformationPage.validatePersonalInfo("Social title",fRow.get("Social title"));
		personalInformationPage.validatePersonalInfo("First name",fRow.get("First name"));
		personalInformationPage.validatePersonalInfo("Last name",fRow.get("Last name"));
		personalInformationPage.validatePersonalInfo("E-mail address",fRow.get("E-mail address"));
		personalInformationPage.validatePersonalInfo("dobDay",fRow.get("dobDay"));
		personalInformationPage.validatePersonalInfo("dobMonth",fRow.get("dobMonth"));
		personalInformationPage.validatePersonalInfo("dobYear",fRow.get("dobYear"));

		homePage.clickLogout();
		BrowserDriver.closeBrowser();
	}
}
