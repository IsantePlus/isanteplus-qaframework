Feature: Forms and Patient Cover Sheet

  Background:
    Given user logs into Isanteplus system and goes to the Home page

  @form
  Scenario: Form adding
  When user searches for a patient and load their cover page "<searchText>"
  And user Starts a consultation  
    Examples:
   |searchText |
   | cliff   |
