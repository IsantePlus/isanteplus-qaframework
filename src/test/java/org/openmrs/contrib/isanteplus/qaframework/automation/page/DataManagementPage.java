package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class DataManagementPage extends Page {
	
	private final static By MERGE_PATIENT_ELECTRONIC_RECORD = By.id("coreapps-mergePatientsHomepageLink-app");
	
	private final static By PATIENT_ID_1 = By.id("patient1-text");
	
	private final static By PATIENT_ID_2 = By.id("patient2-text");
	
	private final static By CONFIRM_BUTTON = By.id("confirm-button");
	
	private final static By YES_CONTINUE = By.id("second-patient");
	
	private final static By CANCEL_BUTTON = By.id("cancel-button");
	
	private final static By PATIENT_SEARCH = By.id("patient-search");
	
	public DataManagementPage(Page parent) {
		super(parent);
	}
	
	public MergePatientPage goToMergePatientPage() {
		clickOn(MERGE_PATIENT_ELECTRONIC_RECORD);
		return new MergePatientPage(this);
	}
	
	public void enterPatient1(String patient1) {
		setText(PATIENT_ID_1, patient1);
	}
	
	public void enterPatient2(String patient2) {
		setText(PATIENT_ID_2, patient2);
	}
	
	public PatientVisitsDashboardPage clickOnContinue() {
		waitForElementToBeEnabled(CONFIRM_BUTTON);
		clickOn(CONFIRM_BUTTON);
		return new PatientVisitsDashboardPage(this);
	}
	
	public void clickOnMergePatient() {
		waitForElement(YES_CONTINUE);
		clickOn(YES_CONTINUE);
	}
	
	public void clickOnCancelButton() {
		clickOn(CANCEL_BUTTON);
	}
	
	public void waitCount() {
		clickOn(CONFIRM_BUTTON);
	}
	
	public void searchId(String id) {
		setTextToFieldNoEnter(PATIENT_SEARCH, id);
	}
	
	public String search() {
		return findElement(PATIENT_SEARCH).getText();
	}
	
	@Override
	public String getPageUrl() {
		return "/datamanagement/dataManagement.page";
	}
	
}
