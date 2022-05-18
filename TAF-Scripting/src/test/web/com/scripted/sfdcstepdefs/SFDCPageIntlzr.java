package com.scripted.sfdcstepdefs;

import org.openqa.selenium.support.PageFactory;

import com.scripted.sfdcObj.SFDCAccountsPage;
import com.scripted.sfdcObj.SFDCContactsPage;
import com.scripted.sfdcObj.SFDCHomePage;
import com.scripted.sfdcObj.SFDCLoginPage;
import com.scripted.sfdcObj.SFDCOpportunitiesPage;
import com.scripted.web.BrowserDriver;

public class SFDCPageIntlzr {
	
	public static SFDCLoginPage sfdcLoginPage;
	public static SFDCAccountsPage sfdcAcctsPage;
	public static SFDCHomePage sfdcHomePage;
	public static SFDCContactsPage sfdcCtntsPage;
	public static SFDCOpportunitiesPage sfdcOptntsPage;
	
	public static void SFDCIntlzePgs() {
		sfdcLoginPage = PageFactory.initElements(BrowserDriver.getDriver(), SFDCLoginPage.class);
		sfdcAcctsPage = PageFactory.initElements(BrowserDriver.getDriver(), SFDCAccountsPage.class);
		sfdcHomePage = PageFactory.initElements(BrowserDriver.getDriver(), SFDCHomePage.class);
		sfdcCtntsPage = PageFactory.initElements(BrowserDriver.getDriver(), SFDCContactsPage.class);
		sfdcOptntsPage = PageFactory.initElements(BrowserDriver.getDriver(), SFDCOpportunitiesPage.class);
	}
}
