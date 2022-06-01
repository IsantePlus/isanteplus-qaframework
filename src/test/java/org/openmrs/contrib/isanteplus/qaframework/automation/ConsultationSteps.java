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

public class ConsultationSteps extends RemoteTestBase {

    private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;

    private FindPatientPage findPatientPage;

    private PatientDashBoardPage patientDashBoardPage;

    private LoginPage loginPage;

    private HomePage homePage;
    
	private String endPointToAppend = "ws/fhir2/R4/Patient/";
	
	private String familyName = TestsUtil.generateRandomString();

	private String givenName = TestsUtil.generateRandomString();

	private String name = familyName + " " + givenName;
	
	private String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	private String username = "admin";

	private String password = "Admin123";

    String patientName;

    String patientIdetifier;

    @Before(RunTest.HOOK.CONSULTATION)
    public void setUp() throws IOException {
    	TestsUtil.addPatient(endPointToAppend ,jsonData,username,password);
        System.out.println("....consultation......");
        loginPage = new LoginPage(getDriver());
    }

    @After(RunTest.HOOK.CONSULTATION)
    public void destroy() {
        quit();
    }

    @Given("user logs into Isanteplus application and proceeds to the Home page")
    public void userGoesToLoginPage() throws Exception {
        homePage = loginPage.goToHomePage();
    }

    @When("search for a patient and load their cover page")
    public void searchForAPatient() throws Exception {
    	String searchText = name;
        findPatientPage = homePage.clickOnSearchPatientRecord();
        findPatientPage.enterSearchText(searchText);
        clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
    }
    
    @And("Click ‘Demarrer Consultation’ on the right")
    public void clickStartConsulation() throws Exception {
    	Thread.sleep(5000);
    	clinicianFacingPatientDashboardPage.ClickOnStartConsultation();
    }
    
    @And("Click ‘Confirmer’")
    public void clickConfirmer() throws Exception {
    	patientDashBoardPage = clinicianFacingPatientDashboardPage.clickConfirm();
    	Thread.sleep(2000);
	
   }
    
    @Then("User is redirected to the Forms tab where new forms can be added and a list of history of forms is displayed")
    public void redirectedToForms() throws Exception {
        assertTrue(patientDashBoardPage.containsText("Historique des formulaires (Formulaires remplis précédemment)"));
        assertTrue(patientDashBoardPage.containsText("Formulaires"));
    }
    
    @And("Click “Ajouter consultation antérieure” under “Actions générales” menu on the right")
    public void clickAddPreviousConsultation() throws Exception {
        clinicianFacingPatientDashboardPage.clickAddPreviousConsultation();
    }

    @And("On the Ajouter consultation antérieure pop up enter the Date de début and the Date de fin")
    public void enterDates() throws Exception {
        clinicianFacingPatientDashboardPage.selectDatesForPreviousConsultation();
    }

    @And("Click Confirm")
    public void clickConfirm() throws Exception {
        patientDashBoardPage = clinicianFacingPatientDashboardPage.clickConfirmPreviousConsultation();
        Thread.sleep(5000);
    }
  

}
