package com.scripted.sfdcObj;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.WebHandlers;

public class SFDCLoginPage {
	
	WebDriver driver;
	public static Logger LOGGER = Logger.getLogger(SFDCAccountsPage.class);
	
	@FindBy(xpath = "//input[@type='email']")
	private WebElement txtUsrname;

	@FindBy(id = "password")
	private static WebElement txtPasswd;

	@FindBy(id = "Login")
	private static WebElement btnLogin;
	
	public SFDCLoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void login(String userName, String password) { 
		WebHandlers.enterText(txtUsrname, userName);
		WebHandlers.enterText(txtPasswd, password);
		WebHandlers.click(btnLogin);
		LOGGER.info("Successfully logged in to SFDC Lightening Application");
	}
	

}
