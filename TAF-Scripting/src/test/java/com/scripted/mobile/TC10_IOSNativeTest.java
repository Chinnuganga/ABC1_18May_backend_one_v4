package com.scripted.mobile;




import org.openqa.selenium.WebElement;

import com.scripted.PageObjects.IOSNativeApp;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;



public class TC10_IOSNativeTest {

	
	public static IOSDriver<WebElement> IOSdriver =  null;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{	
		IOSdriver = MobileDriverSettings.funcGetNativeIOSdriver("Todo_iOSNative");	
		IOSNativeApp IOSApp  = new IOSNativeApp(IOSdriver);	
		IOSApp.IOSNativeAppCheck();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			
		}

 	}

}
