package pe.com.interbank.pys.aperturatcv2.microservices.config;

import java.io.File;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class RestClientConfig {

	private static final String SSL = "SSL";
	private static final Logger logger = LoggerFactory.getLogger(RestClientConfig.class);

	protected RestClientConfig() throws IllegalAccessException {
		throw new IllegalAccessException("Clase estatica");
	}

	public static Client msRestClientInit() {
		Client client = null;
		try (PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager()) {
			client = ClientBuilder.newBuilder().build();
			client.property(ClientProperties.CONNECT_TIMEOUT,
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_CONNECT_TIMEOUT));
			client.property(ClientProperties.READ_TIMEOUT,
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_READ_TIMEOUT));

			connectionManager.setMaxTotal(
					Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.CONNECTION_MAX_SIZE)));
			connectionManager.setDefaultMaxPerRoute(Integer
					.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.CONNECTION_MAX_PER_ROUTE)));

			client.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);

		} catch (Exception e) {
			logger.error("Error al inicializar el cliente: " + e.getMessage(), e);
		}
		return client;
	}

	public static Client busRestClientInit() {
		Client client = null;
		try (PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager()) {
			client = ClientBuilder.newBuilder().sslContext(getBusSSLContextCertified())
					.hostnameVerifier((String hostname, javax.net.ssl.SSLSession sslSession) -> true).build();
			HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REST_USUARIO),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REST_KEY));
			client.register(feature);

			client.property(ClientProperties.CONNECT_TIMEOUT,
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_CONNECT_TIMEOUT));
			client.property(ClientProperties.READ_TIMEOUT,
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_READ_TIMEOUT));

			connectionManager.setMaxTotal(
					Integer.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.CONNECTION_MAX_SIZE)));
			connectionManager.setDefaultMaxPerRoute(Integer
					.parseInt(PropertiesCache.getInstance().getProperty(ConfigConstantes.CONNECTION_MAX_PER_ROUTE)));

			client.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
		} catch (Exception e) {
			logger.error("Error al inicializar el cliente: " + e.getMessage(), e);
		}
		return client;
	}

	/**
	 * Retorna el protocolo SSL del contexto actual
	 * 
	 * @return SSLContext Contexto con protocolo SSL
	 */
	public static SSLContext getBusSSLContextCertified() {
		SSLContext context = null;
		try {
			String resourceDir = PropertiesCache.getInstance().getProperty(ConfigConstantes.APP_RESOURCES_LOCATION);
			String keyPassword = PropertiesCache.getInstance().getProperty(ConfigConstantes.KEYSTORE_VALUE);

			SslConfigurator sslConfig = SslConfigurator.newInstance()
					.keyStoreFile(resourceDir + File.separator
							+ PropertiesCache.getInstance().getProperty(ConfigConstantes.KEYSTORE_FILE))
					.keyPassword(keyPassword)
					.keyStoreType(PropertiesCache.getInstance().getProperty(ConfigConstantes.KEYSTORE_TYPE))
					.securityProtocol(SSL);
			context = sslConfig.createSSLContext();

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return context;
	}

}
