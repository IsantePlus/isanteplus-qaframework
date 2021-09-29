package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientCoverSheetSteps extends TestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	@Before(RunTest.HOOK.PATIENT_COVER_SHEET)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}
	
	@After(RunTest.HOOK.PATIENT_COVER_SHEET)
	public void destroy() {
		quit();
	}
	
	@Given("system user logs into Isanteplus application and goes to the Home page")
	public void systemUserVisitLoginPage() throws Exception {
		System.out.println(".... Patient Cover sheet......");
		homePage = loginPage.goToHomePage();
	}
	
	@When("Search for and select Patient {string}")
	public void searchForPatientAndloadDashboardPage(String patientName) throws Exception {
		findPatientPage = homePage.clickOnSearchPatientRecord();
		findPatientPage.enterPatientName(patientName);
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	}
	
	@Then("Selected patient’s ‘Cover Sheet’ will be displayed with all the right details")
	public void loadPatientDashboardPage() {
		assertTrue(clinicianFacingPatientDashboardPage.containsText("DIAGNOSTICS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Signes vitaux"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("RECENT VISITS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Alerte des Patients"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("ALLERGIES"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Dernier test de charge virale"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Courbe de Poids"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("BMI's Graphique"));
	}
}
