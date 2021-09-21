package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.DataManagementPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.MergePatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientMergeSteps extends TestBase {
	
	private HomePage homePage;
	
	private LoginPage loginPage;
	
	private DataManagementPage dataManagementPage;
	
	private MergePatientPage mergePatientsPage;
	
	private ClinicianFacingPatientDashboardPage dashboardPage;
	
	@Before(RunTest.HOOK.PATIENT_MERGE)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}
	
	@After(RunTest.HOOK.PATIENT_MERGE)
	public void destroy() {
		quit();
	}
	
	@Given("User log into the system")
	public void userVisitLoginPage() throws Exception {
		homePage = loginPage.goToHomePage();
	}
	
	@When("User clicks on data management app")
	public void UserClickOnDataManagementApp() {
		dataManagementPage = homePage.clickOnDataManagementPage();
		dataManagementPage.waitForPage();
	}
	
	@And("User Select ‘Merge electronic patient records")
	public void clickOnMergePatientRecord() {
		mergePatientsPage = dataManagementPage.goToMergePatientPage();
	}
	
	@Then("User enter {string} first patient id")
	public void enterPatient1(String firstPatientId) {
		dataManagementPage.enterPatient1(firstPatientId);
	}
	
	@And("User enter {string} second patient id")
	public void enterPatient2(String secondPatientId) {
		dataManagementPage.enterPatient2(secondPatientId);
	}
	
	@Then("User clicks on continue")
	public void clickOnContinue() throws InterruptedException {
		dataManagementPage.clickOnContinue();	
	}
	
	@And("User select the preferred record")
	public void clickOnMergePatient() {
		mergePatientsPage.clickOnMergePatient();
	}
	
	@And("User Click ‘Yes, continue’")
	public void clickOnContinueButton() {
		dashboardPage = mergePatientsPage.clickOnContinue();
	}
	
	@Then("Patient’s cover page with the data for the selected record is loaded")
	public void loadPatientDashboardPage() {
		assertTrue(dashboardPage.containsText("visits"));
	}
	
}
