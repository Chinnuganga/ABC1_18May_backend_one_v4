package com.scripted.stepdefs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.scripted.Allure.AllureListener;
import com.scripted.AutoPracPageobjects.APHomepage;
import com.scripted.AutoPracPageobjects.APMyAccountPage;
import com.scripted.AutoPracPageobjects.APPersonalInformationPage;
import com.scripted.AutoPracPageobjects.APSignInpage;
import com.scripted.Automationpractice.APHomepageMob;
import com.scripted.Automationpractice.APMyAccountPageMob;
import com.scripted.Automationpractice.APPersonalInformationPageMob;
import com.scripted.Automationpractice.APSignInpageMob;
import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.web.BrowserDriver;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;

public class stepdefinitions {

	public static WebDriver driver = null;

	public static HashMap<Integer, String> headermap = new HashMap<Integer, String>();
	public static Map<String, String> fRow;
	public static Map<String, String> fAutoRow;
	public static AndroidDriver<WebElement> androidDriver;
	PropertyDriver propDriver = new PropertyDriver();
	APHomepage homePage;
	APSignInpage signInPage;
	APMyAccountPage myaccountPage;
	APPersonalInformationPage personalInformationPage;
	APHomepageMob homePageMob;
	APSignInpageMob signInPageMob;
	APMyAccountPageMob myaccountPageMob;
	APPersonalInformationPageMob personalInformationPageMob;
	
	RequestParams Attwrapper = new RequestParams();
	RestAssuredWrapper raWrapper = new RestAssuredWrapper();

	@Before("@Web")
	public void setupExcelData() {
		BrowserDriver.getCuPalDriver();
		AllureListener.setDriver(BrowserDriver.getDriver());
		homePage = PageFactory.initElements(BrowserDriver.getDriver(), APHomepage.class);
		signInPage = PageFactory.initElements(BrowserDriver.getDriver(), APSignInpage.class);
		myaccountPage = PageFactory.initElements(BrowserDriver.getDriver(), APMyAccountPage.class);
		personalInformationPage = PageFactory.initElements(BrowserDriver.getDriver(), APPersonalInformationPage.class);
			
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configAutomationPrac.properties");
		String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		String sheetname = "TC001";
		String key = "";
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAutoAllData = obj.getTableData();
		fAutoRow = (getAutoAllData.get("1"));

	}
	
	@Before("@pCloudyWeb")
	public void beforepCloudyWeb() {
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configAutomationPrac.properties");
		String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		String sheetname = "TC001";
		String key = "";
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAutoAllData = obj.getTableData();
		fAutoRow = (getAutoAllData.get("1"));
		
		//androidDriver = MobileDriverSettings.funcGetWebAndroidDriver("AndroidWeb");
		androidDriver = MobileDriverSettings.funcGetpCloudyWebAndroiddriver("AndroidWeb");
		
		System.out.println("pCloudyWeb execution started");
	}

	// ------------------------------Web------------------------------------------

	@Given("I have logged into {string} application")
	public void i_have_logged_into_crm_application(String strProjname) {
		if (strProjname.equalsIgnoreCase("Automation Practice")) {
			BrowserDriver.launchWebURL("http://automationpractice.com/");
			homePage.clickSignin();
			signInPage.login(fAutoRow.get("UserName"), fAutoRow.get("Password"));
		}
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

	// ------------------------------Mobile------------------------------------------
	
	@Given("I have logged into {string} mobile web")
	public void i_have_logged_into_application(String strProjname) {
		homePageMob = new APHomepageMob(androidDriver);
		signInPageMob = new APSignInpageMob(androidDriver);  
		myaccountPageMob = new APMyAccountPageMob(androidDriver);
		personalInformationPageMob = new APPersonalInformationPageMob(androidDriver);
		MobileDriverSettings.launchURL("http://automationpractice.com/");
		homePageMob.clickSignin();
		signInPageMob.login(fAutoRow.get("UserName"), fAutoRow.get("Password"));
	}
	
	@When("I navigate to personal information page in mobile web")
	public void i_navigate_to_personal_information_page_mobileWeb() throws Throwable {
		myaccountPageMob.clickMyPersonalInformation();
	}
	
	@Then("I validate personal details in personal information page in mobile web:")
	public void i_validate_personal_details_in_personal_information_page_mobileWeb(DataTable personalInfo) throws Throwable {

		List<Map<String, String>> resplist = personalInfo.asMaps(String.class, String.class);
		for (int i = 0; i < resplist.size(); i++) {
			String socialTitile = resplist.get(i).get("Social title");
			personalInformationPageMob.validatePersonalInfo("Social title", socialTitile);
			String fname = resplist.get(i).get("First name");
			personalInformationPageMob.validatePersonalInfo("First name", fname);
			String lastname = resplist.get(i).get("Last name");
			personalInformationPageMob.validatePersonalInfo("Last name", lastname);
			String emailAdd = resplist.get(i).get("E-mail address");
			personalInformationPageMob.validatePersonalInfo("E-mail address", emailAdd);
			String dobday = resplist.get(i).get("dobDay");
			personalInformationPageMob.validatePersonalInfo("dobDay", dobday);
			String dobmonth = resplist.get(i).get("dobMonth");
			personalInformationPageMob.validatePersonalInfo("dobMonth", dobmonth);
			String dobyear = resplist.get(i).get("dobYear");
			personalInformationPageMob.validatePersonalInfo("dobYear", dobyear);
		}
	}

	// ------------------------------API------------------------------------------

	@Given("I have sample Get API")
	public void i_have_sample_get_api() throws Throwable {

	}

	@When("I send a {string} Request with {string} properties")
	public void i_send_a_something_request_with_something_properties(String strMethod, String strPropFileName)
			throws Throwable {
		raWrapper.setAPIFileProName(strPropFileName + ".properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendRequest(strMethod, reqSpec);
	}

	@Then("I should get response code {int}")
	public void i_should_get_response_code_something(int strCode) throws Throwable {
		raWrapper.valResponseCode(strCode);
	}

	@And("the response should contain:")
	public void the_response_should_contain(DataTable respTable) throws Throwable {
		List<Map<String, String>> resplist = respTable.asMaps(String.class, String.class);
		for (int i = 0; i < resplist.size(); i++) {
			String jsonPath = resplist.get(i).get("JsonPath");
			String expVal = resplist.get(i).get("ExpectedVal");

			if (expVal.matches("-?(0|[1-9]\\d*)")) {
				int intExpVal = Integer.parseInt(expVal);
				raWrapper.valJsonResponseVal(jsonPath, intExpVal);
			} else {
				raWrapper.valJsonResponseVal(jsonPath, expVal);
			}

		}

	}

	@After("@pCloudyWeb")
	public void afterpCloudyWeb() {
		MobileDriverSettings.getCurrentDriver().quit();
		System.out.println("pCloudyWeb completed");
	}

	@After("@Web")
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		BrowserDriver.closeBrowser();
	}
}
