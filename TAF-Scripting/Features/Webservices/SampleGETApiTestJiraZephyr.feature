@Jira 
Feature: Sample GET method API test with and without parameters 

@SAM-13
Scenario: Sample GET method API test without parameters 
	Given I have sample Get API 
	When I send a "Get" Request with "sampleGETApi" properties 
	Then I should get response code 200 
	And the response should contain: 
		| JsonPath		  | ExpectedVal														   |
		| data.id         | 2                                                                  | 
		| data.email      | janet.weaver@reqres.in                                             | 
		| data.first_name | Janet                                                              | 
		| data.last_name  | Weaver                                                             | 
		| data.avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg |
		
@api
Scenario: Sample GET method API test returns response with json object and array 
	Given I have sample Get API 
	When I send a "Get" Request with "sampleGETApiObAr" properties 
	Then I should get response code 200 
	And the response should contain: 
		| JsonPath           | ExpectedVal                                                        | 
		| page               | 1                                                                  | 
		| per_page           | 3                                                                  | 
		| total              | 12                                                                 | 
		| total_pages        | 4                                                                  | 
		| data[0].id         | 2                                                                  | 
		| data[0].email      | george.bluth@reqres.in                                             | 
		| data[0].first_name | George                                                             | 
		| data[0].last_name  | Bluth                                                              | 
		| data[0].avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg  | 
		| data[1].id         | 2                                                                  | 
		| data[1].email      | janet.weaver@reqres.in                                             | 
		| data[1].first_name | Janet                                                              | 
		| data[1].last_name  | Weaver                                                             | 
		| data[1].avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg |