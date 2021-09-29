package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class PatientDashBoardPage extends Page {

    private static final String DASHBOARD_PATH = "/coreapps/patientdashboard/patientDashboard.page?patientId=7&visitId=7";
    private static final By PRIMARY_CARE_FORM = By.cssSelector("#togglePrimaryCareForms");
    private static final By LAB_FORMS = By.cssSelector("#toggleLabForms");
    private static final By HIV_CARE_FORMS = By.cssSelector("#toggleHivCareForms");
    private static final By PSYCHO_SOCIAL_FORMS = By.cssSelector("#togglePsychoSocialForms");
    private static final By OTHER_FORMS = By.cssSelector("#toggleOtherForms");

    public PatientDashBoardPage(Page parent) {
        super(parent);
    }
    
    public void clickOnPrimaryCareFormLink() {
    	clickOn(PRIMARY_CARE_FORM);
    }
    
    public void clickOnLabFormsLink() {
    	clickOn(LAB_FORMS);
    }
    
    public void clickOnHivCareFormsLink() {
    	clickOn(HIV_CARE_FORMS);
    }
    
    public void clickOnPsychoSocialFormsLink() {
    	clickOn(PSYCHO_SOCIAL_FORMS);
    }
    
    public void clickOnOtherFormsLink() {
    	clickOn(OTHER_FORMS);
    }
    
    @Override
    public String getPageUrl() {
        return DASHBOARD_PATH;
    }
}
