package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class SystemAdministrationPage extends Page {
	
	private ManageAccountsPage manageAccountsPage;
	
	private static final String SYSTEM_ADMIN = "/systemadministration/systemAdministration.page";
	
	private final By APP_MANAGE_ACCOUNTS = By.id("org-openmrs-module-adminui-accounts-app");
	
	public SystemAdministrationPage(Page parent) {
		super(parent);
	}
	
	@Override
	public String getPageUrl() {
		return SYSTEM_ADMIN;
	}
	
	public void clickManageAccounts() {
		clickOn(APP_MANAGE_ACCOUNTS);
	}
	
	public ManageAccountsPage goToManageAccountsPage() {
		clickManageAccounts();
		return new ManageAccountsPage(this);
	}
	
}
