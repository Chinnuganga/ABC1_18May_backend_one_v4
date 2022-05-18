package com.scripted.mobstepdefs;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.scripted.Allure.AllureListener;
import com.scripted.UTSPageObjects.Uts_Irctc;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Listeners({ AllureListener.class })
public class UTS_IRCTCStepDefinition {
	
	public static AndroidDriver<WebElement> androidDriver;
	Uts_Irctc utsIrcts = null;
	
	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot scrShot = ((TakesScreenshot) androidDriver);
			byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		
	}
	
	@Given("I have IRCTC native application")
    public void i_have_irctc_native_application()  {
		androidDriver=MobileDriverSettings.funcGetNativeAndroiddriver("Irctc");
		utsIrcts = new Uts_Irctc(androidDriver);
		System.out.println("AndroidNative started");
       
    }

    @And("I search for tickets without logging")
    public void i_search_for_tickets_without_logging(){
    	utsIrcts.searchTicket();
       
    }

    @And("I select the train from the search list")
    public void i_select_the_train_from_the_search_list() {
    	utsIrcts.selectTrain();
       
    }

    @And("I enters the passenger booking details")
    public void i_enters_the_passenger_booking_details()  {
    	utsIrcts.passengerDetails();
      
    }
    
    @When("I click on book ticket")
    public void i_click_on_book_ticket()  {
    	utsIrcts.bookTicket();
    
    }

    @Then("I should ask for entering the login details")
    public void i_should_ask_for_entering_the_login_details()  {
    	
        
    }
    
    
    @After("@AndroidNative")
	public void after() {
		MobileDriverSettings.closeDriver();
		System.out.println("AndroidNative completed");
	}
	

}
