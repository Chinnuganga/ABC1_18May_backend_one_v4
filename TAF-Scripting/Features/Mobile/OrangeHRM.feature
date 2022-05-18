@test
Feature: OrangeHRM


@test2 @AndroidWeb
Scenario: Verify System Users
	Given I have logged into "OrangeHRM" application
	And I navigate to system users page 	
	When I click on "Admin" user link
	Then I should successfully able to see user details
	And I logout from the application
#Commented code for cucumber parallel execution
@parallelTestNG 
Scenario: Verify System Users
	Given I have logged into "OrangeHRM1" application
	And I navigate to system users page 	
	When I click on "Admin" user link
	Then I should successfully able to see user details
	And I logout from the application
	
@parallelTestNG 
Scenario: Verify System Users
	Given I have logged into "OrangeHRM2" application
	And I navigate to system users page 	
	When I click on "Admin" user link
	Then I should successfully able to see user details
	And I logout from the application
	
@parallelTestNG 
Scenario: Verify System Users
	Given I have logged into "OrangeHRM3" application
	And I navigate to system users page 	
	When I click on "Admin" user link
	Then I should successfully able to see user details
	And I logout from the application
	

	

		
	
	
