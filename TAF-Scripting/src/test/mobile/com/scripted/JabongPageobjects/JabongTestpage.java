package com.scripted.JabongPageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.scripted.mobile.MobileHandlers;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class JabongTestpage {
AndroidDriver<WebElement> androidDriver ;
	
	@AndroidFindBy(id = "com.jabong.android:id/tab_textview")
	private AndroidElement meIcon;
	
	@AndroidFindBy(id="com.jabong.android:id/me_login_btn")
	private AndroidElement loginBTN;
	
	@AndroidFindBy(id="com.jabong.android:id/floating_label_edit_text")
	private AndroidElement emailId_textBox;

	public JabongTestpage(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}
	
	public void clickLoginBTN() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MobileHandlers.click(loginBTN);
	}
	
	
	public void enterText(String value) throws InterruptedException {
		MobileHandlers.enterText(emailId_textBox, value);
	}
	
}
