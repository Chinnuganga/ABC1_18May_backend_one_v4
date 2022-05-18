package com.scripted.mobile;

import org.openqa.selenium.WebElement;

import com.scripted.FoodOrderingPageObjects.FoodOrderingTestPage;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC02_FoodOrderingSampleTestApp {

public static AndroidDriver<WebElement> androidDriver =  null;
	

	public static void main(String[] args) {
		androidDriver = MobileDriverSettings.funcGetNativeAndroiddriver("FoodOrderingNative");
		FoodOrderingTestPage foTestPage = new FoodOrderingTestPage(androidDriver);
		foTestPage.AddtoCart();
}
}
