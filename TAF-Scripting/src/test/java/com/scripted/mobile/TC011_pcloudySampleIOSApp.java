package com.scripted.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.scripted.PcloudySampleIOSApp.PcloudySampleIOSApp;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.ios.IOSDriver;

public class TC011_pcloudySampleIOSApp {
	
	public static IOSDriver<WebElement> iosDriver = null;
	
	public static void main(String[] args) {
		try {
		iosDriver  = MobileDriverSettings.funcGetpCloudyNativeIOSdriver("IOSNative");
		PcloudySampleIOSApp iosApp = new PcloudySampleIOSApp(iosDriver);
		iosApp.login("test@testname.com", "testmunk");
		iosApp.actions();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		iosDriver.quit();
		}
	}
	
	
	/*iosDriver  = MobileDriverSettings.funcGetpCloudyNativeIOSdriver("IOSNative");
	PcloudySampleIOSApp iosApp = new PcloudySampleIOSApp(iosDriver);
	iosApp.login("test@testname.com", "testmunk");
	}catch(Exception e) {
	
	}finally {
		iosDriver.quit();
	}*/
	

}
