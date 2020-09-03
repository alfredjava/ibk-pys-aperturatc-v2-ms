package pe.com.interbank.pys.aperturatcv2.microservices.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCResponseType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.BodyResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderResponseType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.MessageResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Request;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Status;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ApplicationContextProvider;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Path(ConfigConstantes.PATH_REST_GENERAL)
@Component
public class AperturaTcController {

	private static final Logger logger = LoggerFactory.getLogger(AperturaTcController.class);
	private static final String ERROR_LOG = "Error en el controller: ";
	MessagingTemplate template = new MessagingTemplate();

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(ConfigConstantes.PATH_READ)
	public Response read() {
		return Response.ok().build();
	}

	@SuppressWarnings({ "unchecked" })
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(ConfigConstantes.PATH_REST_APERTURA_TC)
	public Response aperturaTarjetaPost(AperturaTcRequest request) {
		String messageId = request.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID);
		AperturaTcResponse response = null;
		String info = "";
		try {
			info = "REST - " + serviceId + " recibio peticion: " + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			Message<?> reply = getSendAndReceive(template, request);
			if (!(reply.getPayload() instanceof AperturaTcResponse)) {
				response = errorHandler((Message<MessagingException>) reply, request, messageId);
			} else {
				Message<AperturaTcResponse> respuesta = (Message<AperturaTcResponse>) reply;
				response = respuesta.getPayload();
				Map<String, Object> map = new HashMap<>();
				map.put(Constantes.MESSAGE_ID_MAP, messageId);
				map.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, request);
				map.put(ConfigConstantes.MESSAGE_TYPE_RESPONSE, response);
				map.putAll(reply.getHeaders());
				// envio a distribucion asincrona
				send(template, map);
			}
			info = "Response hacia Cliente: " + Constantes.MESSAGE_ID_LOG + messageId
					+ response.getMessageResponse().getHeader().getHeaderResponse().getResponse().getMessageId();
			logger.info(info);
			return Response.ok(response).build();
		} catch (MessageHandlingException mhe) {
			String mensaje = ERROR_LOG + mhe.getCause().getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, mhe);
			Message<MessagingException> reply = new GenericMessage<>(mhe);
			response = errorHandler((Message<MessagingException>) reply, request, messageId);
			return Response.ok(response).build();
		} catch (Exception e) {
			String mensaje = ERROR_LOG + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
		}
		logger.debug("Fin de aperturaTarjeta");
		return Response.serverError().build();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void send(MessagingTemplate template, Map<String, Object> aperturaProcess) {
		logger.debug("Enviando mensaje template de aperturaProcess");
		template.send((QueueChannel) ApplicationContextProvider.getChannel("asincronoChannel"),
				new GenericMessage(aperturaProcess));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Message<?> getSendAndReceive(MessagingTemplate template, AperturaTcRequest request) {// NOSONAR
		logger.debug("Reciviendo mensaje template de AperturaTcRequest");
		return template.sendAndReceive(ApplicationContextProvider.getChannel("validarAltaTc"),
				new GenericMessage(request));
	}

	protected AperturaTcResponse errorHandler(Message<MessagingException> reply, AperturaTcRequest request,
			String messageId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		String info = "ErrorHandler timestamp:" + timestamp + Constantes.MESSAGE_ID_LOG + messageId;
		logger.info(info);
		Request headerRequest = request.getMessageRequest().getHeader().getHeaderRequest().getRequest();
		pe.com.interbank.pys.aperturatcv2.microservices.domain.Response response = RequestConversor
				.convertirAperturaTcRequestHeaderToResponseHeader(headerRequest);

		if (reply.getPayload().getRootCause() instanceof MicroserviceException) {
			info = "errorHandler error del tipo MicroserviceException: " + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			String cadenaJson = ((MicroserviceException) reply.getPayload().getRootCause()).getJsonCause();
			if (cadenaJson != null) {
				AperturarTCResponseType errorResponse = null;
				BodyResponse body = new BodyResponse(errorResponse);
				try {
					JsonNode root = new ObjectMapper().readTree(cadenaJson);
					info = "errorHandler estableciendo Status:" + Constantes.MESSAGE_ID_LOG + messageId;
					logger.info(info);
					Status status = new Status(AperturaTcUtils.obtenerValorCadenaSeguro(root, "responseType"),
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "busResponseCode"),
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "busResponseMessage"),
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "srvResponseCode"),
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "srvResponseMessage"), "", "",
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "messageIdResBus"), "2",
							PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_NESTED_ERROR)
									+ " - " + reply.getPayload().getRootCause().getMessage(),
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "msResponseCode"),
							AperturaTcUtils.obtenerValorCadenaSeguro(root, "msResponseMessage"));
					response.setTimestamp(timestamp);
					HeaderResponse headerResponse = new HeaderResponse(response, status);
					HeaderResponseType header = new HeaderResponseType(headerResponse);
					MessageResponse messageResponse = new MessageResponse(header, body);
					return new AperturaTcResponse(messageResponse);
				} catch (IOException e) {
					logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
							+ e.getClass().getName() + " en " + this.getClass().getName() + ":"
							+ e.getStackTrace()[0].getMethodName() + Constantes.MESSAGE_ID_LOG + messageId + " - "
							+ e.getMessage(), e);
				}
			}
		}
		// Respuesta por defecto casos de errores no manejados como Nullpointer
		// exception
		AperturarTCResponseType aperturarTcResponse = null;
		String errorPrefijo = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR);
		if (reply.getPayload().getRootCause() instanceof MicroserviceException) {
			errorPrefijo = "";
		}
		BodyResponse body = new BodyResponse(aperturarTcResponse);
		Status status = new Status("", "", "", "", "", "", "", "", "1",
				errorPrefijo + reply.getPayload().getCause().getMessage(), "", "");
		response.setTimestamp(timestamp);
		HeaderResponse headerResponse = new HeaderResponse(response, status);
		HeaderResponseType header = new HeaderResponseType(headerResponse);
		MessageResponse messageResponse = new MessageResponse(header, body);
		AperturaTcResponse aperturaTcResponse = new AperturaTcResponse(messageResponse);
		info = "Retorna AperturaTcResponse" + Constantes.MESSAGE_ID_LOG + messageId;
		logger.info(info);
		return aperturaTcResponse;
	}

	public void setTemplate(MessagingTemplate template) {
		this.template = template;
	}

}
