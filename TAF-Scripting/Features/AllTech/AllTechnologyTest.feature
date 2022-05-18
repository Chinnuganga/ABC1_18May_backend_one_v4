@Alltech @regression
Feature: Testing Webservices, Web, Mobile and Mainframe applications

  @SAM-13 @Api
  Scenario: Sample GET method API test without parameters
    Given I have sample Get API
    When I send a "Get" Request with "sampleGETApi" properties
    Then I should get response code 200
    And the response should contain:
      | JsonPath        | ExpectedVal                                                        |
      | data.id         |                                                                  2 |
      | data.email      | janet.weaver@reqres.in                                             |
      | data.first_name | Janet                                                              |
      | data.last_name  | Weaver                                                             |
      | data.avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg |

  @SAM-14 @Api
  Scenario: Sample GET method API test returns response with json object and array
    Given I have sample Get API
    When I send a "Get" Request with "sampleGETApiObAr" properties
    Then I should get response code 200
    And the response should contain:
      | JsonPath           | ExpectedVal                                                        |
      | page               |                                                                  1 |
      | per_page           |                                                                  3 |
      | total              |                                                                 12 |
      | total_pages        |                                                                  4 |
      | data[0].id         |                                                                  2 |
      | data[0].email      | george.bluth@reqres.in                                             |
      | data[0].first_name | George                                                             |
      | data[0].last_name  | Bluth                                                              |
      | data[0].avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg  |
      | data[1].id         |                                                                  2 |
      | data[1].email      | janet.weaver@reqres.in                                             |
      | data[1].first_name | Janet                                                              |
      | data[1].last_name  | Weaver                                                             |
      | data[1].avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg |

  @Web
  Scenario: TC-01-Validating perosnal information in Automation practice
    Given I have logged into "Automation Practice" application
    When I navigate to personal information page
    Then I validate personal details in personal information page:
      | Social title | First name | Last name  | E-mail address                     | dobDay | dobMonth | dobYear |
      | Mr.          | Abhilash   | Giridharan | abhilash.giridharan@ust-global.com |      8 | November |    1987 |

  @Web
  Scenario: TC-01-Validating perosnal information in Automation practice
    Given I have logged into "Automation Practice" application
    When I navigate to personal information page
    Then I validate personal details in personal information page:
      | Social title | First name | Last name | E-mail address                     | dobDay | dobMonth | dobYear |
      | Mr.          | Abhilash   | Giri      | abhilash.giridharan@ust-global.com |      8 | November |    1987 |

  @AndroidWebs
  Scenario: Verify System Users
    Given I have logged into "OrangeHRM" application
    And I navigate to system users page
    When I click on "Admin" user link
    Then I should successfully able to see user details
    And I logout from the orangeHrm application

  @Mainframe
  Scenario: TC-01-Creating new customer
    Given I have login to Share Tech Mainframe System
    And I navigate to "Menu" application
    When I create new customer "655557" with below details:
      | LastName | FirstName | Address  | State | ZipCode |
      | lname    | fname     | add,test | kl    |  682030 |
    Then I should see "Customer record added." message

  @Mainframe
  Scenario: TC-02-Deleting Customer
    Given I have login to Share Tech Mainframe System
    And I navigate to "Menu" application
    When I delete the customer "655557" details
    Then I should successfully see the message "Customer deleted."
