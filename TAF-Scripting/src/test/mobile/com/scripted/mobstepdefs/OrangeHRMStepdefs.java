package com.scripted.mobstepdefs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.scripted.Allure.AllureListener;
import com.scripted.OrangeHRMPageObjectsMobile.OrangeHRMPage;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.mobile.PcloudyDeviceSettings;

import io.appium.java_client.MobileDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
@Listeners({ AllureListener.class })
public class OrangeHRMStepdefs {

	//public static AndroidDriver<WebElement> androidDriver;
	public static MobileDriver<WebElement> androidDriver; // *Implementation for cucumber multiple execution*	
	public static ThreadLocal<String> deviceList = new ThreadLocal<String>();
	PcloudyDeviceSettings deviceSettings = new PcloudyDeviceSettings();
	public static String devicePropertyFName = null;
	public static Map<String, String> fRow;
	PropertyDriver propDriver = new PropertyDriver();
	OrangeHRMPage orangeHrmpage;
	TestDataFactory test = new TestDataFactory();
	String sheetname = "TC004";
	String key = "";
	
	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot scrShot = ((TakesScreenshot) androidDriver);
			byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		
	}

	@Given("I have logged into {string} application")
	public void i_have_logged_into_something_application(String DevicePropName) {
		deviceSettings.getConnectionPcloudyAndroidWebTh("pCloudyWeb");
	//	deviceSettings.getConnectionPcloudyAndroidWeb(DevicePropName);
		androidDriver = MobileDriverSettings.getCurrentDriver();
		MobileDriverSettings.launchURL("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
		orangeHrmpage = new OrangeHRMPage(androidDriver);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configOrangehrm.properties");
		String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
		fRow = (getAllData.get("1"));
		orangeHrmpage.login(fRow.get("UserName"), fRow.get("Password"));

	}

	@And("I navigate to system users page")
	public void i_navigate_to_system_users_page() {
		orangeHrmpage.navigate_SystemUser();
	}

	@When("I click on {string} user link")
	public void i_click_on_something_user_link(String user) {
			orangeHrmpage.navigate_userPage(user);
	}

	@Then("I should successfully able to see user details")
	public void i_should_successfully_able_to_see_user_details() {
			orangeHrmpage.verifyUser();
	}

	@And("I logout from the application")
	public void i_logout_from_the_application() {
		orangeHrmpage.logOut();

	}
	
	@Before("@AndroidWeb")
	public void beforeWeb() {
		androidDriver = MobileDriverSettings.funcGetWebAndroidDriver("AndroidWeb");
		System.out.println("AndroidWeb started");
	}
	
	@Before("@PcloudyWeb")
	public void beforepcloudyWeb() {
		androidDriver = MobileDriverSettings.funcGetpCloudyWebAndroiddriver("pcloudyWebConfig");
		System.out.println("PcloudyWeb started");
	}
	

	@After("@AndroidWeb")
	public void afterWeb() {
		MobileDriverSettings.closeDriver();
		System.out.println("AndroidWeb completed");
	}
	
	@After("@PcloudyWeb")
	public void afterPcloudy() {
		androidDriver.quit();
		System.out.println("PcloudyWeb completed");
	}
}
