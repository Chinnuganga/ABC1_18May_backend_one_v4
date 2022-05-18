package com.scripted.mobile;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.scripted.OrangeHRMPageObjectsMobile.OrangeHRMPage;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.ios.IOSDriver;

public class TC12_pcloudyIOSWebTestscripts {
	
	public static IOSDriver<WebElement> iosDriver = null;

	public static void main(String[] args) throws Exception {
		Map<String, String> fRow;
		PropertyDriver propDriver = new PropertyDriver();
		OrangeHRMPage orangeHrmpage;
		TestDataFactory test = new TestDataFactory();
		String sheetname = "TC004";
		String key = "";
		try {
			iosDriver = MobileDriverSettings.funcGetpCloudyWebIOSdriver("AndroidWeb");
			MobileDriverSettings.launchURL("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
			orangeHrmpage = new OrangeHRMPage(iosDriver);
			propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configOrangehrm.properties");
			String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
			ExcelConnector con = new ExcelConnector(filename, sheetname);
			TestDataObject obj = test.GetData(key, con);
			LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
			fRow = (getAllData.get("1"));
			orangeHrmpage.login(fRow.get("UserName"), fRow.get("Password"));
			orangeHrmpage.logOut();
		}catch(Exception e) {
			
		}finally {
			iosDriver.quit();
		}

}
}
