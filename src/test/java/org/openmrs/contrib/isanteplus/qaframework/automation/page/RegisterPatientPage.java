package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegisterPatientPage extends Page {
	
	private static final String PAGE_PATH = "/registrationapp/registerPatient.page?appId=isanteplus.registration";
	
	private final By CHECK_BOX_DATE_TODAY = By.id("checkbox-enable-registration-date");
	
	private static final By FIELD_BIRTHDAY_DAY = By.id("birthdateDay-field");
	
	private static final By FIELD_BIRTHDAY_MONTH = By.id("birthdateMonth-field");
	
	private static final By FIELD_BIRTHDAY_YEAR = By.id("birthdateYear-field");
	
	private static final By FIELD_ESTIMATED_YEARS = By.id("birthdateYears-field");
	
	private static final By FIELD_ERROR = By.id("field-error");
	
	private static final By FIELD_GIVEN_NAME = By.name("givenName");
	
	private static final By FIELD_FAMILY_NAME = By.name("familyName");
	
	private static final By FIELD_MIDDLE_NAME = By.name("middleName");
	
	private static final By DROP_DOWN_GENDER = By.id("gender-field");
	
	private static final By FIELD_ADDRESS_COUNTRY = By.name("country");
	
	private static final By FIELD_ADDRESS1 = By.id("address1");
	
	private static final By FIELD_ADDRESS2 = By.id("address2");
	
	private static final By FIELD_CITY_VILLAGE = By.id("cityVillage");
	
	private static final By FIELD_ST_CODE = By.name("Code ST");
	
	private static final By FIELD_NATIONAL_ID = By.name("Code National");
	
	private static final By LABEL_BIRTHDATE = By.id("birthdateLabel");
	
	private static final By LABEL_PHONE_NUMBER = By.id("phoneNumberLabel");
	
	private static final By LABEL_GENDER = By.id("genderLabel");
	
	private static final By LABEL_CONTACT_INFO_SECTION = By.id("contactInfo_label");
	
	private static final By LABEL_CONFIRM_SECTION = By.id("confirmation_label");
	
	private static final By LABEL_NAME = By.xpath("//*[@id='formBreadcrumb']/li[3]/ul/li[1]/span");
	
	private static final By LABEL_ST_CODE = By.xpath("//*[@id='formBreadcrumb']/li[4]/ul/li[1]/span");
	
	private static final By LABEL_NATIONAL_ID = By.xpath("//*[@id='formBreadcrumb']/li[4]/ul/li[2]/span");
	
	private static final By CONFIRM_SECTION = By.id("confirmation_label");
	
	private static final By BUTTON_SUBMIT = By.id("submit");
	
	private static final By LABEL_ADRESS = By.xpath("//*[@id='formBreadcrumb']/li[5]/ul/li[1]/span");
	
	private static final By COUNTRY_OPTION = By.xpath("//a[starts-with(@id, 'ui-id')]");
	
	private static final By VALIDATION_ERROR = By.xpath("//*[@id='validation-errors-content']/ul/li");
	
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
	
	public void enterNatinalId(String nationalId) {
		clickOn(LABEL_NATIONAL_ID);
		setText(FIELD_NATIONAL_ID, nationalId);
	}
	
	public void enterStCode(String stCode) {
		clickOn(LABEL_ST_CODE);
		setText(FIELD_ST_CODE, stCode);
	}
	
	public void enterAddres(String address) {
		clickOn(LABEL_ADRESS);
		setText(FIELD_ADDRESS_COUNTRY, address);
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
