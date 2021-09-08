package org.openmrs.contrib.isanteplus.qaframework.automation.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.HomePage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.LoginPage;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.Page;
import org.openmrs.contrib.isanteplus.qaframework.automation.page.PatientVisitsDashboardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

/**
 * Superclass for all UI Tests. Contains lots of handy "utilities" needed to setup and tear down
 * tests as well as handy methods needed during tests, such as:
 * <ul>
 * <li>initialize Selenium WebDriver</li>
 * <li>@see {@link #goToLoginPage()}</li>
 * <li>@see {@link #login()}</li>
 * <li>@see {@link #assertPage(Page)} - @see {@link #pageContent()}</li>
 * </ul>
 */
public class TestBase {
	
	public static final int MAX_WAIT_IN_SECONDS = 120;
	
	public static final int MAX_PAGE_LOAD_IN_SECONDS = 120;
	
	public static final int MAX_SERVER_STARTUP_IN_MILLISECONDS = 10 * 60 * 1000;
	
	
	private static volatile boolean serverFailure = false;
	
	
	private WebDriver driver;
	
	protected TestProperties testProperties = TestProperties.instance();
	
	protected LoginPage loginPage;
	
	protected String firstPatientIdentifier;
	
	protected HomePage homePage;
	private static final By SELECTED_LOCATION = By.id("selected-location");
	
	protected PatientVisitsDashboardPage visitsDashboardPage;
	
	protected By patientHeaderId = By.cssSelector("div.identifiers span");

	
	protected Page page;
	
	
	@Before
	public void startWebDriver() throws Exception {
		if (serverFailure) {
			fail("Test killed due to server failure");
		}
		launchBrowser();
	}
	
	public void launchBrowser() throws Exception {
		final TestProperties properties = TestProperties.instance();
		System.out.println("Running locally...");
		final TestProperties.WebDriverType webDriverType = properties.getWebDriver();
		switch (webDriverType) {
			case chrome:
				driver = setupChromeDriver();
				break;
			case firefox:
				driver = setupFirefoxDriver();
				break;
			default:
				// shrug, choose chrome as default
				driver = setupChromeDriver();
				break;
			
		}
		
		driver.manage().timeouts().implicitlyWait(MAX_WAIT_IN_SECONDS, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(MAX_PAGE_LOAD_IN_SECONDS, TimeUnit.SECONDS);
		
	}
	
	@After
	public void stopWebDriver() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	protected WebDriver getWebDriver() {
		return driver;
	}
	
	WebDriver setupFirefoxDriver() {
		if (StringUtils.isBlank(System.getProperty("webdriver.gecko.driver"))) {
			System.setProperty("webdriver.gecko.driver", Thread.currentThread().getContextClassLoader()
			        .getResource(TestProperties.instance().getFirefoxDriverLocation()).getPath());
		}
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		if ("true".equals(TestProperties.instance().getHeadless())) {
			firefoxOptions.addArguments("--headless");
		}
		driver = new FirefoxDriver(firefoxOptions);
		return driver;
	}
	
	WebDriver setupChromeDriver() {
		URL chromedriverExecutable = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		String chromedriverExecutableFilename = null;
		if (SystemUtils.IS_OS_MAC_OSX) {
			chromedriverExecutableFilename = "chromedriver";
			chromedriverExecutable = classLoader.getResource("chromedriver/mac/chromedriver");
		} else if (SystemUtils.IS_OS_LINUX) {
			chromedriverExecutableFilename = "chromedriver";
			chromedriverExecutable = classLoader.getResource("chromedriver/linux/chromedriver");
		} else if (SystemUtils.IS_OS_WINDOWS) {
			chromedriverExecutableFilename = "chromedriver.exe";
			chromedriverExecutable = classLoader.getResource("chromedriver/windows/chromedriver.exe");
		}
		String errmsg = "cannot find chromedriver executable";
		String chromedriverExecutablePath = null;
		if (chromedriverExecutable == null) {
			System.err.println(errmsg);
			Assert.fail(errmsg);
		} else {
			chromedriverExecutablePath = chromedriverExecutable.getPath();
			// This checks to see if the chromedriver file is inside a
			// jar, and if so
			// uses VFS to extract it to a temp directory.
			if (chromedriverExecutablePath.contains(".jar!")) {
				FileObject chromedriver_vfs;
				try {
					chromedriver_vfs = VFS.getManager().resolveFile(chromedriverExecutable.toExternalForm());
					File chromedriver_fs = new File(FileUtils.getTempDirectory(), chromedriverExecutableFilename);
					FileObject chromedriverUnzipped = VFS.getManager().toFileObject(chromedriver_fs);
					chromedriverUnzipped.delete();
					chromedriverUnzipped.copyFrom(chromedriver_vfs, new AllFileSelector());
					chromedriverExecutablePath = chromedriver_fs.getPath();
					if (!SystemUtils.IS_OS_WINDOWS) {
						chromedriver_fs.setExecutable(true);
					}
				}
				catch (FileSystemException e) {
					System.err.println(errmsg + ": " + e);
					e.printStackTrace();
					Assert.fail(errmsg + ": " + e);
				}
			}
		}
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromedriverExecutablePath);
		String chromedriverFilesDir = "target/chromedriverlogs";
		try {
			FileUtils.forceMkdir(new File(chromedriverFilesDir));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
		    chromedriverFilesDir + "/chromedriver-" + getClass().getSimpleName() + ".log");
		ChromeOptions chromeOptions = new ChromeOptions();
		if ("true".equals(TestProperties.instance().getHeadless())) {
			chromeOptions.addArguments("--headless");
		}
		driver = new ChromeDriver(chromeOptions);
		return driver;
	}
	
	/**
	 * Assert we're on the expected page.
	 * 
	 * @param expected page
	 */
	public void assertPage(Page expected) {
		assertTrue(driver.getCurrentUrl().contains(expected.getPageUrl()));
	}
	
	
	public String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

   @Before
    public void before() {
        homePage = new HomePage(page);
    }
   
	public void Steps() {
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

	protected void initiateWithLogin() {
		goToLoginPage();
		goToLoginPage().login(testProperties.getUsername(),
				testProperties.getPassword(), testProperties.getLocation());
		homePage = (HomePage) new HomePage(loginPage).waitForPage();
	}
	
	public String getLocationUuid(Page page) {
        return driver.findElement(SELECTED_LOCATION).getAttribute("location-uuid");
    }

    public LoginPage goToLoginPage() {
        LoginPage loginPage = getLoginPage();

        Response response = (Response) ClientBuilder.newClient()
                .target(TestProperties.instance().getWebAppUrl())
                .path(loginPage.getPageUrl())
                .request().get();

        int status = ((jakarta.ws.rs.core.Response) response).getStatus();

        if (status >= 400 && status <= 599) {
            throw new ServerErrorException(((jakarta.ws.rs.core.Response) response).getStatusInfo().getReasonPhrase(), status);
        }

        loginPage.go();
        loginPage.waitForPage();

        //refresh, just to be sure all css files and images are loaded properly
        driver.navigate().refresh();
        loginPage.waitForPage();

        return loginPage;
    }

    protected LoginPage getLoginPage() {
        return new LoginPage(driver);
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
	

