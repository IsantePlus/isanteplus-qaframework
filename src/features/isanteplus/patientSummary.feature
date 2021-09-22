Feature: Patient Summary

 Background:
    Given setup logs in the system
    
  @patientSummary
  Scenario: Patient Summary widget
  When  User clicks on search Patient Record app
  And   User searches for a patient "<patientName>" patientName and load their cover page
  Then  Selected patient’s ‘Cover Sheet’ will be displayed with the following
   Examples:
      | patientName  | 
      | moses mutesa | 
