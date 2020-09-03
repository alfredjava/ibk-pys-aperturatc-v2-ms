package pe.com.interbank.pys.aperturatcv2.microservices.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.aperturatcv2.microservices.audit.AuditoriaAspect;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.jolt.JoltRequestConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;
import pe.com.interbank.pys.aperturatcv2.microservices.service.AperturaServiceImpl;
import pe.com.interbank.pys.aperturatcv2.microservices.service.AsincronoServiceImpl;
import pe.com.interbank.pys.aperturatcv2.microservices.service.SecurityRestClient;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.exceptions.GenericException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.service.AuditoriaServiceImpl;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AperturaTcEndpointTest {
	@Autowired
	AperturaTcEndpoint aperturaTcEndpoint;
	
	@Autowired
	private AperturaServiceImpl aperturaServiceImpl;
	@Autowired
	private AsincronoServiceImpl asincronoServiceImpl;
	ObjectMapper mapper = new ObjectMapper();
	private String ALTA_TARJETA_RESPONSE = PropertiesCache.getInstance().getProperty("test.altatc.response");
	private String ALTA_TARJETA_REQUEST = PropertiesCache.getInstance().getProperty("test.altatc.request");
	private String ACTUALIZA_MCGENERAL_RESPONSE = PropertiesCache.getInstance().getProperty("test.tramaactualizamcg.response");
	
	
	
	private String ORQ_REQUEST = PropertiesCache.getInstance().getProperty("test.tramamc.request");
	
	private String CONSULTA_ADIC_RESPONSE = PropertiesCache.getInstance().getProperty("test.consultaadic.response");
	private String CREAR_CLIENTE_RESPONSE = PropertiesCache.getInstance().getProperty("test.crearcliente.response");
	
	private String CREAR_CLIENTE_REQUEST = PropertiesCache.getInstance().getProperty("test.tramacreacliente.request");
	
	private String ORQ_REQUEST_CREAR_CMG = PropertiesCache.getInstance().getProperty("test.tramacrearmcg.request");

	private String ORQ_REQUEST_BENEFICIARIO = PropertiesCache.getInstance().getProperty("test.tramabeneficiario.request");


	
	private String JSON_ERROR="{{}";
	
	@Autowired
	AuditoriaAspect aspect;	
	@SuppressWarnings("unchecked")
	@Before
	public void init(){
		aspect.setAuditoriaService(Mockito.mock(AuditoriaServiceImpl.class));
		KafkaProducer<Integer, String> producer= Mockito.mock(KafkaProducer.class);
		asincronoServiceImpl.setProducer(producer);
	}	
	@Test
	public void settersTest() {
		AperturaTcEndpoint endpoint = new AperturaTcEndpoint();
		endpoint.setJoltRequestConversor(new JoltRequestConversor());
		endpoint.setAperturaService(new AperturaServiceImpl());
		endpoint.setMapper(mapper);
		assertNotNull(endpoint);
	}
	
	@Test
	public void prepareResponseTest() throws GenericException {
		GenericMessage<String> messageActivacion = new GenericMessage<String>(ALTA_TARJETA_RESPONSE);
		assertNotNull(aperturaTcEndpoint.prepareResponse(messageActivacion).getPayload());
	}
	
	@Test(expected=MicroserviceException.class)
	public void prepareResponseErrorTest() throws GenericException {
		GenericMessage<String> messageActivacion = new GenericMessage<String>(JSON_ERROR);
		aperturaTcEndpoint.prepareResponse(messageActivacion).getPayload();
	}
	

	@Test(expected=MicroserviceException.class)
	public void validarAltaTcErrorTest() throws GenericException, JsonParseException, JsonMappingException, IOException {
		AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);
		GenericMessage<AperturaTcRequest> messageActivacion = new GenericMessage<AperturaTcRequest>(orq_request);
		aperturaTcEndpoint.setMapper(mapper);
		assertNotNull(aperturaTcEndpoint.validarAltaTc(messageActivacion));
	}
	@Test
	public void validarAltaTcTest() throws GenericException, JsonParseException, JsonMappingException, IOException {
		AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);
		GenericMessage<AperturaTcRequest> messageActivacion = new GenericMessage<AperturaTcRequest>(orq_request);
		aperturaTcEndpoint.setMapper(mapper);
		SecurityRestClient securityRestClient = Mockito.mock(SecurityRestClient.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(String.class)).thenReturn(CONSULTA_ADIC_RESPONSE);
		Mockito.when(securityRestClient.invokeExternalService(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(response);
		aperturaServiceImpl.setSecurityRestClient(securityRestClient);		
		aperturaTcEndpoint.setAperturaService(aperturaServiceImpl);
		aperturaTcEndpoint.validarAltaTc(messageActivacion);
	}
	
	@Test
	public void inicioCrearClienteTest() throws GenericException, JsonParseException, JsonMappingException, IOException {
		AperturaTcRequest orq_request= mapper.readValue(CREAR_CLIENTE_REQUEST, AperturaTcRequest.class);
		GenericMessage<AperturaTcRequest> messageActivacion = new GenericMessage<AperturaTcRequest>(orq_request);
		aperturaTcEndpoint.setMapper(mapper);
		SecurityRestClient securityRestClient = Mockito.mock(SecurityRestClient.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(String.class)).thenReturn(CREAR_CLIENTE_RESPONSE);
		Mockito.when(securityRestClient.invokeExternalService(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(response);
		aperturaServiceImpl.setSecurityRestClient(securityRestClient);		
		aperturaTcEndpoint.setAperturaService(aperturaServiceImpl);
		aperturaTcEndpoint.inicioCrearCliente(messageActivacion);
	}
	
	@Test
	public void splitRequestTokenTest() throws MicroserviceException, JsonParseException, JsonMappingException, IOException {		
		AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);	
		Map<String, Object> map = new HashMap<>();
		map.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
		aperturaTcEndpoint.splitRequest(new GenericMessage<Map<String, Object>>(map));
		//CREAR MEDIO CONTACTO GENERAL
		orq_request= mapper.readValue(ORQ_REQUEST_CREAR_CMG, AperturaTcRequest.class);	
		map = new HashMap<>();
		map.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
		aperturaTcEndpoint.splitRequest(new GenericMessage<Map<String, Object>>(map));
		//BENEFICIARIO
		orq_request= mapper.readValue(ORQ_REQUEST_BENEFICIARIO, AperturaTcRequest.class);	
		map = new HashMap<>();
		map.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
		aperturaTcEndpoint.splitRequest(new GenericMessage<Map<String, Object>>(map));	
		
		//CREAR CLIENTE 
		orq_request= mapper.readValue(ORQ_REQUEST_CREAR_CMG, AperturaTcRequest.class);	
		map = new HashMap<>();
		map.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE), CREAR_CLIENTE_RESPONSE);
		aperturaTcEndpoint.splitRequest(new GenericMessage<Map<String, Object>>(map));
	}
	
	@Test(expected = MicroserviceException.class)
	public void buildResponseCrearClienteMicroserviceExceptionTest() throws GenericException {
		HashMap<String, String> mapCrearCliente = new HashMap<>();
		mapCrearCliente.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
				CREAR_CLIENTE_RESPONSE);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		list.add(mapCrearCliente);		
		aperturaTcEndpoint.buildResponse(list);
	}
	
	@Test
	public void buildResponseAltaTarjetaMicroserviceExceptionTest() throws GenericException {
		HashMap<String, String> mapAltaTarjeta = new HashMap<>();
		mapAltaTarjeta.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
				ALTA_TARJETA_REQUEST);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		list.add(mapAltaTarjeta);		
		aperturaTcEndpoint.buildResponse(list);
	}
	
	@Test(expected = MicroserviceException.class)
	public void buildActualizarMedioContactoMicroserviceExceptionTest() throws GenericException {
		HashMap<String, String> mapAltaTarjeta = new HashMap<>();
		mapAltaTarjeta.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL),
				ACTUALIZA_MCGENERAL_RESPONSE);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		list.add(mapAltaTarjeta);		
		aperturaTcEndpoint.buildResponse(list);
	}	
	@Test
	public void routeMessageTest() throws MicroserviceException, JsonParseException, JsonMappingException, IOException {	
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL), "");
		aperturaTcEndpoint.routeMessage(new GenericMessage<HashMap<String, String>>(map));
		map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL), "");
		aperturaTcEndpoint.routeMessage(new GenericMessage<HashMap<String, String>>(map));
		map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO), "");
		aperturaTcEndpoint.routeMessage(new GenericMessage<HashMap<String, String>>(map));
		map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE), "");
		aperturaTcEndpoint.routeMessage(new GenericMessage<HashMap<String, String>>(map));
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA), "");
		aperturaTcEndpoint.routeMessage(new GenericMessage<HashMap<String, String>>(map));			
	}
	@Test
	public void wsCrearMedioContactoGeneralGatewayTest() throws MicroserviceException, JsonParseException, JsonMappingException, IOException {	
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL), ORQ_REQUEST_CREAR_CMG);
		aperturaTcEndpoint.wsCrearMedioContactoGeneralGateway(new GenericMessage<HashMap<String, String>>(map));				
	}
	@Test
	public void wsActualizarMedioContactoGeneralGatewayTest() throws MicroserviceException, JsonParseException, JsonMappingException, IOException {	
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL), ORQ_REQUEST_CREAR_CMG);
		aperturaTcEndpoint.wsActualizarMedioContactoGeneralGateway(new GenericMessage<HashMap<String, String>>(map));				
	}
	@Test
	public void wsCrearMedioContactoEspecificoGatewayTest() throws MicroserviceException, JsonParseException, JsonMappingException, IOException {	
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO), ORQ_REQUEST_CREAR_CMG);
		aperturaTcEndpoint.wsCrearMedioContactoEspecificoGateway(new GenericMessage<HashMap<String, String>>(map));				
	}
	@Test
	public void wsAltaTarjetaChannelGatewayTest() throws MicroserviceException, JsonParseException, JsonMappingException, IOException {	
		aperturaTcEndpoint.wsAltaTarjetaChannelGateway(new GenericMessage<String>(ALTA_TARJETA_RESPONSE));				
	}
	
	
}
