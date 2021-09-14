package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.RegisterPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientVisitsDashboardPage;
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
	
	private PatientVisitsDashboardPage patientVisitsDashboardPage;
	
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
		homePage = loginPage.goToHomePage();
	}
	
	@When("User selects ‘Register Patient’ From Main Menu")
	public void clickRegisterPatient() {
		registerPatientPage = homePage.clickRegisterPatientApp();
	}
	
	@And("User Enters Date of Visit")
	public void enterDateOfVist() {
		//by default today is selected
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
	
	@And("User Enters Date of Birth for patient to be > 14 years old as {string}")
	public void enterDateOfBirth(String age) {
		registerPatientPage.enterDateOfBirth(age);
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
	}
	
	@Then("‘Form Successfully Saved’ message and the newly added  patient Cover Sheet appears")
	public void patientSaved() {
		// assertTrue(patientVisitsDashboardPage.containsText("General Actions"));
	}
	
}
