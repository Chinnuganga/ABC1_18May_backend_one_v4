package com.scripted.AutoPracPageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;

public class APSignInpage {

	WebDriver driver;

	@FindBy(id = "email")
	private WebElement emailAddress;

	@FindBy(id = "passwd")
	private WebElement password;

	@FindBy(id = "SubmitLogin")
	private WebElement SubmitButton;
	
	public APSignInpage(WebDriver driver) {
		this.driver = BrowserDriver.getDriver();
	}

	public void login(String username, String pass) {
		WebHandlers.enterText(emailAddress, username);
		WebHandlers.enterText(password, pass);
		WebHandlers.click(SubmitButton);
	}
}
