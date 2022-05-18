package com.scripted.Automationpractice;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;
import com.scripted.web.WebHandlers;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APPersonalInformationPageMob {

	AndroidDriver<WebElement> androidDriver ;

	@FindBy(id = "id_gender1")
	private MobileElement socialTileMr;

	@FindBy(id = "id_gender2")
	private MobileElement socialTileMrs;

	@FindBy(id = "firstname")
	private MobileElement firstname;

	@FindBy(id = "lastname")
	private MobileElement lastname;

	@FindBy(id = "email")
	private MobileElement email;

	@FindBy(id = "days")
	private MobileElement day;

	@FindBy(id = "months")
	private MobileElement month;

	@FindBy(id = "years")
	private MobileElement year;
	
	public APPersonalInformationPageMob(MobileDriver<WebElement> androidDriverr) {
		this.androidDriver = (AndroidDriver<WebElement>) androidDriverr;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}


	boolean flag = false;

	public void validatePersonalInfo(String objectName, String strValue) {
		if (objectName.equals("Social title")) {
			if (strValue.equals("Mr.")) {
				//flag = MobileHandlers.radioBtnIsSelected(socialTileMr);
				if (flag == true) {
					System.out.println("Passed");
				}
			}
		}

		if (objectName.equals("First name")) {
			flag = MobileHandlers.verifyText(firstname, strValue);
			if (flag == true) {
				System.out.println("Passed");
			}
		}
		if (objectName.equals("Last name")) {
			flag = MobileHandlers.verifyText(lastname, strValue);
			if (flag == true) {
				System.out.println("Passed");
			}
		}
		if (objectName.equals("E-mail address")) {
			flag = MobileHandlers.verifyText(email, strValue);
			if (flag == true) {
				System.out.println("Passed");
			}
		}
		if (objectName.equals("dobDay")) {
			flag = MobileHandlers.verifyText(day, strValue);
			if (flag == true) {
				System.out.println("Passed");
			}
		}
		if (objectName.equals("dobMonth")) {
			flag = MobileHandlers.verifyText(month, strValue);
			if (flag == true) {
				System.out.println("Passed");
			}
		}
		if (objectName.equals("dobYear")) {
			flag = MobileHandlers.verifyText(year, strValue);
			if (flag == true) {
				System.out.println("Passed");
			}
		}
	}

}
