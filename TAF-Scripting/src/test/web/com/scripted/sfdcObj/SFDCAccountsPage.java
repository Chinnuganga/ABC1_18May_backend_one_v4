package com.scripted.sfdcObj;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.WebHandlers;

public class SFDCAccountsPage {
	
	WebDriver driver;
	public static Logger LOGGER = Logger.getLogger(SFDCAccountsPage.class);
	
	@FindBy(xpath = "//a[@title='New']")
	private WebElement btnNew;
	
	//Account information

	@FindBy(xpath = "//div/label//span[contains(text(),'Account Name')]/../../input")//input[@id="96:1476;a"]
	private WebElement txtAcctName;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Phone')]/../../input")//input[@id='96:1476;a']
	private WebElement txtPhone;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Fax')]/../../input")//input[@id='96:1476;a']
	private WebElement txtFax;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Account Number')]/../../input")//input[@id='96:1476;a']
	private WebElement txtAcctNmbr;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Website')]/../../input")//input[@id='96:1476;a']
	private WebElement txtWebsite;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Account Site')]/../../input")//input[@id='96:1476;a']
	private WebElement txtAcctSite;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Ticker Symbol')]/../../input")//input[@id='96:1476;a']
	private WebElement txtAcctTkrSymbl;
	
	@FindBy(xpath = "//span[contains(text(),'Type')]/../..//a/..")//input[@id='96:1476;a']
	private WebElement drpType;//*[@id="308:1476;a"]/div/div/a
	//span[contains(text(),'Type')]/../..//a
	
	@FindBy(xpath = "//span[contains(text(),'Ownership')]/../..//a")//input[@id='96:1476;a']
	private WebElement drpOwnrshp;//*[@id="308:1476;a"]/div/div/a
	
	@FindBy(xpath = "//span[contains(text(),'Industry')]/../..//a")//input[@id='96:1476;a']
	private WebElement drpIndstry;//*[@id="308:1476;a"]/div/div/a
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Employees')]/../../input")//input[@id='96:1476;a']
	private WebElement txtEmplyees;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Annual Revenue')]/../../input")//input[@id='96:1476;a']
	private WebElement txtAnnlRevnue;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'SIC Code')]/../../input")//input[@id='96:1476;a']
	private WebElement txtSICCode;
	
	//Address Information	
	@FindBy(xpath = "//div/label//span[contains(text(),'Billing Street')]/../../textarea")//input[@id='96:1476;a']
	private WebElement txtBillAddr;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Shipping Street')]/../../textarea")//input[@id='96:1476;a']
	private WebElement txtShipAddr;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Billing City')]/../../input")//input[@id='96:1476;a']
	private WebElement txtBillCty;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Billing State')]/../../input")//input[@id='96:1476;a']
	private WebElement txtBillState;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Shipping City')]/../../input")//input[@id='96:1476;a']
	private WebElement txtShipCty;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Shipping State')]/../../input")//input[@id='96:1476;a']
	private WebElement txtShipState;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Billing Zip')]/../../input")//input[@id='96:1476;a']
	private WebElement txtBillZip;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Billing Country')]/../../input")//input[@id='96:1476;a']
	private WebElement txtBillCtry;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Shipping Zip')]/../../input")//input[@id='96:1476;a']
	private WebElement txtShpZip;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Shipping Country')]/../../input")//input[@id='96:1476;a']
	private WebElement txtShpCtry;
	
	//Additional Information
	
	@FindBy(xpath = "//span[contains(text(),'Customer Priority')]/../..//a")//input[@id='96:1476;a']
	private WebElement drpCustPrty;//*[@id="308:1476;a"]/div/div/a
		
	@FindBy(xpath = "//span[contains(text(),'SLA')]/../..//a")//input[@id='96:1476;a']
	private WebElement drpSLA;//*[@id="308:1476;a"]/div/div/a
	
	@FindBy(xpath = "//span[contains(text(),'SLA Expiration Date')]/../..//input")//input[@id='96:1476;a']
	private WebElement txtSLAExpDt;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'SLA Serial Number')]/../../input")//input[@id='96:1476;a']
	private WebElement txtSLASNo;
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Number of Locations')]/../../input")//input[@id='96:1476;a']
	private WebElement txtNoOfLoc;
	
	@FindBy(xpath = "//span[contains(text(),'Upsell Opportunity')]/../..//a")//input[@id='96:1476;a']
	private WebElement drpUpslOppty;//*[@id="308:1476;a"]/div/div/a
	
	@FindBy(xpath = "//span[contains(text(),'Active')]/../..//a")//input[@id='96:1476;a']
	private WebElement drpActive;//*[@id="308:1476;a"]/div/div/a

	//Descriptive Information
	
	@FindBy(xpath = "//div/label//span[contains(text(),'Description')]/../../textarea")//input[@id='96:1476;a']
	private WebElement txtDescptn;
	
	//Buttons
	
	@FindBy(xpath = "//div[@class='inlineFooter']//button[@title='Save']") // input[@id='96:1476;a']
	private WebElement btnSave;

	@FindBy(xpath = "//div[@class='inlineFooter']//button[@title='Save & New']") // input[@id='96:1476;a']
	private WebElement btnSaveAndNew;

	@FindBy(xpath = "//div[@class='inlineFooter']//button[@title='Cancel']") // input[@id='96:1476;a']
	private WebElement btnCancel;
	
	@FindBy(xpath = "//table[@role='grid']")
	private WebElement tblAccts;
	
	//Success Message 
	
	@FindBy(xpath = "//span[contains(@class,'toastMessage')]")
	private WebElement txtSuccess;
	
	public SFDCAccountsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickNewAccnt() {
		WebHandlers.click(btnNew);
	}
	
	public void enterAcctInfo(Map<String, String> fSFDCRow) {
		
		WebHandlers.enterText(txtAcctName, fSFDCRow.get("AccountName"));
		WebHandlers.enterText(txtPhone, fSFDCRow.get("Phone"));
		WebHandlers.enterText(txtFax, fSFDCRow.get("Fax"));
		WebHandlers.enterText(txtWebsite, fSFDCRow.get("Website"));
		WebHandlers.enterText(txtAcctSite, fSFDCRow.get("AccountSite"));
		WebHandlers.enterText(txtAcctTkrSymbl, fSFDCRow.get("TickerSymbol"));
		WebHandlers.divLinkListBox(drpType, fSFDCRow.get("Type"));
		WebHandlers.divLinkListBox(drpOwnrshp, fSFDCRow.get("Ownsership"));
		WebHandlers.divLinkListBox(drpIndstry, fSFDCRow.get("Industry"));
		WebHandlers.enterText(txtEmplyees, fSFDCRow.get("Employees"));
		WebHandlers.enterText(txtAnnlRevnue, fSFDCRow.get("AnnualRevenue"));
		WebHandlers.enterText(txtSICCode, fSFDCRow.get("SICCode"));
	}
	
	public void enterAddrInfo(Map<String, String> fSFDCRow) {
		WebHandlers.enterText(txtBillAddr, fSFDCRow.get("BillingStreet"));
		WebHandlers.enterText(txtShipAddr, fSFDCRow.get("ShippingStreet"));
		WebHandlers.enterText(txtBillCty, fSFDCRow.get("BillingCity"));
		WebHandlers.enterText(txtBillState, fSFDCRow.get("BillingState"));
		WebHandlers.enterText(txtShipCty, fSFDCRow.get("ShippingCity"));
		WebHandlers.enterText(txtShipState, fSFDCRow.get("ShippingState"));
		WebHandlers.enterText(txtBillZip, fSFDCRow.get("BillingZip"));
		WebHandlers.enterText(txtBillCtry, fSFDCRow.get("BillingCountry"));
		WebHandlers.enterText(txtShpZip, fSFDCRow.get("ShippingZip"));
		WebHandlers.enterText(txtShpCtry, fSFDCRow.get("ShippingCountry"));
	}
	
	public void enterAddtnlInfo(Map<String, String> fSFDCRow) {
		WebHandlers.divLinkListBox(drpCustPrty, fSFDCRow.get("CustomerPriority"));
		WebHandlers.divLinkListBox(drpSLA, fSFDCRow.get("SLA"));
		WebHandlers.divLinkListBox(drpUpslOppty, fSFDCRow.get("UpsellOpprtnty"));
		WebHandlers.enterText(txtSLAExpDt, fSFDCRow.get("SLAExpDt"));
		WebHandlers.enterText(txtSLASNo, fSFDCRow.get("SLASNo"));
		WebHandlers.enterText(txtNoOfLoc, fSFDCRow.get("NoOfLoc"));
		WebHandlers.divLinkListBox(drpActive, fSFDCRow.get("Active"));
		WebHandlers.enterText(txtDescptn, fSFDCRow.get("Description"));
	}
	
	public void clickSave() {
		WebHandlers.click(btnSave);
	}
	
	public void vfyAcctCreatn(Map<String, String> fSFDCRow) throws InterruptedException {
		/*boolean vfyFlag = WebHandlers.vfySFDCTblThVal(tblAccts, fSFDCRow.get("AccountName"));
		if(vfyFlag==true) {
			LOGGER.info("Account " + fSFDCRow.get("AccountName") + " was created successfully");
		}*/
		
		boolean flag=WebHandlers.vfySuccsMsg(txtSuccess);
		Thread.sleep(4000);
		if(flag==true) {
			LOGGER.info("Account " + fSFDCRow.get("AccountName") + " was created successfully");
		}

	}
}
