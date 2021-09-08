package org.openmrs.contrib.isanteplus.qaframework.automation.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class TestProperties {
	
	public static final String WEBDRIVER_PROPERTY = "webdriver";
	
	public static final String DEFAULT_WEBDRIVER = "firefox";
	
	public static final String LOGIN_PASSWORD_PROPERTY = "login.password";
	
	public static final String DEFAULT_PASSWORD = "admin";
	
	public static final String LOGIN_USERNAME_PROPERTY = "login.username";
	
	public static final String DEFAULT_LOGIN_USERNAME = "Admin123";
	
	public static final String WEBAPP_URL_PROPERTY = "webapp.url";
	
	public static final String LOGIN_LOCATION_PROPERTY = "login.location";
	
	public static final String DEFAULT_LOGIN_LOCATION = "Outpatient Clinic";
	
	public static final String DEFAULT_WEBAPP_URL = "http://isanteplusdemo.com:8080/openmrs";
	
	public static final String HEADLESS_PROPERTY = "headless";
	
	public static final String DEFAULT_HEADLESS = "false";
	
	private static TestProperties SINGLETON;
	
	private Properties properties;
	
	public TestProperties() {
		properties = new Properties();
		try {
			URL resource = Thread.currentThread().getContextClassLoader().getResource("test.properties");
			if (resource != null) {
				InputStream input = resource.openStream();
				properties.load(new InputStreamReader(input, "UTF-8"));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException("Could not find test.properties", ioException);
		}
	}
	
	public static TestProperties instance() {
		if (SINGLETON == null) {
			SINGLETON = new TestProperties();
		}
		return SINGLETON;
	}
	
	public String getWebAppUrl() {
		return getProperty(WEBAPP_URL_PROPERTY, DEFAULT_WEBAPP_URL);
	}
	
	public String getUsername() {
		return getProperty(LOGIN_USERNAME_PROPERTY, DEFAULT_LOGIN_USERNAME);
	}
	
	public String getPassword() {
		return getProperty(LOGIN_PASSWORD_PROPERTY, DEFAULT_PASSWORD);
	}
	
	public String getHeadless() {
		return getProperty(HEADLESS_PROPERTY, DEFAULT_HEADLESS);
	}
	
	public String getBrowser() {
		return getProperty(WEBDRIVER_PROPERTY, DEFAULT_WEBDRIVER);
	}
	
	 public String getLocation() {
	        return getProperty(LOGIN_LOCATION_PROPERTY, DEFAULT_LOGIN_LOCATION);
	    }
	
	public WebDriverType getWebDriver() {
		try {
			return WebDriverType.valueOf(getBrowser());
		}
		catch (IllegalArgumentException e) {
			return WebDriverType.firefox;
		}
	}
	
	public String getProperty(String property, String defaultValue) {
		String value = System.getProperty(property);
		if (value == null) {
			value = System.getenv(property);
		}
		if (value == null) {
			value = properties.getProperty(property);
		}
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}
	
	public String getFirefoxDriverLocation() {
		return getProperty("webdriver.gecko.driver", null);
	}
	
	public enum WebDriverType {
		chrome,
		firefox
	}

}
