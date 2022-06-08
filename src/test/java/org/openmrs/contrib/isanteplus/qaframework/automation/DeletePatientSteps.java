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
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientDashBoardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class DeletePatientSteps extends RemoteTestBase {
	
	private ClinicianFacingPatientDashboardPage dashboardPage;
	
	private FindPatientPage findPatientPage;
	
	private PatientDashBoardPage patientDashBoardPage ;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	private String familyName = TestsUtil.generateRandomString();

	private String givenName = TestsUtil.generateRandomString();

	private String patientName = familyName + "  " + givenName;

	private String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	private String endPointToAppend = "ws/fhir2/R4/Patient/";
	
	private String username = "admin";
	
	private String password = "Admin123";
	
	private static String REASON = "patient discharged";
	
	@Before(RunTest.HOOK.DELETE_PATIENT)
	public void setUp() throws IOException {
		TestsUtil.addPatient(endPointToAppend ,jsonData,username,password);
		loginPage = new LoginPage(getDriver());
	}
	
	@After(RunTest.HOOK.DELETE_PATIENT)
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
	
	@And("User searches for a patient and load their cover page")
	public void userSearchForPatient() throws Exception {
		String name = patientName;
		findPatientPage.enterPatientName(name);
		Thread.sleep(2000);
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
