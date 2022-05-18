package com.scripted.webstepdefs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

import com.scripted.Allure.AllureListener;
import com.scripted.AutoPracPageobjects.APHomepage;
import com.scripted.AutoPracPageobjects.APMyAccountPage;
import com.scripted.AutoPracPageobjects.APPersonalInformationPage;
import com.scripted.AutoPracPageobjects.APSignInpage;
import com.scripted.CRMPomObjects.CRMContactsPage;
import com.scripted.CRMPomObjects.CRMLoginPage;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.GenRocketDriver;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.web.BrowserDriver;

import io.cucumber.core.api.Scenario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Listeners({ AllureListener.class })
public class stepdefinitions {
	public static WebDriver driver = null;

	public static HashMap<Integer, String> headermap = new HashMap<Integer, String>();
	public static Map<String, String> fRow;
	public static Map<String, String> fAutoRow;
	PropertyDriver propDriver = new PropertyDriver();
	CRMLoginPage crmLPage;
	CRMContactsPage crmCPage;
	APHomepage homePage;
	APSignInpage signInPage;
	APMyAccountPage myaccountPage;
	APPersonalInformationPage personalInformationPage;
	

	@Before
	public void invokeBrowser() {
		BrowserDriver.funcGetWebdriver();
		
		//for cucmber parallel testing 
		//BrowserDriver.getCuPalDriver();
		
		AllureListener.setDriver(BrowserDriver.getDriver());
		crmLPage = PageFactory.initElements(BrowserDriver.getDriver(), CRMLoginPage.class);
		crmCPage = PageFactory.initElements(BrowserDriver.getDriver(), CRMContactsPage.class);
		homePage = PageFactory.initElements(BrowserDriver.getDriver(), APHomepage.class);
		signInPage = PageFactory.initElements(BrowserDriver.getDriver(), APSignInpage.class);
		myaccountPage = PageFactory.initElements(BrowserDriver.getDriver(), APMyAccountPage.class);
		personalInformationPage = PageFactory.initElements(BrowserDriver.getDriver(), APPersonalInformationPage.class);
		
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
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/Cogmento.properties");
		String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		String sheetname = "TC001";
		String key = "";
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
		fRow = (getAllData.get("1"));

		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configAutomationPrac.properties");
		filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		sheetname = "TC001";
		key = "";
		con = new ExcelConnector(filename, sheetname);
		test = new TestDataFactory();
		obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAutoAllData = obj.getTableData();
		fAutoRow = (getAutoAllData.get("1"));
		
	}

	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot scrShot = ((TakesScreenshot) BrowserDriver.getDriver());
			byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		BrowserDriver.closeBrowser();
	}

	/*
	 * @After public void embedScreenshot(Scenario scenario) { if
	 * (scenario.isFailed()) { try { byte[] screenshot = ((TakesScreenshot) driver)
	 * .getScreenshotAs(OutputType.BYTES); scenario.embed(screenshot, "image/png");
	 * } catch (Exception e) { e.printStackTrace(); } } }
	 */

	@Given("I have logged into {string} application")
	public void i_have_logged_into_something_application(String strProjname) throws Throwable {
		if (strProjname.equalsIgnoreCase("Cogmento")) {
			BrowserDriver.launchWebURL("https://ui.freecrm.com");
			crmLPage.login(fRow.get("userName"), fRow.get("password"));
		}
		if (strProjname.equalsIgnoreCase("Automation Practice")) {
			BrowserDriver.launchWebURL("http://automationpractice.com/");
			homePage.clickSignin();
			signInPage.login(fAutoRow.get("UserName"), fAutoRow.get("Password"));
		}

	}

	@And("I navigate to contact page")
	public void i_navigate_to_contact_page() throws Throwable {
		crmCPage.clickContacts();
	}

	@And("I enter personal details to a create contact")
	public void i_enter_personal_details_to_a_create_contact() throws Throwable {
		crmCPage.enterPersonalDetails(fRow.get("firstName"), fRow.get("lastName"), fRow.get("company"),
				fRow.get("timezone"));
		crmCPage.enterBOD(fRow.get("bdate"), fRow.get("bmonth"), fRow.get("byear"));
	}
	
	@And("I enter {string} and {string} to a create contact")
	public void i_enter_somename_somebirthdate_to_a_create_contact(String name, String bdate) throws Throwable {
		crmCPage.enterPersonalDetails(name, fRow.get("lastName"), fRow.get("company"), fRow.get("timezone"));
		crmCPage.enterBOD(bdate, fRow.get("bmonth"), fRow.get("byear"));
		System.out.format("Thread ID - %2d - %s from %s feature file.\n", Thread.currentThread().getId(), name,
				bdate);
	}


	@When("I click on save button")
	public void i_click_on_save_button() throws Throwable {
		crmCPage.clickSaveBtn();
		Thread.sleep(3000);
	}

	@And("I should be able to delete contact added")
	public void i_should_be_able_to_delete_contact_added() throws Throwable {
		crmCPage.deleteRecord(fRow.get("firstName") + " " + fRow.get("lastName"));
	}

	@And("I logout from the application")
	public void i_logout_from_the_application() throws Throwable {
		Thread.sleep(2000);
		crmCPage.logout();
	}

	@Then("I should successfully able to see the contact created")
	public void i_should_succeffully_able_to_see_the_contact_created() throws Throwable {
	}

	@When("I navigate to personal information page")
	public void i_navigate_to_personal_information_page() throws Throwable {
		myaccountPage.clickMyPersonalInformation();
	}

	@Then("I validate personal details in personal information page:")
	public void i_validate_personal_details_in_personal_information_page(DataTable personalInfo) throws Throwable {

		List<Map<String, String>> resplist = personalInfo.asMaps(String.class, String.class);
		for (int i = 0; i < resplist.size(); i++) {
			String socialTitile = resplist.get(i).get("Social title");
			personalInformationPage.validatePersonalInfo("Social title", socialTitile);
			String fname = resplist.get(i).get("First name");
			personalInformationPage.validatePersonalInfo("First name", fname);
			String lastname = resplist.get(i).get("Last name");
			personalInformationPage.validatePersonalInfo("Last name", lastname);
			String emailAdd = resplist.get(i).get("E-mail address");
			personalInformationPage.validatePersonalInfo("E-mail address", emailAdd);
			String dobday = resplist.get(i).get("dobDay");
			personalInformationPage.validatePersonalInfo("dobDay", dobday);
			String dobmonth = resplist.get(i).get("dobMonth");
			personalInformationPage.validatePersonalInfo("dobMonth", dobmonth);
			String dobyear = resplist.get(i).get("dobYear");
			personalInformationPage.validatePersonalInfo("dobYear", dobyear);
		}
	}
}
