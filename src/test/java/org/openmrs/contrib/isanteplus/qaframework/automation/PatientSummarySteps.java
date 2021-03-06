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
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientSummaryPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class PatientSummarySteps extends RemoteTestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private String endPointToAppend = "ws/fhir2/R4/Patient/";

	private String familyName = TestsUtil.generateRandomString();

	private String givenName = TestsUtil.generateRandomString();
	
	private String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";
	
	private String username = "admin";

	private String password = "Admin123";
	
	private String name = familyName + " " + givenName;
	
	private PatientSummaryPage patientSummaryPage;
	
	private HomePage homePage;
	
	@Before(RunTest.HOOK.PATIENT_SUMMARY)
	public void setUp() throws IOException {
		TestsUtil.addPatient(endPointToAppend,jsonData,username,password);
		loginPage = new LoginPage(getDriver());
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
	
	@When("Search for a Patient")
	public void searchForPatientAndloadPatientDashboardPage() throws Exception {
		String searchText = name;
		findPatientPage = homePage.clickOnSearchPatientRecord();
		findPatientPage.enterPatientName(searchText);
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
	}
	
	@And("Select ???Patient Summary??? on the right hand side menu")
	public void clickOnPatientSummaryWidget() throws Exception {
		patientSummaryPage = clinicianFacingPatientDashboardPage.clickOnPatientSummary();
		patientSummaryPage.waitForPage();
	}
	
	@And("Check that the following exist")
	public void checkPatientSummary() throws Exception {
		assertTrue(patientSummaryPage.containsText("R????sum???? du Dossier M????dical"));
		assertTrue(patientSummaryPage.containsText("Informations d????mographiques"));
		assertTrue(patientSummaryPage.containsText("Visites/Fiches (dernier 6 mois et premi????re visite)"));
		assertTrue(patientSummaryPage.containsText("Examens cliniques"));
		assertTrue(patientSummaryPage.containsText("R????sultats de laboratoire"));
		assertTrue(patientSummaryPage.containsText("M????dicaments dispens????s"));
		assertTrue(patientSummaryPage.containsText("Derniers signes vitaux"));
		assertTrue(patientSummaryPage.containsText("Motifs de consultation"));
		assertTrue(patientSummaryPage.containsText("Impressions cliniques et diagnostiques"));	
	}
	
	@Then("Patient summary should display in pdf format on the screen")
	public void PatientSummaryDisplayed() {
		assertTrue(patientSummaryPage.containsText("Dernier test de charge virale"));
	}
}
