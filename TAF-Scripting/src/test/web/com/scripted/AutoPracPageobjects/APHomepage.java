package com.scripted.AutoPracPageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;

public class APHomepage {

	WebDriver driver;

	@FindBy(className = "login")
	private WebElement Signin;

	@FindBy(className = "logout")
	private WebElement logout;

	public APHomepage(WebDriver driver) {
		this.driver = BrowserDriver.getDriver();
	}

	public void clickSignin() {
		WebHandlers.click(Signin);
	}

	public void clickLogout() {
		WebHandlers.click(logout);
	}

}
