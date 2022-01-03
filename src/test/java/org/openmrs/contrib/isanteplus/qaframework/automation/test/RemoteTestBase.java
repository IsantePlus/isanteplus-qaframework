package org.openmrs.contrib.isanteplus.qaframework.automation.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteTestBase implements ITestBase {

	public static final int MAX_WAIT_IN_SECONDS = 120;

	public static final int MAX_PAGE_LOAD_IN_SECONDS = 120;

	public static final String REMOTE_URL_CHROME = "http://localhost:4444/wd/hub";

	public static String REMOTE_URL_FIREFOX = "http://localhost:4445/wd/hub";

	public static final int MAX_SERVER_STARTUP_IN_MILLISECONDS = 10 * 60 * 1000;

	protected By patientHeaderId = By.cssSelector("div.identifiers span");

	private static volatile boolean serverFailure = false;

	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	public RemoteTestBase() {
		try {
			setup();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Before
	public void setup() throws Exception {
		setupThread("Chrome");
	}

	@After
	public void teardown() {
		quit();
	}

	/**
	 * Assert we're on the expected page.
	 * 
	 * @param expected page
	 */
	public void assertPage(Page expected) {
		assertTrue(getDriver().getCurrentUrl().contains(expected.getPageUrl()));
	}
	
	protected void quit() {
		if (getDriver() != null) {
			getDriver().quit();
		}
	}
	
	public String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	protected void setupThread(String browserName) throws MalformedURLException
	{
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.out.println("Inside Chrome");
			ChromeOptions options = new ChromeOptions();
			driver.set(new RemoteWebDriver(new URL(REMOTE_URL_CHROME), options));
		}
		else if (browserName.equalsIgnoreCase("firefox"))
		{
			System.out.println("Inside Firefox");
			FirefoxOptions options = new FirefoxOptions();
			driver.set(new RemoteWebDriver(new URL(REMOTE_URL_FIREFOX), options));
		}
	}

	protected WebDriver getDriver()
	{
		return driver.get();
	}
}
