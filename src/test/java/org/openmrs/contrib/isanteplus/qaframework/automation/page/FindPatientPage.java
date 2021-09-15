package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class FindPatientPage extends Page {
	
	private static final String PAGE_URL = "/coreapps/findpatient/findPatient.page?app=coreapps.findPatient";
	
	private static final By PATIENT_SEARCH = By.id("patient-search");

	private static final By PATIENT_ID_SEARCH_RESULT = By.cssSelector("#patient-search-results-table tr:first-child td:first-child");	
	private static final By PATIENT_NAME_SEARCH_RESULT = By.cssSelector("#patient-search-results-table tbody tr:first-child td:nth-child(2)");
  private static final By DELETE_PATIENT_REASON = By.cssSelector("#delete-reason");
	
	private static final By DELETE_PATIENT_CONFIRM_BUTTON = By
	        .cssSelector("#delete-patient-creation-dialog > div.dialog-content > button.confirm.right");
	
	private static final By DELETE_PATIENT_CANCEL_BUTTON = By
	        .cssSelector("#delete-patient-creation-dialog > div.dialog-content > button.cancel");
	
	private static final By DELETE_PATIENT = By.id("org.openmrs.module.coreapps.deletePatient");
	
	private static String REASON = "patient discharged";
	
	private static final By PATIENT_ID_SEARCH_RESULT = By
	        .cssSelector("#patient-search-results-table tr:first-child td:first-child");
	
	private static final By PATIENT_NAME_SEARCH_RESULT = By
	        .cssSelector("#patient-search-results-table tbody tr:first-child td:nth-child(2)");
	
	public FindPatientPage(Page page) {
		super(page);
	}
	
	public void enterPatientName(String patientSearch) {
		setText(PATIENT_SEARCH, patientSearch);
	}
	
	public ClinicianFacingPatientDashboardPage clickOnFirstPatient() {
		clickOn(PATIENT_NAME_SEARCH_RESULT);
		return new ClinicianFacingPatientDashboardPage(this);
	}

	public ClinicianFacingPatientDashboardPage clickOnDeletePatient() {
		clickOn(DELETE_PATIENT);
		setTextToFieldNoEnter(DELETE_PATIENT_REASON, REASON);
		clickOn(DELETE_PATIENT_CONFIRM_BUTTON);
		return new ClinicianFacingPatientDashboardPage(this);	
	}

	public void search(String text) {
		setText(PATIENT_SEARCH, text);
	}

	/**
	 * Finds first record from the result table
	 * 
	 * @return patient id
	 */
	public String getFirstPatientIdentifier() {
		getFirstPatientName();
		return getText(PATIENT_ID_SEARCH_RESULT);

	public void enterSearchText(String text) {
		setTextToFieldNoEnter(PATIENT_SEARCH, text);

	}
	
	public String getFirstPatientName() {
		return getText(PATIENT_NAME_SEARCH_RESULT);
	}
	
	public String getFirstPatientIdentifier() {
		return getText(PATIENT_ID_SEARCH_RESULT);
	}
	
	@Override
	public String getPageUrl() {
		return PAGE_URL;
	}
}
