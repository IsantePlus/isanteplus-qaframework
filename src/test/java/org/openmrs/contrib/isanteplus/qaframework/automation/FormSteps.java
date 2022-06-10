package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HtmlFormPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientDashBoardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientHistoryFormPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.RemoteTestBase;
import org.openmrs.contrib.isanteplus.qaframework.util.TestsUtil;

public class FormSteps extends RemoteTestBase {

    private LoginPage loginPage;

    private FindPatientPage findPatientPage;

    private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;

    private PatientDashBoardPage patientDashBoardPage;

    private HtmlFormPage htmlPage;

    private PatientHistoryFormPage patientHistoryFormPage;

    private HomePage homePage;
    
    private String familyName = TestsUtil.generateRandomString();

	private String givenName = TestsUtil.generateRandomString();

	private String patientName = familyName + "  " + givenName;
	
	private String username = "admin";
	
	private String password = "Admin123";

	private String jsonData = "{\"resourceType\":\"Patient\",\"identifier\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/patient/identifier#location\",\"valueReference\":{\"reference\":\"Location/0a2c0967-2a56-41c9-9ad5-0bd959861b42\",\"type\":\"Location\",\"display\":\"CS de la Croix-des-Bouquets\"}}],\"use\":\"usual\",\"type\":{\"text\":\"Code ST\"},\"system\":\"http://localhost:8000/openmrs/fhir2/6-code-st\",\"value\":\"" + TestsUtil.generateCodeST() + "\"}],\"active\":true,\"name\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"family\":\"" + familyName + "\",\"given\":[\"" + givenName + "\"]}],\"gender\":\"male\",\"birthDate\":\"1971-04-11\",\"deceasedBoolean\":false,\"address\":[{\"id\":\"" + TestsUtil.generateRandomUUID() + "\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address\",\"extension\":[{\"url\":\"http://fhir.openmrs.org/ext/address#address1\",\"valueString\":\"Address17001\"}]}],\"use\":\"home\",\"city\":\"City7001\",\"state\":\"State7001\",\"postalCode\":\"47002\",\"country\":\"Country7001\"}]}";

	private String endPointToAppend = "ws/fhir2/R4/Patient/";

    @After(RunTest.HOOK.FORM)
    public void destroy() {
        quit();
    }

    @Before(RunTest.HOOK.FORM)
    public void setLoginPage() throws IOException {
    	TestsUtil.addPatient(endPointToAppend ,jsonData,username,password);
        System.out.println("..... form .......");
        loginPage = new LoginPage(getDriver());
    }

    @Given("user logs into Isanteplus system and goes to the Home page")
    public void clientVisitLoginPage() throws Exception {
        homePage = loginPage.goToHomePage();
    }

    @When("user searches for a patient and load their cover page")
    public void patientSearch() throws Exception {
    	String searchText = patientName;
        findPatientPage = homePage.clickOnSearchPatientRecord();
        findPatientPage.enterSearchText(searchText);
        clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
        clinicianFacingPatientDashboardPage.waitForPage();
    }

    @And("user Starts a consultation")
    public void startConsultation() throws Exception {
        clinicianFacingPatientDashboardPage.clickStartConsultation();
        patientDashBoardPage = clinicianFacingPatientDashboardPage.clickConfirm();
        Thread.sleep(5000);
    }

    @And("User is redirected to the forms tab")
    public void userSentToFormsTab() throws Exception {
        assertTrue(patientDashBoardPage.containsText("Soins de santé primaires"));
        assertTrue(patientDashBoardPage.containsText("Laboratoire/Dispensation"));
        assertTrue(patientDashBoardPage.containsText("Soins VIH"));
        assertTrue(patientDashBoardPage.containsText("PsychoSociale"));
    }

    @And("Click on the category name to display the forms")
    public void clickCategoryName() throws Exception {
        patientDashBoardPage.clickLaboratory();
    }

    @And("Click the form and the user is redirected to the forms page")
    public void clickOnFormAndUserRedirectedToFormPage() throws Exception {
        htmlPage = patientDashBoardPage.clickLaboratoryAnalysis();
        assertTrue(htmlPage.containsText("Analyses de laboratoire"));
        assertTrue(htmlPage.containsText("Mycobacteriologie"));
        assertTrue(htmlPage.containsText("Biochimie"));
    }

    @And("The required field “Date visite” should be filled with the current date")
    public void fieldDateShouldBeCurrentDate() throws Exception {
        assertEquals(htmlPage.getVisitDate().trim(), getCurrentDate());
    }

    @And("The form may consist of several sections. Click the section tab to display the fields")
    public void clickSectionsTab() throws Exception {
        htmlPage.clickTabUnderSection();
        Thread.sleep(5000);
    }

    @And("Click the Sauvegarder button to save the form")
    public void saveForm() throws Exception {
        htmlPage.clickSaveForm();
    }

    @And("A prompt that the form was saved successfully is displayed and the user is redirected to the forms tab")
    public void saveMessage() throws Exception {
        Thread.sleep(9000);
        assertTrue(patientDashBoardPage.containsText("Entré Analyse de Laboratoire pour sharif2"));
    }

    @Then("The form should be listed under Historique des formulaires")
    public void formListed() throws Exception {
        assertTrue(patientDashBoardPage.containsText("Analyse de Laboratoire"));
    }

    @And("Click on the recent consultation on the patient summary sheet")
    public void clickFormulaires() throws Exception {
        patientDashBoardPage = clinicianFacingPatientDashboardPage.clickRecentConsultation();
    }

    @And("The Form History page is displayed")
    public void formsHistoryPage() throws Exception {
        assertTrue(patientDashBoardPage.containsText("Historique des formulaires (Formulaires remplis précédemment)"));
    }

    @And("Click the consultation tab")
    public void clickFormName() throws Exception {
        patientDashBoardPage.clickConsulationTab();
    }

    @And("Click the Modifier button to open the form in EDIT mode")
    public void clickModifierPencilIcon() throws Exception {
        htmlPage = patientDashBoardPage.clickModifier();
    }

    @And("Make changes to the form")
    public void makeChangesToTheForm() throws Exception {
        htmlPage.checkHemogramme();
    }

    @Then("The form is saved and user redirected to the forms tab")
    public void formIsSaved() throws Exception {
        Thread.sleep(23000);
       // assertTrue(patientDashBoardPage.containsText("Modifié Analyse de Laboratoire pour sharif2"));
        assertTrue(patientDashBoardPage.containsText("Analyse de Laboratoire"));
    }

}
