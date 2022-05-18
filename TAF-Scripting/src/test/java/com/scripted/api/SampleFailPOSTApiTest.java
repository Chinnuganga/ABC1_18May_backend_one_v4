package com.scripted.api;

import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;

public class SampleFailPOSTApiTest {

	@Test
	public void testing(){		
		RequestParams Attwrapper = new RequestParams();
		RestAssuredWrapper raWrapper = new RestAssuredWrapper();
		Attwrapper.setJsonbody("PostApiJsonFailReq");
		raWrapper.setAPIFileProName("SampleFailPOSTApi.properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);		
		raWrapper.sendRequest("Post",reqSpec);
		raWrapper.valResponseCode(400);
		raWrapper.valJsonResponseVal("error","Missing password");
	}
}
