@test_allApi
Feature: Sample GET method API test with and without parameters 

@getApi_allApis
Scenario: Sample GET method API test
	Given I have sample "Get" API
	When I send a "Get" Request with "SampleGETApi" properties 
	Then I should get response code 200 
	And the response should contain: 
		| JsonPath		  | ExpectedVal														   |
		| data.id         | 2                                                                  | 
		| data.email      | janet.weaver@reqres.in                                             | 
		| data.first_name | Janet                                                              | 
		| data.last_name  | Weaver                                                             | 
		| data.avatar     | https://reqres.in/img/faces/2-image.jpg |
		
@postApi_allApi
Scenario: Sample POST method API test
	Given I have sample "Post" API
	When I send a "Post" Request with "SamplePOSTApi" and "PostApiJsonReq"
	Then I should get response code 201 
	And the response should contain: 
		| JsonPath| ExpectedVal|
		| name| morpheus| 
		| job|leader| 
		
@putApi_allApi
Scenario: Sample PUT method API test
	Given I have sample "Put" API
	When I send a "Post" Request with "SamplePUTApi" and "PutApiJsonReq"
	Then I should get response code 201 
	And the response should contain: 
		| JsonPath| ExpectedVal|
		| name| morpheus| 
		| job|zion resident| 
		
@DeleteApi_allApi
Scenario: Sample DELETE method API test
	Given I have sample "Delete" API
	When I send a "Delete" Request with "SampleDELETEApi" properties 
	Then I should get response code 204