package com.scripted.aksObj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;

public class AlbertsonShoppingPage {
	
	WebDriver driver;
	
	@FindBy(xpath = "//*[@id=\"static-aisles\"]/div[1]/div/div")
	public WebElement FuitsnVeggies;

	
	@FindBy(xpath="/html/body/div[2]/div/div/div[1]/div/div/div/div[2]/div[2]/div[1]/div/div/div[3]/ul/li[1]/a/span[2]")
	public WebElement SignUp;
	
	
	@FindBy(xpath="//*[@id=\"sign-in-modal-link\"]")
	public WebElement SignInMain;

	@FindBy(xpath="//*[@id=\"onboardingCloseButton\"]")
	public WebElement CloseBtn;
	
	//*[@id="onboardingCloseButton"]
	
	@FindBy(xpath="//*[@id=\"pg184060007Img\"]/img")
	public WebElement Banana;
	
	//*[@id="pg184060007Img"]/img
	
	@FindBy(id="addButton")
	public WebElement AddButton;
	
	//*[@id="addButton"]
	
	@FindBy(id="label-email")
	public WebElement email;
	
	@FindBy(id="label-password")
	public WebElement password;
	
	@FindBy(id="btnSignIn")
	public WebElement SignIn;
			
	@FindBy(id="abCartMini")
	public WebElement Cart;
	
	//*[@id="abCartMini"]
	
	@FindBy(id="mc184060007Title")
	public WebElement CartItem;
	
	@FindBy(id="miniCartClose")
	public WebElement miniCartClose;
	
	//*[@id="abCartMini"]

	@FindBy(xpath="//*[@id=\"miniCart\"]/div/div[2]/div[1]/div[2]/div[3]/button")
	public WebElement RemoveItem;
	
	public void ValidateShopping(String UserName,String Password) throws InterruptedException
	{

		
		Thread.sleep(1000);
		WebHandlers.click(CloseBtn);
		WebHandlers.click(SignUp);
		WebHandlers.click(SignInMain);
		Thread.sleep(1000);
		WebHandlers.enterText(email, UserName);
		WebHandlers.enterText(password, Password);
		WebHandlers.click(SignIn);
		BrowserDriver.pageWait();
		Thread.sleep(10000);
	}
}
