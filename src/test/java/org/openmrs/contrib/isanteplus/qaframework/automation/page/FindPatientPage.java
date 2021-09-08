package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class FindPatientPage extends Page {
	
	private static final By PATIENT_SEARCH = By.id("patient-search");
	private static final By PATIENT_ID_SEARCH_RESULT = By.cssSelector("#patient-search-results-table tr:first-child td:first-child");	
	private static final By PATIENT_NAME_SEARCH_RESULT = By.cssSelector("#patient-search-results-table tbody tr:first-child td:nth-child(2)");
	
	public FindPatientPage(Page parent) {
		super(parent);
	}
	
	public void enterPatient(String patient) {
		setTextToFieldNoEnter(PATIENT_SEARCH, patient);
	}
	
	public  ClinicianFacingPatientDashboardPage clickOnFirstPatient() {
		clickOn(PATIENT_NAME_SEARCH_RESULT);
		return new ClinicianFacingPatientDashboardPage(this);
	}
	
	public void search(String text) {
		setTextToFieldNoEnter(PATIENT_SEARCH, text);
	}
	
	/**
	 * Finds first record from the result table
	 * 
	 * @return patient id
	 */
	public String getFirstPatientIdentifier() {
		getFirstPatientName();
		return findElement(PATIENT_ID_SEARCH_RESULT).getText();
	}
	
	public String getFirstPatientName() {
		return findElement(PATIENT_NAME_SEARCH_RESULT).getText();
	}

	@Override
	public String getPageUrl() {
		return "/coreapps/findpatient/findPatient.page";
	}
	
}
