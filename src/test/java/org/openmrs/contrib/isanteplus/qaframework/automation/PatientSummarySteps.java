package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientSummaryPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientSummarySteps extends TestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private PatientSummaryPage patientSummaryPage;
	
	private HomePage homePage;
	
	@Before(RunTest.HOOK.PATIENT_SUMMARY)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}
	
	@After(RunTest.HOOK.PATIENT_SUMMARY)
	public void destroy() {
		quit();
	}
	
	@Given("system user logs into  and goes to the Home page")
	public void ClientVisitLoginPage() throws Exception {
		System.out.println(".... Patient summary......");
		homePage = loginPage.goToHomePage();
	}
	
	@When("Search for a Patient {string}")
	public void searchForPatientAndloadPatientDashboardPage(String patientName) throws Exception {
		findPatientPage = homePage.clickOnSearchPatientRecord();
		findPatientPage.enterPatientName(patientName);
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	}
	
	@And("Select ‘Patient Summary’ on the right hand side menu")
	public void clickOnPatientSummaryWidget() throws Exception {
		patientSummaryPage = clinicianFacingPatientDashboardPage.clickOnPatientSummary();
		patientSummaryPage.waitForPage();
	}
	
	@And("Check that the following exist")
	public void checkPatientSummary() throws Exception {
		assertTrue(patientSummaryPage.containsText("RÃ©sumÃ© du Dossier MÃ©dical"));
		assertTrue(patientSummaryPage.containsText("Informations dÃ©mographiques"));
		assertTrue(patientSummaryPage.containsText("Visites/Fiches (dernier 6 mois et premiÃ¨re visite)"));
		assertTrue(patientSummaryPage.containsText("Examens cliniques"));
		assertTrue(patientSummaryPage.containsText("RÃ©sultats de laboratoire"));
		assertTrue(patientSummaryPage.containsText("MÃ©dicaments dispensÃ©s"));
		assertTrue(patientSummaryPage.containsText("Derniers signes vitaux"));
		assertTrue(patientSummaryPage.containsText("Motifs de consultation"));
		assertTrue(patientSummaryPage.containsText("Impressions cliniques et diagnostiques"));	
	}
	
	@Then("Patient summary should display in pdf format on the screen")
	public void PatientSummaryDisplayed() {
		assertTrue(patientSummaryPage.containsText("Dernier test de charge virale"));
	}
}
