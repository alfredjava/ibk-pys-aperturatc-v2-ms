package pe.com.interbank.pys.aperturatcv2.microservices.config;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author XT7189 JaxRsApplication: indica que clases son las que se escanearan
 *         en busca de anotaciones jax rs
 */
@Configuration
public class JaxRsApplication {

	private static final Logger logger = LoggerFactory.getLogger(JaxRsApplication.class);

	private ApplicationContext appCtx;

	@Inject
	public JaxRsApplication(ApplicationContext appCtx) {
		this.appCtx = appCtx;
	}

	/**
	 * JaxRsApplication: Register JAX-RS application components.
	 */
	@Bean
	public ResourceConfig jersey() {
		ResourceConfig config = new ResourceConfig();
		logger.info("Jersey resource classes found:");
		appCtx.getBeansWithAnnotation(Path.class).forEach((name, resource) -> {
			logger.info("Path -> {}", resource.getClass().getName());
			config.register(resource);
		});
		appCtx.getBeansWithAnnotation(Provider.class).forEach((name, resource) -> {
			logger.info("Provider -> {}", resource.getClass().getName());
			config.register(resource);
		});
		config.register(JacksonFeature.class);
		return config;
	}
}
