package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ConsultationPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientDashBoardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;

public class CheckFormSteps extends RemoteTestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private ConsultationPage consultationPage;
	
	private PatientDashBoardPage patientDashBoardPage;
	
	private HomePage homePage;
	
	@Before(RunTest.HOOK.CHECK_FORMS)
	public void setUp() {
		loginPage = new LoginPage(getDriver());
	}
	
	@After(RunTest.HOOK.CHECK_FORMS)
	public void destroy() {
		quit();
	}
	
	@Given("User log in the system and load homePage")
	public void visitHomePage() throws Exception {
		System.out.println(".......Check forms......");
		homePage = loginPage.goToHomePage();
	}
	
	@When("Search for a patient {string}   and load their cover page")
	public void systemLoadSearchPatientApp(String patientSearch) throws Exception {
		findPatientPage = homePage.clickOnSearchPatientRecord();
		findPatientPage.enterPatientName(patientSearch);
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	}
	
	@And("User Click ‘Start Consultation’ on the right hand menu")
	public void clickOnStartConsultationlink() {
		clinicianFacingPatientDashboardPage.clickStartConsultation();
	}
	
	@And("User Click ‘To confirm’")
	public void clickOnConfirmButton() {
		patientDashBoardPage = clinicianFacingPatientDashboardPage.clickConfirm();
		patientDashBoardPage.waitForPageToLoad();
	}
	
	@Then("Check that the following forms exist on the ‘Formulaire’ page")
	public void confirmFormAvailabilityOnConsultationPage() {
		patientDashBoardPage.clickOnPrimaryCareFormLink();
		assertTrue(patientDashBoardPage.containsText("SSP - Première Consultation"));
		assertTrue(patientDashBoardPage.containsText("SSP - Consultation"));
		
		patientDashBoardPage.clickOnLabFormsLink();
		assertTrue(patientDashBoardPage.containsText("Analyse de Laboratoire"));
		assertTrue(patientDashBoardPage.containsText("Ordonnance Médicale Adulte"));
		
		patientDashBoardPage.clickOnHivCareFormsLink();
		assertTrue(patientDashBoardPage.containsText("Saisie Première Visite Adult"));
		assertTrue(patientDashBoardPage.containsText("Visite de Suivi"));
		assertTrue(patientDashBoardPage.containsText("Adhérence"));
		
		patientDashBoardPage.clickOnPsychoSocialFormsLink();
		assertTrue(patientDashBoardPage.containsText("Fiche Psychosociale Adulte"));
		
		patientDashBoardPage.clickOnOtherFormsLink();
		assertTrue(patientDashBoardPage.containsText("Vaccination"));
		assertTrue(patientDashBoardPage.containsText("Rapport d'arrêt du VIH/SIDA"));
		assertTrue(patientDashBoardPage.containsText("Imagerie et Autres"));
		assertTrue(patientDashBoardPage.containsText("Visite à domicile"));
		
	}
	
}
