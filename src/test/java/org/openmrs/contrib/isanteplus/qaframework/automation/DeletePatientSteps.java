package org.openmrs.contrib.isanteplus.qaframework.automation;

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




public class DeletePatientSteps extends TestBase {
	
	private ClinicianFacingPatientDashboardPage dashboardPage;
	
	private  FindPatientPage findPatientPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	

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
		homePage = loginPage.goToHomePage();
    }
	
	@Then("User clicks on search Patient Record app")
	public void userVisitFindPatientRecordApp() throws Exception {
		findPatientPage = homePage.clickOnSearchPatientRecord();
    }
	
	@And("Search for a patient {string} patientName and load their cover page")
	public void userSearchForPatient(String REASON) {
		findPatientPage.enterPatientName(REASON);
		findPatientPage.getFirstPatientIdentifier();
		findPatientPage.clickOnFirstPatient();
	}

    @Then("Click 'Delete Patient'")
	public void userClickOnDeletePatient(){
       dashboardPage.deletePatient();
	}
    
    @And("Enter reason")
    public void enterdeleteReason(String REASON) {
    	dashboardPage.enterReason(REASON);
    }
    
    @Then("Click ‘Confirmer’")
    public void confirmDeletingPatient() {
    	dashboardPage.clickConfirmPatient();
    }
     
}
