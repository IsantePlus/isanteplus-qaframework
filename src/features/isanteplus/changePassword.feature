Feature: Change Password

  @password
  Scenario Outline: Changing password 
  When user logs into Isanteplus application and goes to the Home page
  And user clicks System Administration  
  And User clicks Manage Accounts App and goes to Manage Accounts page
  And User clicks edit button action and goes to account page
  And User clicks on modifier under user account detaials and enters username "<userName>" and privillage "<newPrivillage>"
   Examples:
    |userName |newPrivillage |
    |admin     |Full         |
