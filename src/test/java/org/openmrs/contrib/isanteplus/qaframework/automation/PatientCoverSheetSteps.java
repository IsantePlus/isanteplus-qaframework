package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class PatientCoverSheetSteps extends RemoteTestBase {
	
	private FindPatientPage findPatientPage;
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	private String endPointToAppend = "ws/fhir2/R4/Patient/";

	private String familyName = TestsUtil.generateRandomString();

	private String givenName = TestsUtil.generateRandomString();

	private String name = familyName + " " + givenName;

	private String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	private String username = "admin";

	private String password = "Admin123";
	
	@Before(RunTest.HOOK.PATIENT_COVER_SHEET)
	public void setUp() throws IOException {
		TestsUtil.addPatient(endPointToAppend,jsonData,username,password);
		loginPage = new LoginPage(getDriver());
	}
	
	@After(RunTest.HOOK.PATIENT_COVER_SHEET)
	public void destroy() {
		quit();
	}
	
	@Given("system user logs into Isanteplus application and goes to the Home page")
	public void systemUserVisitLoginPage() throws Exception {
		System.out.println(".... Patient Cover sheet......");
		homePage = loginPage.goToHomePage();
	}
	
	@When("Search for and select Patient")
	public void searchForPatientAndloadDashboardPage() throws Exception {
		String patientName = name;
		findPatientPage = homePage.clickOnSearchPatientRecord();
		findPatientPage.enterPatientName(patientName);
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
		clinicianFacingPatientDashboardPage.waitForPage();
	}
	
	@Then("Selected patient’s ‘Cover Sheet’ will be displayed with all the right details")
	public void loadPatientDashboardPage() {
		assertTrue(clinicianFacingPatientDashboardPage.containsText("DIAGNOSTICS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Signes vitaux"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("RECENT VISITS"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Alerte des Patients"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("ALLERGIES"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Dernier test de charge virale"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Courbe de Poids"));
		assertTrue(clinicianFacingPatientDashboardPage.containsText("Courbe IMC"));
	}
}
