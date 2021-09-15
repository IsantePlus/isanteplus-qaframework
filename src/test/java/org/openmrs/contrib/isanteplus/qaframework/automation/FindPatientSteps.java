package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

public class FindPatientSteps extends TestBase {
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private FindPatientPage findPatientPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	String patientName;
	
	String patientIdetifier;
	
	@Before(RunTest.HOOK.FINDPATIENT)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}
	
	@After(RunTest.HOOK.FINDPATIENT)
	public void destroy() {
		quit();
	}
	
	@Given("User logs in the system")
	public void userVisitLoginPage() throws Exception {
		homePage = loginPage.goToHomePage();
	}
	
	@And("From the home page, User clicks 'search patient record'")
	public void clickOnSearchPatientRecord() {
		findPatientPage = homePage.clickOnSearchPatientRecord();
	}
	
	@And("User Enters search Text {string} in 'Patient Search' box")
	public void enterPatientName(String searchText) {
		findPatientPage.enterSearchText(searchText);
	}
	
	@Then("User Identifies patient in list")
	public void returnResults() throws InterruptedException {
		Thread.sleep(1000);
		assertNotNull(findPatientPage.getFirstPatientIdentifier());
		patientIdetifier = findPatientPage.getFirstPatientIdentifier().trim();
		assertNotNull(findPatientPage.getFirstPatientName());
		patientName = findPatientPage.getFirstPatientName().trim();
	}
	
	@When("User Clicks row with the patient being searching for")
	public void clickFirstPatient() {
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
		clinicianFacingPatientDashboardPage.waitForPage();
	}
	
	@Then("Selected patient’s 'Cover Page' will be displayed for the searchType {string}")
	public void loadPatientSelectedCoverPage(String searchType) {
		if (searchType.trim().equals("Names")) {
			assertTrue(clinicianFacingPatientDashboardPage.patientIdsMatch(patientIdetifier));
		} else if (searchType.trim().equals("ST Code")) {
			assertEquals(clinicianFacingPatientDashboardPage.getPatientNames().trim(), patientName);
		}
	}
}
