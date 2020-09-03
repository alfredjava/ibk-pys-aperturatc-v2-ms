package pe.com.interbank.pys.aperturatcv2.microservices.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
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

import pe.com.interbank.pys.aperturatcv2.microservices.audit.AuditoriaAspect;
import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.service.AuditoriaServiceImpl;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class SecurityRestClientTest {

	@Autowired
	SecurityRestClient securityRestClient;
	
	@Autowired
	AuditoriaAspect aspect;	
	private String ALTA_TARJETA_RESPONSE = PropertiesCache.getInstance().getProperty("test.altatc.response");
	private String ALTA_TARJETA_REQUEST = PropertiesCache.getInstance().getProperty("test.altatc.request");
	
	@Autowired
	private AsincronoServiceImpl asincronoServiceImpl;
	
	@SuppressWarnings("unchecked")
	@Before
	public void init(){
		KafkaProducer<Integer, String> producer= Mockito.mock(KafkaProducer.class);
		asincronoServiceImpl.setProducer(producer);
	}
	
	@Test
	public void invokeExternalServiceTest() throws MicroserviceException{
		aspect.setAuditoriaService(Mockito.mock(AuditoriaServiceImpl.class));
		Client client = Mockito.mock(Client.class);
		WebTarget target = Mockito.mock(WebTarget.class);
		Builder builder = Mockito.mock(Builder.class);
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.readEntity(String.class)).thenReturn(ALTA_TARJETA_RESPONSE);
		Mockito.when(builder.post(Mockito.any(Entity.class))).thenReturn(response);
		Mockito.when(target.request(Mockito.anyString())).thenReturn(builder);
		Mockito.when(target.path(Mockito.anyString())).thenReturn(target);
		Mockito.when(client.target(Mockito.anyString())).thenReturn(target);
		
		securityRestClient.setClientBus(client);
		securityRestClient.setClientMs(client);
		securityRestClient.invokeExternalService(ALTA_TARJETA_REQUEST, "", "", "SCRTESTMS001", -10L, "", true);
		securityRestClient.invokeExternalService(ALTA_TARJETA_REQUEST, "", "", "SCRTESTMS002", -10L, "", false);
	}
	@Test(expected=MicroserviceException.class)
	public void initExceptionTest() throws MicroserviceException{
		securityRestClient.setClientBus(null);
		securityRestClient.setClientMs(null);
		securityRestClient.init("", "", "SCRTESTMS001", "messageId", -10, true);
	}

}
