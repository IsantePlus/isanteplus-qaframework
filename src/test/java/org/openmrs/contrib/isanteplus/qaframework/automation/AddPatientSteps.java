package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.RegisterPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddPatientSteps extends TestBase {

	private LoginPage loginPage;

	private HomePage homePage;

	private RegisterPatientPage registerPatientPage;

	private ClinicianFacingPatientDashboardPage patientVisitsDashboardPage;

	@After(RunTest.HOOK.REGISTRATION)
	public void destroy() {
		quit();
	}

	@Before(RunTest.HOOK.REGISTRATION)
	public void setLoginPage() {
		loginPage = new LoginPage(getWebDriver());
	}

	@Given("User logins in and goes to Home Page")
	public void visitLoginPage() throws Exception {
		System.out.println(".... Adding a Patient......");
		homePage = loginPage.goToHomePage();
	}

	@When("From Main Menu, User selects 'Save Patient'")
	public void clickRegisterPatient() {
		registerPatientPage = homePage.clickRegisterPatientApp();
	}

	@And("User Enters Date of Visit")
	public void enterDateOfVist() {
		// by default today is selected
		assertTrue(registerPatientPage.registrationDateIsChecked());
	}

	@And("User Enters patient’s First Name {string}")
	public void enterGivenName(String givenName) {
		registerPatientPage.enterGivenName(givenName);
	}

	@And("User Enters patient’s Last Name {string}")
	public void enterFamilyName(String familyName) {
		registerPatientPage.enterFamilyName(familyName);
	}

	@And("User Enters Sex {string}")
	public void selectGender(String gender) {
		registerPatientPage.selectGender(gender);
	}

	@And("User Enters Date of Birth for patient as {string}")
	public void enterDateOfBirth(String age) {
		registerPatientPage.enterDateOfBirth(age);
	}

	@And("User Enters ST Code {string}")
	public void enterStCode(String stCode) {
		registerPatientPage.enterStCode(stCode);
	}

	@And("User Enters National ID {string}")
	public void enterNationalId(String nationalId) {
		registerPatientPage.enterNatinalId(nationalId);
	}

	@And("User Enters Address {string}")
	public void enterAddress(String address) {
		registerPatientPage.enterAddres(address);
	}

	@And("User Clicks Save")
	public void clickSave() {
		patientVisitsDashboardPage = registerPatientPage.savePatient();
		if (registerPatientPage.hasValidationError()) {
			UUID uuid = UUID.randomUUID();
			registerPatientPage.enterStCode(uuid.toString());
			patientVisitsDashboardPage = registerPatientPage.savePatient();
		}
		patientVisitsDashboardPage.waitForPage();
	}

	@Then("‘Form Successfully Saved’ message and the newly added  patient Cover Sheet appears")
	public void patientSaved() {
		assertTrue(patientVisitsDashboardPage.hasVistActionsColumn());
	}
}
