Feature: Patient Merge

Background:
Given User log into the system

@patientMerge
Scenario: Patient Merge
When User clicks on data management app
And  User Select ‘Merge electronic patient records
Then User enter "<firstPatientId>" first patient id
And  User enter "<secondPatientId>" second patient id
Then User clicks on continue
And  User select the preferred record
Then User Click ‘Yes, continue’
 Examples:
      | firstPatientId |secondPatientId |
      | 1008UY         | 1008LC         |
      
 
