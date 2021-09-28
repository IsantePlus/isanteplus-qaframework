package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends TestBase {
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	@After(RunTest.HOOK.LOGIN)
	public void destroy() {
		quit();
	}
	
	@Before(RunTest.HOOK.LOGIN)
	public void setLoginPage() {
		System.out.println(".... User Login......");
		loginPage = new LoginPage(getWebDriver());
	}
	
	@Given("User visits login page")
	public void visitLoginPage() throws Exception {
		loginPage.go();
	}
	
	@When("User enters {string} username")
	public void anyUsername(String username) {
		loginPage.enterUsername(username);
	}
	
	@And("User enters {string} password")
	public void anyPassword(String password) {
		loginPage.enterPassword(password);
	}
	
	@And("User Selects {string} Login Location")
	public void selectLoginLocation(String loginLocation) {
		if ("setUPLocation".equals(loginLocation)) {
			loginPage.selectLocation();
		}
	}
	
	@And("User Logs in")
	public void userLogsIn() {
		loginPage.clickLoginButton();
	}
	
	@Then("System Evaluates Login {string}")
	public void evaluateLogin(String status) {
		homePage = new HomePage(loginPage);
		if (status.trim().endsWith("true")) {
			assertTrue(homePage.hasLogOutLink());
		} else if (status.trim().endsWith("false")) {
			assertTrue(loginPage.hasLoginButton());
		}
	}
	
	@And("User Logs in into the System")
	public void login() {
		homePage = loginPage.goToHomePage();
	}
	
	@When("Once inside the application, click the `Logout` link")
	public void logout() {
		homePage.clickLogout();
	}
	
	@Then("The user is logged out of iSantePlus and redirected to the login page")
	public void redirectedToLoginPage() {
		assertTrue(loginPage.hasLoginButton());
	}
}
