package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

/**
 * This class represents the Home page
 */
public class HomePage extends Page {
	
	private RegisterPatientPage registerPatientPage;

	private final By LINK_LOGOUT = By.className("logout");

	private final String PATH_HOME = "/referenceapplication/home.page";
	private static final By FIND_PATIENT_RECORD = By.id("coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension");
	private static final By ACTIVE_VISITS_APP_ID = By.id("org-openmrs-module-coreapps-activeVisitsHomepageLink-org-openmrs-module-coreapps-activeVisitsHomepageLink-extension");
	private static final By ATOM_FEED_APP_ID = By.id("atomfeed-homepage-atomfeed-homepage-extension");
	private static final By REPORT_APP_ID = By.id("reportingui-reports-homepagelink-reportingui-reports-homepagelink-extension");
	private static final By DATA_MANAGEMENT = By.cssSelector("#coreapps-datamanagement-homepageLink-coreapps-datamanagement-homepageLink-extension");
	private static final By CONFIGURE_METADATA = By.cssSelector("#org-openmrs-module-adminui-configuremetadata-homepageLink-org-openmrs-module-adminui-configuremetadata-homepageLink-extension");
    private static final By SYSTEM_ADMINISTRATION = By.cssSelector("coreapps-systemadministration-homepageLink-coreapps-systemadministration-homepageLink-extension");
	
	private final By REGISTER_PATIENT = By.id(
	    "referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension");
	
	public HomePage(Page page) {
		super(page);
	}
	
	@Override
	public String getPageUrl() {
		return PATH_HOME;
	}
	
	public Boolean hasLogOutLink() {
		return hasElement(LINK_LOGOUT);
	}
	
	public void clickLogout() {
		clickOn(LINK_LOGOUT);
	}

	public RegisterPatientPage clickRegisterPatientApp() {
		clickOn(REGISTER_PATIENT);
		return new RegisterPatientPage(this);
	}

	public FindPatientPage goToFindPatientRecord() {
		clickOn(FIND_PATIENT_RECORD);
		return new FindPatientPage(this);
	}
}
