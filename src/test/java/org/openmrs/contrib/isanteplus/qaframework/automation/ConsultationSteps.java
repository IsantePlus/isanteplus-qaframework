package org.openmrs.contrib.isanteplus.qaframework.automation;

import static org.junit.Assert.assertTrue;

import org.openmrs.contrib.isanteplus.qaframework.RunTest;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.ClinicianFacingPatientDashboardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.FindPatientPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientDashBoardPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.test.TestBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConsultationSteps extends TestBase {

    private ClinicianFacingPatientDashboardPage clinicianFacingPatientDashboardPage;

    private FindPatientPage findPatientPage;

    private PatientDashBoardPage patientDashBoardPage;

    private LoginPage loginPage;

    private HomePage homePage;

    String patientName;

    String patientIdetifier;

    @Before(RunTest.HOOK.CONSULTATION)
    public void setUp() {
        System.out.println(".... consultation ......");
        loginPage = new LoginPage(getWebDriver());
    }

    @After(RunTest.HOOK.CONSULTATION)
    public void destroy() {
        quit();
    }

    @Given("user logs into Isanteplus application and proceeds to the Home page")
    public void userGoesToLoginPage() throws Exception {
        homePage = loginPage.goToHomePage();
    }

    @When("search for a patient and load their cover page {string}")
    public void searchForAPatient(String searchText) throws Exception {
        findPatientPage = homePage.clickOnSearchPatientRecord();
        findPatientPage.enterSearchText(searchText);
        clinicianFacingPatientDashboardPage = findPatientPage.clickOnFirstPatient();
        clinicianFacingPatientDashboardPage.waitForPage();
    }

    @And("Click ‘Demarrer Consultation’ under “Actions générales” menu on the right")
    public void clickStartConsulation() throws Exception {
        clinicianFacingPatientDashboardPage.clickStartConsultation();
    }

    @And("Click ‘Confirmer’")
    public void clickConfirmer() throws Exception {
        patientDashBoardPage = clinicianFacingPatientDashboardPage.clickConfirm();
        Thread.sleep(5000);
    }

    @Then("User is redirected to the Forms tab where new forms can be added and a list of history of forms is displayed")
    public void redirectedToForms() throws Exception {
        assertTrue(patientDashBoardPage.containsText("Historique des formulaires (Formulaires remplis précédemment)"));
        assertTrue(patientDashBoardPage.containsText("Formulaires"));
    }
}
