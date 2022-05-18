package com.scripted.OrangeHRMPageObjectsMobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OrangeHRMPage {
	MobileDriver<WebElement> androidDriver ;
	IOSDriver< WebElement> iosDriver;
	
	@FindBy(id = "txtUsername")
	private AndroidElement loginUserName;

	@FindBy(id = "txtPassword")
	private AndroidElement loginPassword;

	@FindBy(id = "btnLogin")
	private AndroidElement btnLogin;
	
	@FindBy(id="welcome")
	private AndroidElement welcomeAdmin;

	@FindBy(id = "menu_admin_viewAdminModule")
	private AndroidElement linkAdmin;
	
	@FindBy(id="btnAdd")
	private AndroidElement addUser;
	
	@FindBy(id="systemUser_userType")
	private AndroidElement userType;
	
	@FindBy(id="systemUser_employeeName_empName")
	private AndroidElement employeeName;
	
	@FindBy(id="systemUser_userName")
	private AndroidElement employee_userName;
	
	@FindBy(id="systemUser_password")
	private AndroidElement employee_password;
	
	@FindBy(id="systemUser_confirmPassword")
	private AndroidElement employee_Confirmpassword;
	
	@FindBy(id="btnSave")
	private AndroidElement saveUser;
	
	@FindBy(id="btnDelete")
	private AndroidElement deleteUser;
	
	@FindBy(id="dialogDeleteBtn")
	private AndroidElement delete_OK;
	
	@FindBy(id="resultTable")
	private AndroidElement usertable;
	
	@FindBy(xpath=".//a[contains(@href,'saveSystemUser') and text()='Admin']")
	private AndroidElement adminLink;
	
	@FindBy(xpath="//*[@id=\"systemUser_userType\"]/option[1]")
	private AndroidElement userrole_Admin;
	
	@FindBy(id="btnCancel")
	private AndroidElement btncancel;
	
	@FindBy(xpath = "//a[text()='Logout']")
	private AndroidElement linkLogout;
	
	public OrangeHRMPage(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public OrangeHRMPage(IOSDriver<WebElement> iosDriverr) {
		this.iosDriver = iosDriverr;
		PageFactory.initElements(new AppiumFieldDecorator(iosDriverr), this);
	}
	public OrangeHRMPage(MobileDriver<WebElement> androidDriver2) {
		this.androidDriver = androidDriver2;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver2), this);
	}
	
	public void login(String userName, String passWord) {
		MobileHandlers.enterText(loginUserName,userName);
		MobileHandlers.enterText( loginPassword, passWord);
		MobileHandlers.clickByJsExecutor(btnLogin);
		//click(btnLogin);
	}
	
	public void navigate_SystemUser() {
		MobileHandlers.click(linkAdmin);
	}
	
	public void adduser(String empType, String empName, String empUserName, String empPwd, String empConfirmPwd) {
		MobileHandlers.click(addUser);
		MobileHandlers.dropDownSetByVal(userType, empType);
		MobileHandlers.enterText(employeeName, empName);
		MobileHandlers.enterText(employee_userName, empUserName);
		MobileHandlers.enterText(employee_password, empPwd);
		MobileHandlers.enterText(employee_Confirmpassword, empConfirmPwd);
	}
	
	public void saveUser() {
		MobileHandlers.clickByJsExecutor(saveUser);
	}
	
	public void checkUser(String userName) {
		MobileHandlers.getIndexofVal(usertable, userName);
	}
	
	public void deleteUser(String userName) {
		MobileHandlers.TblCelChkboxClick(usertable, userName);
		MobileHandlers.click(deleteUser);
		MobileHandlers.click(delete_OK);
		
	}
	
	public void  navigate_userPage(String user) {
		MobileHandlers.clickByJsExecutor(adminLink);
	}
	
	public void verifyUser() {
		MobileHandlers.verifyText(userrole_Admin, "Admin");
		MobileHandlers.click(btncancel);
	}
	
	public void logOut() {
		MobileHandlers.click(welcomeAdmin);
		MobileHandlers.click(linkLogout);
	}
	

	public void headerVal() {
		System.out.println(MobileHandlers.getTblHeaderVal(usertable));
	}
	
	public void BodyVal() {
		System.out.println(MobileHandlers.getTblBodyVal(usertable));
	}
	
	public void tdValue() {
		System.out.println(MobileHandlers.getTblTdVal(usertable,2,1));
	}
	
	public void thValue() {
		System.out.println(MobileHandlers.getTblThVal(usertable,0,3));
	}
	
	public void inxValue() {
		System.out.println(MobileHandlers.getIndexofVal(usertable, "vijaykumar"));
	}
	
	public void colmapHdrValue(String str) {
		System.out.println(MobileHandlers.getColMapByHdrVal(usertable,str));
}
	public void rowmapHdrValue(int rowHeader) {
		System.out.println(MobileHandlers.getRowMapByIndxVal(usertable,rowHeader));
}
	public void ColMapByIndxVal() {
		System.out.println(MobileHandlers.getColMapByIndxVal(usertable,2));
	}
	public void RowMapByIndxVal() {
		System.out.println(MobileHandlers.getRowMapByIndxVal(usertable,2));
	}
	public void tblClick(String str) {
		MobileHandlers.TblCelChkboxClick(usertable, str);
	}

	public void tblLink(String str) {
		MobileHandlers.TblCelLinkClick(usertable, str);
	}
	
public void orangeHRMTableLogin_Android(String userName, String passWord) {
		
		
		MobileHandlers.enterText(loginUserName,userName);
		MobileHandlers.verifyText(loginUserName, "Hello");
		MobileHandlers.clearText(loginUserName);
		System.out.println("text cleared");
		MobileHandlers.existText(loginUserName);
		MobileHandlers.enterText(loginUserName,userName);
		MobileHandlers.enterText( loginPassword, passWord);
		MobileHandlers.objExists(btnLogin);
		MobileHandlers.objDisabled(btnLogin);
		MobileHandlers.verifyProperty(btnLogin,"name", "Submit");
		MobileHandlers.clickByJsExecutor(btnLogin);
		MobileHandlers.click(linkAdmin);
	
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
