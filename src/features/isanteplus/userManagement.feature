Feature: User Management

  Background:
    Given client logs into Isanteplus application and goes to the Home page

  @userManagement
  Scenario Outline: Adding a user
  When From the main menu select System Administration app
  And Select Manage Accounts App 
  And Click add new account
  And Enter name,firstname and sex "<name>" "<firstName>"
  And Click the checkbox next to add user account
  And Enter a username "<userName>"
  And Select 'full' under privillage level "<privillage>" "<password>" "<confirmPassword>"
  And click force password
  And select the checkboxes under ‘capacites’ with the appropriate privileges based on roles defined
  And Click the checkbox next to ‘Add a supplier account?
  And Enter the same username as entered above in ‘Identifier’ field "<userName>"
  And Select the relevant role under ‘service provider role’ "<role>"
  And click save
  Then User redirected to the ‘Gerer les comptes’ page and user appears in the user table
  Examples:
    |name  |firstName |userName |privillage |password  |confirmPassword |role            |
    |john  |DO        |johnDo   |Full       |Messi123  |Messi123        |Clinical Doctor |

  @userManagement
  Scenario Outline: Searching a user
  When From the main menu select System Administration app
  And Select Manage Accounts App 
  And Enter the name of the user in the ‘Search’ text box "<user>"
  Then the User table should filter, only displaying results of the search                                                                              
   Examples:
      | user        |
      | john smith  | 

  @userManagement
  Scenario Outline: Deleting a user
  When  From the main menu select System Administration app
  And Select Manage Accounts App
  And Search for user or find a user in the user table "<user>"
  And Click the pencil icon in the ‘Action’ column next to the desired user name
  And Click ‘Retire’ under user account details
  And Enter Reason "<reason>"
  Then Notification ‘Retire avec success’ and the user’s account details  appear crossed out

  Examples:
      | user        |reason             |
      | DO john     |user nolonger used |   

  @userManagement
  Scenario Outline: Setting user access
  When From the main menu select System Administration app
  And Select Manage Accounts App 
  And Click the pencil icon in the ‘Action’ column next to the desired user name
  And Select the modifier icon in the top right corner of the ‘details du compte d’utilisateur’
  And select the appropriate privileges under ‘capacites’ checkboxes "<userName>" "<privillage>"
  And admin clicks save
  Then User should see a notification ‘Changements sauvegardés avec succès'
  Examples:
    |userName |privillage |           
    |admin    |Full       |
            