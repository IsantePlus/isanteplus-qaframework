Feature: Form and Patient Cover Sheet

  Background:
    Given user logs into Isanteplus system and goes to the Home page

  @form
  Scenario: Form adding
  When user searches for a patient and load their cover page
  And user Starts a consultation  
  And User is redirected to the forms tab
  And Click on the category name to display the forms
  And Click the form and the user is redirected to the forms page
  And The required field “Date visite” should be filled with the current date
  And The form may consist of several sections. Click the section tab to display the fields
  And Click the Sauvegarder button to save the form
  #And A prompt that the form was saved successfully is displayed and the user is redirected to the forms tab
  #Then The form should be listed under Historique des formulaires
