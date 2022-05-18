package com.scripted.web;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.scripted.dataload.PropertyDriver;

public class CreateWebDriver {
	
	public static String BrowserDriver = null;
	public static WebDriver driver = null;
	public static String strBrowserName = null;
	private static final Logger log = Logger.getLogger(BrowserDriver.class);

	public static WebDriver funcGetWebdriver() {
		try {
			PropertyDriver p = new PropertyDriver();
			if (driver == null) {
				p.setPropFilePath("src/main/resources/properties/browserconfig.properties");
				strBrowserName = p.readProp("browserName");
			}
			if (strBrowserName == null || strBrowserName.equals(" ")) {
				log.info("Browser name is null, please check the value of browserName in config.properties");
				System.exit(0);
			} else {
				log.info("Browser : " + strBrowserName);
				strBrowserName = strBrowserName.toLowerCase();

				switch (strBrowserName) {

				case "chrome":
					ChromeSettings chromeSettings = new ChromeSettings();
					driver = new ChromeDriver(chromeSettings.setBychromeOptions(p.getFilePath()));
					break;

				case "ie":
					IExplorerSettings iExplorerSettings = new IExplorerSettings();
					driver = new InternetExplorerDriver(iExplorerSettings.setByIExplorerOptions(p.getFilePath()));
					break;

				case "firefox":
					FireFoxSettings fireFoxSettings = new FireFoxSettings();
					driver = new FirefoxDriver(fireFoxSettings.setByFirefoxOptions(p.getFilePath()));
					break;

				case "phantom":
					
					PhatomJSSettings phatomJSSettings = new PhatomJSSettings();
					driver = new PhantomJSDriver(phatomJSSettings.setByPhatomJSSettings(p.getFilePath()));
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}

	public static void launchWebURL(String strURL) {
		driver.get(strURL);
		pageWait();
		log.info("Application launched successfully");
	}

	public static void closeBrowser() {
		try {
			driver.close();
			driver = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void pageWait() {
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
}
