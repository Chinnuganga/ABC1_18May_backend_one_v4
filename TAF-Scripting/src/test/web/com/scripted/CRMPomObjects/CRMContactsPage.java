package com.scripted.CRMPomObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;

public class CRMContactsPage {
	WebDriver driver;

	@FindBy(xpath = "//input[@name='last_name']")
	private WebElement Lname;
	@FindBy(xpath = "//input[@name='first_name']")
	private WebElement Fname;
	@FindBy(xpath = "//div[@name='company']//input[@class='search']")
	private WebElement Comname;
	@FindBy(xpath = "//input[@name='day']")
	private WebElement Bdate;
	@FindBy(xpath = "//span[text()='May']")
	private WebElement Bmonth;
	@FindBy(xpath = "//input[@name='year']")
	private WebElement Byear;
	@FindBy(xpath = "//i[@class='save icon']")
	private WebElement savebtn;
	@FindBy(xpath = "//i[@class='edit icon']")
	private WebElement newbtn;
	@FindBy(xpath = "//div[@name='timezone']/input")
	private WebElement tzone;
	@FindBy(xpath = "//div[@name='month']")
	private WebElement bmonth;
	@FindBy(xpath = "//i[@class='trash icon']")
	private WebElement delbtn;
	@FindBy(xpath = "//i[@class='remove icon']")
	private WebElement okbtn;
	@FindBy(xpath = "//div[@id='top-header-menu']//div[@class='ui buttons']/div")
	private WebElement settingicn;
	@FindBy(xpath = "//span[text()='Log Out']")
	private WebElement Logout;
	@FindBy(xpath = "//span[contains(text(),'Contacts')]")
	private WebElement contacts;
	@FindBy(xpath = ".//div[contains(@class,'main-content')]//table[@class='ui celled sortable striped table custom-grid table-scroll']")
	private WebElement table;
	@FindBy(xpath = "//div[@name='source']")
	private WebElement source;
	@FindBy(xpath = "//div[@name='category']")
	private WebElement category;
	@FindBy(xpath = "//div[@name='status']")
	private WebElement status;
	@FindBy(xpath = "//div[@class='ui toggle checkbox']//input[@name='do_not_call']")
	private WebElement radio1;
	@FindBy(xpath = "//div[@class='ui toggle checkbox']//input[@name='do_not_email']")
	private WebElement radio2;
	@FindBy(xpath = "//button[@class='ui button']")
	private WebElement dltbtn;

	public CRMContactsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterPersonalDetails(String fname, String lname, String cname, String timezone)
			throws InterruptedException {
		WebHandlers.click(newbtn);
		WebHandlers.enterText(Fname, fname);
		WebHandlers.enterText(Lname, lname);
		WebHandlers.enterText(Comname, cname);
		Comname.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		WebHandlers.enterText(tzone, timezone);
		// WebHandlers.click(radio1);
		// WebHandlers.click(radio2);

	}

	public void clickContacts() {
		WebHandlers.click(contacts);
	}

	public void enterBOD(String date, String month, String year) {
		WebHandlers.enterText(Bdate, date);
		WebHandlers.divSpanListBox(bmonth, month);
		WebHandlers.enterText(Byear, year);
	}

	public void clickSaveBtn() {
		WebHandlers.click(savebtn);
	}

	public void deleteRecord(String strValue) throws InterruptedException {
		WebHandlers.click(contacts);
		Thread.sleep(3000);
		WebHandlers.TblCelChkboxClick(table, strValue, 1);
		TblCelEleClick(table, strValue, "Delete");
	}

	public void TblCelEleClick(WebElement objTable, String tableVal, String objectName) {

		try {
			By byEle = WebHandlers.webElementToBy(objTable);
			String strEle = byEle.toString();
			String[] splitStrEle = strEle.split(":");
			String tblXpath = splitStrEle[1];
			String index = WebHandlers.getFirstIndexofVal(objTable, tableVal);
			String[] arrSplit = index.split(",");

			if (objectName.equalsIgnoreCase("Delete")) {
				driver.findElement(By.xpath(
						tblXpath + "//tr[" + arrSplit[0] + "]//td[7]//div//button[@class='ui icon inverted button']"))
						.click();
				WebWaitHelper.waitForElementPresence(dltbtn);
				WebHandlers.click(dltbtn);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void logout() {
		WebHandlers.click(settingicn);
		WebHandlers.click(Logout);
	}

	public void headerVal() {
		System.out.println(WebHandlers.getTblHeaderVal(table));
	}

	public void BodyVal() {
		System.out.println(WebHandlers.getTblBodyVal(table));
	}

	public void tdValue() {
		System.out.println(WebHandlers.getTblTdVal(table, 2, 1));
	}

	public void thValue() {
		System.out.println(WebHandlers.getTblThVal(table, 0, 3));
	}

	public void inxValue() {
		System.out.println(WebHandlers.getIndexofVal(table, "Shamir Ahamed"));
	}

	public void colmapHdrValue(String str) {
		System.out.println(WebHandlers.getColMapByHdrVal(table, str));
	}

	public void rowmapHdrValue(int rowHeader) {
		System.out.println(WebHandlers.getRowMapByIndxVal(table, rowHeader));
	}

	public void ColMapByIndxVal() {
		System.out.println(WebHandlers.getColMapByIndxVal(table, 2));
	}

	public void RowMapByIndxVal() {
		System.out.println(WebHandlers.getRowMapByIndxVal(table, 2));
	}

	public void tblLink(String str) {
		WebHandlers.TblCelLinkClick(table, str);
	}

	public void selectListBoxes(String src, String cat, String stat) {
		WebHandlers.divSpanListBox(source, src);
		WebHandlers.divSpanListBox(category, cat);
		WebHandlers.divSpanListBox(status, stat);
	}

}
