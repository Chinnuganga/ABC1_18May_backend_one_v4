package com.scripted.api;

import java.util.HashMap;

import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;

import io.restassured.specification.RequestSpecification;

public class SampleGETXmlApiTest {

	public static void main(String[] args) {
		RequestParams Attwrapper = new RequestParams();
		RestAssuredWrapper raWrapper = new RestAssuredWrapper();
		raWrapper.setAPIFileProName("SampleGETXmlApi.properties");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("q", "London");
		params.put("APPID", "b110aef38de749c461af939ba52cb6f4");
		params.put("mode", "xml");
		raWrapper.setGetQueryParams(params);
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendGetRequestWithParams(reqSpec);
		raWrapper.valResponseCode(200);
		raWrapper.valXmlResponseVal("current.city.@id", "2643743");
		raWrapper.valXmlResponseVal("current.city.@name", "London");
		raWrapper.valXmlResponseVal("current.city.country", "GB");	
	}
}
