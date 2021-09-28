package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;

import org.openmrs.contrib.isanteplus.qaframework.automation.page.AccountsPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ManageAccountsPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.SystemAdministrationPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class UserManagementSteps extends TestBase {

    private LoginPage loginPage;

    private SystemAdministrationPage systemPage;

    private ManageAccountsPage manageAccountsPage;

    private AccountsPage accountsPage;

    private HomePage homePage;

    @After(RunTest.HOOK.USERMANAGEMENT)
    public void destroy() {
        quit();
    }

    @Before(RunTest.HOOK.USERMANAGEMENT)
    public void setLoginPage() {
        System.out.println(".... User management......");
        loginPage = new LoginPage(getWebDriver());
    }

    @Given("client logs into Isanteplus application and goes to the Home page")
    public void clientVisitLoginPage() throws Exception {
        homePage = loginPage.goToHomePage();
    }

    @When("From the main menu select System Administration app")
    public void systemLoadsSystemAdministrationPage() throws Exception {
        systemPage = homePage.clickSystemAdministrationApp();
    }

    @And("Select Manage Accounts App")
    public void visitManageAccountsAppsPage() throws Exception {
        manageAccountsPage = systemPage.goToManageAccountsPage();
    }

    @And("Click add new account")
    public void clickAddAccount() throws Exception {
        accountsPage = manageAccountsPage.clickAddAccount();
    }

    @And("Enter name,firstname and sex {string} {string}")
    public void enterPersonDetails(String name, String firstName) throws Exception {
        accountsPage.enterName(name);
        accountsPage.enterFirstName(firstName);
        accountsPage.clickGenderButton();
    }

    @And("Click the checkbox next to add user account")
    public void enableAddUserAccount() throws Exception {
        accountsPage.addUserIsChecked();
    }

    @And("Enter a username {string}")
    public void enterUsername(String userName) throws Exception {
        accountsPage.enterUserName(userName);
    }

    @And("Select 'full' under privillage level {string} {string} {string}")
    public void enterPrivillage(String privillage, String password, String confirmPassword) throws Exception {
        accountsPage.selectPrevillageLevel(privillage);
        accountsPage.enterPassword(password);
        accountsPage.confirmPassword(confirmPassword);
    }

    @And("click force password")
    public void forceChangePassword() throws Exception {
        accountsPage.checkForcePassword();
    }

    @And("select the checkboxes under ‘capacites’ with the appropriate privileges based on roles defined")
    public void checkRoles() throws Exception {
        accountsPage.administerSystemIsChecked();
    }

    @And("Click the checkbox next to ‘Add a supplier account?")
    public void checkAddSupplierAccount() throws Exception {
        accountsPage.addSupplierAccountIsChecked();
    }

    @And("Enter the same username as entered above in ‘Identifier’ field {string}")
    public void enterUserNameInIdentify(String identify) throws Exception {
        accountsPage.enterIdentifier(identify);
    }

    @And("Select the relevant role under ‘service provider role’ {string}")
    public void selectProviderRole(String role) throws Exception {
        accountsPage.selectProviderRole(role);
    }

    @And("click save")
    public void saveNewUserAccount() throws Exception {
        accountsPage.saveAccount();
    }

    @Then("User redirected to the ‘Gerer les comptes’ page and user appears in the user table")
    public void saveNewUserrAccount() throws Exception {
        assertTrue(manageAccountsPage.containsText("Ajouter un nouveau compte"));
    }

    @And("Enter the name of the user in the ‘Search’ text box {string}")
    public void enterUserNameToSearch(String user) throws Exception {
        manageAccountsPage.clickSearchTextBox(user);
    }

    @Then("the User table should filter, only displaying results of the search")
    public void resultsOfTheSearch() throws Exception {
        assertTrue(manageAccountsPage.containsText("John Smith"));
        assertTrue(manageAccountsPage
                .containsText("Affichage de 1 à 1 sur 1 entrées Filtré à partir de 1 entrées au total"));
    }

    @And("Search for user or find a user in the user table {string}")
    public void searchForTheUser(String user) {
        manageAccountsPage.clickSearchTextBox(user);
    }

    @And("Click the pencil icon in the ‘Action’ column next to the desired user name")
    public void clickThePencilIcon() throws Exception {
        accountsPage = manageAccountsPage.goToAccountsPage();
    }

    @And("Click ‘Retire’ under user account details")
    public void clickRetireUnderUserAccountDetails() throws Exception {
        accountsPage.retireUser();
    }

    @And("Enter Reason {string}")
    public void enterTheReason(String reason) throws Exception {
        accountsPage.retireReason(reason);
    }

    @And("Select 'To confirm")
    public void enterToConfirm() throws Exception {
        accountsPage.clickConfimButton();
    }

    @Then("Notification ‘Retire avec success’ and the user’s account details  appear crossed out")
    public void retireMessage() throws Exception {
        accountsPage.containsText("Retire avec success");
    }

    @And("Click the pencil icon in the ‘Action’ column next to the preffred user name")
    public void clickPencilIcon() throws Exception {
        manageAccountsPage.goToAccountsPage();
    }

    @And("Select the modifier icon in the top right corner of the ‘details du compte d’utilisateur’")
    public void selectModifierIcon() throws Exception {
        accountsPage.clickEditButton();
    }

    @And("select the appropriate privileges under ‘capacites’ checkboxes {string} {string}")
    public void selectAppropriatePrivillages(String userName, String privillage) throws Exception {
        accountsPage.enterUserName(userName);
        accountsPage.selectPrevillageLevel(privillage);
        Thread.sleep(5000);
        accountsPage.checkHasSuperPriviledges();
    }

    @And("admin clicks save")
	public void saveRoles() throws InterruptedException {
		Thread.sleep(5000);
		accountsPage.saveRoles();
		 Thread.sleep(5000);
	}

    @Then("User should see a notification ‘Changements sauvegardés avec succès'")
    public void savePrivillageLevel() throws Exception {
        assertTrue(accountsPage.containsText("Changements sauvegardés avec succès"));
    }
}
