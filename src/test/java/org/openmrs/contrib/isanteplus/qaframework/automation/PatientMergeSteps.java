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
import org.openmrs.contrib.isanteplus.qaframework.automation.page.DataManagementPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.MergePatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class PatientMergeSteps extends RemoteTestBase {

	private HomePage homePage;

	private LoginPage loginPage;

	private DataManagementPage dataManagementPage;

	private MergePatientPage mergePatientsPage;
	
	private  String url = "https://iplus3.openelis-global.org/openmrs/ws/fhir2/R4/Patient/";

	private String username = "admin";

	private String password = "Admin123";
	
	private String jsonData1 = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code National\"},\"system\":\"http://localhost:8000/openmrs/fhir2/5-code-national\",\"value\":\"" + TestsUtil.generateCodeNational() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"Chris\",\"given\":[\"Keneth\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";
	 
	private String jsonData2 = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code National\"},\"system\":\"http://localhost:8000/openmrs/fhir2/5-code-national\",\"value\":\"" + TestsUtil.generateCodeNational() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"Timothy\",\"given\":[\"Geofrey\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	private ClinicianFacingPatientDashboardPage dashboardPage;

	@Before(RunTest.HOOK.PATIENT_MERGE)
	public void setUp() throws IOException {
		TestsUtil.addPatient(url,jsonData1,username,password);
		
		TestsUtil.addPatient(url,jsonData2,username,password);
		
		loginPage = new LoginPage(getDriver());
	}

	@After(RunTest.HOOK.PATIENT_MERGE)
	public void destroy() {
		quit();
	}

	@Given("User log into the system")
	public void userVisitLoginPage() throws Exception {
		System.out.println(".... Patient Merge.......");
		homePage = loginPage.goToHomePage();
	}

	@When("User clicks on data management app")
	public void UserClickOnDataManagementApp() {
		dataManagementPage = homePage.clickOnDataManagementPage();
		dataManagementPage.waitForPage();
	}

	@And("User Select ‘Merge electronic patient records")
	public void clickOnMergePatientRecord() {
		mergePatientsPage = dataManagementPage.goToMergePatientPage();
	}

	@Then("User enter {string} first patient id")
	public void enterPatient1(String firstPatientId) {
		dataManagementPage.enterPatient1(firstPatientId);
	}

	@And("User enter {string} second patient id")
	public void enterPatient2(String secondPatientId) {
		dataManagementPage.enterPatient2(secondPatientId);
	}

	@Then("User clicks on continue")
	public void clickOnContinue() throws InterruptedException {
		dataManagementPage.clickOnContinue();
		dataManagementPage.waitForPageToLoad();
	}

	@And("User select the preferred record")
	public void clickOnMergePatient() {
		mergePatientsPage.clickOnMergePatient();
		mergePatientsPage.waitForPageToLoad();
	}

	@And("User Click ‘Yes, continue’")
	public void clickOnContinueButton() {
		dashboardPage = mergePatientsPage.clickOnContinue();
		dashboardPage.waitForPageToLoad();
	}

	@Then("Patient’s cover page with the data for the selected record is loaded")
	public void loadPatientDashboardPage() {
		//assertTrue(dashboardPage.containsText("visits"));
	}

}
