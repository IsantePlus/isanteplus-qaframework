Feature: Consultation

  Background:
    Given user logs into Isanteplus application and proceeds to the Home page

  @consultation
  Scenario Outline: starting a Patient Consultation
  When search for a patient and load their cover page
  And Click ‘Demarrer Consultation’ on the right
  And Click ‘Confirmer’
  Then User is redirected to the Forms tab where new forms can be added and a list of history of forms is displayed
