# Web Automation with Cucumber, Selenium, JUnit, and Allure

This project demonstrates web automation testing using Cucumber for BDD, Selenium WebDriver for browser automation, JUnit for test execution, and Allure for reporting.

## Project Structure

```
WebAutomation/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── io/
│   │           └── cucumber/
│   │               └── skeleton/
│   └── test/
│       ├── java/
│       │   └── io/
│       │       └── cucumber/
│       │           └── zaidan/
│       │               ├── Hooks.java
│       │               ├── Pages.java
│       │               ├── RunCucumberTest.java
│       │               ├── StepDefinitions.java
│       │               └── WebDriverManager.java
│       └── resources/
│           ├── allure.properties
│           ├── junit-platform.properties
│           └── io/
│               └── cucumber/
│                   └── zaidan/
│                       ├── login.feature
│                       └── logout.feature
├── pom.xml
└── README.md
```

## Setup

No manual setup is required. WebDriverManager automatically downloads and configures the Microsoft Edge WebDriver when you run the tests.

## Test Cases

The project includes the following test cases:
1. Login with valid credentials (Positive)
2. Login with invalid credentials (Negative)
3. Logout functionality (Positive)

## Running Tests

To run the tests, execute:
```
mvnw test
```

## Generating Reports

To generate and view the Allure report, execute:
```
mvnw allure:serve
```

## Key Components

- **Feature Files**: Define test scenarios in Gherkin language
- **Step Definitions**: Implement test steps in Java
- **Page Objects**: Encapsulate page elements and actions
- **WebDriverManager**: Handle browser driver setup
- **Hooks**: Handle setup and teardown operations
- **RunCucumberTest**: Configure and run the tests

## Technologies Used

- Cucumber: BDD testing framework
- Selenium WebDriver: Web automation tool
- JUnit: Test execution framework
- Allure: Test reporting tool
- Microsoft Edge: Web browser for testing
