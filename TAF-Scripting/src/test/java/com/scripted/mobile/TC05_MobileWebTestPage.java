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

import io.appium.java_client.android.AndroidDriver;

public class TC05_MobileWebTestPage {

	public static AndroidDriver<WebElement> androidDriver = null;
	
	public void createUser(OrangeHRMPage orangeHrmpage,Map<String, String> fRow) {
		  orangeHrmpage.navigate_SystemUser();
		  orangeHrmpage.adduser(fRow.get("UserRole"),
	        		fRow.get("EmpName"), 
	        		fRow.get("EmpUserName"), 
	        		fRow.get("EmpPwd"),
	        		fRow.get("EmpConfirmPwd"));
		  orangeHrmpage.saveUser();
		  orangeHrmpage.checkUser(fRow.get("EmpUserName"));
		  orangeHrmpage.deleteUser(fRow.get("EmpUserName"));
	}
	

	public static void main(String[] args) {
		
		AndroidDriver<WebElement> androidDriver ;
		Map<String, String> fRow;
		PropertyDriver propDriver = new PropertyDriver();
		OrangeHRMPage orangeHrmpage ;
		TestDataFactory test = new TestDataFactory();
		
		String sheetname = "TC004";
		String key = "";
		try {
			androidDriver = MobileDriverSettings.funcGetWebAndroidDriver("AndroidWeb");
			MobileDriverSettings.launchURL("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
		 orangeHrmpage = new OrangeHRMPage(androidDriver);
		 propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configOrangehrm.properties");
		 String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
		  ExcelConnector con = new ExcelConnector(filename, sheetname);
		  TestDataObject obj = test.GetData(key, con);
		  LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
		  fRow = (getAllData.get("1"));
		  orangeHrmpage.login(fRow.get("UserName"), fRow.get("Password"));
		  orangeHrmpage.navigate_SystemUser();
		  orangeHrmpage.navigate_userPage("admin");
		  orangeHrmpage.verifyUser();
		  orangeHrmpage.logOut();
		
		
		}catch(Exception e) {
			
		}finally {
			MobileDriverSettings.closeDriver();
			
		}
		
		
		
	}

}
