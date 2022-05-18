package com.scripted.OrangeHRMPageObjectsMobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.MobileElement;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OrangeHRMPageios {

	IOSDriver<WebElement> IOSdriver=null;
	
	@FindBy(id = "txtUsername")
	private MobileElement loginUserName;

	@FindBy(id = "txtPassword")
	private MobileElement loginPassword;

	@FindBy(id = "btnLogin")
	private MobileElement btnLogin;
	
	@FindBy(id="welcome")
	private MobileElement welcomeAdmin;

	@FindBy(id = "menu_admin_viewAdminModule")
	private MobileElement linkAdmin;
	
	@FindBy(id="btnAdd")
	private MobileElement addUser;
	
	@FindBy(id="systemUser_userType")
	private MobileElement userType;
	
	@FindBy(id="systemUser_employeeName_empName")
	private MobileElement employeeName;
	
	@FindBy(id="systemUser_userName")
	private MobileElement employee_userName;
	
	@FindBy(id="systemUser_password")
	private MobileElement employee_password;
	
	@FindBy(id="systemUser_confirmPassword")
	private MobileElement employee_Confirmpassword;
	
	@FindBy(id="btnSave")
	private MobileElement saveUser;
	
	@FindBy(id="btnDelete")
	private MobileElement deleteUser;
	
	@FindBy(id="dialogDeleteBtn")
	private MobileElement delete_OK;
	
	@FindBy(id="resultTable")
	private MobileElement usertable;
	
	@FindBy(xpath=".//a[contains(@href,'saveSystemUser') and text()='Admin']")
	private MobileElement adminLink;
	
	@FindBy(xpath="//*[@id=\"systemUser_userType\"]/option[1]")
	private MobileElement userrole_Admin;
	
	@FindBy(id="btnCancel")
	private MobileElement btncancel;
	
	@FindBy(xpath = "//*[@id=\"welcome-menu\"]/ul/li[3]/a")
	private MobileElement linkLogout;
	
	@FindBy(id="menu_leave_viewLeaveModule")
	private MobileElement leaveTab;
	
	@FindBy(id="btnSave")
	private MobileElement savetab;
	//<input type="button" class="" id="btnSave" name="btnSave" value="Save">
	
	public OrangeHRMPageios(IOSDriver<WebElement> IOSdriver) {
		this.IOSdriver = IOSdriver;
		PageFactory.initElements(new AppiumFieldDecorator(IOSdriver), this);
	}
	
	public void login(String userName, String passWord) {
		MobileHandlers.enterText(loginUserName,userName);
		MobileHandlers.enterText( loginPassword, passWord);
		MobileHandlers.clickByJsExecutor(btnLogin);
	}
	
	public void navigate_SystemUser() {
		MobileHandlers.click(linkAdmin);
	}
	
	public void navigate_Leavetab()
	{
		MobileHandlers.click(leaveTab);
		MobileHandlers.click(savetab);
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
		//MobileHandlers.clickByJsExecutor(adminLink);
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
		System.out.println(MobileHandlers.getIndexofVal(usertable, "johnma"));
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
	
public void orangeHRMTableLogin_IOS(String userName, String passWord) {
		
		
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
