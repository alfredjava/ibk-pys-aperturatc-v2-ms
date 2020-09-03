package pe.com.interbank.pys.aperturatcv2.microservices.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;

/**
 * Clase que genera el cliente Rest y maneja SSL
 * 
 * @author Alex Aguirre
 *
 */
@Component
public class SecurityRestClient {

	@Autowired
	@Qualifier("clientBus")
	private Client clientBus;

	@Autowired
	@Qualifier("clientMs")
	private Client clientMs;

	private static final Logger logger = LoggerFactory.getLogger(SecurityRestClient.class);

	public Builder init(String host, String path, String serviceId, String messageId, long offset,
			boolean isMicroservice) throws MicroserviceException {
		try {
			WebTarget target;
			if (isMicroservice) {
				target = clientMs.target(host).path(path);
			} else {
				target = clientBus.target(host).path(path);
			}

			return target.request(Constantes.MEDIA_TYPE_JSON_UTF_8);
		} catch (Exception e) {
			String mensaje = "Excepcion el la inicializacion de SecurityRestClient - Error: " + e.getMessage()
					+ Constantes.MESSAGE_ID_LOG + messageId;
			if (offset != -10L) {
				mensaje = mensaje.concat(Constantes.OFFSET_LOG + offset);
			}
			mensaje = mensaje.concat(" para" + Constantes.SERVICE_ID_LOG + serviceId);
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	public Response invokeExternalService(String request, String host, String path, String messageId, long offset,
			String serviceId, boolean isMicroservice) throws MicroserviceException{
		String info="conexion al servicio host:"+host+" path:"+path+" serviceId:"+serviceId;
		logger.debug(info);		
		Builder restClient = init(host, path, serviceId, messageId, offset, isMicroservice);
		return restClient.post(Entity.entity(request, Constantes.MEDIA_TYPE_JSON_UTF_8));
	}

	public void setClientBus(Client clientBus) {
		this.clientBus = clientBus;
	}

	public void setClientMs(Client clientMs) {
		this.clientMs = clientMs;
	}

}
