Feature: Delete Patient

  Background:
    Given setup logs in the system
    
  @deletePatient
  Scenario: Delete Patient
  And  User clicks on search Patient Record app
  And  Search for a patient "<patientName>" patientName and load their cover page
  Then Click ‘Effacer Patient’,Enter reason,and Click ‘Confirmer’
   Examples:
      | patientName  | 
      | moses mutesa     | 