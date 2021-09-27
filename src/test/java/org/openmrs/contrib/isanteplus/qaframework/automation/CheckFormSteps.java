package org.openmrs.contrib.isanteplus.qaframework.automation;
import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ConsultationPage;
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

public class CheckFormSteps extends TestBase {
	
	private FindPatientPage findPatientPage;

	private ClinicianFacingPatientDashboardPage  clinicianFacingPatientDashboardPage;

	private LoginPage loginPage;
	
	private ConsultationPage consultationPage;

	private HomePage homePage;

	@Before(RunTest.HOOK.CHECK_FORMS)
	public void setUp() {
		loginPage = new LoginPage(getWebDriver());
	}

	@After(RunTest.HOOK.CHECK_FORMS)
	public void destroy() {
		quit();
	}

	@Given("User log in the system and load homePage")
	public void visitHomePage() throws Exception {
		System.out.println(".... Check forms......");
		homePage = loginPage.goToHomePage();
	}
	
	@When("Search for a patient {string}   and load their cover page")
	public void systemLoadSearchPatientApp(String  patientSearch) throws Exception {
	   findPatientPage = homePage.clickOnSearchPatientRecord();
	   findPatientPage.enterPatientName(patientSearch);
       clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	}
	
	@And("User Click ‘Start Consultation’ on the right hand menu")
    public void clickOnStartConsultationlink() {
    	consultationPage = clinicianFacingPatientDashboardPage.ClickOnStartConsultation();
    }
	
    @And("User Click ‘To confirm’")
	public void clickOnConfirmButton() {
		consultationPage = clinicianFacingPatientDashboardPage.clickOnConfirmButton();
	}
    
    @Then("Check that the following forms exist on the ‘Formulaire’ page")
    public void confirmFormAvailabilityOnConsultationPage() {
    	assertTrue(consultationPage.containsText("Soins de santé primaires"));
    	assertTrue(consultationPage.containsText("SSP - Consultation"));
    	assertTrue(consultationPage.containsText("Laboratoire/Dispensation"));
    	assertTrue(consultationPage.containsText("Saisie Première Visite Adult"));
    	assertTrue(consultationPage.containsText("Adhérence"));
    	assertTrue(consultationPage.containsText("Soins VIH"));
    }
    
}
