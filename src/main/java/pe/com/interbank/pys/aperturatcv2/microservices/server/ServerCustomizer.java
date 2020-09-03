package pe.com.interbank.pys.aperturatcv2.microservices.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class ServerCustomizer implements JettyServerCustomizer {

	@Override
	/**
	 * este metodo provee espacio para configurar el servidor jetty por el
	 * momento no hace falta agregar personalizaciones
	 */
	public void customize(Server server) {
		 //Falta personalizacion
        // Personalizar el pool de conexiones
       final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
       threadPool.setMaxThreads(Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVER_MAX_THREADS)));
       threadPool.setMinThreads(Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVER_MIN_THREADS)));
       threadPool.setIdleTimeout(Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVER_IDLE_TIMEOUT)));

	}

}
