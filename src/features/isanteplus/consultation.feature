Feature: Consultation

  Background:
    Given user logs into Isanteplus application and proceeds to the Home page

  @consultation
  Scenario Outline: starting a Patient Consultation
  When search for a patient and load their cover page "<searchText>"
  And Click ‘Demarrer Consultation’ on the right
  And Click ‘Confirmer’
    Examples:
    |searchText |
    |Clair      |
