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
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeletePatientSteps extends TestBase {
	
	private ClinicianFacingPatientDashboardPage dashboardPage;
	
	private FindPatientPage findPatientPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	private static String REASON = "patient discharged";
	
	@Before(RunTest.HOOK.DELETEPATIENT)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}
	
	@After(RunTest.HOOK.DELETEPATIENT)
	public void destroy() {
		quit();
	}
	
	@Given("setup logs in the system")
	public void setupLoginPage() throws Exception {
		System.out.println("....Delete Patient......");
		homePage = loginPage.goToHomePage();
	}
	
	@When("User clicks on search Patient Record app")
	public void userVisitFindPatientRecordApp() throws Exception {
		findPatientPage = homePage.clickOnSearchPatientRecord();
	}
	
	@And("User searches for a patient {string} patientName and load their cover page")
	public void userSearchForPatient(String Reason) {
		findPatientPage.enterPatientName(Reason);
		findPatientPage.getFirstPatientIdentifier();
		findPatientPage.clickOnFirstPatient();
	}
	
	@And("User clicks 'Delete Patient'")
	public void userClickOnDeletePatient() {
		dashboardPage = findPatientPage.clickOnDeletePatient();
	}
	
	@Then("Patient deleted successfully message appears, and redirected to patient search page")
	public void loadFindPatientPage() {
		assertTrue(findPatientPage.hasSearchPatientRecord());
	}
}
