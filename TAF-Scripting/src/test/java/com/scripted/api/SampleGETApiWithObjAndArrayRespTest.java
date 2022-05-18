package com.scripted.api;

import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;

import io.restassured.specification.RequestSpecification;

public class SampleGETApiWithObjAndArrayRespTest {

	public static void main(String[] args) {		
		RequestParams Attwrapper = new RequestParams();
		RestAssuredWrapper raWrapper = new RestAssuredWrapper();
		raWrapper.setAPIFileProName("SampleGETApiObAr.properties");
		RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
		raWrapper.sendRequest("Get",reqSpec);
		raWrapper.valResponseCode(200);
		raWrapper.valJsonResponseVal("page",1);
		raWrapper.valJsonResponseVal("per_page",3);
		raWrapper.valJsonResponseVal("total",12);
		raWrapper.valJsonResponseVal("total_pages",4);
		raWrapper.valJsonResponseVal("data[0].id",1);
		raWrapper.valJsonResponseVal("data[0].email","george.bluth@reqres.in");
		raWrapper.valJsonResponseVal("data[0].first_name","George");
		raWrapper.valJsonResponseVal("data[0].last_name","Bluth");
		raWrapper.valJsonResponseVal("data[0].avatar","https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
		raWrapper.valJsonResponseVal("data[1].id",2);
		raWrapper.valJsonResponseVal("data[1].email","janet.weaver@reqres.in");
		raWrapper.valJsonResponseVal("data[1].first_name","Janet");
		raWrapper.valJsonResponseVal("data[1].last_name","Weaver");
		raWrapper.valJsonResponseVal("data[1].avatar","https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg");
	}
}
