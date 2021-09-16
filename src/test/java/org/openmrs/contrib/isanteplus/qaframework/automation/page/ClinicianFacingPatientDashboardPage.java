package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class ClinicianFacingPatientDashboardPage extends Page {
	
	public static final String URL_PATH = "/coreapps/clinicianfacing/patient.page";
	
	private static final By START_VISIT = By.id("org.openmrs.module.coreapps.createVisit");
	
	private static final By SHOW_CONTACT_INFO = By.id("patient-header-contactInfo");
	
	private static final By EDIT_CONTACT_INFO = By.id("contact-info-inline-edit");
	
	private static final By EDIT_PATIENT = By.cssSelector("#edit-patient-demographics a");
	
	private static final By RECENT_VISITS = By.cssSelector("visitbyencountertype > ul > li:nth-child(1) > a");
	
	private static final By ALLERGIES_LINK = By.id("allergyui-editAllergies");
	
	private static final By REGISTRATION_SUMMARY_LINK = By.cssSelector("#clinicianFacingOverallActionsLink");
	
	private static final By GROWTH_CHART = By.cssSelector("#isanteplus\\.growthCharts");
	
	private static final By FORM_HISTORY = By.cssSelector("#isanteplus\\.formsHistory");
	
	private static final By LAB_HISTORY = By.cssSelector("#isanteplus\\.labHistory");
	
	private static final By DRUG_HISTORY = By.cssSelector("#isanteplus\\.drugsHistory");
	
	private static final By MERGE_VISIT = By.id("org.openmrs.module.coreapps.mergeVisits");
	
	private static final By MARK_PATIENT_DECEASED = By.id("org.openmrs.module.coreapps.markPatientDead");
	
	private static final By DELETE_PATIENT = By.id("org.openmrs.module.coreapps.deletePatient");
	
	private static final By VIRAL_LOAD_HISTORY = By.id("isanteplus.viralLoadHistory");
	
	private static final By PATIENT_SUMMARY = By.id("isanteplusreports.patientSummary");
	
	private static final By DIAGNOSIS_HISTORY = By.id("isanteplus.diagnosis.label");
	
	private static final By END_VISIT = By.id("referenceapplication.realTime.endVisit");
	
	private static final By COLUMN_VIST_ACTIONS = By.xpath("//*[@id='content']/div[8]/div/div[3]");
	
	protected By PATIENT_ID_HEADER = By.cssSelector("div.identifiers span");
	
	protected By PATIENT_NAME_HEADER = By.xpath("//*[@id='breadcrumbs']/li[2]");
	
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
}
