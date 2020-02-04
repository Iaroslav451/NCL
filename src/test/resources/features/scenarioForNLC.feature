@testScenarios

Feature: For NLC

  @test
  Scenario: 1 guest explores Ports of Departure
    Given a Guest
    And I am on Homepage
    And I navigated to "Ports" page
    When I search for "Honolulu" port
    Then Map zoomed to show selected port
#      And Port is on the middle of the map
    And Port displayed as "Port Of Departure"

  @test
  Scenario: 2 Guest explores shore excursions destinations
    Given a Guest
    And I am on Homepage
    And I navigated to "Shore Excursion" page
    When I search for destination "Alaska Cruises"
    And I click Find Excursions
    Then Shore Excursions page is present
    And Results are filtered by "Alaska Cruises"
    And Filter By Ports are only belong to "Alaska, British Columbia"

  @test
  Scenario: 3 Guest filters shore excursions results using price range
    Given a Guest
    And I am on Homepage
    And I navigated to "Shore Excursion" page
    And I click Find Excursions
    And Shore Excursions page is present
    When Price Range is filtered to "$0-$30"
    Then Only shore excursions within range are displayed