package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class AccountsPage extends Page {
	
	private static final String PAGE_PATH = "adminui/systemadmin/accounts/account.page?personId=1&";
		
	private static final By MODIFIER = By.xpath("//form[@name='userDetailsForm']//i[@class='icon-edit edit-action right']");
	
	private static final By LABEL_USER_NAME = By.xpath("//input[starts-with(@id,'adminui-username')]");
	
	private final By CHECK_FORCE_PASSWORD = By.id("adminui-forceChangePassword");
	
	private final By CHECK_ADMIN_SYSTEM = By.id("adminui-capabilities-Application: Administers System");

	private static final By LABEL_NAME = By.xpath("//input[starts-with(@id,'adminui-familyName-field')]");

	private static final By LABEL_FIRST_NAME = By.xpath("//input[starts-with(@id,'adminui-givenName-field')]");

	private final By CHECK_GENDER = By.id("adminui-gender-0-field");

	private final By PASSWORD = By.xpath("//input[starts-with(@id,'adminui-password-field')]");

	private final By CONFIRM_PASSWORD = By.xpath("//input[starts-with(@id,'adminui-confirmPassword-field')]");

	private final By PROVIDER = By.xpath("//input[starts-with(@id,'adminui-addProviderAccount')]");

	private final By IDENTIFIER = By.xpath("//input[starts-with(@id,'adminui-identifier-field')]");

	private final By SERVICE_PROVIDER_ROLE = By.xpath("//select[starts-with(@id,'adminui-providerRole-field')]");

	private final By CHECK_ADD_USER_ACCOUNT = By.id("adminui-addUserAccount");
	
	private final By CHECK_SUPER_USER = By
            .xpath("/html/body/div/div[3]/div[3]/div/form/fieldset/div/div[1]/div[2]/table/tbody/tr[4]/td[2]/input");
		
	private static final By DROP_DOWN_PRIVILEGE_LEVEL = By.xpath("//select[starts-with(@id,'adminui-privilegeLevel')]");
	
	private static final By BUTTON_SAVE = By.xpath("//button[starts-with(@id,'adminui-user-save')]");

	private static final By BUTTON_SAVE_ACCONT = By.xpath("//input[starts-with(@id,'save-button')]");

	private static final By BUTTON_RETIRE = By.xpath("/html/body/div/div[3]/div[3]/div/form/fieldset/div/div[1]/div[1]/div/button");

	private static final By BUTTON_RETIRE_REASON = By.xpath("/html/body/div[2]/div[2]/div[2]/p/input");

	private static final By BUTTON_CONFIRM = By.xpath("/html/body/div[3]/div[2]/div[2]/div/button[1]/font/font");


	
	public AccountsPage(Page page) {
		super(page);
	}
	
	@Override
	public String getPageUrl() {
		return PAGE_PATH;
	}
	
	public void clickEditButton() {
		clickOn(MODIFIER);
	}

	public void checkHasSuperPriviledges() {
		clickOn(CHECK_SUPER_USER);
	}
	
	public void enterUserName(String userName) {
		setText(LABEL_USER_NAME, userName);
		
	}
	
	public void selectPrevillageLevel(String previllage) {
		selectFrom(DROP_DOWN_PRIVILEGE_LEVEL, previllage);
	}
	
	public void checkForcePassword() {
		if (isChecked(CHECK_FORCE_PASSWORD)) {} else {
			clickOn(CHECK_FORCE_PASSWORD);
		}		
	}
	
	public void administerSystemIsChecked() {
		clickOn(CHECK_ADMIN_SYSTEM);
	}
	
	public Boolean hasSuperUserPrivillagesIsChecked() {
		return isChecked(CHECK_SUPER_USER);
	}
	
	public void clickLogoutButton() {
		goToPage("appui/header/logout.action?successUrl=openmrs");
		goToPage("/login.htm");
	}
	
	public void saveRoles() {
		clickOn(BUTTON_SAVE);
	}	

	public void enterName(String name) {
		setTextToFieldNoEnter(LABEL_NAME, name);
	}

	public void enterFirstName(String firstName) {
		setTextToFieldNoEnter(LABEL_FIRST_NAME, firstName);
	}

	public void enterPassword(String password) {
		setTextToFieldNoEnter(PASSWORD, password);
	}

	public void confirmPassword(String confirmPassword) {
		setTextToFieldNoEnter(CONFIRM_PASSWORD, confirmPassword);
	}

	public void enterIdentifier(String identifier) {
		setTextToFieldNoEnter(IDENTIFIER, identifier);
	}

	public void clickGenderButton() {
		clickOn(CHECK_GENDER);
	}

	public void addUserIsChecked() {
		clickOn(CHECK_ADD_USER_ACCOUNT);
	}

	public void addSupplierAccountIsChecked() {
		clickOn(PROVIDER);
	}

	public void selectProviderRole(String gender) {
		clickOn(SERVICE_PROVIDER_ROLE);
		selectOptionFromDropDown(SERVICE_PROVIDER_ROLE);
	}

	public void saveAccount() {
		clickOn(BUTTON_SAVE_ACCONT);
	}

	public void retireUser() {
		clickOn(BUTTON_RETIRE);
	}

	public void clickConfimButton() {
		clickOn(BUTTON_CONFIRM);
	}

	public void retireReason(String reason) {
		clickOn(BUTTON_RETIRE_REASON);
		setTextToFieldNoEnter(BUTTON_RETIRE_REASON, reason);
	}
}
