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
		
		public static final String FIND_PATIENT = "@findPatient";
		
		public static final String PASSWORD = "@password";
		
		public static final String DELETEPATIENT = "@deletePatient";
		
		public static final String REPORTS = "@reports";

		public static final String DELETE_PATIENT = "@deletePatient";

		public static final String ADD_USER = "@addUser";
		
		public static final String SEARCH_USER = "@searchUser";
		
		public static final String PATIENT_COVER_SHEET = "@patientCoverSheet";

		public static final String USER_MANAGEMENT = "@userManagement";

		public static final String PATIENT_SUMMARY = "@patientSummary";

		public static final String CONSULTATION = "@consultation";
		
		public static final String CHECK_FORMS    = "@checkForms";

		public static final String PATIENT_MERGE = "@patientMerge";

		public static final String FORM = "@form";
	}
}
