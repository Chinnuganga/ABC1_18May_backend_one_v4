package com.scripted.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.scripted.angularObj.CafeObj;
import com.scripted.web.BrowserDriver;

public class CafeAddAndDelete {
	public static WebDriver driver = BrowserDriver.funcGetNgWebdriver();

	public static void main(String args[]) throws Exception {
		CafeObj ag = PageFactory.initElements(driver, CafeObj.class);
		BrowserDriver.launchWebURL("http://cafetownsend-angular-rails.herokuapp.com/login");
		ag.login();
		ag.create();
		ag.delete();
		BrowserDriver.closeBrowser();
	}
}
