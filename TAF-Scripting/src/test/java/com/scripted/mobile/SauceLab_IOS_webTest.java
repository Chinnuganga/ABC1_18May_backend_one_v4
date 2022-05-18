package com.scripted.mobile;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.scripted.OrangeHRMPageObjectsMobile.OrangeHRMPage;
import com.scripted.OrangeHRMPageObjectsMobile.OrangeHRMPageios;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.ios.IOSDriver;

public class SauceLab_IOS_webTest {
	public static IOSDriver<WebElement> IOSdriver =  null;
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
		// TODO Auto-generated method stub
		Map<String, String> fRow;
		PropertyDriver propDriver = new PropertyDriver();
		OrangeHRMPage orangeHrmpage ;
		TestDataFactory test = new TestDataFactory();
		
		String sheetname = "TC004";
		String key = "";
		
		try
		{
			IOSdriver = MobileDriverSettings.funcGetSauceWebIOSdriver("Mobile");
			IOSdriver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
			OrangeHRMPageios ORMpage = new OrangeHRMPageios(IOSdriver);
			propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configOrangehrm.properties");
			String filename = FileUtils.getCurrentDir() + PropertyDriver.readProp("excelName");
			ExcelConnector con = new ExcelConnector(filename, sheetname);
			TestDataObject obj = test.GetData(key, con);
			LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
			fRow = (getAllData.get("1"));
			ORMpage.login(fRow.get("UserName"), fRow.get("Password"));
			ORMpage.navigate_SystemUser();
			ORMpage.logOut();
			
		}
		catch (Exception e)
		{
			
		}
		
		finally
		{
			IOSdriver.quit();
		}

	}
	

}
