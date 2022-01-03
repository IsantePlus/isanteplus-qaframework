package org.openmrs.contrib.isanteplus.qaframework.automation.test;

import org.openmrs.contrib.isanteplus.qaframework.automation.page.Page;

public interface ITestBase {

	int MAX_WAIT_IN_SECONDS = 120;
	int MAX_PAGE_LOAD_IN_SECONDS = 120;

	void assertPage(Page expected);

	void teardown();

	void setup() throws Exception;
}
