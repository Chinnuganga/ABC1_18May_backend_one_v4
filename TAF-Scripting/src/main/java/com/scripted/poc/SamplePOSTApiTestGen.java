package com.scripted.poc;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.scripted.Allure.AllureListener;
import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;
import com.scripted.dataload.GenRocketDriver;

import io.restassured.specification.RequestSpecification;

@Listeners({ AllureListener.class })
public class SamplePOSTApiTestGen {
	@Test
	public static void test() {

		GenRocketDriver.updateSceLoopCount("JsonRequestGenScenario.grs", "Jsonrequest", "JSONFileReceiver", "1");

		RequestParams Attwrapper = new RequestParams();
		RestAssuredWrapper raWrapper = new RestAssuredWrapper();

		Attwrapper.setGenJsonbody("requestJson");

		raWrapper.setAPIFileProName("samplePOSTApi.properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendRequest("Post", reqSpec);
		raWrapper.valResponseCode(201);
		raWrapper.valJsonResponseVal("name", "Aaron");
		raWrapper.valJsonResponseVal("job", "leader");
	}
}
