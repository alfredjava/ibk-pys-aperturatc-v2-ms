package pe.com.interbank.pys.aperturatcv2.microservices.integration;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.trace.microservices.message.ProducerCallbackOrq;
import pe.com.interbank.pys.trace.microservices.util.Encryptor;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class RecoverEndpoint {

	@Autowired
	private KafkaProducer<Integer, String> producer;
	private static final Logger logger = LoggerFactory.getLogger(RecoverEndpoint.class);

	@ServiceActivator(inputChannel = "recoverChannel")
	public void enviarTopicoRecover(Message<String> msg) {
		String mensaje = msg.getPayload();
		String messageId = JsonUtil.getCampoTrama("messageId", mensaje);
		try {
			String encryptMessage = Encryptor.encrypt(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.AES_KEY),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.AES_VECTOR), mensaje);
			ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.TOPICO_RECOVER), encryptMessage);
			ProducerCallbackOrq producerCallback = new ProducerCallbackOrq();
			producerCallback.setMessage(encryptMessage);
			producerCallback.setTopico(PropertiesCache.getInstance().getProperty(ConfigConstantes.TOPICO_RECOVER));
			producerCallback.setDestination(PropertiesCache.getInstance().getProperty(ConfigConstantes.TOPICO_RECOVER));
			producerCallback.setMessageId(messageId);
			producer.send(producerRecord, producerCallback).get(
					Long.parseLong(PropertiesCache.getInstance().getProperty(ConfigConstantes.SEND_TIMEOUT_MS)),
					TimeUnit.MILLISECONDS);
			String info="Fin de enviarTopicoRecover: " + Constantes.MESSAGE_ID_LOG + messageId;
			logger.debug(info);
		} catch (Exception e) {
			logger.error("Error generico al enviar al topico recover: " + " en " + this.getClass().getName() + ":"
					+ e.getStackTrace()[0].getMethodName() + " - " + e.getMessage() + Constantes.MESSAGE_ID_LOG
					+ messageId, e);
		}
	}

	public void setProducer(KafkaProducer<Integer, String> producer) {
		this.producer = producer;
	}
	
	
}
