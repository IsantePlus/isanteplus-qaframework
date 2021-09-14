package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class AccountsPage extends Page {

    private static final String PAGE_PATH = "adminui/systemadmin/accounts/account.page?personId=1&";

    private static final By EDIT_BUTTON = By.id("editButton");

    private static final By USER_NAME = By.name("userName");

    private static final By PRIVILEGE = By.id("newPrivillage");

    private static final By MODIFIER = By
            .xpath("//form[@name='userDetailsForm']//i[@class='icon-edit edit-action right']");

    private static final By LABEL_USER_NAME = By.xpath("//input[starts-with(@id,'adminui-username')]");

    private final By CHECK_FORCE_PASSWORD = By.id("adminui-forceChangePassword");

    private final By CHECK_ADMIN_SYSTEM = By.id("checkbox-enable-administers-system");

    private final By CHECK_CONFIGURE_FORM = By.id("checkbox-enable-configure-form");

    private final By CHECK_ENTER_VITALS = By.id("checkbox-enable-enter-vitals");

    private static final By DROP_DOWN_PRIVILEGE_LEVEL = By.xpath("//select[starts-with(@id,'adminui-privilegeLevel')]");

    private static final By BUTTON_SAVE = By.id("save");

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

    public void enterUserName(String userName) {
        setText(LABEL_USER_NAME, userName);

    }

    public void selectPrevillageLevel(String previllage) {
        selectFrom(DROP_DOWN_PRIVILEGE_LEVEL, previllage);
    }

    public Boolean forcePasswordIsChecked() {
        return isChecked(CHECK_FORCE_PASSWORD);
    }

    public Boolean administerSystemIsChecked() {
        return isChecked(CHECK_ADMIN_SYSTEM);
    }

    public Boolean configuresFormIsChecked() {
        return isChecked(CHECK_CONFIGURE_FORM);
    }

    public Boolean enterVitalsIsChecked() {
        return isChecked(CHECK_ENTER_VITALS);
    }

    public void saveRoles() {
        clickOn(BUTTON_SAVE);
    }

}
