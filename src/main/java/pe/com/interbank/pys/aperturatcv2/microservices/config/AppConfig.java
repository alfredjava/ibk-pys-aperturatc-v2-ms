package pe.com.interbank.pys.aperturatcv2.microservices.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.ws.rs.client.Client;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import pe.com.interbank.pys.aperturatcv2.microservices.filter.SecurityFilter;
import pe.com.interbank.pys.aperturatcv2.microservices.jolt.JoltFileProcessor;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.message.KafkaAuthentication;
import pe.com.interbank.pys.trace.microservices.service.AuditoriaService;
import pe.com.interbank.pys.trace.microservices.service.AuditoriaServiceImpl;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

/**
 * 
 * @author XT7189
 *
 */
@Configuration
public class AppConfig {

	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	/**
	 * genera una instancia generadora de productores kafka
	 * 
	 * @return ProducerFactory
	 */
	@Bean
	public KafkaProducer<Integer, String> producerFactory() {
		return KafkaAuthentication.getProducer();
	}

	/**
	 * Bean para filtro de seguridad
	 * 
	 * @return
	 */
	@Bean
	public Filter securityFilter() {
		return new SecurityFilter();
	}

	/**
	 * Bean para registrar el Filtro de seguridad
	 * 
	 * @return
	 */
	@Bean
	FilterRegistrationBean securityFilterBean() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(securityFilter());
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	@Qualifier("clientBus")
	Client clientBus() {
		return RestClientConfig.busRestClientInit();
	}

	@Bean
	@Qualifier("clientMs")
	Client clientMs() {
		return RestClientConfig.msRestClientInit();
	}

	@Bean
	@Qualifier("conversorList")
	public Map<String, List<Object>> conversorList() {
		HashMap<String, List<Object>> specList = new HashMap<>();
		JoltFileProcessor.readConversors(specList);
		String info="cargados conversors: "+ ((specList.keySet()!=null)?specList.keySet().toString():"");
		logger.info(info);
		return specList;
	}

	@Bean
	@Qualifier("sorterList")
	public Map<String, List<Object>> sorterList() {
		HashMap<String, List<Object>> specList = new HashMap<>();
		JoltFileProcessor.readSorters(specList);
		String info="cargados sorters: "+ ((specList.keySet()!=null)?specList.keySet().toString():"");
		logger.info(info);		
		return specList;
	}
	
	@Bean
    public AuditoriaService auditoriaService() {
        return new AuditoriaServiceImpl();
    }
	
	@Bean(name = "auditoriaThreadPoolTaskExecutor")
	public Executor auditoriaThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(
				Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.AUDITORIA_MIN_THREADS)));
		executor.setMaxPoolSize(
				Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.AUDITORIA_MAX_THREADS)));
		executor.setQueueCapacity(
				Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.AUDITORIA_QUEUE_SIZE)));
		executor.setThreadNamePrefix("AuditoriaAsync-");
		executor.initialize();
		return executor;
	}

}
