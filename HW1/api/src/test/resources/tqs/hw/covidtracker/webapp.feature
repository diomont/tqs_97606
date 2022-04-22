Feature: Lookup COVID-19 data on the website


    Scenario: Lookup global data for a given day
        When I navigate to "http://localhost:5000"
        And I enter "2022-04-19" on the date picker
        And I click the Lookup button
        Then I should see "Global" in the results
        And I should see "2022-04-19" in the results

    Scenario: Lookup global data for a period of time
        When I navigate to "http://localhost:5000"
        And I click the Period tab
        And I enter "2022-04-17" on the start date picker
        And I enter "2022-04-19" on the end date picker
        And I click the Lookup button
        Then I should see "Global" in the results
        And I should see "2022-04-17 to 2022-04-19" in the results
    
    Scenario: Lookup region data
        When I navigate to "http://localhost:5000"
        And I select "Portugal" as the region
        And I click the Lookup button
        Then I should see "Portugal" in the results
    
    Scenario: Lookup region data for a given day
        When I navigate to "http://localhost:5000"
        And I select "Portugal" as the 
        And I enter "2022-04-19" on the date picker
        And I click the Lookup button
        Then I should see "Portugal" in the results
        And I should see "2022-04-19" in the results
    
    Scenario: Lookup region data for a period of time
        When I navigate to "http://localhost:5000"
        And I select "Portugal" as the region
        And I click the Period tab
        And I enter "2022-04-17" on the start date picker
        And I enter "2022-04-19" on the end date picker
        And I click the Lookup button
        Then I should see "Portugal" in the results
        And I should see "2022-04-17 to 2022-04-19" in the results
