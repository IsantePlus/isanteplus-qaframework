Feature: Patient Search

  Background:
    Given User logs in the system

  @findPatient
  Scenario: Searching for a patient Using Name and ST code
  And From the home page, User clicks 'search patient record' 
  And User Enters search Text "<searchText>" in 'Patient Search' box
  Then User Identifies patient in list
  When User Clicks row with the patient being searching for
  Then Selected patient’s 'Cover Page' will be displayed for the searchType "<searchType>"
   Examples:
      | searchText    |searchType |
      | moses2 mutesa2| Names     |
      | STCODE2       | ST Code   |
 
