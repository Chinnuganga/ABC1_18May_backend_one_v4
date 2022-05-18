package com.scripted.mobile;

import org.openqa.selenium.WebElement;

import com.scripted.UTSPageObjects.Uts_Irctc;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC04_android_UTS_IRCTC {

	public static AndroidDriver<WebElement> androidDriver = null;

	public static void main(String[] args) {
		androidDriver = MobileDriverSettings.funcGetNativeAndroiddriver("Irctc");
		
		try {
			Uts_Irctc utsIrcts = new Uts_Irctc(androidDriver);
			utsIrcts.searchTicket();
			utsIrcts.selectTrain();
			utsIrcts.passengerDetails();
			utsIrcts.bookTicket();
			utsIrcts.verifyText("Enter your credentials");
			utsIrcts.login();
			utsIrcts.logout();
		}catch(Exception e) {
			
		}finally {
			MobileDriverSettings.closeDriver();
			
		}
		
	
		
		
	}
}
