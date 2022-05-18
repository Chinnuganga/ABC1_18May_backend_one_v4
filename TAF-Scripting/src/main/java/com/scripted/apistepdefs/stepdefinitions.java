package com.scripted.apistepdefs;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.scripted.Allure.AllureListener;
import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.generic.FileUtils;
import com.scripted.roi.ROIExeTime;
import org.json.JSONObject;
import io.cucumber.core.api.Scenario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;

@Listeners({ AllureListener.class })
public class stepdefinitions {
	RequestParams Attwrapper = new RequestParams();
	RestAssuredWrapper raWrapper = new RestAssuredWrapper();
	private Scenario scenario;
	public String setComplexity;
	public Collection<String> tags;
	public String filename;
	PropertyDriver propDriver = new PropertyDriver();

	@Before("@High")
	public void setComplexityHigh(Scenario scenario) {
		this.scenario = scenario;
		setComplexity = "High";

	}

	@Before("@Medium")
	public void setComplexityMedium(Scenario scenario) {
		this.scenario = scenario;
		setComplexity = "Medium";

	}

	@Before("@Low")
	public void setComplexityLow(Scenario scenario) {
		this.scenario = scenario;
		setComplexity = "Low";
	}

	@Before
	public void beforeScenario(Scenario scenario) {
		ROIExeTime.startTime();
		this.scenario = scenario;
		tags = scenario.getSourceTagNames();
		for (String tag : tags) {
			if (tag.contains("data-")) {
				String[] tagSplit = tag.split("data-");
				filename = "./src/main/resources/WebServices/DataFiles/" + tagSplit[1] + ".xlsx";
			}
		}
	}

	@After
	public void afterScenario(Scenario scenario) {
		try {
			byte[] responseData = raWrapper.convertFileToByte();
			scenario.embed(responseData, "text/plain");
			String totalhours = ROIExeTime.endTime();
			// ROIAPIrequest.callROIApi(setComplexity,totalhours);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Given("I have sample Get API")
	public void i_have_sample_get_api() throws Throwable {

	}
	
	@Given("I have sample {string} API")
	public void i_have_sample_api(String apiName) throws Throwable {

	}
	
	@And("I send a {string} Request with {string} and {string}")
	public void  api_json_request_in_something(String strMethod, String strPropFileName,String apijsonRequest) {
		Attwrapper.setJsonbody(apijsonRequest);
		raWrapper.setAPIFileProName(strPropFileName + ".properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendRequest(strMethod, reqSpec);
	}

	@When("I send a {string} Request with {string} properties")
	public void i_send_a_something_request_with_something_properties(String strMethod, String strPropFileName)
			throws Throwable {
		raWrapper.setAPIFileProName(strPropFileName + ".properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendRequest(strMethod, reqSpec);
	}

	@Then("I should get response code {int}")
	public void i_should_get_response_code_something(int strCode) throws Throwable {
		raWrapper.valResponseCode(strCode);
	}

	@And("the response should contain:")
	public void the_response_should_contain(DataTable respTable) throws Throwable {
		List<Map<String, String>> resplist = respTable.asMaps(String.class, String.class);
		for (int i = 0; i < resplist.size(); i++) {
			String jsonPath = resplist.get(i).get("JsonPath");
			String expVal = resplist.get(i).get("ExpectedVal");

			if (expVal.matches("-?(0|[1-9]\\d*)")) {
				int intExpVal = Integer.parseInt(expVal);
				raWrapper.valJsonResponseVal(jsonPath, intExpVal);
			} else {
				raWrapper.valJsonResponseVal(jsonPath, expVal);
			}

		}

	}

	@And("the response should contain expected values in {string}")
	public void the_response_should_contain_expected_values_in_something(String sheetname) throws Throwable {
		String key = "";
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
		for (int i = 1; i < getAllData.size(); i++) {
			Map<String, String> fRow = getAllData.get(String.valueOf(i));
			String jsonPath = fRow.get("JsonPath");
			String expVal = fRow.get("ExpectedVal");
			if (expVal.matches("-?(0|[1-9]\\d*)")) {
				int intExpVal = Integer.parseInt(expVal);
				raWrapper.valJsonResponseVal(jsonPath, intExpVal);
			} else {
				raWrapper.valJsonResponseVal(jsonPath, expVal);
			}
		}
	}

	@And("the response should not contain expected element in {string}")
	public void the_response_should_contain_expected_element_in_something(String sheetname) throws Throwable {
		String key = "";
		boolean sflag = true;
		ExcelConnector con = new ExcelConnector(filename, sheetname);
		TestDataFactory test = new TestDataFactory();
		TestDataObject obj = test.GetData(key, con);
		LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
		for (int i = 1; i < getAllData.size(); i++) {
			Map<String, String> fRow = getAllData.get(String.valueOf(i));
			String jsonpath = fRow.get("Jsonpath");
			String jsonkey = fRow.get("JsonKey");
			JSONObject jobjval = raWrapper.conRespStringToJson();
			boolean flag = raWrapper.valJsonEleNotExists(jobjval, jsonpath, jsonkey);
			if (flag == false) {
				this.scenario.write("Element : " + jsonkey + " is exist in the response");
				sflag=false;

			}
			 //raWrapper.validateJsonKeyExistence(jsonPath);
		}
		if (!sflag) {
			Assert.fail();
		}
	}
}
