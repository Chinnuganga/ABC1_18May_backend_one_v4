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
import com.scripted.mobile.AppiumSettings;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC008_MobileTableOperations {

	public static AndroidDriver<WebElement> androidDriver =  null;
	
	public static void main(String[] args) {
		
		
		try 
		{
			androidDriver = MobileDriverSettings.funcGetWebAndroidDriver("AndroidWeb");
			MobileDriverSettings.launchURL("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
			OrangeHRMPage SamplepageTableOPs = new OrangeHRMPage(androidDriver);	
			PropertyDriver propDriver = new PropertyDriver();
			propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/configOrangehrm.properties");
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
			
			SamplepageTableOPs.orangeHRMTableLogin_Android(fRow.get("UserName"), fRow.get("Password"));
			SamplepageTableOPs.headerVal();
			SamplepageTableOPs.BodyVal();
			SamplepageTableOPs.ColMapByIndxVal();
			SamplepageTableOPs.RowMapByIndxVal();
			SamplepageTableOPs.rowmapHdrValue(1);
			SamplepageTableOPs.colmapHdrValue("Username");
			SamplepageTableOPs.inxValue();
			SamplepageTableOPs.tblClick("Alarm");
			SamplepageTableOPs.tdValue();
			SamplepageTableOPs.thValue();
		}
		catch(Exception e){}
		finally
		{
			MobileDriverSettings.closeDriver();
			AppiumSettings.stopAppiumServer();
		}

	}

}
