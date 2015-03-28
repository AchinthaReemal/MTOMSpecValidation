package org.wso2.mtom.suitesetup;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.wso2.mtom.service.ImagePublisher;


public class TestSuiteSetup {
	
	ImagePublisher imagePublisher = new ImagePublisher();

	@BeforeSuite(alwaysRun = true)
	public void setupSuite() throws InterruptedException {
		imagePublisher.startService();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws InterruptedException {
		imagePublisher.stopService();
	}
	
}
