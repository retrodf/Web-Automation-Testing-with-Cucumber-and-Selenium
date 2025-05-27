Feature: Logout Functionality

  Scenario: Check login page after login using valid credentials as role "administrator"
    Given User has opened the browser
    And User has been logged in as an Administrator
    And User has opened administrator dashboard page
    When User clicks on logout button
    And User clicks "Ya" button
    Then User is navigated to login page
    And System displays login page elements correctly
