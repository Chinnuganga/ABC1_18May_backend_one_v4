package com.scripted.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;
import com.scripted.mobile.MobileWaitHelper;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SampleSwitchFramePage {
	
	AndroidDriver<WebElement> androidDriver ;
	
	@FindBy(name = "iframe1")
	private AndroidElement SwitchFrameBtn;
	
	
	//iframe2
	@FindBy(id = "content")
	private AndroidElement SwitchFrameClk;
	

	public SampleSwitchFramePage(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void CheckSwitchToFrame()
	{
	
		MobileHandlers.switchToFrame(SwitchFrameBtn);	
		MobileWaitHelper.waitForElement(SwitchFrameClk);
		MobileHandlers.click(SwitchFrameClk);
		MobileHandlers.switchBackFromFrame();		
	
		
	}

}
