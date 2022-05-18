@test
Feature: UTS_IRCTC

@test1 @AndroidNative
Scenario: Search and Book Ticket
    Given I have IRCTC native application 
   	And I search for tickets without logging
	And I select the train from the search list
    And I enters the passenger booking details 
    When I click on book ticket
    Then I should ask for entering the login details
    