Feature: Form and Patient Cover Sheet

  Background:
    Given user logs into Isanteplus system and goes to the Home page

  @form
  Scenario: Form adding
  When user searches for a patient and load their cover page "<searchText>"
  And user Starts a consultation  
  And User is redirected to the forms tab
  And Click on the category name to display the forms
  And Click the form and the user is redirected to the forms page
  And The required field “Date visite” should be filled with the current date
  And The form may consist of several sections. Click the section tab to display the fields
  And Click the Sauvegarder button to save the form
  And A prompt that the form was saved successfully is displayed and the user is redirected to the forms tab
  Then The form should be listed under Historique des formulaires
  Examples:
   |searchText |
   |  sharif2  |

  @form
  Scenario: Updating a form 
  When user searches for a patient and load their cover page "<searchText>"
  And Click on the recent consultation on the patient summary sheet
  And The Form History page is displayed
  And Click the consultation tab
  And Click the Modifier button to open the form in EDIT mode
  And Make changes to the form
  And Click the Sauvegarder button to save the form
  Then The form is saved and user redirected to the forms tab
  Examples:
   |searchText |
   |  sharif2  |

  @form
  Scenario: Delete a form 
  When user searches for a patient and load their cover page "<searchText>"
  And Click Formulaires under Actions generales menu on the right
  And The Form History page is now displayed
  And Check the box on the Enlever column 
  And Click the Supprimer selectionnee button to delete the selected forms
  Then The form is deleted and the form history reloaded
  Examples:
   |searchText |
   |  sharif2  |