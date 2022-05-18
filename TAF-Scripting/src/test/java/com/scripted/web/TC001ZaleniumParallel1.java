package com.scripted.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.scripted.Allure.AllureListener;
import com.scripted.CRMPomObjects.CRMContactsPage;
import com.scripted.CRMPomObjects.CRMLoginPage;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.web.BrowserDriver;
@Listeners({ AllureListener.class })
public class TC001ZaleniumParallel1 {

	static HashMap<Integer, String> headermap = new HashMap<Integer, String>();
	public static WebDriver driver = null;

	@Test
	public static void script() {
		try {
			driver = BrowserDriver.getZaleniumDriverParallel("chrome","78.0.3904.108");
			BrowserDriver.launchWebURL("https://ui.freecrm.com");
			System.out.println(driver.getTitle());

			CRMLoginPage crmLPage = PageFactory.initElements(driver, CRMLoginPage.class);
			CRMContactsPage crmCPage = PageFactory.initElements(driver, CRMContactsPage.class);

			PropertyDriver propDriver = new PropertyDriver();
			propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/Cogmento.properties");
			String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
			String sheetname = "TC001";
			String key = "";

			ExcelConnector con = new ExcelConnector(filename, sheetname);
			TestDataFactory test = new TestDataFactory();
			TestDataObject obj = test.GetData(key, con);

			LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();

			Map<String, String> fRow = (getAllData.get("1"));
			crmLPage.login(fRow.get("userName"), fRow.get("password"));
			crmCPage.clickContacts();
			crmCPage.enterPersonalDetails(fRow.get("firstName"), fRow.get("lastName"), fRow.get("company"),
					fRow.get("timezone"));
			crmCPage.enterBOD(fRow.get("bdate"), fRow.get("bmonth"), fRow.get("byear"));

			crmCPage.clickSaveBtn();
			Thread.sleep(2000);
			crmCPage.deleteRecord(fRow.get("firstName") + " " + fRow.get("lastName"));
			Thread.sleep(2000);
			crmCPage.logout();
			BrowserDriver.closeBrowser();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
