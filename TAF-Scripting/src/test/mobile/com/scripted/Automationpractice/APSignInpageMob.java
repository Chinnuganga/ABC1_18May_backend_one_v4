package com.scripted.Automationpractice;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APSignInpageMob {

	MobileDriver<WebElement> androidDriver;

	@FindBy(id = "email")
	private MobileElement emailAddress;

	@FindBy(id = "passwd")
	private MobileElement password;

	@FindBy(id = "SubmitLogin")
	private MobileElement SubmitButton;

	public APSignInpageMob(MobileDriver<WebElement> androidDriverr) {
		this.androidDriver = androidDriverr;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void login(String username, String pass) {
		MobileHandlers.enterText(emailAddress, username);
		MobileHandlers.enterText(password, pass);
		MobileHandlers.click(SubmitButton);
	}
}
