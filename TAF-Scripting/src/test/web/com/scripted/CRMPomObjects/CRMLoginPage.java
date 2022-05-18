package com.scripted.CRMPomObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.WebHandlers;

public class CRMLoginPage {
	WebDriver driver;

	@FindBy(name = "email")
	private WebElement loginUname;
	@FindBy(name = "password")
	private WebElement loginPass;
	@FindBy(xpath = "//div[text()='Login']")
	private WebElement loginbtn;
	@FindBy(xpath = "//input[@name='button']")
	private WebElement acceptBtn;

	public CRMLoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void login(String user, String pass) {
		//WebHandlers.click(acceptBtn);
		WebHandlers.enterText(loginUname, user);
		WebHandlers.enterText(loginPass, pass);
		WebHandlers.click(loginbtn);
		
	}

}
