package com.scripted.UTSPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.scripted.mobile.MobileHandlers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Uts_Irctc {
	AndroidDriver<WebElement> androidDriver ;
	String packageName = "com.abhiandroid.foodordering";
    String appActivity = "com.abhiandroid.foodordering.Activities.MainActivity";
    
    
	@AndroidFindBy(xpath=".//*[@text='Book Ticket']")
	AndroidElement bookTicket;
	
	@AndroidFindBy(xpath=".//*[@text='Normal Booking']")
	AndroidElement normalBooking;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/btn_book_and_print")
	AndroidElement bookAndPrntPaper;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/Nb_Src_Stn")
	//@AndroidFindBy(xpath=".//*[@text='ADONI - AD']")
	AndroidElement sourceStation;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/Nb_Dstn_Stn")
	AndroidElement destinationStation;
	
	@AndroidFindBy(xpath=".//*[@text='DONE']")
	AndroidElement donBTN;
	
	@AndroidFindBy(xpath=".//*[@resource-id='com.cris.utsmobile:id/linearLayout1' and @bounds='[50,447][1030,594]']")
	AndroidElement viaStation_one;
	
	@AndroidFindBy(xpath=".//*[@resource-id='com.cris.utsmobile:id/linearLayout1' and @bounds='[50,627][1030,774]']")
	AndroidElement viaStation_two;
	
	@AndroidFindBy(xpath=".//*[@text='ONE (1)']")
	AndroidElement nb_Adult;
	
	@AndroidFindBy(xpath=".//*[@text='TWO (2)']")
	AndroidElement nb_Adult_count;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/Nb_Child")
	AndroidElement nb_Child;
	
	@AndroidFindBy(xpath=".//*[@text='ONE (1)']")
	AndroidElement nb_Child_count;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/Nb_traintype")
	AndroidElement trainType;
	
	@AndroidFindBy(xpath=".//*[@text='SUPERFAST (S)']")
	AndroidElement trainType_SF;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/Nb_Get_Fare")
	AndroidElement getFare_OKBTN;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/BT_Done")
	AndroidElement bookTicketBTN;
	
	@AndroidFindBy(xpath=".//*[@text='Mobile Number']")
	AndroidElement userName;
	
	@AndroidFindBy(xpath=".//*[@text='Password']")
	AndroidElement password;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/Login")
	AndroidElement loginBtn;
	
	@AndroidFindBy(id="com.cris.utsmobile:id/menu_item_home")
	AndroidElement homeBtn;
	
	@AndroidFindBy(xpath=".//*[@text='Logout']")
	AndroidElement logOutBtn;
	
	
	
	public Uts_Irctc(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	
	
	public void searchTicket() {
		MobileHandlers.click(bookTicket);
		MobileHandlers.click(normalBooking);
		MobileHandlers.click(bookAndPrntPaper);
	}
	
	public void selectTrain() {
		MobileHandlers.enterText(sourceStation, "ALAPPUZHA - ALLP");
		MobileHandlers.enterText(destinationStation, "THRISUR - TCR");
		MobileHandlers.click(donBTN);
		MobileHandlers.click(donBTN);
		MobileHandlers.click(viaStation_one);
	}
	
	public void passengerDetails() {
	/*if(androidDriver.isKeyboardShown())
			((AppiumDriver)androidDriver).hideKeyboard();
		MobileHandlers.click(nb_Adult);*/
		MobileHandlers.click(nb_Adult_count);
		MobileHandlers.click(nb_Child);
		MobileHandlers.click(nb_Child_count);
		MobileHandlers.click(trainType);
		MobileHandlers.click(trainType_SF);
		MobileHandlers.click(getFare_OKBTN);
	}
	
	
	public void bookTicket() {
		MobileHandlers.click(bookTicketBTN);
	}
	
	public void login() {
		MobileHandlers.enterText(userName, "");
		MobileHandlers.enterText(password, "");
		MobileHandlers.click(loginBtn);
	}
	
	public void verifyText(String expectedValue) {
		String bodyText = androidDriver.findElement(By.tagName("body")).getText();
		Boolean status =bodyText.contains(expectedValue);
	}
	
	public void logout() {
		MobileHandlers.click(homeBtn);
		MobileHandlers.click(logOutBtn);
	}
	
	

}
