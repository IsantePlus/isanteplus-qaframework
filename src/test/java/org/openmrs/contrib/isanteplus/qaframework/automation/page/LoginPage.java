package org.openmrs.contrib.isanteplus.qaframework.automation.page;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * This class represents the Login page
 */
public class LoginPage extends Page {
    
    private final String LOGIN_PATH = "/login.htm";
    
    private final String MODULES_NOT_RUNNING_MESSAGE = "If you are seeing this page, it means that the OpenMRS Platform is running";
    
    private final String LOGOUT_PATH = "/logout";
    
    private final By FIELD_USERNAME = By.id("username");
    
    private final By FIELD_PASSWORD = By.id("password");
    
    private final By BUTTON_LOGIN = By.id("loginButton");
    
    private final By LOCATIONS = By.cssSelector("#sessionLocation li");
    
    private String username;
    
    private String password;
    
    
    public LoginPage(WebDriver driver) {
        super(driver);
        username = properties.getUsername();
        password = properties.getPassword();
    }
    
    @Override
    public void go() {
        goToPage(LOGIN_PATH);
    }
    
    @Override
    public String getPageUrl() {
        return LOGIN_PATH;
    }
    
    @Override
    public String getPageRejectUrl() {
        return "index.htm";
    }
    
    public void enterUsername(String username) {
        setText(FIELD_USERNAME, username);
    }
    
    public void enterPassword(String password) {
        setText(FIELD_PASSWORD, password);
    }
    
    public void clickLoginButton() {
        clickOn(BUTTON_LOGIN);
    }
    
    public void selectLocation() {
        clickOn(LOCATIONS);
    }
    
    public HomePage goToHomePage() {
        go();
        enterUsername(this.username);
        enterPassword(this.password);
        selectLocation();
        clickLoginButton();
        return new HomePage(this);
    }
    
    public Boolean hasLoginButton() {
        return hasElement(BUTTON_LOGIN);
    }
    public void login(String user, String password, String location) {
		 postLoginForm(user, password, location);
	     return;	
	}

	private void postLoginForm(String user, String password2, String location) {
        String postJs;
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/post.js");
            postJs = IOUtils.toString(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }

        String post = postJs + " post('" + getContextPageUrl() + "', {username: '" + user + "', password: '" + password;
        if (location != null) {
            post += "', sessionLocation: " + location + "});";
        } else {
            post += "});";
        }
        ((JavascriptExecutor) driver).executeScript(post);

	}
}
