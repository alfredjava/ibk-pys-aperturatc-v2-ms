package pe.com.interbank.pys.aperturatcv2.microservices.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;

public class RequestConversorTest {
	@Test(expected= IllegalAccessError.class)
	public void constructorTest(){
		new RequestConversor();
	}
	@Test(expected=MicroserviceException.class)
	public void extraerCodigoCorrespondenciaTest() throws MicroserviceException{
		Map<String, String> crearActualizarMedioContacto=new HashMap<>();
		crearActualizarMedioContacto.put("test", "{{}");		
		RequestConversor.extraerCodigoCorrespondencia(crearActualizarMedioContacto);
	}

}
