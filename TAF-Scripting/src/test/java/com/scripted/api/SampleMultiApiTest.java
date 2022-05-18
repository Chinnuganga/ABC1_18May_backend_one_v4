package com.scripted.api;

import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.scripted.Allure.AllureListener;
import com.scripted.api.RequestParams;
import com.scripted.api.RestAPIClient;
import com.scripted.api.RestAssuredWrapper;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Listeners({ AllureListener.class })
public class SampleMultiApiTest {
	@Test
	public static void test() {
		RequestParams Attwrapper = new RequestParams();
		RestAssuredWrapper raWrapper = new RestAssuredWrapper();
		raWrapper.setAPIFileProName("POCGet.properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendRequest("Get", reqSpec);
		raWrapper.valResponseCode(200);

		Response response = raWrapper.getResponse();

		String respBody = response.asString();
		JSONObject jObject = new JSONObject(respBody);
		System.out.println("Parent API Response : " + jObject);

		String keyVal = raWrapper.fetchJsonPathValue(jObject, "data[0]", "id").toString();

		String Uri = "https://reqres.in/api/users/" + keyVal;
		RestAPIClient.restApiGetReqRes(Uri);

		RestAPIClient.valJsonResponseVal("data", "id", 7);
		RestAPIClient.valJsonResponseVal("data", "email", "michael.lawson@reqres.in");
		RestAPIClient.valJsonResponseVal("data", "first_name", "Michael");
		RestAPIClient.valJsonResponseVal("data", "last_name", "Lawson");
		RestAPIClient.valJsonResponseVal("data", "avatar",
				"https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg");
	}
}
