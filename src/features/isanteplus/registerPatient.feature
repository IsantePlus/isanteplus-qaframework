Feature: Adding a Patient 

  Background:
    Given User logins in and goes to Home Page

  @register
  Scenario: Adding an Adult and Pediatric Patient
  When From Main Menu, User selects 'Register a patient'
  And User Enters Date of Visit
  And User Enters patient’s First Name "<firstName>"
  And User Enters patient’s Last Name "<lastName>"
  And User Enters Sex "<gender>"
  And User Enters Date of Birth for patient as "<age>"
  And User Enters Birthplace "<address>"
  And User Enters ST Code "<stCode>"
  And User Enters National ID "<nationalId>"
  And User Enters Address "<address>"
  And User Clicks Save
  Then ‘Form Successfully Saved’ message and the newly added  patient Cover Sheet appears
  Examples:
    |firstName |lastName |gender | age|stCode  |nationalId| address|
    |moses     |mutesa   |Male   | 20 |STCODE1 |HAIT123  | haiti   |
    |moses2    |mutesa2  |Male   | 5  |STCODE2 |HAIT100  | haiti   |
    |sharif    | sharif1 |Male   | 21 |STCODE3 |HAIT123  | haiti   |
    |sharif1   |sharif1  |Male   |10 |STCODE3 |HAITI101 | haiti  |
    |moses3    |moses3   |Male   |17  |STCODE55|HAITI103 | haiti   |
    |sharif2   |sharif2  |Male   |12  |STCODE4 |HAITI102 | haiti   |

