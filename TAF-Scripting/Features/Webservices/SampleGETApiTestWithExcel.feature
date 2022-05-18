 @test 
Feature: Sample GET method API test with and without parameters 

@testapi @data-APITestData
Scenario: Sample GET method API test without parameters 
	Given I have sample Get API 
	When I send a "Get" Request with "sampleGETApi" properties 
  Then I should get response code 200 
	And the response should contain expected values in "TD-001"
		
@testapi @data-APITestData
Scenario: Sample GET method API test without parameters 
	Given I have sample Get API 
	When I send a "Get" Request with "sampleGETApi" properties 
	Then I should get response code 200 
	And the response should not contain expected element in "TD-002"
	
		