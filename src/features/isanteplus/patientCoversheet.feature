Feature: Patient Cover sheet

 Background:
    Given system user logs into Isanteplus application and goes to the Home page

  @patientCoverSheet
  Scenario: Patient Cover sheet
  When   Search for and select Patient
  Then   Selected patient’s ‘Cover Sheet’ will be displayed with all the right details
