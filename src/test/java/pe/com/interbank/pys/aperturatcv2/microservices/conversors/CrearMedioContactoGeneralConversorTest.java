package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class CrearMedioContactoGeneralConversorTest {
	@Test(expected = IllegalAccessException.class)
	public void crearMedioContactoGeneralConversorTest() throws IllegalAccessException {
		new CrearMedioContactoGeneralConversor();
	}
}
