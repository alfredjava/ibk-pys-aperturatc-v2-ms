package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class CrearMedioContactoEspecializadoConversorTest {
	
	@Test(expected = IllegalAccessException.class)
	public void crearMedioContactoEspecializadoConversorTest() throws IllegalAccessException {
		new CrearMedioContactoEspecializadoConversor();
	}
}
