package com.scripted;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class VSCodeTesting {

	public static void main(String[] args) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("name", "VSCodeWebTesting");
		capabilities.setCapability("build", "spvust");
		RemoteWebDriver  driver = null ;
		try {
					driver = new RemoteWebDriver(new URL("https://zalenium.workaround.gq/wd/hub"),
					capabilities);	
		} catch (Exception e) {
			System.out.println("Could not connect to zalenium");
		}
		
		driver.get("http://automationpractice.com/");
		driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		driver.quit();
		System.out.println("Test Completed");
	}

}
