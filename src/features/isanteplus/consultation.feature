Feature: Consultation

  Background:
    Given user logs into Isanteplus application and proceeds to the Home page

  @consultation
  Scenario Outline: starting a Patient Consultation
  When search for a patient and load their cover page "<searchText>"
  And Click ‘Demarrer Consultation’ under “Actions générales” menu on the right
  And Click ‘Confirmer’
  Then User is redirected to the Forms tab where new forms can be added and a list of history of forms is displayed
  Examples:
    |searchText |
    |mutesa     |

  @consultation
  Scenario Outline: Adding Prior Consultation  
  When search for a patient and load their cover page "<searchText>"
  And Click “Ajouter consultation antérieure” under “Actions générales” menu on the right
  And On the Ajouter consultation antérieure pop up enter the Date de début and the Date de fin
  And Click Confirm
  Then User is redirected to the Forms tab where new forms can be added and a list of history of forms is displayed
  Examples:
    |searchText |
    |mutesa     |