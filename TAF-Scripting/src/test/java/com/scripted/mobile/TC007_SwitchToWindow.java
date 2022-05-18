package com.scripted.mobile;

import org.openqa.selenium.WebElement;

import com.scripted.PageObjects.SampleSwitchToWindowPage;
import com.scripted.mobile.AppiumSettings;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC007_SwitchToWindow {

	public static AndroidDriver<WebElement> androidDriver =  null;
	
	public static void main(String[] args) {
		
		try {
			
			androidDriver = MobileDriverSettings.funcGetWebAndroidDriver("AndroidWeb");
			MobileDriverSettings.launchURL("https://chandanachaitanya.github.io/selenium-practice-site/");
			SampleSwitchToWindowPage SamplepageWindow = new SampleSwitchToWindowPage(androidDriver);
			SamplepageWindow.CheckSwitchToWindow();
			} catch (Exception e){}
			finally
			{
				MobileDriverSettings.closeDriver();
				AppiumSettings.stopAppiumServer();
			}
			

		}


	}

