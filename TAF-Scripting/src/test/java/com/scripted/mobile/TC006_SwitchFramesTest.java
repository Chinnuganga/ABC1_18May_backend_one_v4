package com.scripted.mobile;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.scripted.PageObjects.SampleSwitchFramePage;
import com.scripted.mobile.AppiumSettings;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;

public class TC006_SwitchFramesTest {

	public static AndroidDriver<WebElement> androidDriver =  null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
		
		androidDriver = MobileDriverSettings.funcGetWebAndroidDriver("AndroidWeb");
				MobileDriverSettings.launchURL("https://www.toolsqa.com/iframe-practice-page/");
		SampleSwitchFramePage Samplepage = new SampleSwitchFramePage(androidDriver);
		
		 List<WebElement> iframeElements = androidDriver.findElements(By.tagName("iframe"));
		 System.out.println("The total number of iframes are " + iframeElements.size());
		Samplepage.CheckSwitchToFrame();
		} catch (Exception e){}
		finally
		{
			MobileDriverSettings.closeDriver();
			AppiumSettings.stopAppiumServer();
		}
		

	}

}
