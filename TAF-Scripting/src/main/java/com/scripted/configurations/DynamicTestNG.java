package com.scripted.configurations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.scripted.api.RestAssuredWrapper;

public class DynamicTestNG {
	public static LinkedHashMap<String, String> classMap = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> testMap = new LinkedHashMap<String, String>();
	public static Logger LOGGER = Logger.getLogger(RestAssuredWrapper.class);

	public static void setTestNGClassNames() {
		try {
			/*
			 * classMap.put("1", "com.scripted.testscripts.ExcelDataValidate");
			 * classMap.put("2", "com.scripted.runners.WebTestNGRunner"); classMap.put("3",
			 * "com.scripted.runners.APITestNGRunner");
			 */
			classMap.put("1", "com.scripted.runners.APITestNGRunner");
			testMap.put("1", "API Test");
			/*classMap.put("2", "com.scripted.runners.WebTestNGRunner");
			testMap.put("2", "Web Test");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void genExceTestNG() {
		try {
			TestNG myTestNG = new TestNG();
			XmlSuite mySuite = new XmlSuite();
			String testName = null;
			mySuite.setName("SkriptMate Automation Suite");
			mySuite.setThreadCount(1);
			List<XmlTest> myTests = new ArrayList<XmlTest>();
			XmlTest myTest;
			List<XmlClass> myClasses = null;

			for (int i = 1; i <= testMap.size(); i++) {
				myTest = new XmlTest(mySuite);
				myClasses = new ArrayList<XmlClass>();
				System.out.println(testMap.get(Integer.toString(i)));
				myTest.setName(testMap.get(Integer.toString(i)));

				System.out.println(classMap.get(Integer.toString(i)));
				myClasses.add(new XmlClass(classMap.get(Integer.toString(i))));
				myTest.setXmlClasses(myClasses);
				myTests.add(myTest);
			}

			mySuite.setTests(myTests);
			List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
			mySuites.add(mySuite);
			myTestNG.setXmlSuites(mySuites);
			System.out.println(mySuite.toXml().toString());
			
			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
			System.out.println("*                                                                         *");
			System.out.println("*   \t\t\tTotal " + myTests.size() + " tests selected  \t                  *");
			System.out.println("*                                                                         *");
			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

			myTestNG.run();
			
			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
			System.out.println("*                                                                         *");
			System.out.println("*                   \tTest Execution completed\t                  *");
			System.out.println("*                                                                         *");
			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			LOGGER.info(e);
		}
	}
}
