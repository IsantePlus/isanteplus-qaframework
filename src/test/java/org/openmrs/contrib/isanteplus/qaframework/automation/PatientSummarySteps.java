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
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class PatientSummarySteps extends TestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage  clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	@Before(RunTest.HOOK.PATIENT_SUMMARY)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}

	@After(RunTest.HOOK.PATIENT_SUMMARY)
	public void destroy() {
		quit();
	}

	@Given("setup logs in the system")
	public void setupLoginPage() throws Exception {
		homePage = loginPage.goToHomePage();
	}

	@When("User clicks on search Patient Record app")
	public void userVisitsFindPatientRecordApp() throws Exception {
		findPatientPage = homePage.clickOnSearchPatientRecord();
	}

	@And("User searches for a patient {string} patientName and load their cover page")
	public void userSearchForPatient(String Reason) {
     	findPatientPage.enterPatientName(Reason);
		findPatientPage.getFirstPatientIdentifier();
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	}

	@And("Selected patient’s ‘Cover Sheet’ will be displayed with the following")
	public void loadPatientDashboardPage() {
		assertTrue(clinicianFacingPatientDashboardPage.containsText("DIAGNOSES"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("VITALS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("RECENT VISITS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("APPOINTMENTS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("ALLERGIES"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("HEALTH TREND SUMMARY"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("WEIGHT GRAPH"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("LATEST OBSERVATIONS"));
	}
}
