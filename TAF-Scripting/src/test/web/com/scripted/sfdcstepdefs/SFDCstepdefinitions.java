package com.scripted.sfdcstepdefs;

import java.util.LinkedHashMap;
import java.util.Map;

import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.GenRocketDriver;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.web.BrowserDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SFDCstepdefinitions extends SFDCPageIntlzr {

	PropertyDriver propDriver = new PropertyDriver();
	public static Map<String, String> fRow;
	public static Map<String, String> fSFDCAcctRow;
	public static Map<String, String> fSFDCContRow;
	public static Map<String, String> fSFDCOpptRow;

	// SFDC Lightening

	/*
	 * SFDCLoginPage sfdcLoginPage; SFDCAccountsPage sfdcAcctsPage; SFDCHomePage
	 * sfdcHomePage; SFDCContactsPage sfdcCtntsPage; SFDCOpportunitiesPage
	 * sfdcOptntsPage;
	 */
	/*
	 * public static Map<String, String> fSFDCAcctRow; public static Map<String,
	 * String> fSFDCContRow; public static Map<String, String> fSFDCOpptRow;
	 */

	@Before
	public void invokeBrowser() {
		BrowserDriver.funcGetWebdriver();
		// SFDC Lightening
		SFDCPageIntlzr.SFDCIntlzePgs();
	}

	@Before("@Genrocket")
	public void setupGenrocketData() {
		GenRocketDriver.updateSceLoopCount("CRMContactPageScenario.grs", "CRMContact", "ExcelFileReceiver", "10");

		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/Cogmento.properties");
		String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("genExcelPath");
		String sheetname = "GenRocket";
		String key = "";

		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
		fRow = (getAllData.get("1"));
	}

	@Before("@Excel")
	public void setupExcelData() {
		// SFDC Lightening
		String filename = "";
		String sheetname = "";
		String key = "";

		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/SFDCLightening.properties");
		filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		sheetname = "Account";
		key = "";
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getSFDCAcctData = obj.getTableData();
		fSFDCAcctRow = (getSFDCAcctData.get("1"));

		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/SFDCLightening.properties");
		filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		sheetname = "Contact";
		key = "";
		con = new ExcelConnector(filename, sheetname);
		test = new TestDataFactory();
		obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getSFDCAllCntctData = obj.getTableData();
		fSFDCContRow = (getSFDCAllCntctData.get("1"));

		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/SFDCLightening.properties");
		filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		sheetname = "Opportunities";
		key = "";
		con = new ExcelConnector(filename, sheetname);
		test = new TestDataFactory();
		obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getSFDCAllOpptntsData = obj.getTableData();
		fSFDCOpptRow = (getSFDCAllOpptntsData.get("1"));
	}

	@Given("I have logged into {string} application")
	public void i_have_logged_into_something_application(String strProjname) throws Throwable {
		if (strProjname.equalsIgnoreCase("SFDC Lightening")) {
			BrowserDriver.launchWebURL("https://login.salesforce.com/");
			sfdcLoginPage.login(fSFDCAcctRow.get("Username"),
					fSFDCAcctRow.get("Password"));
		}
	}

	@And("I navigate to {string} page")

	public void i_navigate_to_some_page(String strpageName) throws Throwable {
		if (strpageName.equalsIgnoreCase("SFDCAccounts")) {
			sfdcHomePage.clckAccnts();
		}
		if (strpageName.equalsIgnoreCase("SFDCContacts")) {
			sfdcHomePage.clckCntcts();
		}
		if (strpageName.equalsIgnoreCase("SFDCOpportunities")) {
			sfdcHomePage.clckOpprtnts();
		}
	}

	@Then("I should be able to see the {string} created")
	public void i_should_be_able_to_see_account_created(String strMsg) throws InterruptedException {
		if (strMsg.equalsIgnoreCase("SFDCAccount")) {
			/*
			 * Thread.sleep(5000); sfdcHomePage.clckAccnts();
			 */
			sfdcAcctsPage.vfyAcctCreatn(fSFDCAcctRow);
		}
		if (strMsg.equalsIgnoreCase("SFDCContact")) {
			/*
			 * Thread.sleep(5000); sfdcHomePage.clckCntcts();
			 */
			sfdcCtntsPage.vfyCntctCreatn(fSFDCContRow);
		}
		if (strMsg.equalsIgnoreCase("SFDCOpportunities")) {
			/*
			 * Thread.sleep(5000); sfdcHomePage.clckOpprtnts();
			 */
			sfdcOptntsPage.vfyOpptntCreatn(fSFDCOpptRow);
		}
		/*
		 * if (strMsg.equalsIgnoreCase("SFDCContactactivities")) {
		 * sfdcCtntsPage.vfyActvtyCrtn(fSFDCContRow); }
		 */
	}

	@When("I enter {string} creation details and click save")
	public void i_enter_creation_details_and_save(String option) throws InterruptedException {
		if (option.equalsIgnoreCase("SFDCAccount")) {
			sfdcAcctsPage.clickNewAccnt();
			sfdcAcctsPage.enterAcctInfo(fSFDCAcctRow);
			sfdcAcctsPage.enterAddrInfo(fSFDCAcctRow);
			sfdcAcctsPage.enterAddtnlInfo(fSFDCAcctRow);
			sfdcAcctsPage.clickSave();
		}
		if (option.equalsIgnoreCase("SFDCContact")) {
			sfdcCtntsPage.entrNewCntct();
			sfdcCtntsPage.enterCntctInfo(fSFDCContRow);
			sfdcCtntsPage.enterAddrInfo(fSFDCContRow);
			sfdcCtntsPage.enterAddtnlInfo(fSFDCContRow);
			sfdcCtntsPage.clickSave();
		}
		if (option.equalsIgnoreCase("SFDCOpportunities")) {
			sfdcOptntsPage.clickNewOpptnts();
			sfdcOptntsPage.enterAcctInfo(fSFDCOpptRow);
			sfdcOptntsPage.enterAddrInfo(fSFDCOpptRow);
			sfdcOptntsPage.enterDescInfo(fSFDCOpptRow);
			sfdcOptntsPage.clickSave();
		}
	}

	@When("I enter activites details and click save")
	public void i_enter_activities_details() {
		sfdcCtntsPage.clkCntct(fSFDCContRow);
		sfdcCtntsPage.crtNewTsk(fSFDCContRow);
		sfdcCtntsPage.crtNewLogCl(fSFDCContRow);
		sfdcCtntsPage.crtNewEvnt(fSFDCContRow);
	}

	@Then("I should be able to see the activities listed")
	public void i_should_see_activities_listed() {
		sfdcCtntsPage.vfyActvtyCrtn(fSFDCContRow);
		System.out.println("Success");
	}

}
