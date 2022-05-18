package com.scripted.Automationpractice;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APHomepageMob {

	MobileDriver<WebElement> androidDriver;

	@FindBy(className = "login")
	private MobileElement signin;

	@FindBy(className = "logout")
	private MobileElement logout;
	
	public APHomepageMob(MobileDriver<WebElement> androidDriverr) {
		this.androidDriver = androidDriverr;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void clickSignin()
	{
		MobileHandlers.click(signin);
	}
	
	public void clickLogout()
	{
		MobileHandlers.click(logout);
	}
		
}
