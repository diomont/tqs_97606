Feature: Booking a flight in the "Blazedemo" application

  Scenario: Choosing departure and destination cities
    When I navigate to "https://blazedemo.com/"
    And I select "San Diego" as my departure city
    And I select "New York" as my destination city
    And I click Search
    Then the results page should show "Departs: San Diego" in the departures column header
    And the results page should show "Arrives: New York" in the arrivals column header
