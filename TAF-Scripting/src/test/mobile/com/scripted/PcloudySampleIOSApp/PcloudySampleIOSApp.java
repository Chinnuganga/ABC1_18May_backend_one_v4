package com.scripted.PcloudySampleIOSApp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PcloudySampleIOSApp {
	
	public static IOSDriver<WebElement> iosDriver = null;
	
	@FindBy(xpath="//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextField[1]")
	private IOSElement email;
	
	@FindBy(xpath="//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeSecureTextField[1]")
	private IOSElement password;
	
	@FindBy(xpath="//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]\")")
	private IOSElement signINBTN;
	
	@FindBy(xpath="//XCUIElementTypeButton[@name=\"Skip\"]")
	private IOSElement  skipBtn;
	
	@FindBy(xpath="//XCUIElementTypeButton[@name=\"iconSlide\"]")
	private IOSElement  menuIcon;
	
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Photos\"]")
	private IOSElement  photosmenu;
	
	@FindBy(xpath="//XCUIElementTypeImage[@name=\"sample2.jpg\"]")
	private IOSElement pictureOne;
	
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Home\"]")
	private IOSElement homebtn;
	
	@FindBy(xpath="//XCUIElementTypeButton[@name=\"SHOW ALERT VIEW\"]")
	private IOSElement showAlert;
	
	@FindBy(xpath="//XCUIElementTypeButton[@name=\"Do Something\"]")
	private IOSElement doSomething_alertoption;
	
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"ON\"]")
	private IOSElement switchONBTN;
	
	
	
	
	public PcloudySampleIOSApp(IOSDriver<WebElement> iosDriver) {
		this.iosDriver = iosDriver;
		PageFactory.initElements(new AppiumFieldDecorator(iosDriver), this);
	}
	
	public void login(String emailID, String pswd) {
		MobileHandlers.enterText(email, emailID);
		MobileHandlers.enterText(password, pswd);
		//MobileHandlers.click(signINBTN);
		MobileHandlers.click(skipBtn);
	}
	
	public void actions() {
		MobileHandlers.click(menuIcon);
		MobileHandlers.click(photosmenu);
		MobileHandlers.click(pictureOne);
		MobileHandlers.click(menuIcon);
		MobileHandlers.click(homebtn);
		MobileHandlers.click(showAlert);
		MobileHandlers.click(doSomething_alertoption);
		
	}
	
	

}
