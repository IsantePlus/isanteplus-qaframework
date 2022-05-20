package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class FindPatientSteps extends RemoteTestBase {
	
	private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;
	
	private FindPatientPage findPatientPage;
	
	private LoginPage loginPage;
	
	private HomePage homePage;
	
	private String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"Joel\",\"given\":[\"Ken\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	private  String url = "https://iplus3.openelis-global.org/openmrs/ws/fhir2/R4/Patient/";

	private String username = "admin";

	private String password = "Admin123";
	
	String patientName;
	
	String patientIdetifier;
	
	@Before(RunTest.HOOK.FIND_PATIENT)
	public void setUp() throws IOException {
		TestsUtil.addPatient(url,jsonData,username,password);
		loginPage = new LoginPage(getDriver());
	}
	
	@After(RunTest.HOOK.FIND_PATIENT)
	public void destroy() {
		quit();
	}
	
	@Given("User logs in the system")
	public void userVisitLoginPage() throws Exception {
		System.out.println(".... Patient Search......");
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
	public void clickFirstPatient() throws InterruptedException {
		clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
		clinicianFacingPatientDashboardPage.waitForPage();
	}
	
	@Then("Selected patient’s 'Cover Page' will be displayed for the searchType {string}")
	public void loadPatientSelectedCoverPage(String searchType) {
		assertTrue(clinicianFacingPatientDashboardPage.patientIdsMatch(patientIdetifier));
	}
	
}
