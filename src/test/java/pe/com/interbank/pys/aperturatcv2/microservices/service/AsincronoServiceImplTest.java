package pe.com.interbank.pys.aperturatcv2.microservices.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Test;
import org.mockito.Mockito;

import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;

public class AsincronoServiceImplTest {
	
	@SuppressWarnings("unchecked")
	@Test(expected = LegacyException.class)
	public void legacyExceptionTest() throws LegacyException, MicroserviceException {
		AsincronoServiceImpl serviceImpl = new AsincronoServiceImpl();
		KafkaProducer<Integer, String> kafkaProducer = Mockito.mock(KafkaProducer.class);
		serviceImpl.setProducer(kafkaProducer);
		serviceImpl.escribirTopicoAsincronoTemplate("testservice",
				"{\"trama\":no,\"esperada\":\"para\",\"error\":\"legacy\"}", "testtopic");
	}

	@SuppressWarnings("unchecked")
	@Test(expected = MicroserviceException.class)
	public void microserviceExceptionTest() throws LegacyException, MicroserviceException {
		AsincronoServiceImpl serviceImpl = new AsincronoServiceImpl();
		KafkaProducer<Integer, String> kafkaProducer = Mockito.mock(KafkaProducer.class);
		serviceImpl.setProducer(kafkaProducer);
		serviceImpl.escribirTopicoAsincronoTemplate("testservice", "{\"bad\":\"json\"}}", "testtopic");
	}
}
