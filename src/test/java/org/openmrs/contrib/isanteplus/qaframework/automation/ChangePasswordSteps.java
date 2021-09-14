package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.AccountsPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ManageAccountsPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.SystemAdministrationPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class ChangePasswordSteps extends TestBase {

	private LoginPage loginPage;

	private SystemAdministrationPage systemPage;

	private ManageAccountsPage manageAccountsPage;

	private AccountsPage accountsPage;

	private HomePage homePage;

	@After(RunTest.HOOK.PASSWORD)
	public void destroy() {
		quit();
	}

	@Before(RunTest.HOOK.PASSWORD)
	public void setLoginPage() {
		loginPage = new LoginPage(getWebDriver());
	}

	@When("user logs into Isanteplus application and goes to the Home page")
	public void userVisitLoginPage() throws Exception {
		homePage = loginPage.goToHomePage();
	}

	@And("user clicks System Administration")
	public void systemLoadsSystemAdministrationPage() {
		systemPage = homePage.clickSystemAdministrationApp();
	}

	@And("User clicks Manage Accounts App and goes to Manage Accounts page")
	public void visitManageAccountsAppsPage() throws Exception {
		manageAccountsPage = systemPage.goToManageAccountsPage();
	}

	@And("User clicks edit button action and goes to account page")
	public void userClicksEditButton() throws Exception {
		accountsPage = manageAccountsPage.editAccount();
	}

	@And("User clicks on modifier under user account detaials and enters username {string} and privillage {string}")
	public void modifyAccount(String userName, String newPrivillage) throws Exception {
		accountsPage.clickEditButton();
		accountsPage.enterUserName(userName);
		accountsPage.selectPrevillageLevel(newPrivillage);
	}

	@And("User checks force password")
	public void enableForcedPassword() {
		accountsPage.forcePasswordIsChecked();
	}

}