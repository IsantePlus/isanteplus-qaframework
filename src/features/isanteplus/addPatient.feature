Feature: Adding a Patient 

  Background:
    Given User logins in and goes to Home Page

  @register
  Scenario Outline: Adding an Adult and Pediatric Patient
  When From Main Menu, User selects 'Save Patient'
  And User Enters Date of Visit
  And User Enters patient’s First Name "<firstName>"
  And User Enters patient’s Last Name "<lastName>"
  And User Enters Sex "<gender>"
  And User Enters Date of Birth for patient as "<age>"
  And User Enters ST Code "<stCode>"
  And User Enters National ID "<nationalId>"
  And User Enters Address "<address>"
  And User Clicks Save
  Then ‘Form Successfully Saved’ message and the newly added  patient Cover Sheet appears
  Examples:
    |firstName |lastName |gender | age|stCode  |nationalId| address|
    |moses     |mutesa   |Male   | 20 |STCODE1 |HAIT123  | haiti   |
    |moses2    |mutesa2  |Male   | 5  |STCODE2 |HAIT100  | haiti   |
