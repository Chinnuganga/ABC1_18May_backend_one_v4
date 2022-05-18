package com.scripted.OrangeHRMPageObjectsMobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OrangeHRMPage_TableOperations {
	
AndroidDriver<WebElement> androidDriver ;
	
	@FindBy(id = "txtUsername")
	private AndroidElement loginUserName;

	@FindBy(id = "txtPassword")
	private AndroidElement loginPassword;

	@FindBy(id = "btnLogin")
	private AndroidElement btnLogin;

	@FindBy(id = "menu_admin_viewAdminModule")
	private AndroidElement linkAdmin;
	
	@FindBy(id = "resultTable")
	private AndroidElement table;
	
	
	
	public OrangeHRMPage_TableOperations(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void headerVal() {
		System.out.println(MobileHandlers.getTblHeaderVal(table));
	}
	
	public void BodyVal() {
		System.out.println(MobileHandlers.getTblBodyVal(table));
	}
	
	public void tdValue() {
		System.out.println(MobileHandlers.getTblTdVal(table,2,1));
	}
	
	public void thValue() {
		System.out.println(MobileHandlers.getTblThVal(table,0,3));
	}
	
	public void inxValue() {
		System.out.println(MobileHandlers.getIndexofVal(table, "vijaykumar"));
	}
	
	public void colmapHdrValue(String str) {
		System.out.println(MobileHandlers.getColMapByHdrVal(table,str));
}
	public void rowmapHdrValue(int rowHeader) {
		System.out.println(MobileHandlers.getRowMapByIndxVal(table,rowHeader));
}
	public void ColMapByIndxVal() {
		System.out.println(MobileHandlers.getColMapByIndxVal(table,2));
	}
	public void RowMapByIndxVal() {
		System.out.println(MobileHandlers.getRowMapByIndxVal(table,2));
	}
	public void tblClick(String str) {
		MobileHandlers.TblCelChkboxClick(table, str);
	}

	public void tblLink(String str) {
		MobileHandlers.TblCelLinkClick(table, str);
	}
	
	public void orangeHRMLogin_Android(String userName, String passWord) {
		MobileHandlers.enterText(loginUserName,userName);
		MobileHandlers.enterText( loginPassword, passWord);
		MobileHandlers.clickByJsExecutor(btnLogin);
		MobileHandlers.click(linkAdmin);
		
		
		
	}
	

}
