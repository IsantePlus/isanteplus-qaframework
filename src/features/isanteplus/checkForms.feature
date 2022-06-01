Feature: Check Forms

 Background:
    Given User log in the system and load homePage

  @checkForms
  Scenario: Check Forms
  When  Search for a patient and load their 'Cover Page'
  And   User Click ‘Start Consultation’ on the right hand menu
  And   User Click ‘To confirm’
  Then  Check that the following forms exist on the ‘Formulaire’ page

