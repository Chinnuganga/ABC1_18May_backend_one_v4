package com.scripted.PCloudySampleApp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.scripted.mobile.MobileHandlers;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PCloudyAndroidNativeApp {
	
	AndroidDriver<WebElement> androidDriver ;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/accept")
	private AndroidElement accept;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/ecLoginButton")
	private AndroidElement loginBTN;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/username")
	private AndroidElement UserName;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/password")
	private AndroidElement UserPassword;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/loginbtn")
	private AndroidElement UserLoginBtn;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/searchFlights")
	private AndroidElement SearchFlights;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/spinnerfrom")
	private AndroidElement departure;
	
	@AndroidFindBy(xpath=".//*[@text='Bangalore, India (BLR)']")
	private AndroidElement departure_place;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/spinnerto")
	private AndroidElement arrival;
	
	@AndroidFindBy(xpath=".//*[@text='New Delhi, India(DEL)']")
	private AndroidElement arrival_place;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/singleTrip")
	private AndroidElement singlrTrip;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/no_adults")
	private AndroidElement adults;
	
	@AndroidFindBy(xpath=".//*[@text='3']")
	private AndroidElement NoAdults;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/no_children")
	private AndroidElement childrens;
	
	@AndroidFindBy(xpath=".//*[@text='1']")
	private AndroidElement Nochildrens;
	
	@AndroidFindBy(id="com.pcloudy.appiumdemo:id/spinnerflight")
	private AndroidElement flightClass;
	
	@AndroidFindBy(xpath=".//*[@text='Premium Economy']")
	private AndroidElement flightClass_type;
	
	
	
	

	public PCloudyAndroidNativeApp(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void login() {
		MobileHandlers.click(accept);
		MobileHandlers.click(loginBTN);
		MobileHandlers.clearof(UserName);
		MobileHandlers.enterText(UserName, "1234");
		MobileHandlers.enterText(UserPassword, "123");
		MobileHandlers.click(UserLoginBtn);
		
	}
	
	public void bookingDetails() {
		MobileHandlers.click(departure);
		MobileHandlers.click(departure_place);
		MobileHandlers.click(arrival);
		MobileHandlers.click(arrival_place);
		MobileHandlers.click(singlrTrip);
		MobileHandlers.click(adults);
		MobileHandlers.click(NoAdults);
		MobileHandlers.click(childrens);
		MobileHandlers.click(Nochildrens);
		MobileHandlers.click(flightClass);
		MobileHandlers.click(flightClass_type);
	}
	
	public void searchFlights() {
		MobileHandlers.click(SearchFlights);
	}
	
	public void assertConfirmMsg(String msg) {
		
	}
}
