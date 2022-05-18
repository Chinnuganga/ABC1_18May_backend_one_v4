package com.scripted.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;
//import com.scripted.mobile.MobileWaitHelper;

import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class IOSNativeApp {
	
	IOSDriver<WebElement> IOSdriver=null;



	@FindBy(xpath="//XCUIElementTypeApplication[@name=\"Todo\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]")
	private MobileElement DisneyButton;
	
	@FindBy(xpath="//XCUIElementTypeApplication[@name=\"Todo\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField")
	private MobileElement DisneyText;
	
	@FindBy(xpath="//XCUIElementTypeApplication[@name=\"Todo\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[1]")
	private MobileElement DateField;
	
	@FindBy(xpath="//XCUIElementTypeApplication[@name=\"Todo\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[2")
	private MobileElement MonthField;
	
	@FindBy(xpath="//XCUIElementTypeApplication[@name=\"Todo\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[3]")
	private MobileElement YearField;
	
	@FindBy(xpath="//XCUIElementTypeButton[@name=\"Done\"]")
	private MobileElement DoneButton;
	
	
	
		
	public IOSNativeApp(IOSDriver<WebElement> IOSdriver) {
		this.IOSdriver = IOSdriver;
		PageFactory.initElements(new AppiumFieldDecorator(IOSdriver), this);
	}
	
	public void IOSNativeAppCheck()
	{
		System.out.println("I am inside pageobject");
		try 
		{
			MobileHandlers.click(DisneyButton);
			MobileHandlers.clearText(DisneyText);
			MobileHandlers.enterText(DisneyText, "Travel Planning");
			MobileHandlers.click(DoneButton);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	
		
		
	}
	

}
