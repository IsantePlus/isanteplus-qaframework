package org.openmrs.contrib.isanteplus.qaframework.automation;
import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientDashBoardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class FormcheckTest extends TestBase {
	  private LoginPage loginPage;

	  

	    private FindPatientPage findPatientPage;

	    private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;

	    private PatientDashBoardPage patientDashBoardPage;



	    private HomePage homePage;

	    @After(RunTest.HOOK.FORM)
	    public void destroy() {
	        quit();
	    }

	    @Before(RunTest.HOOK.FORM)
	    public void setLoginPage() {
	        System.out.println("..... form .......");
	        loginPage = new LoginPage(getWebDriver());
	    }

	    @Given("user logs into Isanteplus system and goes to the Home page")
	    public void clientVisitLoginPage() throws Exception {
	        homePage = loginPage.goToHomePage();
	    }

	    @When("user searches for a patient and load their cover page {string}")
	    public void patientSearch(String searchText) throws Exception {
	        findPatientPage = homePage.clickOnSearchPatientRecord();
	        findPatientPage.enterSearchText(searchText);
	        clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	        clinicianFacingPatientDashboardPage.waitForPage();
	        Thread.sleep(5000);
	    }
	    
	    @And("user Starts a consultation")
	    public void startConsultation() throws Exception {
	        clinicianFacingPatientDashboardPage.clickStartConsultation();
	        patientDashBoardPage = clinicianFacingPatientDashboardPage.clickConfirm();
	    }
}
