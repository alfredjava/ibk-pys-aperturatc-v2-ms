package pe.com.interbank.pys.aperturatcv2.microservices.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class MicroserviceUtils {

	private static final Logger logger = LoggerFactory.getLogger(MicroserviceUtils.class);
	private static final String ERROR_LOG = "Error en el microservicio : ";

	protected MicroserviceUtils() {
		throw new IllegalAccessError("Clase Utils");
	}

	public static String registrarTimeOut(String requestEnv) {
		String messageId = JsonUtil.getCampoTrama(Constantes.PATH_MSG_MESSAGE_ID, requestEnv);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNodeEnv = objectMapper.readTree(requestEnv);
			JsonNode rootNodeError = objectMapper
					.readTree(PropertiesCache.getInstance().getProperty(ConfigConstantes.BODY_ERROR));
			JsonNode requestHeader = JsonUtil.getNodeReference(Constantes.MSG_TYPE_REQUEST, rootNodeEnv);
			JsonNode messageResponse = rootNodeError.path("MessageResponse").path("Header").path("HeaderResponse");
			((ObjectNode) messageResponse).set("response", requestHeader);
			String info="Se contruyo el mensaje Response Error TimeOut" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);

			return rootNodeError.toString();
		} catch (Exception e) {
			logger.error("Error al construir mensaje timeout " + Constantes.MESSAGE_ID_LOG + messageId + " Error: "
					+ e.getMessage(), e);
		}
		return "Error al registrar timeout";
	}

	public static JsonNode obtenerNode(String response, String msCode, String msMessage) {
		String messageId = JsonUtil.getCampoTrama(Constantes.PATH_MSG_MESSAGE_ID, response);
		JsonNode responseNode = null;
		try {
			responseNode = new ObjectMapper().readTree(response);
			JsonNode statusNode = responseNode.path("MessageResponse").path("Header").path("HeaderResponse")
					.path("status");
			if (statusNode != null && !(statusNode instanceof MissingNode)) {
				((ObjectNode) statusNode).put("apiResponseCode", msCode);
				((ObjectNode) statusNode).put("apiResponseMessage", msMessage);
			}
		} catch (IOException e) {
			String mensaje = ERROR_LOG + e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId;
			logger.error(mensaje, e);
		}

		return responseNode;
	}

}
