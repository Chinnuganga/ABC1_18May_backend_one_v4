package com.scripted.mobile;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.scripted.PlaystorePageobjects.PlaystoreAndroidPage;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC01_MobileAndroidSampleTestScript {
	
	public static AndroidDriver<WebElement> androidDriver =  null;
	

	public static void main(String[] args) {
		androidDriver = MobileDriverSettings.funcGetNativeAndroiddriver("PlayStoreNative");
		PlaystoreAndroidPage playStorePage = new PlaystoreAndroidPage(androidDriver);
		playStorePage.testPlaystore();
	}

}
