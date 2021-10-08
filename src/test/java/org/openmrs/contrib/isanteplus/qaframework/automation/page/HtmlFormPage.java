package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class HtmlFormPage extends Page {

    private static final String HTML_PATH = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?patientId=f9a3ccb7-2913-4576-b626-2659c58c8625&visitId=8&definitionUiResource=isanteplus:htmlforms/Lab.xml";

    private static final By VISIT_DATE = By.xpath(
            "/html/body/div[1]/div[3]/div[2]/form/htmlform/div[1]/div[1]/table/tbody/tr[2]/td[2]/span[1]/input[1]");

    private static final By TAB_BIOCHIMIE = By
            .xpath("/html/body/div[1]/div[3]/div[2]/form/htmlform/div[1]/div[2]/div/ul/li[2]");

    private static final By SAVE_FORM = By
            .xpath("/html/body/div[1]/div[3]/div[2]/form/htmlform/div[1]/div[1]/table/tbody/tr[2]/td[3]/input");

    private static final By HEMOGRAMME = By
            .xpath("/html/body/div[1]/div[3]/div[2]/form/htmlform/div[1]/div[2]/div/div/div[1]/table/tbody/tr/td[1]/table/tbody/tr[1]/td/span[5]/input[1]");


    public HtmlFormPage(Page parent) {
        super(parent);
    }

    @Override
    public String getPageUrl() {
        return HTML_PATH;
    }

    public String getVisitDate() {
        return getValue(VISIT_DATE);
    }

    public void clickTabUnderSection() {
        clickOn(TAB_BIOCHIMIE);
    }

    public void clickSaveForm() {
        clickOn(SAVE_FORM);
    }

    public void checkHemogramme() {
        clickOn(HEMOGRAMME);
    }
}