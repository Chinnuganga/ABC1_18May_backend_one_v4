package com.scripted.AutoPracPageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;

public class APMyAccountPage {

	WebDriver driver;

	@FindBy(xpath = "//span[text()='Order history and details']")
	private WebElement orderHistoryandDetails;
	
	@FindBy(xpath = "//span[text()='My personal information']")
	private WebElement myPersonalInformation;


	public APMyAccountPage(WebDriver driver) {
		this.driver = BrowserDriver.getDriver();
	}
	
	public void clickMyPersonalInformation() {
		WebHandlers.click(myPersonalInformation);		
	}

}
