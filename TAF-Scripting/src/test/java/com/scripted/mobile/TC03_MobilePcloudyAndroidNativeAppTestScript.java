package com.scripted.mobile;

import org.openqa.selenium.WebElement;

import com.scripted.PCloudySampleApp.PCloudyAndroidNativeApp;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC03_MobilePcloudyAndroidNativeAppTestScript {
	public static AndroidDriver<WebElement> androidDriver =  null;
	
	public static void main(String[] args) throws Exception {
		androidDriver = MobileDriverSettings.funcGetpCloudyNativeAndroiddriver("AndroidNative");
		PCloudyAndroidNativeApp pCloudySampleApp = new PCloudyAndroidNativeApp(androidDriver);
		pCloudySampleApp.login();
		pCloudySampleApp.bookingDetails();
		pCloudySampleApp.searchFlights();
		androidDriver.quit();
	}

}
