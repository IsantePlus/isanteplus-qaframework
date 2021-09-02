Feature: Adding a Patient 

  Background:
    Given User logins in and goes to Home Page

  @register
  Scenario Outline: Adding an Adult Patient
  When User selects ‘Register Patient’ From Main Menu
  And User Enters Date of Visit
  And User Enters patient’s First Name "<firstName>"
  And User Enters patient’s Last Name "<lastName>"
  And User Enters Sex "<gender>"
  And User Enters Date of Birth for patient to be > 14 years old as "<age>"
  And User Enters National ID "<nationalId>"
  And User Enters Address "<address>"
  And User Clicks Save
  Then ‘Form Successfully Saved’ message and the newly added  patient Cover Sheet appears
  Examples:
    |firstName |lastName |gender | age| nationalId| address|
    |moses     |mutesa   |Male   | 20 | HAIT123   | haiti  |
