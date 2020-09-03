package pe.com.interbank.pys.aperturatcv2.microservices.service;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.message.ProducerCallbackOrq;
import pe.com.interbank.pys.trace.microservices.util.Encryptor;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class AsincronoServiceImpl implements AsincronoService {

	private static final Logger logger = LoggerFactory.getLogger(AsincronoServiceImpl.class);
	@Autowired
	private KafkaProducer<Integer, String> producer;

	@Override
	public void escribirTopicoAsincronoTemplate(String serviceId, String request, String topico)
			throws LegacyException, MicroserviceException {
		String messageId = JsonUtil.getCampoTrama(Constantes.MESSAGE_ID_MAP, request);
		String className = this.getClass().getName();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(request);
			((ObjectNode) rootNode).put("operation", serviceId);
			String kafkaRequest = objectMapper.writeValueAsString(rootNode);
			String encryptMessage = Encryptor.encrypt(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.AES_KEY),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.AES_VECTOR), kafkaRequest);
			ProducerCallbackOrq producerCallback = new ProducerCallbackOrq();
			producerCallback.setMessage(encryptMessage);
			producerCallback.setTopico(topico);
			producerCallback.setDestination(serviceId);
			producerCallback.setMessageId(messageId);
			ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topico, encryptMessage);
			producer.send(producerRecord, producerCallback).get(
					Long.parseLong(PropertiesCache.getInstance().getProperty(ConfigConstantes.SEND_TIMEOUT_MS)),
					TimeUnit.MILLISECONDS);
		} catch (JsonProcessingException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ e.getClass().getName() + " en " + className + ":" + e.getStackTrace()[0].getMethodName()
					+ " - Error: " + e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId;
			logger.error(mensaje, e);
			throw new LegacyException(mensaje, e);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ e.getClass().getName() + " en " + className + ":" + e.getStackTrace()[0].getMethodName()
					+ " -  Error: " + e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId;
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	public void setProducer(KafkaProducer<Integer, String> producer) {
		this.producer = producer;
	}

}
