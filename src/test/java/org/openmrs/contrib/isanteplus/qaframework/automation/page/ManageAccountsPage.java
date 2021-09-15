package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class ManageAccountsPage extends Page {
	
	private static final String MANAGE_ACCOUNTS = "/adminui/systemadmin/accounts/manageAccounts.page";
	
	private static final String ACCOUNTS = "/adminui/systemadmin/accounts/account.page?personId=1&";
	
	private static final By EDIT_ACTION_BUTTON = By.xpath("//*[@class='icon-pencil edit-action']");
	
	public ManageAccountsPage(Page page) {
		super(page);
	}
	
	@Override
	public String getPageUrl() {
		return MANAGE_ACCOUNTS;
	}
	
	public AccountsPage editAccount() {
		
		clickOn(EDIT_ACTION_BUTTON);
		return new AccountsPage(this);
	}
	
}
