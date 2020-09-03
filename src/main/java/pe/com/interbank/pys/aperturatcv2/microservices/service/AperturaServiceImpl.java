package pe.com.interbank.pys.aperturatcv2.microservices.service;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ApplicationContextProvider;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class AperturaServiceImpl implements AperturaService {

	private static final Logger logger = LoggerFactory.getLogger(AperturaServiceImpl.class);
	private static final String ERROR_RESPUESTA = "Error en la respuesta de ";

	@Autowired
	private SecurityRestClient securityRestClient;

	@Override
	public void validarRespuesta(Map<String, String> map) throws MicroserviceException {
		logger.debug("Inicio de validarRespuesta");
		String info="";
		TreeMap<String, String> myMap = new TreeMap<>();
		myMap.putAll(map);
		String respuesta = myMap.get(myMap.firstKey());
		String servicio = myMap.firstKey();
		String messageId = JsonUtil.getCampoTrama("messageId", respuesta);
		if (!validarCodigoRespuesta(respuesta, servicio)) {
			String request = map.get(ConfigConstantes.MESSAGE_TYPE_REQUEST);
			if (request != null) {
				enviarTopicoKafka(request);
			}
			info="Codigo de respuesta no valido |respuesta:" + respuesta + Constantes.SERVICE_ID_LOG + servicio
					+ Constantes.MESSAGE_ID_LOG + messageId;
			logger.debug(info);
			MicroserviceException microserviceException = new MicroserviceException(ERROR_RESPUESTA + servicio);
			microserviceException.setJsonCause(respuesta);
			throw microserviceException;
		}
		info="Fin de validarRespuesta:" + Constantes.MESSAGE_ID_LOG + messageId;
		logger.debug(info);
	}

	private boolean validarCodigoRespuesta(String respuesta, String servicio) throws MicroserviceException {
		String messageId = JsonUtil.getCampoTrama(Constantes.MESSAGE_ID_MAP, respuesta);
		boolean valido = false;
		try {
			JsonNode root = new ObjectMapper().readTree(respuesta);
			JsonNode responseType = root
					.findValue(PropertiesCache.getInstance().getProperty(ConfigConstantes.RESPONSE_CODE_MS));
			String info="";
			if (responseType != null) {
				String codigoRespuesta = responseType.textValue();
				info="Servicio " + servicio + " codigo respuesta: " + codigoRespuesta + Constantes.MESSAGE_ID_LOG
						+ messageId;
				logger.info(info);
				return PropertiesCache.getInstance().getProperty(ConfigConstantes.OK_CODE_MS).equals(codigoRespuesta);
			} else {
				info="Error en el mensaje, no se encontro la propiedad: "
						+ PropertiesCache.getInstance().getProperty(ConfigConstantes.RESPONSE_CODE_MS)
						+ Constantes.MESSAGE_ID_LOG + messageId;
				logger.info(info);
			}
		} catch (IOException e) {
			MicroserviceException microserviceException = new MicroserviceException(
					"Error en estructura Json de respuesta del ms: " + servicio + Constantes.MESSAGE_ID_LOG + messageId
							+ " Error: " + e.getMessage(),
					e);
			microserviceException.setJsonCause(respuesta);
			throw microserviceException;
		}
		return valido;
	}

	@Override
	public String validarAltaTc(String request, String messageId) throws MicroserviceException, LegacyException {
		return invocarServicioRestTemplate(PropertiesCache.getInstance().getProperty(ConfigConstantes.LISTA_ADICIONALES), PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICIO_FRAMEWORK4_BUS_HOST),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.LISTAADICIONALES_PATH), request, false, messageId);
	}

	
	@Override
	public String altaTarjeta(String request, String messageId) throws LegacyException, MicroserviceException {
		return invocarServicioRestTemplate(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICIO_BUS_HOST),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_PATH), request, false,
				messageId);
	}

	@Override
	public String crearMedioContactoGeneral(String request, String messageId)
			throws LegacyException, MicroserviceException {
		return invocarServicioRestTemplate(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL_HOST),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL_PATH), request,
				true, messageId);
	}

	@Override
	public String actualizarMedioContactoGeneral(String request, String messageId)
			throws LegacyException, MicroserviceException {
		return invocarServicioRestTemplate(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL_HOST),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL_PATH),
				request, true, messageId);
	}

	@Override
	public String crearMedioContactoEspecializado(String request, String messageId)
			throws LegacyException, MicroserviceException {
		return invocarServicioRestTemplate(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO_HOST),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO_PATH),
				request, true, messageId);
	}

	@Override
	public String crearCliente(String request, String messageId) throws LegacyException, MicroserviceException {
		return invocarServicioRestTemplate(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_HOST),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_PATH), request, true,
				messageId);
	}

	private String invocarServicioRestTemplate(String serviceId, String host, String path, String request,
			boolean isMicroservice, String messageId) throws LegacyException, MicroserviceException {
		String info="Inicio de invocarServicioRestTemplate para:" + Constantes.SERVICE_ID_LOG + serviceId
				+ Constantes.MESSAGE_ID_LOG + messageId;
		logger.debug(info);
		String className = this.getClass().getName();
		try {
			Response response = securityRestClient.invokeExternalService(request, host, path, messageId, -10L,
					serviceId, isMicroservice);
			String responseString = response.readEntity(String.class);
			int responseStatus = response.getStatus();
			if (responseStatus != 200) {
				response.close();
				throw new LegacyException("Fallo al invocar al Servicio Bus " + serviceId + " : HTTP error code : "
						+ responseStatus + Constantes.MESSAGE_ID_LOG + messageId);
			}
			response.close();
			info="Respuesta de MS : " + serviceId + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			return responseString;
		} catch (RestClientException e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + className + ":" + e.getStackTrace()[0].getMethodName() + " - " + e.getMessage()
					+ Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException("Error invocando al Microservicio via rest: " + serviceId, e);
		} catch (ProcessingException e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + className + ":" + e.getStackTrace()[0].getMethodName() + " - " + e.getMessage()
					+ Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new LegacyException("Error al invocar el Servicio Bus " + serviceId, e);
		} catch (Exception e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + className + ":" + e.getStackTrace()[0].getMethodName() + " - " + e.getMessage()
					+ Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException("Error invocando al Microservicio: " + serviceId, e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void enviarTopicoKafka(String request){
		MessagingTemplate template = new MessagingTemplate();
		template.send(ApplicationContextProvider.getChannel("recoverChannel"), new GenericMessage(request));
	}

	public void setSecurityRestClient(SecurityRestClient securityRestClient) {
		this.securityRestClient = securityRestClient;
	}
	
}
