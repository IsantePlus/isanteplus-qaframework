package org.openmrs.contrib.isanteplus.qaframework.automation.page;


public class ConsultationPage extends Page {

	public ConsultationPage(Page page) {
		super(page);
	
	}

	@Override
	public String getPageUrl() {
	
		return "/coreapps/patientdashboard/patientDashboard.page";
	}
	
}
