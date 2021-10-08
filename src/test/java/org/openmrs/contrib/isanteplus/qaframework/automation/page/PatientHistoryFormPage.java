package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import org.openqa.selenium.By;

public class PatientHistoryFormPage extends Page {

    public static final String URL_PATH = "/isanteplus/patientFormsHistory.page?patientId=f9a3ccb7-2913-4576-b626-2659c58c8625&all=1&returnUrl=%2Fopenmrs%2Fcoreapps%2Fclinicianfacing%2Fpatient.page%3FpatientId%3D7%26";

    private static final By ENLEVER = By.xpath("/html/body/div/div[3]/div[3]/div/table/tbody/tr[1]/th[9]/input");

    private static final By SUPPRIMER_BUTTON = By.xpath("/html/body/div/div[3]/div[3]/div/input");


    public PatientHistoryFormPage(Page parent) {
        super(parent);
    }

    @Override
    public String getPageUrl() {
        return URL_PATH;
    }

    public void checkEnlever() {
		clickOn(ENLEVER);
    }

    public void clickSupprimerButton() {
		clickOn(SUPPRIMER_BUTTON);
	}
} 