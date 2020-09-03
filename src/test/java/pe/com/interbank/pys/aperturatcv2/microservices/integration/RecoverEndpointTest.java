package pe.com.interbank.pys.aperturatcv2.microservices.integration;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.com.interbank.pys.aperturatcv2.microservices.server.AperturaTcServer;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;
@RunWith(SpringJUnit4ClassRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AperturaTcServer.class })
@PrepareForTest(MessagingTemplate.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class RecoverEndpointTest {
	private String CREAR_CLIENTE_REQUEST = PropertiesCache.getInstance().getProperty("test.tramacreacliente.request");
	@Autowired
	RecoverEndpoint recoverEndpoint;
	
	@SuppressWarnings("unchecked")
	@Test
	public void enviarTopicoRecoverTest(){
		KafkaProducer<Integer, String> producer= Mockito.mock(KafkaProducer.class);
		recoverEndpoint.setProducer(producer);
		recoverEndpoint.enviarTopicoRecover(new GenericMessage<String>(CREAR_CLIENTE_REQUEST));
		
	}
}
