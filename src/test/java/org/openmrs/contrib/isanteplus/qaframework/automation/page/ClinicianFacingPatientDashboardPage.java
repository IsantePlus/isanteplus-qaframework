package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class ClinicianFacingPatientDashboardPage extends Page {
	
	public static final String URL_PATH = "/coreapps/clinicianfacing/patient.page";
	
	private static final By CONSULTATION = By.xpath("/html/body/div[1]/div[3]/div[8]/div/div[3]/div/ul/li[1]/a");

	private static final By CONFIRM = By.xpath("/html/body/div[5]/div/div/div[2]/button[1]");

	private static final By RECENT_CONSULTATION = By.xpath("/html/body/div[1]/div[3]/div[8]/div/div[2]/div/div[2]/visitbyencountertype/ul/li/a");

	private static final By TERMINATE_CONSULTATION = By.xpath("/html/body/div[1]/div[3]/div[8]/div/div[3]/div/ul[1]/li/a");

	private static final By FORMULAIRES = By.xpath("/html/body/div[1]/div[3]/div[8]/div/div[3]/div/ul[2]/li[4]/a");

	private static final By CONFIRM_TERMINATION = By.xpath("/html/body/div[5]/div/div/div[2]/button[1]");

	private static final By PREVIOUS_CONSULTATION = By.id("org.openmrs.module.coreapps.createRetrospectiveVisit");

	private static final By CONFIRM_PREVIOUS_CONSULTATION = By.xpath("/html/body/div[5]/div/div/div[2]/button[2]");

	private static final By DATE_PREVIOUS_CONSULTATION = By.xpath("/html/body/div[2]/div[3]/table/tbody/tr[1]/td[1]");
	
	private static final By RECENT_VISITS = By.cssSelector("visitbyencountertype > ul > li:nth-child(1) > a");
	
	private static final By NEW_PATIENT_SUMMARY = By.id("isanteplusreports.patientSummary");
			
	private static final By COLUMN_VIST_ACTIONS = By.xpath("//*[@id='content']/div[8]/div/div[3]");
	
	protected By PATIENT_ID_HEADER = By.cssSelector("div.identifiers span");
	
	protected By PATIENT_NAME_HEADER = By.xpath("//*[@id='breadcrumbs']/li[2]");
	
	private static final By START_CONSULTATION = By.cssSelector("#content > div.container > div > div.action-container.column > div > ul > h3");

	private static final By START_CONFIRM_BUTTON = By.cssSelector("#start-visit-with-visittype-confirm > font > font");
	
	private static final By ACTIVE_VISITS = By.cssSelector("#coreapps-fr6425 > div.info-body > visitbyencountertype > ul > li:nth-child(1) > a");

	public ClinicianFacingPatientDashboardPage(Page parent) {
		super(parent);
	}
	
	@Override
	public String getPageUrl() {
		return URL_PATH;
	}
		
	public Boolean hasVistActionsColumn() {
		return hasElement(COLUMN_VIST_ACTIONS);
	}

	public PatientDashBoardPage clickRecentConsultation() {
		clickOn(RECENT_CONSULTATION);
		return new PatientDashBoardPage(this);
	}

	public void clickTerminateConsultation() {
		clickOn(TERMINATE_CONSULTATION);
	}

	public void clickConfirmTermination() {
		clickOn(CONFIRM_TERMINATION);
	}

	public PatientHistoryFormPage clickFormulaires() {
		clickOn(FORMULAIRES);
		return new PatientHistoryFormPage(this);
	}
	
	public PatientSummaryPage clickOnPatientSummary() {
		clickOn(NEW_PATIENT_SUMMARY);
		return new PatientSummaryPage(this);
	}

	public void clickStartConsultation() {
		clickOn(CONSULTATION);
	}

	public PatientDashBoardPage clickConfirm() {
		clickOn(CONFIRM);
		 return new PatientDashBoardPage(this);
	}
	
	private String trimPatientId(String id) {
		id = id.replace("Recent", "");
		if (id.indexOf("[") > 0) {
			id = id.split("\\[")[0];
		}
		if (id.indexOf(" ") > 0) {
			id = id.split(" ")[0];
		}
		return id;
	}
	
	public Boolean patientIdsMatch(String patientId) {
		return getText(PATIENT_ID_HEADER).contains(trimPatientId(patientId));
	}
	
	public String getPatientNames() {
		return getText(PATIENT_NAME_HEADER);
	}

	public void selectDatesForPreviousConsultation() {
		clickOn(DATE_PREVIOUS_CONSULTATION);
	}

	public void clickAddPreviousConsultation() {
		clickOn(PREVIOUS_CONSULTATION);
	}

	public PatientDashBoardPage clickConfirmPreviousConsultation() {
		clickOn(CONFIRM_PREVIOUS_CONSULTATION);
		return new PatientDashBoardPage(this);
	}
	
	public void ClickOnStartConsultation() {
		clickOn(START_CONSULTATION);
	}

	public ConsultationPage clickOnConfirmButton() {
		waitForElement(START_CONFIRM_BUTTON);
		clickOn(START_CONFIRM_BUTTON);
		return new ConsultationPage(this);
	}
	
	public Boolean hasRecentVisits() {
		return hasElement(ACTIVE_VISITS);
	}
	
	public PatientDashBoardPage clickOnRecentVisit() {
		clickOn(RECENT_VISITS);
		return new PatientDashBoardPage(this);
	}
}
