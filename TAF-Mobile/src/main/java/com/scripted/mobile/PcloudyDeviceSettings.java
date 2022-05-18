package com.scripted.mobile;

import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableMap;
import com.ssts.pcloudy.appium.PCloudyAppiumSession;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class PcloudyDeviceSettings {

	public static Logger LOGGER = Logger.getLogger(PcloudyDeviceSettings.class);
	public static AndroidDriver<WebElement> androidDriver = null;
	public static IOSDriver<WebElement> iosDriver = null;
	public static PCloudyAppiumSession pCloudySession;
	private String proxyHost;
	private String proxyPort;
	private String pCloudyHubURL = "https://device.pcloudy.com/appiumcloud/wd/hub";
	DesiredCapabilities capability = null;

	public static ThreadLocal<AndroidDriver<WebElement>> thDriver = new ThreadLocal<AndroidDriver<WebElement>>();

	public static ThreadLocal<AndroidDriver<WebElement>> getThDriver() {
		return thDriver;
	}

	public static void setThDriver(ThreadLocal<AndroidDriver<WebElement>> thDriver) {
		PcloudyDeviceSettings.thDriver = thDriver;
	}

	public PcloudyDeviceSettings() {
		this.capability = new DesiredCapabilities();
	}

	public AndroidDriver<WebElement> getConnectionPcloudyAndroidNativeApp(Properties mobConfigProp) {
		try {
			if (mobConfigProp.getProperty("https.proxyHost") != null
					&& mobConfigProp.getProperty("https.proxyPort") != null) {
				proxyHost = mobConfigProp.getProperty("https.proxyHost");
				proxyPort = mobConfigProp.getProperty("https.proxyPort");

				// Override SSL certificate for pCloudy
				getRestTemplate();
			}
			capability.setCapability("pCloudy_Username", mobConfigProp.getProperty("Username"));
			capability.setCapability("pCloudy_ApiKey", mobConfigProp.getProperty("ApiKey"));
			capability.setCapability("pCloudy_ApplicationName", mobConfigProp.getProperty("appName"));
			capability.setCapability("pCloudy_DurationInMinutes", mobConfigProp.getProperty("DurationInMinutes"));
			capability.setCapability("pCloudy_DeviceManafacturer", mobConfigProp.getProperty("DeviceManafacturer"));
			capability.setCapability("pCloudy_DeviceVersion", mobConfigProp.getProperty("DeviceVersion"));
			capability.setCapability("pCloudy_DeviceFullName", mobConfigProp.getProperty("DeviceFullName"));
			capability.setCapability("newCommandTimeout", mobConfigProp.getProperty("newCommandTimeout"));
			capability.setCapability("launchTimeout", mobConfigProp.getProperty("launchTimeout"));
			capability.setCapability("appPackage", mobConfigProp.getProperty("appPackage"));
			capability.setCapability("appActivity", mobConfigProp.getProperty("appActivity"));
			capability.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			capability.setCapability("automationName", "UiAutomator2");
			capability.setCapability("pCloudy_WildNet", "false");
			capability.setBrowserName("");
			androidDriver = new AndroidDriver<WebElement>(new URL(pCloudyHubURL), capability);
			androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return androidDriver;
	}

	public AndroidDriver<WebElement> getConnectionPcloudyAndroidWeb(Properties mobConfigProp) {
		try {
			if (mobConfigProp.getProperty("https.proxyHost") != null
					&& mobConfigProp.getProperty("https.proxyPort") != null) {
				proxyHost = mobConfigProp.getProperty("https.proxyHost");
				proxyPort = mobConfigProp.getProperty("https.proxyPort");

				// Override SSL certificate for pCloudy
				getRestTemplate();
			}
			capability.setCapability("pCloudy_Username", mobConfigProp.getProperty("Username"));
			capability.setCapability("pCloudy_ApiKey", mobConfigProp.getProperty("ApiKey"));
			capability.setCapability("pCloudy_DurationInMinutes", mobConfigProp.getProperty("DurationInMinutes"));
			capability.setCapability("pCloudy_DeviceManafacturer", mobConfigProp.getProperty("DeviceManafacturer"));
			capability.setCapability("pCloudy_DeviceVersion", mobConfigProp.getProperty("DeviceVersion"));
			capability.setCapability("pCloudy_DeviceFullName", mobConfigProp.getProperty("DeviceFullName"));
			capability.setCapability("newCommandTimeout", mobConfigProp.getProperty("newCommandTimeout"));
			capability.setCapability("launchTimeout", mobConfigProp.getProperty("launchTimeout"));
			capability.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			capability.setCapability("automationName", "UiAutomator2");
			capability.setCapability("pCloudy_WildNet", "false");
			capability.setBrowserName("Chrome");
			androidDriver = new AndroidDriver<WebElement>(new URL(pCloudyHubURL), capability);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return androidDriver;
	}

	public ThreadLocal<AndroidDriver<WebElement>> getConnectionPcloudyAndroidWebTh(String mobConfigPropf) {
		Properties mobConfigProp = new Properties();
		try {
			FileReader reader = new FileReader(
					"src/main/resources/Mobile/pCloudy/Properties/" + mobConfigPropf + ".properties");
			mobConfigProp.load(reader);
			if (mobConfigProp.getProperty("https.proxyHost") != null
					&& mobConfigProp.getProperty("https.proxyPort") != null) {
				proxyHost = mobConfigProp.getProperty("https.proxyHost");
				proxyPort = mobConfigProp.getProperty("https.proxyPort");

				// Override SSL certificate for pCloudy
				getRestTemplate();
			}

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("pCloudy_Username", mobConfigProp.getProperty("Username"));
			capabilities.setCapability("pCloudy_ApiKey", mobConfigProp.getProperty("ApiKey"));
			capabilities.setCapability("pCloudy_DurationInMinutes", mobConfigProp.getProperty("DurationInMinutes"));
			capabilities.setCapability("newCommandTimeout",
					Integer.parseInt(mobConfigProp.getProperty("newCommandTimeout")));
			capabilities.setCapability("launchTimeout", Integer.parseInt(mobConfigProp.getProperty("launchTimeout")));
			capabilities.setCapability("pCloudy_DeviceManafacturer", mobConfigProp.getProperty("DeviceManafacturer"));
			capabilities.setCapability("pCloudy_DeviceVersion", mobConfigProp.getProperty("DeviceVersion"));
			capabilities.setCapability("platformVersion", mobConfigProp.getProperty("platformVersion"));
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("automationName", "uiautomator2");
			capabilities.setBrowserName("Chrome");
			capabilities.setCapability("pCloudy_WildNet", "false");
			capabilities.setCapability("pCloudy_EnableVideo", "false");
			capabilities.setCapability("pCloudy_EnablePerformanceData", "false");
			capabilities.setCapability("pCloudy_EnableDeviceLogs", "true");
			capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			thDriver.set(new AndroidDriver<WebElement>(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"),
					capabilities));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return thDriver;
	}

	public IOSDriver<WebElement> getConnectionPcloudyIOSNativeApp(Properties mobConfigProp) {
		try {
			if (mobConfigProp.getProperty("https.proxyHost") != null
					&& mobConfigProp.getProperty("https.proxyPort") != null) {
				proxyHost = mobConfigProp.getProperty("https.proxyHost");
				proxyPort = mobConfigProp.getProperty("https.proxyPort");

				// Override SSL certificate for pCloudy
				getRestTemplate();
			}
			capability.setCapability("pCloudy_Username", mobConfigProp.getProperty("Username"));
			capability.setCapability("pCloudy_ApiKey", mobConfigProp.getProperty("ApiKey"));
			capability.setCapability("pCloudy_DurationInMinutes", mobConfigProp.getProperty("DurationInMinutes"));
			capability.setCapability("newCommandTimeout", mobConfigProp.getProperty("newCommandTimeout"));
			capability.setCapability("launchTimeout", mobConfigProp.getProperty("launchTimeout"));
			capability.setCapability("pCloudy_DeviceVersion", mobConfigProp.getProperty("DeviceVersion"));
			capability.setCapability("pCloudy_DeviceManafacturer", "APPLE");
			capability.setCapability("platformVersion", mobConfigProp.getProperty("platformVersion"));
			capability.setCapability("pCloudy_DeviceFullName", mobConfigProp.getProperty("DeviceFullName"));
			capability.setCapability("platformName", "ios");
			capability.setCapability("acceptAlerts", true);
			capability.setCapability("automationName", "XCUITest");
			capability.setCapability("pCloudy_ApplicationName", mobConfigProp.getProperty("appName"));
			capability.setCapability("bundleId", mobConfigProp.getProperty("bundleId"));
			capability.setCapability("pCloudy_WildNet", "false");
			iosDriver = new IOSDriver<WebElement>(new URL(pCloudyHubURL), capability);
			iosDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iosDriver;
	}

	public IOSDriver<WebElement> getConnectionPcloudyIOSWeb(Properties mobConfigProp) {
		try {
			if (mobConfigProp.getProperty("https.proxyHost") != null
					&& mobConfigProp.getProperty("https.proxyPort") != null) {
				proxyHost = mobConfigProp.getProperty("https.proxyHost");
				proxyPort = mobConfigProp.getProperty("https.proxyPort");

				// Override SSL certificate for pCloudy
				getRestTemplate();
			}
			capability.setCapability("pCloudy_Username", mobConfigProp.getProperty("Username"));
			capability.setCapability("pCloudy_ApiKey", mobConfigProp.getProperty("ApiKey"));
			capability.setCapability("pCloudy_DurationInMinutes", mobConfigProp.getProperty("DurationInMinutes"));
			capability.setCapability("newCommandTimeout", mobConfigProp.getProperty("newCommandTimeout"));
			capability.setCapability("launchTimeout", mobConfigProp.getProperty("launchTimeout"));
			capability.setCapability("pCloudy_DeviceVersion", mobConfigProp.getProperty("DeviceVersion"));
			capability.setCapability("pCloudy_DeviceManafacturer", "APPLE");
			capability.setCapability("platformVersion", mobConfigProp.getProperty("platformVersion"));
			capability.setCapability("pCloudy_DeviceFullName", mobConfigProp.getProperty("DeviceFullName"));
			capability.setCapability("platformName", "ios");
			capability.setCapability("acceptAlerts", true);
			capability.setCapability("automationName", "XCUITest");
			capability.setBrowserName(mobConfigProp.getProperty("browserName"));
			capability.setCapability("pCloudy_WildNet", "false");
			iosDriver = new IOSDriver<WebElement>(new URL(pCloudyHubURL), capability);
			iosDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iosDriver;
	}

	public static PCloudyAppiumSession getAppiumPCloudySession() {
		return pCloudySession;
	}

	public DesiredCapabilities getPcloudyCapabilities() {
		return capability;
	}

	private ClientHttpRequestFactory requestFactory() {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		try {
			requestFactory.setHttpClient(getHttpClient());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFactory;
	}

	private HttpClient getHttpClient() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial((chain, authType) -> true).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}

		// {"SSLv2Hello", "SSLv3", "TLSv1","TLSv1.1", "TLSv1.2" }

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,

				new String[] { "TLSv1" }, null, NoopHostnameVerifier.INSTANCE);

		CloseableHttpClient httpClient = HttpClients.custom()

				.setProxy(new HttpHost(proxyHost, Integer.parseInt(proxyPort)))

				.setSSLSocketFactory(sslConnectionSocketFactory).build();

		return httpClient;

	}

	public HttpEntity<?> getHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		try {
			getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			getRestTemplate().getMessageConverters().add(new StringHttpMessageConverter());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate(requestFactory());
		return template;
	}

}
