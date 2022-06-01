package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

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
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class CheckFormSteps extends RemoteTestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private ConsultationPage consultationPage;
	
	private PatientDashBoardPage patientDashBoardPage;
	
	private HomePage homePage;
	
	String familyName = TestsUtil.generateRandomString();
	
	String givenName = TestsUtil.generateRandomString();
	
	String name = familyName + "  " + givenName;
	
	String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";
    
	String endPointToAppend = "ws/fhir2/R4/Patient/";

	private String username = "admin";

	private String password = "Admin123";
	
	@Before(RunTest.HOOK.CHECK_FORMS)
	public void setUp() throws IOException {
		TestsUtil.addPatient(endPointToAppend,jsonData,username,password);
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
	
	@When("Search for a patient and load their 'Cover Page'")
	public void systemLoadSearchPatientApp() throws Exception {
		String patientSearch = name;
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
