package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class ActualizarMedioContactoGeneralConversorTest {
	
	
	@Test(expected = IllegalAccessException.class)
	public void actualizarMedioContactoGeneralExceptionTest() throws IllegalAccessException {
		new ActualizarMedioContactoGeneralConversor();
	}
	
}
