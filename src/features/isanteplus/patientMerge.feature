Feature: Patient Merge

Background:
Given User log into the system

@patientMerge
Scenario: Patient Merge
When User clicks on data management app
And  User Select ‘Merge electronic patient records
Then User enter first patient id
And  User enter second patient id
Then User clicks on continue
And  User select the preferred record
And  User Click ‘Yes, continue’
Then Patient’s cover page with the data for the selected record is loaded
