package pe.com.interbank.pys.aperturatcv2.microservices.server;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "pe.com.interbank.pys.aperturatcv2.microservices")
@EnableIntegration
@IntegrationComponentScan
@EnableAsync
public class AperturaTcServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "aperturatcv2-server");
		Locale.setDefault(new Locale("es", "pe"));
		SpringApplication.run(AperturaTcServer.class);//NOSONAR
	}

	@Bean
	public ServletRegistrationBean jerseyServlet(@Qualifier("jersey") ResourceConfig jersey) {
		return new ServletRegistrationBean(new ServletContainer(jersey), "/rest/*");
	}

	@Bean
	public EmbeddedServletContainerFactory containerFactory() {
		final JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory = new JettyEmbeddedServletContainerFactory() {
			@Override
			protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {
				return new JettyEmbeddedServletContainer(server);
			}
		};
		jettyEmbeddedServletContainerFactory.setSessionTimeout(80, TimeUnit.SECONDS);
		jettyEmbeddedServletContainerFactory.addServerCustomizers(new ServerCustomizer());
		return jettyEmbeddedServletContainerFactory;
	}

}
