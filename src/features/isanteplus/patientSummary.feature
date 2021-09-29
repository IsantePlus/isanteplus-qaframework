Feature: Patient Summary

 Background:
    Given system user logs into  and goes to the Home page

  @patientSummary
  Scenario: Patient Summary
  When    Search for a Patient "<patientName>"
  And     Select ‘Patient Summary’ on the right hand side menu
  And     Check that the following exist
  Then    Patient summary should display in pdf format on the screen
   Examples:
      | patientName    |
      | moses2 mutesa2 | 
