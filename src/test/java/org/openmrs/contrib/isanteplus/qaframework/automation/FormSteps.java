package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HtmlFormPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientDashBoardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientHistoryFormPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormSteps extends TestBase {

    private LoginPage loginPage;

    private FindPatientPage findPatientPage;

    private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;

    private PatientDashBoardPage patientDashBoardPage;

    private HtmlFormPage htmlPage;

    private PatientHistoryFormPage patientHistoryFormPage;

    private HomePage homePage;

    @After(RunTest.HOOK.FORM)
    public void destroy() {
        quit();
    }

    @Before(RunTest.HOOK.FORM)
    public void setLoginPage() {
        System.out.println("..... form .......");
        loginPage = new LoginPage(getWebDriver());
    }

    @Given("user logs into Isanteplus system and goes to the Home page")
    public void clientVisitLoginPage() throws Exception {
        homePage = loginPage.goToHomePage();
    }

    @When("user searches for a patient and load their cover page {string}")
    public void patientSearch(String searchText) throws Exception {
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
        assertTrue(patientDashBoardPage.containsText("Modifié Analyse de Laboratoire pour sharif2"));
        assertTrue(patientDashBoardPage.containsText("Analyse de Laboratoire"));
    }

    @And("Click Formulaires under Actions generales menu on the right")
    public void clickFormulairesTab() throws Exception {
        patientHistoryFormPage = clinicianFacingPatientDashboardPage.clickFormulaires();
    }

    @And("The Form History page is now displayed")
    public void labFormHistory() throws Exception {
        assertTrue(patientHistoryFormPage.containsText("Analyse de Laboratoire"));
        assertTrue(patientHistoryFormPage.containsText("Historique des formulaires (Formulaires remplis précédemment)"));
    }

    @And("Check the box on the Enlever column")
    public void checkEnleverColumn() throws Exception {
        patientHistoryFormPage.checkEnlever();
    }

    @And("Click the Supprimer selectionnee button to delete the selected forms")
    public void clickSupprimerSelectionneeButton() throws Exception {
        patientHistoryFormPage.clickSupprimerButton();
        patientHistoryFormPage.acceptAlert();
        patientHistoryFormPage.acceptAlert();
    }

    @Then("The form is deleted and the form history reloaded")
    public void formIsDeleted() throws Exception {
        assertTrue(patientHistoryFormPage.containsText("Supprimé"));
        assertFalse(patientHistoryFormPage.containsText("Inconnu"));
        assertTrue(patientHistoryFormPage.containsText("Historique des formulaires (Formulaires remplis précédemment)"));
    }

    public String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }  
}
