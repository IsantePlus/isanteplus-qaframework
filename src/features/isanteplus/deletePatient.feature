Feature: Delete Patient

  Background:
    Given setup logs in the system

  @deletePatient
  Scenario: Delete Patient
  When  User clicks on search Patient Record app
  And   User searches for a patient "<patientName>" patientName and load their cover page
  And   User clicks 'Delete Patient'
  Then  Patient deleted successfully message appears, and redirected to patient search page
   Examples:
      | patientName  | Reason |
      | moses mutesa | patient discharged|
