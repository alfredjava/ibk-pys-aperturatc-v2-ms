package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class GrabarLDPDConversorTest {
	@Test(expected = IllegalAccessException.class)
	public void grabarLDPDConversorTest() throws IllegalAccessException {
		new GrabarLDPDConversor();
	}
}
