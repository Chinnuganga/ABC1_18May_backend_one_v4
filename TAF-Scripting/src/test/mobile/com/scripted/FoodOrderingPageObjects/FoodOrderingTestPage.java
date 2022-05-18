package com.scripted.FoodOrderingPageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.AppiumSettings;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FoodOrderingTestPage {
	
	AndroidDriver<WebElement> androidDriver ;
	String packageName = "com.abhiandroid.foodordering";
    String appActivity = "com.abhiandroid.foodordering.Activities.MainActivity";
    
    @AndroidFindBy(id="com.abhiandroid.foodordering:id/menu")
    AndroidElement menuIcon;
    
	@AndroidFindBy(xpath=".//*[@text='My Profile']")
	AndroidElement myProfile;
	
	@AndroidFindBy(xpath=".//*[@text='My Orders']")
	AndroidElement myOrders;
	
	@AndroidFindBy(xpath=".//*[@text='Order Now']")
	AndroidElement orderNow;
	
	@AndroidFindBy(id="com.abhiandroid.foodordering:id/txtSkipLogin")
    AndroidElement skipLogin;
	
	@AndroidFindBy(id="com.abhiandroid.foodordering:id/loginNow")
	AndroidElement loginBtn;
	
	@AndroidFindBy(id="com.abhiandroid.foodordering:id/email")
	AndroidElement email;
	
	@AndroidFindBy(id="com.abhiandroid.foodordering:id/password")
	AndroidElement password;
	
	@AndroidFindBy(id="com.abhiandroid.foodordering:id/signIn")
	AndroidElement signIn;
	
	@AndroidFindBy(xpath=".//*[@text='COOKIES']")
	AndroidElement cookies;
	
	public FoodOrderingTestPage(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void loginTest() {
		
		try {
		MobileHandlers.enterText(email, "rajalekshmy25@gmail.com");
		MobileHandlers.enterText(password, "test1234");
		MobileHandlers.click(signIn);
		MobileHandlers.click(menuIcon);
		MobileHandlers.click(myOrders);
		MobileHandlers.click(orderNow);
		MobileHandlers.scrollAndClick(cookies, "horizontal");
		
		}catch(Exception e) {
			
		}finally {
			AppiumSettings.stopAppiumServer();
		}
	}
	
	
public void AddtoCart() {
		
		try {
		MobileHandlers.click(skipLogin);
		MobileHandlers.scrollAndClick(cookies, "horizontal");
		
		}catch(Exception e) {
			
		}finally {
			AppiumSettings.stopAppiumServer();
		}
	}
	
	

}
