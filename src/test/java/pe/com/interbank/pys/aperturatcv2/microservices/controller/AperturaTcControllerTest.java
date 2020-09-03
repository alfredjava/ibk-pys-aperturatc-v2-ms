package pe.com.interbank.pys.aperturatcv2.microservices.controller;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;
import pe.com.interbank.pys.aperturatcv2.microservices.service.AsincronoServiceImpl;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;


@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@PrepareForTest(MessagingTemplate.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AperturaTcControllerTest {
	private AperturaTcController aperturaTcController;
	private String ORQ_REQUEST = PropertiesCache.getInstance().getProperty("test.trama.request");
	private String ORQ_RESPONSE = PropertiesCache.getInstance().getProperty("test.trama.response");
	
	private String ALTA_TARJETA_RESPONSE = PropertiesCache.getInstance().getProperty("test.altatc.response");
	private String ALTA_TARJETA_RESPONSE_ERROR = PropertiesCache.getInstance().getProperty("test.altatc.error");
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private AsincronoServiceImpl asincronoServiceImpl;
	
	@SuppressWarnings("unchecked")
	@Before
	public void init(){
		KafkaProducer<Integer, String> producer= Mockito.mock(KafkaProducer.class);
		asincronoServiceImpl.setProducer(producer);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void orquestarTest() throws JsonParseException, JsonMappingException, IOException {
		aperturaTcController = Mockito.spy(new AperturaTcController());
		MessagingTemplate messagingTemplate = PowerMockito.mock(MessagingTemplate.class);
		Message<AperturaTcResponse> message = Mockito.spy(Message.class);
		MessageHeaders messageHeader = Mockito.mock(MessageHeaders.class);
		Mockito.when(message.getHeaders()).thenReturn(messageHeader);
		Mockito.when(messageHeader.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE)))
				.thenReturn(ALTA_TARJETA_RESPONSE);		
		AperturaTcResponse orq_response= mapper.readValue(ORQ_RESPONSE, AperturaTcResponse.class);		
		Mockito.when(message.getPayload()).thenReturn(orq_response);		
		AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);				
		PowerMockito.doReturn(message).when(aperturaTcController).getSendAndReceive(messagingTemplate, orq_request);
		aperturaTcController.setTemplate(messagingTemplate);
		Response r = aperturaTcController.aperturaTarjetaPost(orq_request);
		assertNotNull(r);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void orquestarExceptionTest() throws JsonParseException, JsonMappingException, IOException {
		aperturaTcController = Mockito.spy(new AperturaTcController());
		MessagingTemplate messagingTemplate = PowerMockito.mock(MessagingTemplate.class);
		Message<AperturaTcResponse> message = Mockito.spy(Message.class);
		MessageHeaders messageHeader = Mockito.mock(MessageHeaders.class);
		Mockito.when(message.getHeaders()).thenReturn(messageHeader);
		Mockito.when(messageHeader.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE)))
				.thenReturn(ALTA_TARJETA_RESPONSE);
		Mockito.when(message.getPayload()).thenReturn(null);		
		AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);			
		PowerMockito.doReturn(message).when(aperturaTcController).getSendAndReceive(messagingTemplate, orq_request);
		aperturaTcController.setTemplate(messagingTemplate);
		Response r = aperturaTcController.aperturaTarjetaPost(orq_request);
		assertNotNull(r);
	}
	

	@Test
	@SuppressWarnings({ "rawtypes" })
	public void orquestarMessageHandlingExceptionTest() {
		try {
			aperturaTcController = Mockito.spy(new AperturaTcController());
			MessagingTemplate messagingTemplate = PowerMockito.mock(MessagingTemplate.class);
			Message message = PowerMockito.mock(Message.class);
			MessageHeaders messageHader = PowerMockito.mock(MessageHeaders.class);
			MessagingException messagingException = PowerMockito.mock(MessagingException.class);
			MicroserviceException throwable = PowerMockito.mock(MicroserviceException.class);
			PowerMockito.doReturn(messageHader).when(message).getHeaders();
			PowerMockito.doReturn(ALTA_TARJETA_RESPONSE_ERROR).when(messageHader)
					.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE));
			PowerMockito.doReturn(messagingException).when(message).getPayload();
			PowerMockito.doReturn(throwable).when(messagingException).getRootCause();
			PowerMockito.doReturn(ALTA_TARJETA_RESPONSE_ERROR).when(throwable).getJsonCause();
			AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);			
			PowerMockito.doReturn(message).when(aperturaTcController).getSendAndReceive(messagingTemplate, orq_request);
			Response r = aperturaTcController.aperturaTarjetaPost(orq_request);
			assertNotNull(r);
		} catch (Exception e) {
		}
	}


	@Test
	public void orquestarMessagingExceptionTest() {
		try {		
			aperturaTcController = Mockito.spy(new AperturaTcController());
			MessagingTemplate messagingTemplate = PowerMockito.mock(MessagingTemplate.class);
			@SuppressWarnings("unchecked")
			Message<MessagingException> message = Mockito.spy(Message.class);			
			MessageHeaders messageHader = PowerMockito.mock(MessageHeaders.class);
			MicroserviceException microserviceException = new MicroserviceException("JUNITMicroserviceException");
			microserviceException.setJsonCause(ALTA_TARJETA_RESPONSE_ERROR);
			MessagingException messagingException = new MessagingException("JUNITMessagingException",microserviceException);
			PowerMockito.doReturn(messageHader).when(message).getHeaders();
			PowerMockito.doReturn(ALTA_TARJETA_RESPONSE_ERROR).when(messageHader)
					.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE));
			PowerMockito.doReturn(messagingException).when(message).getPayload();
			AperturaTcRequest orq_request= mapper.readValue(ORQ_REQUEST, AperturaTcRequest.class);	
			PowerMockito.doReturn(message).when(aperturaTcController).getSendAndReceive(messagingTemplate, orq_request);
			Response r = aperturaTcController.aperturaTarjetaPost(orq_request);
			assertNotNull(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
