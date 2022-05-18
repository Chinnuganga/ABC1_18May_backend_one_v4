package com.scripted.api;

import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;

import io.restassured.specification.RequestSpecification;

public class SamplePOSTApiTest {

	public static void main(String[] args) {		
		RequestParams Attwrapper = new RequestParams();
		RestAssuredWrapper raWrapper = new RestAssuredWrapper();
		Attwrapper.setJsonbody("PostApiJsonReq");
		raWrapper.setAPIFileProName("SamplePOSTApi.properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);		
		raWrapper.sendRequest("Post",reqSpec);
		raWrapper.valResponseCode(201);
		raWrapper.valJsonResponseVal("name","morpheus");
		raWrapper.valJsonResponseVal("job","leader");
	}
}
