@demo
Feature: Google Search

  Background:
    Given Open default website

  @Open_Page
  Scenario: Open Page
    Then I see default page

  @Check_Search
  Scenario Outline: Check Search
    When I set text '<text>' at default page
    Then I did not see search button
    When I set text '<text>' at default page
    When I click 'Search2' button
    Then I see first link text '<title>' at results page
    When I click at '1' results link
    Then I see first link title '<title>' at new tab

  Examples:
    |text     | title                              |
    |Selenium | Selenium - Web Browser Automation  |
    |Cucumber | Cucumber - Making BDD fun          |

