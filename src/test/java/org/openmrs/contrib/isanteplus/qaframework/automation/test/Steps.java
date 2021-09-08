package org.openmrs.contrib.isanteplus.qaframework.automation.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientVisitsDashboardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Steps extends TestBase {
	
	protected TestProperties testProperties = TestProperties.instance();
	protected LoginPage loginPage;
	protected String firstPatientIdentifier;
	protected PatientVisitsDashboardPage visitsDashboardPage;
	protected By patientHeaderId = By.cssSelector("div.identifiers span");

    @Before
    public void before() {
        homePage = new HomePage(page);
    }
	public Steps() {
		try {
			startWebDriver();
			loginPage = getLoginPage();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	protected void quit() {
		if (driver != null) {
			driver.quit();
		}
	}

	protected WebElement getElement(By elementBy) {
		try {
			return driver.findElement(elementBy);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	protected boolean textExists(String text) {
		return driver.findElements(
				By.xpath("//*[contains(text(),'" + text + "')]")).size() > 0;
	}

	protected void initiateWithLogin() {
		goToLoginPage();
		goToLoginPage().login(testProperties.getUsername(),
				testProperties.getPassword(), testProperties.getLocation());
		homePage = (HomePage) new HomePage(loginPage).waitForPage();
	}

	protected String trimPatientId(String id) {
		id = id.replace("Recent", "");
		if (id.indexOf("[") > 0) {
			id = id.split("\\[")[0];
		}
		if (id.indexOf(" ") > 0) {
			id = id.split(" ")[0];
		}
		return id;
	}

	protected void matchPatientIds(String patientId) {
		List<String> ids = new ArrayList<>();
		driver.findElements(patientHeaderId).forEach(id-> {
			ids.add(trimPatientId(id.getText()));
		});
		assertTrue(ids.contains(trimPatientId(patientId)));
	}
}
