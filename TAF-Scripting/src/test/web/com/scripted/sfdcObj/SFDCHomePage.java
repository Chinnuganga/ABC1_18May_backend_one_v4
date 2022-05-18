package com.scripted.sfdcObj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.WebHandlers;

public class SFDCHomePage {
	
	WebDriver driver;
	
	@FindBy(xpath = "//a[@title='Accounts']//span")
	private WebElement lnkAccnts;

	@FindBy(xpath = "//a[@title='Leads']//span")
	private WebElement lnkLeads;
	
	@FindBy(xpath = "//a[@title='Contacts']//span")
	private WebElement lnkCntcts;
	
	@FindBy(xpath = "//a[@title='Opportunities']//span")
	private WebElement lnkOpprtnts;
	
	
	public SFDCHomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clckAccnts() {
		WebHandlers.clickByJsExecutor(lnkAccnts);
	}
	
	public void clckLeads() {
		WebHandlers.clickByJsExecutor(lnkLeads);
	}
	
	public void clckCntcts() {
		WebHandlers.clickByJsExecutor(lnkCntcts);
	}
	
	public void clckOpprtnts() {
		WebHandlers.clickByJsExecutor(lnkOpprtnts);
	}
}
