package org.openmrs.contrib.isanteplus.qaframework.automation.page;


public class PatientSummaryPage extends Page {

	public PatientSummaryPage(Page page) {
		super(page);

	}

	@Override
	public String getPageUrl() {
	
		return "/isanteplusreports/patientSummary.page";
	}
	
}
