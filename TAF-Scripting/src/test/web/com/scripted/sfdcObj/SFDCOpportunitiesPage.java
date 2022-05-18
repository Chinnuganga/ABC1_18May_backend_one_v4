package com.scripted.sfdcObj;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.WebHandlers;

public class SFDCOpportunitiesPage {

	WebDriver driver;
	public static Logger LOGGER = Logger.getLogger(SFDCOpportunitiesPage.class);

	@FindBy(xpath = "//a[@title='New']")
	private WebElement btnNew;

	// Opportunities information

	@FindBy(xpath = "//div/label//span[contains(text(),'Amount')]/../../input") // input[@id="96:1476;a"]
	private WebElement txtAmount;

	@FindBy(xpath = "//div/label//span[contains(text(),'Private')]/../../input") // input[@id='96:1476;a']
	private WebElement chkPrvte;

	@FindBy(xpath = "//div/label//span[contains(text(),'Close Date')]/../..//input") // input[@id='96:1476;a']
	private WebElement txtClseDt;

	@FindBy(xpath = "//div/label//span[contains(text(),'Opportunity Name')]/../..//input") // input[@id='96:1476;a']
	private WebElement txtOpptName;

	@FindBy(xpath = "//div/label//span[contains(text(),'Next Step')]/../..//input") // input[@id='96:1476;a']
	private WebElement txtNxtStp;

	@FindBy(xpath = "//div/label//span[contains(text(),'Account Name')]/../..//input")
	private WebElement txtAcctName;

	@FindBy(xpath = "//span//span[text()='Stage']/../..//a")
	private WebElement drpStage;

	@FindBy(xpath = "//span[contains(text(),'Type')]/../..//a")
	private WebElement drpType;

	@FindBy(xpath = "//span[contains(text(),'Probability')]/../..//input")
	private WebElement txtPrblty;

	@FindBy(xpath = "//span[contains(text(),'Lead Source')]/../..//a")
	private WebElement drpLeadSrc;

	@FindBy(xpath = "//span[contains(text(),'Primary Campaign Source')]/../..//input")
	private WebElement txtPrmCmpgnSrc;

	@FindBy(xpath = "//div/label//span[contains(text(),'Account Site')]/../../input") // input[@id='96:1476;a']
	private WebElement txtAcctSite;

	@FindBy(xpath = "//div/label//span[contains(text(),'Ticker Symbol')]/../../input") // input[@id='96:1476;a']
	private WebElement txtAcctTkrSymbl;

	@FindBy(xpath = "//span[contains(text(),'Ownership')]/../..//a") // input[@id='96:1476;a']
	private WebElement drpOwnrshp;// *[@id="308:1476;a"]/div/div/a

	@FindBy(xpath = "//span[contains(text(),'Industry')]/../..//a") // input[@id='96:1476;a']
	private WebElement drpIndstry;// *[@id="308:1476;a"]/div/div/a

	@FindBy(xpath = "//div/label//span[contains(text(),'Employees')]/../../input") // input[@id='96:1476;a']
	private WebElement txtEmplyees;

	@FindBy(xpath = "//div/label//span[contains(text(),'Annual Revenue')]/../../input") // input[@id='96:1476;a']
	private WebElement txtAnnlRevnue;

	@FindBy(xpath = "//div/label//span[contains(text(),'SIC Code')]/../../input") // input[@id='96:1476;a']
	private WebElement txtSICCode;

	// Address Information
	@FindBy(xpath = "//span[contains(text(),'Order Number')]/../..//input")
	private WebElement txtOrdNm;

	@FindBy(xpath = "//span[contains(text(),'Main Competitor(s)')]/../..//input")
	private WebElement txtMainCmptr;

	@FindBy(xpath = "//span[contains(text(),'Current Generator(s)')]/../..//input")
	private WebElement txtCrntGnrtr;

	@FindBy(xpath = "//span[contains(text(),'Delivery/Installation Status')]/../..//a")
	private WebElement drpDlInSts;

	@FindBy(xpath = "//span[contains(text(),'Tracking Number')]/../..//input")
	private WebElement txtTkNmbr;

	// Descriptive Information

	@FindBy(xpath = "//div/label//span[contains(text(),'Description')]/../../textarea") // input[@id='96:1476;a']
	private WebElement txtDescptn;

	// Buttons

	@FindBy(xpath = "//div[@class='inlineFooter']//button[@title='Save']") // input[@id='96:1476;a']
	private WebElement btnSave;

	@FindBy(xpath = "//div[@class='inlineFooter']//button[@title='Save & New']") // input[@id='96:1476;a']
	private WebElement btnSaveAndNew;

	@FindBy(xpath = "//div[@class='inlineFooter']//button[@title='Cancel']") // input[@id='96:1476;a']
	private WebElement btnCancel;


	@FindBy(xpath = "//table[@role='grid']")
	private WebElement tblAccts;

	// Success Message

	@FindBy(xpath = "//span[contains(@class,'toastMessage')]")
	private WebElement txtSuccess;

	public SFDCOpportunitiesPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickNewOpptnts() {
		WebHandlers.click(btnNew);
	}

	public void enterAcctInfo(Map<String, String> fSFDCRow) {

		WebHandlers.enterText(txtAmount, fSFDCRow.get("Amount"));
		if (fSFDCRow.get("Private").equalsIgnoreCase("Yes")) {
			WebHandlers.click(chkPrvte);
		}
		WebHandlers.enterText(txtClseDt, fSFDCRow.get("Close Date"));
		WebHandlers.enterText(txtOpptName, fSFDCRow.get("Opportunity Name"));
		WebHandlers.enterText(txtNxtStp, fSFDCRow.get("Next Step"));
		WebHandlers.divListBox(txtAcctName, fSFDCRow.get("Account Name"));
		WebHandlers.divLinkListBox(drpStage, fSFDCRow.get("Stage"));
		WebHandlers.divLinkListBox(drpType, fSFDCRow.get("Type"));
		//WebHandlers.enterText(txtPrblty, fSFDCRow.get("Probability"));
		WebHandlers.divLinkListBox(drpLeadSrc, fSFDCRow.get("Lead Source"));
		//WebHandlers.divLinkListBox(txtPrmCmpgnSrc,fSFDCRow.get("Primary Campaign Source"));
	}

	public void enterAddrInfo(Map<String, String> fSFDCRow) {
		WebHandlers.enterText(txtOrdNm, fSFDCRow.get("Order Number"));
		WebHandlers.enterText(txtMainCmptr, fSFDCRow.get("Main Competitor"));
		WebHandlers.enterText(txtCrntGnrtr, fSFDCRow.get("Current Generator"));
		WebHandlers.divLinkListBox(drpDlInSts, fSFDCRow.get("Delivery Installation Status"));
		WebHandlers.enterText(txtTkNmbr, fSFDCRow.get("Tracking Number"));
	}

	public void enterDescInfo(Map<String, String> fSFDCRow) {
		WebHandlers.enterText(txtDescptn, fSFDCRow.get("Description"));
	}

	public void clickSave() {
		WebHandlers.click(btnSave);
	}

	public void vfyOpptntCreatn(Map<String, String> fSFDCRow) throws InterruptedException {
		boolean flag=WebHandlers.vfySuccsMsg(txtSuccess);
		Thread.sleep(4000);
		if(flag==true) {
			System.out.println("ToastMessage");
			LOGGER.info("Opportunity " + fSFDCRow.get("Opportunity Name") + " was created successfully");
		}
		/*
		boolean vfyFlag = WebHandlers.vfySFDCTblThVal(tblAccts, fSFDCRow.get("Opportunity Name"));
		if (vfyFlag == true) {
			LOGGER.info("Opportunity " + fSFDCRow.get("Opportunity Name") + " was created successfully");
		}*/
	}
}
