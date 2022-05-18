package com.scripted.PlaystorePageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PlaystoreAndroidPage {
	
AndroidDriver<WebElement> androidDriver ;
	
	@AndroidFindBy(id = "com.android.vending:id/navigation_button")
	private AndroidElement menuIcon;
	
	@AndroidFindBy(id="com.android.vending:id/search_box_idle_text")
	private AndroidElement searchBox_click;
	
	@AndroidFindBy(id="com.android.vending:id/search_box_text_input")
	private AndroidElement searchBox;
	
	@AndroidFindBy(xpath=".//*[@text='About Google Play']")
	private AndroidElement aboutGooglePlay;
	
	

	public PlaystoreAndroidPage(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void testPlaystore()  {
		MobileHandlers.click(searchBox_click);
		MobileHandlers.enterText(searchBox, "assas");
		MobileHandlers.click(menuIcon);
		
		MobileHandlers.scrollAndClick(aboutGooglePlay, "vertical");
	}


}
