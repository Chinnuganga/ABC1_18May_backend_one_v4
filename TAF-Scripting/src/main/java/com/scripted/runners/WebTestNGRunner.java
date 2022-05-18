package com.scripted.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "Features/Web", plugin = {  "json:target/cucumber.json", "com.ust.noskript.cucumber.ScenarioReporter" }, glue = { "com.scripted.webstepdefs" }, monochrome = true, tags = { "@tag" })
public class WebTestNGRunner extends AbstractTestNGCucumberTests {
	/*
	 * @BeforeSuite public void setup() { SkriptmateConfigurations.beforeSuite(); }
	 * 
	 * @AfterSuite public void teardown() { SkriptmateConfigurations.afterSuite(); }
	 */
}
