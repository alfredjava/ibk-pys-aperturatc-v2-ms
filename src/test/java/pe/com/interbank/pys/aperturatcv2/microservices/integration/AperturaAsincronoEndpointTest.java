package pe.com.interbank.pys.aperturatcv2.microservices.integration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.core.MessagingTemplate;
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
import pe.com.interbank.pys.aperturatcv2.microservices.service.AsincronoServiceImpl;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.service.AuditoriaServiceImpl;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@PrepareForTest(MessagingTemplate.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AperturaAsincronoEndpointTest {
	private static final Logger logger = LoggerFactory.getLogger(AperturaAsincronoEndpointTest.class);
	private String ORQ_REQUEST = PropertiesCache.getInstance().getProperty("test.tramamc.request");
	
	private String ALTA_TARJETA_RESPONSE = PropertiesCache.getInstance().getProperty("test.altatc.response");
	private String ALTA_TARJETA_REQUEST = PropertiesCache.getInstance().getProperty("test.altatc.request");
	private String CREAR_CLIENTE_RESPONSE = PropertiesCache.getInstance().getProperty("test.crearcliente.response");

	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private AperturaAsincronoEndpoint asincronoEndpoint;
	
	@Autowired
	private AsincronoServiceImpl asincronoServiceImpl;
	
	@Autowired
	AuditoriaAspect aspect;	
	@SuppressWarnings("unchecked")
	@Before
	public void init(){
		aspect.setAuditoriaService(Mockito.mock(AuditoriaServiceImpl.class));
		KafkaProducer<Integer, String> producer= Mockito.mock(KafkaProducer.class);
		asincronoServiceImpl.setProducer(producer);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void prepareKafkaTest() throws IOException {
		try {
			AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);	
			Map<String, Object> map = new HashMap<>();
			map.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			KafkaProducer<Integer, String> producer=Mockito.mock(KafkaProducer.class);
			asincronoServiceImpl.setProducer(producer);
			asincronoEndpoint.prepareKafka(new GenericMessage<Map<String, Object>>(map));
		} catch (MicroserviceException e) {
			logger.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void sendKafkaNotificacionTest() throws JsonParseException, JsonMappingException, IOException {
		try {
			Map<String, Object> mapHeaders = new HashMap<>();			
			
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE),
					ALTA_TARJETA_RESPONSE);			
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_NOTIFICACION));

			AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);				
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			
			KafkaProducer<Integer, String> producer=Mockito.mock(KafkaProducer.class);
			asincronoServiceImpl.setProducer(producer);			
			
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_GRABAR_LDPD
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_GRABAR_LDPD));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_ACTUALIZAR_CAMPANIA
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_ACTUALIZAR_CAMPANIA));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_ACTUALIZAR_CAMPANIA
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_REGISTRAR_ADQ
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_AFILIAR_SEGURO_JLT
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_AFILIAR_SEGURO_JLT));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_MS_REGISTRAR_MONITOR
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_REGISTRAR_MONITOR));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			//SERVICE_ID_MS_FECHA_HORA_ENTREGA
			mapHeaders = new HashMap<>();
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
					ALTA_TARJETA_REQUEST);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_FECHA_HORA_ENTREGA));
			mapHeaders.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, orq_request);
			mapHeaders.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE),
					CREAR_CLIENTE_RESPONSE);	
			asincronoEndpoint.sendKafka(new GenericMessage<Map<String, Object>>(mapHeaders));
			
		} catch (MicroserviceException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
	@Test
	public void settersTest() {
		asincronoEndpoint.setAsincronoServiceImpl(new AsincronoServiceImpl());
		asincronoEndpoint.setJoltRequestConversor(new JoltRequestConversor());
		asincronoEndpoint.setMapper(new ObjectMapper());
	}

	@Test(expected=MicroserviceException.class)
	public void obtenerRequestAsincronoExceptionTest() throws MicroserviceException {
	        Object mockItem = PowerMockito.mock(Object.class);
	        Mockito.when(mockItem.toString()).thenReturn(mockItem.getClass().getName());	        
	        asincronoEndpoint.obtenerRequestAsincrono(mockItem,"UNITTEST");
	       
	   
	}
	

}
