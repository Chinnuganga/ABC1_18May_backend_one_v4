package com.scripted.Automationpractice;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APMyAccountPageMob {

	MobileDriver<WebElement> androidDriver;

	@FindBy(xpath = "//span[text()='Order history and details']")
	private MobileElement orderHistoryandDetails;
	
	@FindBy(xpath = "//span[text()='My personal information']")
	private MobileElement myPersonalInformation;
	
	public APMyAccountPageMob(MobileDriver<WebElement> androidDriverr) {
		this.androidDriver = androidDriverr;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}

	public void clickMyPersonalInformation() {
		//MobileHandlers.click(myPersonalInformation);	
		MobileHandlers.scrollAndClick(myPersonalInformation, "vertical");
	}

}
