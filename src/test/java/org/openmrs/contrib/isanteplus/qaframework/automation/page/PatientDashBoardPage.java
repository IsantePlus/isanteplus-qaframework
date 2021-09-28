package org.openmrs.contrib.isanteplus.qaframework.automation.page;

public class PatientDashBoardPage extends Page {

    private static final String DASHBOARD_PATH = "/coreapps/patientdashboard/patientDashboard.page?patientId=7&visitId=7";


    public PatientDashBoardPage(Page parent) {
        super(parent);
    }

    @Override
    public String getPageUrl() {
        return DASHBOARD_PATH;
    }
}