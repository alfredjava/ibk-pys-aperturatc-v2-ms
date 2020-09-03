package pe.com.interbank.pys.aperturatcv2.microservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.support.PeriodicTrigger;

@Configuration
public class SpringIntegrationConfig {

	@Bean
	@Description("validarAltaTc: punto de entrada dirige el el mensaje al service activator subscrito a este canal.")
	public MessageChannel validarAltaTc() {
		return new DirectChannel();
	}
	
	@Bean
	@Description("requestChannel: punto de entrada dirige el el mensaje al service activator subscrito a este canal.")
	public MessageChannel requestChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("routerChannel: canal que tendra la logica segun la cual el mensaje se mueve por el flujo.")
	public MessageChannel routerChannel() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(10);
		tpte.setMaxPoolSize(100);
		tpte.setQueueCapacity(150);
		tpte.setThreadNamePrefix("RouterThread");
		tpte.initialize();
		return new ExecutorChannel(tpte);
	}

	@Bean
	@Description("crearMedioContactoGeneralChannel: canal que se encarga de realizar la llamada REST a Crear medio de contacto general")
	public MessageChannel crearMedioContactoGeneralChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("actualizarMedioContactoGeneralChannel: canal que se encarga de realizar la llamada REST a Crear medio de contacto general")
	public MessageChannel actualizarMedioContactoGeneralChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("crearMedioContactoEspecificoChannel: canal que se encarga de realizar la llamada REST a Crear medio de contacto general")
	public MessageChannel crearMedioContactoEspecificoChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("splitterChannel: canal que se encarga de realizar la llamada REST a Crear cliente")
	public MessageChannel splitterChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("altaTarjetaChannel: canal que creara el objeto request para alta de tarjeta.")
	public MessageChannel altaTarjetaChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("aggregateChannel: canal que recibira las respuestas de los servicios y unificara el mensaje.")
	public MessageChannel aggregateChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("finalChannel: punto de salida dirige el el mensaje al controller subscrito a este canal y finaliza la parte sincrona del servicio.")
	public MessageChannel finalChannel() {
		return new DirectChannel();
	}

	@Bean
	@Description("asincronoChannel: canal que se encarga de realizar el inicio del proceso asincrono")
	public QueueChannel asincronoChannel() {
		return new QueueChannel(100);
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata defaultPoller() {
		PollerMetadata pollerMetadata = new PollerMetadata();
		pollerMetadata.setTrigger(new PeriodicTrigger(10));
		return pollerMetadata;
	}

	@Bean
	@Description("auditoriaChannel: canal que tendra la logica del envio a auditoria para hacer este asincronamente.")
	public MessageChannel auditoriaChannel() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(10);
		tpte.setMaxPoolSize(100);
		tpte.setQueueCapacity(150);
		tpte.setThreadNamePrefix("AuditoriaThread");
		tpte.initialize();
		return new ExecutorChannel(tpte);
	}

	@Bean
	@Description("prepareRequestChannel: dirige el mensaje al service activator para su estructuracion en sus respectivos request.")
	public MessageChannel prepareRequestChannel() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(10);
		tpte.setMaxPoolSize(100);
		tpte.setQueueCapacity(150);
		tpte.setThreadNamePrefix("prepareRequestThread");
		tpte.initialize();
		return new ExecutorChannel(tpte);
	}

	@Bean
	@Description("kafkaChannel: dirige el el mensaje al service activator para su escritura en un topico kafka.")
	public MessageChannel kafkaChannel() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(10);
		tpte.setMaxPoolSize(100);
		tpte.setQueueCapacity(150);
		tpte.setThreadNamePrefix("KafkaThread");
		tpte.initialize();
		return new ExecutorChannel(tpte);
	}

	@Bean
	@Description("recoverChannel: ")
	public MessageChannel recoverChannel() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(10);
		tpte.setMaxPoolSize(100);
		tpte.setQueueCapacity(150);
		tpte.setThreadNamePrefix("RecoverThread");
		tpte.initialize();
		return new ExecutorChannel(tpte);
	}
}
