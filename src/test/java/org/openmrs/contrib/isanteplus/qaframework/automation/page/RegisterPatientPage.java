package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class RegisterPatientPage extends Page {
	
	private static final String PAGE_PATH = "/registrationapp/registerPatient.page?appId=isanteplus.registration";
	
	private final By CHECK_BOX_DATE_TODAY = By.id("checkbox-enable-registration-date");
		
	private static final By FIELD_ESTIMATED_YEARS = By.id("birthdateYears-field");
		
	private static final By FIELD_GIVEN_NAME = By.name("givenName");
	
	private static final By FIELD_FAMILY_NAME = By.name("familyName");
		
	private static final By DROP_DOWN_GENDER = By.id("gender-field");
	
	private static final By FIELD_ADDRESS_COUNTRY = By.name("country");
	
	private static final By FIELD_ST_CODE = By.name("Code ST");
	
	private static final By FIELD_NATIONAL_ID = By.name("Code National");
	
	private static final By LABEL_BIRTHDATE = By.id("birthdateLabel");
		
	private static final By LABEL_GENDER = By.id("genderLabel");
			
	private static final By LABEL_NAME = By.xpath("//*[@id='formBreadcrumb']/li[3]/ul/li[1]/span");
	
	private static final By LABEL_ST_CODE = By.xpath("//*[@id='formBreadcrumb']/li[5]/ul/li[1]/span");
	
	private static final By LABEL_NATIONAL_ID = By.xpath("//*[@id='formBreadcrumb']/li[5]/ul/li[2]/span");
	
	private static final By CONFIRM_SECTION = By.id("confirmation_label");
	
	private static final By BUTTON_SUBMIT = By.id("submit");
	
		private static final By LABEL_ADDRESS = By.xpath("//*[@id='contactInfo_label']//following::ul/li[1]/span");

	private static final By VALIDATION_ERROR = By.xpath("//*[@id='validation-errors-content']/ul/li");

	private static final By FIELD_BIRTHPLACE = By.name("obsgroup.CIEL:165194.obs.CIEL:165198"); // By.xpath("//*[@id='placeOfBirth-conainer']/div/p[2]/input");

	private static final By LABEL_BIRTHPLACE = By.id("birthPlace_label");

	public RegisterPatientPage(Page parent) {
		super(parent);
	}
	
	@Override
	public String getPageUrl() {
		return PAGE_PATH;
	}

	public void enterGivenName(String givenName) {
		clickOn(LABEL_NAME);
		setText(FIELD_GIVEN_NAME, givenName);
	}
	
	public void enterFamilyName(String familyName) {
		clickOn(LABEL_NAME);
		setText(FIELD_FAMILY_NAME, familyName);
	}
	
	public void enterDateOfBirth(String age) {
		clickOn(LABEL_BIRTHDATE);
		setText(FIELD_ESTIMATED_YEARS, age);
	}
	
	public void selectGender(String gender) {
		clickOn(LABEL_GENDER);
		// selectFrom(DROP_DOWN_GENDER, gender);
		selectOptionFromDropDown(DROP_DOWN_GENDER);
	}
	
	public void enterNationalId(String nationalId) {
		clickOn(LABEL_NATIONAL_ID);
		setText(FIELD_NATIONAL_ID, nationalId);
	}
	
	public void enterStCode(String stCode) {
		clickOn(LABEL_ST_CODE);
		setText(FIELD_ST_CODE, stCode);
	}
	
	public void enterAddress(String address) {
		clickOn(LABEL_ADDRESS);
		setText(FIELD_ADDRESS_COUNTRY, address);
	}

	public void enterBirthplace(String address) {
		clickOn(LABEL_BIRTHPLACE);
		setText(FIELD_BIRTHPLACE, address);
	}

	public ClinicianFacingPatientDashboardPage savePatient() {
		clickOn(CONFIRM_SECTION);
		clickOn(BUTTON_SUBMIT);
		return new ClinicianFacingPatientDashboardPage(this);
	}
	
	public Boolean registrationDateIsChecked() {
		return isChecked(CHECK_BOX_DATE_TODAY);
	}
	
	public Boolean hasValidationError() {
		return hasElementWithoutWait(VALIDATION_ERROR);
	}
	
	public String getValidationError() {
		return getText(VALIDATION_ERROR);
	}
}
