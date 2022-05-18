package com.scripted.web;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.scripted.dataload.PropertyDriver;

public class PCloudyWebDriver {
	public static WebDriver driver;
	public static String strBrowserName = null;
	public static String strBrowserVersion = null;
	public static String strPlatform = null;
	public static String strPlatformVersion = null;
	public static String strClientName = null;
	public static String strPcloudyAccessKey = null;
	public static String strPcloudyURL = null;
	public static String strEmail = null;
	public static DesiredCapabilities dcap;

	public static Logger LOGGER = Logger.getLogger(PCloudyWebDriver.class);

	public static WebDriver funcPcloudyWebdriver() {
		try {
			PropertyDriver pcWebConfig = new PropertyDriver();
			pcWebConfig.setPropFilePath("src/main/resources/Mobile/pCloudy/Properties/pCloudyWeb.properties");
			strBrowserName = pcWebConfig.readProp("browserName");
			strPlatform = pcWebConfig.readProp("os");
			strPlatformVersion = pcWebConfig.readProp("osversion");
			strBrowserVersion = pcWebConfig.readProp("browserversion");
			strClientName = pcWebConfig.readProp("Clientname");
			strPcloudyAccessKey = pcWebConfig.readProp("apiKey");
			strEmail = pcWebConfig.readProp("Email");
			strPcloudyURL = pcWebConfig.readProp("PcloudyURL");
			/*if (!(pcWebConfig.readProp("httpsproxyHost").isEmpty()) && !(pcWebConfig.readProp("httpsproxyPort").isEmpty())) {
				System.setProperty("https.proxyHost", pcWebConfig.readProp("httpsproxyHost"));
				System.setProperty("https.proxyPort", pcWebConfig.readProp("httpsproxyPort"));
			}*/

			if (strBrowserName == null || strBrowserName.equals(" ")) {
				LOGGER.info("Browser name is null, please check the value of browserName in config.properties");
				System.exit(0);
			}else {
				LOGGER.info("Browser : " + strBrowserName);
				if (strBrowserName.equals("chrome")) {
					dcap = DesiredCapabilities.chrome();
				} else if (strBrowserName.equals("ie")) {
					dcap = DesiredCapabilities.internetExplorer();
				} else if (strBrowserName.equals("firefox")) {
					dcap = DesiredCapabilities.firefox();
				} else if (strBrowserName.equals("edge")) {
					dcap = DesiredCapabilities.edge();
				}
				dcap.setCapability("os", strPlatform);
				dcap.setCapability("osVersion", strPlatformVersion);
				dcap.setCapability("browserVersion", strBrowserVersion);
				dcap.setCapability("clientName", strClientName);
				dcap.setCapability("apiKey", strPcloudyAccessKey);
				dcap.setCapability("email", strEmail);
				driver = new RemoteWebDriver(new URL(strPcloudyURL), dcap);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while configuring webdrivers" + "Exception :" + e);
			Assert.fail("Webdriver initialisation issues" + "Exception :" + e);
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		BrowserDriver.driver = driver;
		return driver;
	}
}
