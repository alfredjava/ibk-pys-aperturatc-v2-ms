package pe.com.interbank.pys.aperturatcv2.microservices.modifiers;

import org.junit.Assert;
import org.junit.Test;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.InformacionEntrega;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class RequestModifierTest {
//	@Test(expected= IllegalAccessError.class)
//	public void constructorTest(){
//		new RequestModifier();
//	}
	@Test
	public void obtenerFechaHoraEntregaTest() throws MicroserviceException{
		InformacionEntrega informacionEntrega=new 
				InformacionEntrega("indicador", "direccion", 
						"referencia", "codigoDistrito", "codigoProvincia",
						"codigoDepartamento", "codigoTiendaEntrega", "tipoDireccion",
						"numeroTelefono", "mail", "2018-02-01", "09:00", "10:30");
		
		Assert.assertNotNull(RequestModifier.obtenerFechaHoraEntrega(informacionEntrega, "2018-02-15", 
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD), "messageId"));

	}
	@Test(expected=MicroserviceException.class)
	public void obtenerFechaHoraEntregaErrorFormatTest() throws MicroserviceException{
		InformacionEntrega informacionEntrega=new 
				InformacionEntrega("indicador", "direccion", 
						"referencia", "codigoDistrito", "codigoProvincia",
						"codigoDepartamento", "codigoTiendaEntrega", "tipoDireccion",
						"numeroTelefono", "mail", "2018-02-01", "09:00", "10:30");
		
		RequestModifier.obtenerFechaHoraEntrega(informacionEntrega, "2018/13/15", 
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD), "messageId");

	}				
}
