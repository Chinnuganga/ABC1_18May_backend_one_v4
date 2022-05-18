package com.scripted.sfdcObj;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;

public class SFDCContactsPage {

	WebDriver driver;
	boolean flag = false;
	public static Logger LOGGER = Logger.getLogger(SFDCContactsPage.class);

	// Contact Information

	@FindBy(xpath = "//a[@title='New']")
	private WebElement btnNew;

	@FindBy(xpath = "//div//label//span[text()='Phone']/../../input")
	private WebElement txtPhone;

	@FindBy(xpath = "//span[contains(text(),'Salutation')]/../..//a")
	private WebElement drpSalutatn;

	@FindBy(xpath = "//div//label//span[contains(text(),'Home Phone')]/../../input")
	private WebElement txtHomePhone;

	@FindBy(xpath = "//div//label//span[contains(text(),'First Name')]/../../input")
	private WebElement txtFName;

	@FindBy(xpath = "//div//label//span[contains(text(),'Last Name')]/../../input")
	private WebElement txtLName;

	@FindBy(xpath = "//div/label//span[contains(text(),'Account Name')]/../..//input")
	private WebElement txtAcctName;

	@FindBy(xpath = "//div//label//span[contains(text(),'Mobile')]/../../input")
	private WebElement txtMobile;

	@FindBy(xpath = "//div//label//span[contains(text(),'Title')]/../../input")
	private WebElement txtTitle;

	@FindBy(xpath = "//div//label//span[contains(text(),'Other Phone')]/../../input")
	private WebElement txtOthrPh;

	@FindBy(xpath = "//div//label//span[contains(text(),'Department')]/../../input")
	private WebElement txtDept;

	@FindBy(xpath = "//div//label//span[contains(text(),'Fax')]/../../input")
	private WebElement txtFax;

	@FindBy(xpath = "//div//label//span[contains(text(),'Birthdate')]/../..//input")
	private WebElement txtBirthDt;

	@FindBy(xpath = "//div//label//span[contains(text(),'Email')]/../../input")
	private WebElement txtEmail;

	@FindBy(xpath = "//div//label//span[contains(text(),'Reports To')]/../..//input")
	private WebElement txtReprtsTo;

	@FindBy(xpath = "//div//label//span[contains(text(),'Assistant')]/../../input")
	private WebElement txtAsstnt;

	@FindBy(xpath = "//span[contains(text(),'Lead Source')]/../..//a")
	private WebElement drpLeadSrc;

	@FindBy(xpath = "//div//label//span[contains(text(),'Asst. Phone')]/../../input")
	private WebElement txtAsstPhne;

	// Address Information

	@FindBy(xpath = "//div/label//span[contains(text(),'Mailing Street')]/../../textarea") // input[@id='96:1476;a']
	private WebElement txtMailSt;

	@FindBy(xpath = "//div/label//span[contains(text(),'Other Street')]/../../textarea") // input[@id='96:1476;a']
	private WebElement txtOthrSt;

	@FindBy(xpath = "//div//label//span[contains(text(),'Mailing City')]/../../input")
	private WebElement txtMailCty;

	@FindBy(xpath = "//div//label//span[contains(text(),'Mailing State')]/../../input")
	private WebElement txtMailState;

	@FindBy(xpath = "//div//label//span[contains(text(),'Other City')]/../../input")
	private WebElement txtOthrCty;

	@FindBy(xpath = "//div//label//span[contains(text(),'Other State')]/../../input")
	private WebElement txtOthrState;

	@FindBy(xpath = "//div//label//span[contains(text(),'Mailing Zip')]/../../input")
	private WebElement txtMailZip;

	@FindBy(xpath = "//div//label//span[contains(text(),'Mailing Country')]/../../input")
	private WebElement txtMailCtry;

	@FindBy(xpath = "//div//label//span[contains(text(),'Other Zip')]/../../input")
	private WebElement txtOthrZip;

	@FindBy(xpath = "//div//label//span[contains(text(),'Other Country')]/../../input")
	private WebElement txtOthrCtry;

	// Additional Information

	@FindBy(xpath = "//div//label//span[contains(text(),'Languages')]/../../input")
	private WebElement txtLangs;

	@FindBy(xpath = "//span[contains(text(),'Level')]/../..//a")
	private WebElement drpLevel;

	// Descriptive Information

	@FindBy(xpath = "//div/label//span[contains(text(),'Description')]/../../textarea") // input[@id='96:1476;a']
	private WebElement txtDescptn;

	// Buttons

	@FindBy(xpath = "//button[@title='Save']") // input[@id='96:1476;a']
	private WebElement btnSave;

	@FindBy(xpath = "//button[@title='Save & New']") // input[@id='96:1476;a']
	private WebElement btnSaveAndNew;

	@FindBy(xpath = "//button[@title='Cancel']") // input[@id='96:1476;a']
	private WebElement btnCancel;

	// Contact creation verification

	@FindBy(xpath = "//table[@role='grid']")
	private WebElement tblContcts;

	// Activities new Task

	@FindBy(xpath = "//span[contains(text(),'New Task')]")
	private WebElement lnkNewTask;

	@FindBy(xpath = "//span[contains(text(),'Create a task')]")
	private WebElement btnCrtTask;

	@FindBy(xpath = "(//label[contains(text(),'Subject')]/..//input)[1]")
	private WebElement drpNewTkSb;

	@FindBy(xpath = "//span[contains(text(),'Due Date')]/../..//input")
	private WebElement txtDueDt;

	@FindBy(xpath = "(//span[contains(text(),'Related To')])[1]/../..//input")
	private WebElement drpNwTkReltdTo;

	@FindBy(xpath = "//span[contains(text(),'Status')]/../..//a")
	private WebElement drpStatus;
	// span[contains(text(),'Status')]/../..//a

	@FindBy(xpath = "(//div[@class='slds-grid bottomBar'])[1]//span[text()='Save']")
	private WebElement btnNwTskSv;

	// Activities Log a call

	@FindBy(xpath = "//span[contains(text(),'Log a Call')]")
	private WebElement lnkLgACl;

	@FindBy(xpath = "(//label[contains(text(),'Subject')]/..//input)[2]")
	private WebElement drpLgClSb;

	@FindBy(xpath = "//span[contains(text(),'Comments')]/../..//textarea")
	private WebElement txtCmnts;

	@FindBy(xpath = "(//span[contains(text(),'Related To')])[2]/../..//input")
	private WebElement drpLgClReltdTo;

	@FindBy(xpath = "(//div[@class='slds-grid bottomBar'])[2]//span[text()='Save']")
	private WebElement btnLgClSv;

	// Activities New Event

	@FindBy(xpath = "//span[contains(text(),'New Event')]")
	private WebElement lnkNwEvnt;

	@FindBy(xpath = "(//label[contains(text(),'Subject')])[3]/..//input")
	private WebElement drpNwEvtSb;

	@FindBy(xpath = "//span[contains(text(),'All-Day Event')]/../..//input")
	private WebElement chkAllDyEt;

	@FindBy(xpath = "//span[contains(text(),'Location')]/../..//input")
	private WebElement txtLoctn;

	@FindBy(xpath = "(//span[contains(text(),'Related To')])[3]/../..//input")
	private WebElement drpNwEtReltdTo;

	@FindBy(xpath = "(//div[@class='slds-grid bottomBar'])[3]//span[text()='Save']")
	private WebElement btnNwEtSv;

	// Success Message
	//span[contains(@class,'toastMessage') and contains(text(),'created')]
	@FindBy(xpath = "//span[contains(@class,'toastMessage')]")
	private WebElement txtSuccess;

	public SFDCContactsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void entrNewCntct() {
		WebHandlers.click(btnNew);
	}

	public void enterCntctInfo(Map<String, String> fSFDCRow) {
		WebHandlers.enterText(txtPhone, fSFDCRow.get("Phone"));
		WebHandlers.enterText(txtHomePhone, fSFDCRow.get("Home Phone"));
		WebHandlers.divLinkListBox(drpSalutatn, fSFDCRow.get("Salutation"));
		WebHandlers.enterText(txtFName, fSFDCRow.get("First Name"));
		WebHandlers.enterText(txtLName, fSFDCRow.get("Last Name"));
		WebHandlers.divListBox(txtAcctName, fSFDCRow.get("Account Name"));
		WebHandlers.enterText(txtMobile, fSFDCRow.get("Mobile"));
		WebHandlers.enterText(txtTitle, fSFDCRow.get("Title"));
		WebHandlers.enterText(txtOthrPh, fSFDCRow.get("Other Phone"));
		WebHandlers.enterText(txtDept, fSFDCRow.get("Department"));
		WebHandlers.enterText(txtFax, fSFDCRow.get("Fax"));
		WebHandlers.enterText(txtBirthDt, fSFDCRow.get("BirthDate"));
		WebHandlers.enterText(txtEmail, fSFDCRow.get("Email"));
		//WebHandlers.divListBox(txtReprtsTo,fSFDCRow.get("Reports To"));
		WebHandlers.enterText(txtAsstnt, fSFDCRow.get("Assistant"));
		WebHandlers.divLinkListBox(drpLeadSrc, fSFDCRow.get("Lead Source"));
		WebHandlers.enterText(txtAsstPhne, fSFDCRow.get("Asst Phone"));
	}

	public void enterAddrInfo(Map<String, String> fSFDCRow) {
		WebHandlers.enterText(txtMailSt, fSFDCRow.get("Mailing Street"));
		WebHandlers.enterText(txtOthrSt, fSFDCRow.get("Other Street"));
		WebHandlers.enterText(txtMailCty, fSFDCRow.get("Mailing City"));
		WebHandlers.enterText(txtMailState, fSFDCRow.get("Mailing State"));
		WebHandlers.enterText(txtOthrCty, fSFDCRow.get("Other City"));
		WebHandlers.enterText(txtOthrState, fSFDCRow.get("Other State"));
		WebHandlers.enterText(txtMailZip, fSFDCRow.get("Mailing Zip"));
		WebHandlers.enterText(txtMailCtry, fSFDCRow.get("Mailing Country"));
		WebHandlers.enterText(txtOthrZip, fSFDCRow.get("Other Zip"));
		WebHandlers.enterText(txtOthrCtry, fSFDCRow.get("Other Country"));
	}

	public void enterAddtnlInfo(Map<String, String> fSFDCRow) {
		WebHandlers.enterText(txtLangs, fSFDCRow.get("Languages"));
		WebHandlers.divLinkListBox(drpLevel, fSFDCRow.get("Level"));
		WebHandlers.enterText(txtDescptn, fSFDCRow.get("Description"));
	}

	public void clickSave(){
		WebHandlers.click(btnSave);
	}

	public void vfyCntctCreatn(Map<String, String> fSFDCRow) throws InterruptedException {
		boolean cntctFlag=WebHandlers.vfySuccsMsg(txtSuccess);
		Thread.sleep(4000);
		if(cntctFlag==true) {
			System.out.println("Toastmessage");
			LOGGER.info("Contact " + fSFDCRow.get("First Name") + " " + fSFDCRow.get("Last Name") + " was created successfully");
		}
		/*boolean vfyFlag = WebHandlers.vfySFDCTblThVal(tblContcts, lookUpText);
		if (vfyFlag == true) {
			LOGGER.info("Opportunity " + lookUpText + " was created successfully");
		}*/
	}

	public void clkCntct(Map<String, String> fSFDCRow) {
		WebHandlers.TblSFDCThCelLkClk(tblContcts, fSFDCRow.get("First Name") + " " + fSFDCRow.get("Last Name"));
	}

	public void crtNewTsk(Map<String, String> fSFDCRow) {
		WebHandlers.click(lnkNewTask);
		WebHandlers.click(btnCrtTask);
		WebHandlers.enterText(drpNewTkSb, fSFDCRow.get("NewTk Subject"));
		WebHandlers.enterText(txtDueDt, fSFDCRow.get("Due Date"));
		WebHandlers.divListBox(drpNwTkReltdTo, fSFDCRow.get("Account Name"));
		WebHandlers.divLinkListBox(drpStatus, fSFDCRow.get("Status"));
		//WebHandlers.divListBox(drpStatus, fSFDCRow.get("Status"));
		WebHandlers.click(btnNwTskSv);
		flag=WebHandlers.vfySuccsMsg(txtSuccess);;
		if(flag==true) {
			System.out.println("ToastMessage");
			LOGGER.info("New Task with Subject " +fSFDCRow.get("NewTk Subject") + " is created successfully" );
		}
	}

	public void crtNewLogCl(Map<String, String> fSFDCRow) {
		WebHandlers.click(lnkLgACl);
		WebHandlers.clrTxtByJavaScript(drpLgClSb);
		WebHandlers.enterText(drpLgClSb, fSFDCRow.get("LogCl Subject"));
		WebHandlers.enterText(txtCmnts, fSFDCRow.get("Comments"));
		WebHandlers.divListBox(drpLgClReltdTo, fSFDCRow.get("Account Name"));
		WebHandlers.click(btnLgClSv);
		flag=WebHandlers.vfySuccsMsg(txtSuccess);
		if(flag==true) {
			System.out.println("ToastMessage");
			LOGGER.info("Log a Call with Subject " +fSFDCRow.get("LogCl Subject") + " is created successfully" );
		}
	}

	public void crtNewEvnt(Map<String, String> fSFDCRow) {
		WebHandlers.click(lnkNwEvnt);
		WebHandlers.clrTxtByJavaScript(drpNwEvtSb);
		WebHandlers.enterText(drpNwEvtSb, fSFDCRow.get("NewEt Subject"));
		if (fSFDCRow.get("All-Day Event").equalsIgnoreCase("Yes")) {
			WebHandlers.click(chkAllDyEt);
		}
		WebHandlers.divListBox(drpNwEtReltdTo, fSFDCRow.get("Account Name"));
		WebHandlers.enterText(txtLoctn, fSFDCRow.get("Location"));
		WebHandlers.click(btnNwEtSv);
		flag=WebHandlers.vfySuccsMsg(txtSuccess);
		if(flag==true) {
			System.out.println("ToastMessage");
			LOGGER.info("New Event with Subject " +fSFDCRow.get("NewEt Subject") + " is created successfully" );
		}
	}
	
	public void vfyActvtyCrtn(Map<String, String> fSFDCRow) {
		if(flag==true) {
			System.out.println("ToastMessage");
			LOGGER.info("Activity creation for subject " +fSFDCRow.get("First Name") + " " + fSFDCRow.get("Last Name") + " is successful" );
		}
	}
}
