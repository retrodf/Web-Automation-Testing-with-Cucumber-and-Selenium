Feature: Login Functionality
  As a user of the Education Fund Payment Management System
  I want to be able to login with valid credentials
  So that I can access the system dashboard

  Background:
    Given User has opened the browser
    And User has navigated on the login page Education Fund Payment Management System for Zaidan Educare School app

  @positive @TC1
  Scenario: Successful login with valid bendahara credentials 
    When User enters username "bendahara" and password "admin123"
    And User clicks on login button
    Then User is navigated to the dashboard page
    And User should be able to see navigation bar for bendahara

  @negative @TC2
  Scenario: Unsuccessful login with unregistered username
    When User enters username "indra" and password "admin123"
    And User clicks on login button
    Then User should be able to see "un-successful login" notification message
