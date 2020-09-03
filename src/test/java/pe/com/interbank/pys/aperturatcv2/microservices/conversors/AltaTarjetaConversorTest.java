package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.junit.Test;

public class AltaTarjetaConversorTest {
	
	@Test(expected = IllegalAccessException.class)
	public void altaTarjetaConversorExceptionTest() throws IllegalAccessException {
		new AltaTarjetaConversor();
	}
	
	public void obtenerBeneficiariosAliasTest(){
		
	}
}
