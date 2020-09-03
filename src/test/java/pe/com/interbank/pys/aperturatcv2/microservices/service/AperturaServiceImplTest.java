package pe.com.interbank.pys.aperturatcv2.microservices.service;

import java.util.HashMap;

import javax.ws.rs.core.Response;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;
import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;


@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AperturaServiceImplTest {
	
	@Autowired
	AperturaServiceImpl aperturaServiceImpl;
	private String ORQ_RESPONSE = PropertiesCache.getInstance().getProperty("test.trama.response");
	private String ALTA_TARJETA_RESPONSE = PropertiesCache.getInstance().getProperty("test.altatc.response");

	private String CREAR_MEDIDOCONTGENERAL_RESPONSE = "{\"header\":{\"response\":{\"serviceId\":\"SRM\",\"consumerId\":\"BPI\",\"moduleId\":\"\",\"channelCode\":\"02\",\"messageId\":\"WBRK7_UAT000369661560170c011e80a8b80821e1a970000\",\"timestamp\":\"2018-02-21T08:36:27.954454\",\"countryCode\":\"PE\",\"groupMember\":\"G0003\",\"referenceNumber\":\"01153\"},\"status\":{\"responseType\":\"0\",\"msResponseCode\":\"0\",\"msResponseMessage\":\"OK\",\"busResponseCode\":\"0\",\"busResponseMessage\":\"EJECUCION CON EXITO\",\"srvResponseCode\":\"00\",\"srvResponseMessage\":\"\",\"messageIdResBus\":\"DPI_SAL_UAT_01_190032204810011036015700000000000\"}},\"body\":{\"idSecundario\":\"003EMAPER\"}}";

	private String ACTUALIZAR_MEDIDOCONTGENERAL_RESPONSE="{\"header\":{\"response\":{\"serviceId\":\"SRM\",\"consumerId\":\"WBP\",\"moduleId\":\"aperturaTCv2\",\"channelCode\":\"06\",\"messageId\":\"DPI_SAL_UAT_02_356322519100110360156000000000000\",\"timestamp\":\"2018-02-20T11:14:49.638966\",\"countryCode\":\"PE\",\"groupMember\":\"G0003\",\"referenceNumber\":\"01153\"},\"status\":{\"responseType\":\"0\",\"msResponseCode\":\"0\",\"msResponseMessage\":\"OK\",\"busResponseCode\":\"0\",\"busResponseMessage\":\"EJECUCION CON EXITO\",\"srvResponseCode\":\"00\",\"srvResponseMessage\":\"\",\"messageIdResBus\":\"DPI_SAL_UAT_01_188970377610011036015700000000000\"}},\"body\":{\"idSecundario\":\"003EMAJOB\"}}";

	private String CREAR_MEDIDOCONTESPECI_RESPONSE="{\"header\":{\"response\":{\"serviceId\":\"SRM\",\"consumerId\":\"WBP\",\"moduleId\":\"aperturaTCv2\",\"channelCode\":\"06\",\"messageId\":\"DPI_SAL_UAT_02_416591251100110360156000000000000\",\"timestamp\":\"2018-02-21T10:14:30.033313\",\"countryCode\":\"PE\",\"groupMember\":\"G0003\",\"referenceNumber\":\"01153\"},\"status\":{\"responseType\":\"0\",\"msResponseCode\":\"0\",\"msResponseMessage\":\"OK\",\"busResponseCode\":\"0\",\"busResponseMessage\":\"EJECUCION CON EXITO\",\"srvResponseCode\":\"00\",\"srvResponseMessage\":\"\",\"messageIdResBus\":\"DPI_SAL_UAT_01_190189472010011036015700000000000\"}},\"body\":{\"numeroSecuencia\":\"002\",\"codigoUso\":\"CORR01\"}}";
	
	@Autowired
	private AsincronoServiceImpl asincronoServiceImpl;
	
	@SuppressWarnings("unchecked")
	@Before
	public void init(){
		KafkaProducer<Integer, String> producer= Mockito.mock(KafkaProducer.class);
		asincronoServiceImpl.setProducer(producer);
	}
	
	@Test
	public void validarRespuestaTest() throws MicroserviceException {
		HashMap<String, String> respuesta = new HashMap<>();
		respuesta.put("aperturatcv2", ORQ_RESPONSE);
		aperturaServiceImpl.validarRespuesta(respuesta);
	}
	
	@Test
	public void altaTarjetaTest() throws LegacyException, MicroserviceException {
		SecurityRestClient securityRestClient = Mockito.mock(SecurityRestClient.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(String.class)).thenReturn(ALTA_TARJETA_RESPONSE);
		Mockito.when(securityRestClient.invokeExternalService(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(response);
		aperturaServiceImpl.setSecurityRestClient(securityRestClient);
		aperturaServiceImpl.altaTarjeta("{request}", "MSGIDTEST001");
	}
	
	@Test
	public void crearMedioContactoGeneralTest() throws LegacyException, MicroserviceException {
		SecurityRestClient securityRestClient = Mockito.mock(SecurityRestClient.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(String.class)).thenReturn(CREAR_MEDIDOCONTGENERAL_RESPONSE);
		Mockito.when(securityRestClient.invokeExternalService(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(response);
		aperturaServiceImpl.setSecurityRestClient(securityRestClient);
		aperturaServiceImpl.crearMedioContactoGeneral("{request}", "MSGIDTEST002");
	}
	
	@Test
	public void actualizarMedioContactoGeneralTest() throws LegacyException, MicroserviceException {
		SecurityRestClient securityRestClient = Mockito.mock(SecurityRestClient.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(String.class)).thenReturn(ACTUALIZAR_MEDIDOCONTGENERAL_RESPONSE);
		Mockito.when(securityRestClient.invokeExternalService(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(response);
		aperturaServiceImpl.setSecurityRestClient(securityRestClient);
		aperturaServiceImpl.actualizarMedioContactoGeneral("{request}", "MSGIDTEST003");
	}
	
	@Test
	public void crearMedioContactoEspecializadoTest() throws LegacyException, MicroserviceException {
		SecurityRestClient securityRestClient = Mockito.mock(SecurityRestClient.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(String.class)).thenReturn(CREAR_MEDIDOCONTESPECI_RESPONSE);
		Mockito.when(securityRestClient.invokeExternalService(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(response);
		aperturaServiceImpl.setSecurityRestClient(securityRestClient);
		aperturaServiceImpl.crearMedioContactoEspecializado("{request}", "MSGIDTEST004");
	}
	
	
}
