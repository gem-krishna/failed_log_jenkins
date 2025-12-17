Feature: Google Search
  Scenario: Search for something on Google
    Given I am on the Google search page
    When I search for "Selenium"
    Then the page title should contain "Selenium"

