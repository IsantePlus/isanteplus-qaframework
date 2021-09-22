package org.openmrs.contrib.isanteplus.qaframework;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        // defining ordered by name features folder
        features = { "src/features" },
        // defining the definition steps package
        glue = "org.openmrs.contrib.isanteplus.qaframework.automation", plugin = { "html:target/index.html",
                "message:target/cucumber.ndjson" }, monochrome = true)
public class RunTest {
	
	public class HOOK {
		
		public static final String LOGIN = "@login";
		
		public static final String REGISTRATION = "@register";
		
		public static final String FINDPATIENT = "@findPatient";
		
		public static final String PASSWORD = "@password";
		
		public static final String DELETEPATIENT = "@deletePatient";
	}
}
