package com.scripted.mobstepdefs;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.scripted.Allure.AllureListener;
import com.scripted.PCloudySampleApp.PCloudyAndroidNativeApp;
import com.scripted.mobile.MobileDriverSettings;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Listeners({ AllureListener.class })
public class PcloudysampleAppStepdefs {
	
	public static AndroidDriver<WebElement> androidDriver;
	PCloudyAndroidNativeApp pCloudySampleNativeApp;
	
	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot scrShot = ((TakesScreenshot) androidDriver);
			byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		
	}
	@Given("I have pcloudyappiumdemo native application")
	    public void i_have_pcloudyappiumdemo_native_application()  {
		 androidDriver=MobileDriverSettings.funcGetpCloudyNativeAndroiddriver("AndroidNative"); 
		pCloudySampleNativeApp=new PCloudyAndroidNativeApp(androidDriver);
		System.out.println("pcloudynativeApp started");
	    }
	 
	 @And("I have logged into application")
	    public void i_have_logged_into_application()  {
		 pCloudySampleNativeApp.login();
	    }

	    @And("I enters the booking details")
	    public void i_enters_the_booking_details()  {
	    	pCloudySampleNativeApp.bookingDetails();
	    }

	    @When("I click on search flights")
	    public void i_click_on_search_flights()  {
	    	pCloudySampleNativeApp.searchFlights();
	    }

	    @Then("I should see booking confirmation message")
	    public void i_should_see_booking_confirmation_message(){
	    	pCloudySampleNativeApp.assertConfirmMsg("");
	    	MobileDriverSettings.getAndroidDriver().quit();
	    }
	    
	    
	    @After("@pcloudynativeApp")
		public void afterNativeApp() {
	    	MobileDriverSettings.getAndroidDriver().quit();
	    	System.out.println("pcloudynativeApp completed");
}
}
