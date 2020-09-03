package pe.com.interbank.pys.aperturatcv2.microservices.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AppConfigTest {

	static {
		System.setProperty("ambiente", "dev");
		System.setProperty("version", "unittest");
		System.setProperty("propertiesSensitivePath", "target/test-classes/config-sensibles");
		System.setProperty("propertiesNonSensitivePath", "target/test-classes/config");
	}

	@Test
	public void AppconfigTest() {
		AppConfig appConfig = new AppConfig();
		assertNotNull(appConfig);
	}

}
