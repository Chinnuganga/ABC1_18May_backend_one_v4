package com.scripted.zephyr;

import java.io.InputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import junit.framework.Assert;

public class zephyrConfig {

	public static boolean initilizestatus = false;
	public InputStream isConfig;
	public static String curdir;
	public static Configuration config = null;

 static Logger log = Logger.getLogger(zephyrConfig.class);

	static String JIRA_BASEURL = "";
	static String PROJECT_KEY = "";
	static String RELEASE_VERSION = "";
	static String TESTCYCLE_NAME = "";
	static String ZAPI_URI_CONTEXT = "/public/rest/api/1.0/";
	static String JIRA_API_CONTEXT = "/rest/api/2/";
	static String PROJECT_ID = "";
	static String JIRA_AUTHORIZATION = "";
	static String ZAPI_BASEURL = "";
	static String ACCESS_KEY = "";
	static String SECRET_KEY = "";
	static String USER_NAME = "";
	static String VERSION_ID = "";
	static String CYCLE_ID = "";
	static String DEFECT_FLAG = "";
	static String STEP_STATUS_FLAG="";
	static String STEP_SCREENSHOT_FLAG="";

	public zephyrConfig() {

	}

	public static void propSet(boolean intialize) {
		try {
			config = initialize();
			if (config.getString("https.proxyHost") != null && config.getString("https.proxyPort") != null) {
				System.setProperty("https.proxyHost", config.getString("https.proxyHost"));
				System.setProperty("https.proxyPort", config.getString("https.proxyPort"));
			}
			JIRA_BASEURL = config.getString("jira.baseurl");
			PROJECT_KEY = config.getString("project.key");
			RELEASE_VERSION = config.getString("release.version");
			TESTCYCLE_NAME = config.getString("testcycle.name");
			JIRA_AUTHORIZATION = config.getString("jira.authorization");
			JIRA_AUTHORIZATION = "Basic " + JIRA_AUTHORIZATION;
			PROJECT_ID = config.getString("project.id");
			ZAPI_BASEURL = config.getString("zapi.baseurl");
			ACCESS_KEY = config.getString("zapi.accesskey");
			SECRET_KEY = config.getString("zapi.secretkey");
			USER_NAME = config.getString("zapi.userName");
			DEFECT_FLAG= config.getString("zephyr.create.defect");
			STEP_STATUS_FLAG= config.getString("zephyr.step.status");
			STEP_SCREENSHOT_FLAG= config.getString("zephyr.step.attachscreenshot");
			VERSION_ID = JiraAPI.getVersionId();
			CYCLE_ID = ZephyrAPI.getCycleId();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Unable to load properties file"+ex);
			Assert.fail("Unable to load properties file"+ex);
		}
	}

	public static Configuration initialize() {
		curdir = System.getProperty("user.dir");
		System.setProperty("currentDir", curdir);
	//	PropertyConfigurator.configure("src/main/resources/logConf/log4j.properties");
		if (!initilizestatus) {
			log.info("------------------initilizing----------------");
			try {
				ConfigurationFactory factory = new ConfigurationFactory(curdir+"/src/main/resources/Integrations/Jira/Zephyr/Properties/config.xml");
				config = factory.getConfiguration();
			} catch (ConfigurationException e) {
				e.printStackTrace();
				log.error("Error while initialising configuration for Zephyr"+e);
				Assert.fail("Error while initialising configuration for Zephyr"+e);
			}
			initilizestatus = true;
		} else {
			log.info("Initilization is Already Done");
		}
		return config;
	}

	public enum Status {
		passed, failed;
	}

}
