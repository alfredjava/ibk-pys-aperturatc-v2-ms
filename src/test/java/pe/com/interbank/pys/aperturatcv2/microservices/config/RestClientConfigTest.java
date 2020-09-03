package pe.com.interbank.pys.aperturatcv2.microservices.config;

import org.junit.Test;

public class RestClientConfigTest {

	@Test
	public void clientExceptionTest() {
		RestClientConfig.busRestClientInit();
		RestClientConfig.msRestClientInit();
	}

	@Test
	public void sSLContextCertifiedExceptionTest() {
		RestClientConfig.getBusSSLContextCertified();
	}

	@Test(expected = IllegalAccessException.class)
	public void restClientConfigExceptionTest() throws IllegalAccessException {
		new RestClientConfig();
	}

}
