Feature: Create activity

  Scenario: Successfully creating a new activity
    Given an activity named "Paragliding" with price 50 and 15 places does not exist
    When I create a new activity named "Paragliding" with price 50 and 15 places
    Then the activity "Paragliding" should be registered in the system

  Scenario: An employment wants to search an activity which does not exists
    When I ask for an activity "karting"
    Then it does not exists
