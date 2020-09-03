package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class CrearClienteConversorTest {
	@Test(expected = IllegalAccessException.class)
	public void crearClienteConversorExceptionTest() throws IllegalAccessException {
		new CrearClienteConversor();
	}
}
