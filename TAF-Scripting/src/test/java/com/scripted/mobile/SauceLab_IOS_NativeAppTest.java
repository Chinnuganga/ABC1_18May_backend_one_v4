package com.scripted.mobile;




import org.openqa.selenium.WebElement;

import com.scripted.PageObjects.IOSNativeApp;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.ios.IOSDriver;



public class SauceLab_IOS_NativeAppTest {

	
	public static IOSDriver<WebElement> IOSdriver =  null;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{	
		IOSdriver = MobileDriverSettings.funcGetSauceNativeIOSdriver("Mobile");	
		IOSNativeApp IOSApp  = new IOSNativeApp(IOSdriver);	
		IOSApp.IOSNativeAppCheck();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			IOSdriver.quit();
		}

 	}

}
