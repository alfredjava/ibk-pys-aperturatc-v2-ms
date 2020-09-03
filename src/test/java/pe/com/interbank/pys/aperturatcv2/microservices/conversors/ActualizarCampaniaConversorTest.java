package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class ActualizarCampaniaConversorTest {
	@Test(expected = IllegalAccessException.class)
	public void actualizarCampaniaConversorExceptionTest() throws IllegalAccessException {
		new ActualizarCampaniaConversor();
	}
}
