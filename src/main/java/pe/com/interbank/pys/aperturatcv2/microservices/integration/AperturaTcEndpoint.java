package pe.com.interbank.pys.aperturatcv2.microservices.integration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.ActualizarMedioContactoGeneralRequest;
import pe.com.interbank.pys.altatarjeta.client.domain.AltaTarjetaRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AltaTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCResponseType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.BodyResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Cliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DatosCliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderResponseType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.MessageResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.OperacionMedioContacto;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Response;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Status;
import pe.com.interbank.pys.aperturatcv2.microservices.jolt.JoltRequestConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.service.AperturaService;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.crearcliente.client.domain.CrearClienteRequest;
import pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.CrearMedioContactoEspecializadoRequest;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.CrearMedioContactoGeneralRequest;
import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class AperturaTcEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(AperturaTcEndpoint.class);
	@Autowired
	private AperturaService aperturaService;

	@Autowired
	private JoltRequestConversor joltRequestConversor;

	private ObjectMapper mapper = new ObjectMapper();

	private String messageId;

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void setAperturaService(AperturaService aperturaService) {
		this.aperturaService = aperturaService;
	}

	@ServiceActivator(inputChannel = "validarAltaTc", outputChannel = "requestChannel")
	public Message<AperturaTcRequest> validarAltaTc(Message<AperturaTcRequest> msg) throws MicroserviceException {
		AperturaTcRequest requestOriginal = msg.getPayload();
		messageId = requestOriginal.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		logger.info("Transaccion de validar alta tc messageId", messageId);
		try {
			String strRequestOriginal = mapper.writeValueAsString(requestOriginal);
			String requestConsultaAdicionales = joltRequestConversor.obtenerConsultaAdicionalesRequest(strRequestOriginal);
			String response = aperturaService.validarAltaTc(requestConsultaAdicionales, messageId);
			Map<String, String> actual = new HashMap<>();
			actual.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.LISTA_ADICIONALES), response);
			aperturaService.validarRespuesta(actual);
			AperturaTcUtils.validateFechaAlta(response, messageId);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		return new GenericMessage<>(requestOriginal, msg.getHeaders());
	}

	@ServiceActivator(inputChannel = "requestChannel", outputChannel = "splitterChannel")
	public Message<Map<String, Object>> inicioCrearCliente(Message<AperturaTcRequest> msg) throws MicroserviceException {
		AperturaTcRequest requestOriginal = msg.getPayload();
		messageId = requestOriginal.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		Map<String, Object> resultado = new HashMap<>();
		AperturarTCType aperturaRequest = requestOriginal.getMessageRequest().getBody().getAperturarTC();
		DatosCliente datosCliente = aperturaRequest.getDatosCliente();
		HeaderRequest headerRequest = requestOriginal.getMessageRequest().getHeader();
		if (datosCliente != null) {
			Cliente cliente = datosCliente.getCliente();
			if (cliente.getIdCliente() == null || cliente.getIdCliente().isEmpty()) {
				// si no existe codigo unico es un nuevo cliente
				/**
				 * Anular Crea y Actualizar Medio contacto general si es cliente nuevo
				 * 07/09/2017 pedido Jaz, Edu
				 * ***************************************************************
				 */
				if (requestOriginal.getMessageRequest().getBody().getAperturarTC().getOperacionMedioContacto() != null) {
					requestOriginal.getMessageRequest().getBody().getAperturarTC().getOperacionMedioContacto().setActualizarMedioContactoGeneral(null);
					requestOriginal.getMessageRequest().getBody().getAperturarTC().getOperacionMedioContacto().setCrearMedioContactoGeneral(null);
				}
				/**
				 * ****************************************************************
				 */
				try {
					HashMap<String, String> mapCrearCliente = obtenerRequestCrearCliente(cliente, headerRequest);
					String crearClienteRequest = mapCrearCliente.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE));
					String response = aperturaService.crearCliente(crearClienteRequest, messageId);
					Map<String, String> actual = new HashMap<>();
					actual.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE), response);
					aperturaService.validarRespuesta(actual);
					resultado.putAll(actual);
				} catch (Exception e) {
					String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
					logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
					throw new MicroserviceException(mensaje, e);
				}
			}
		}
		resultado.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, requestOriginal);
		return new GenericMessage<>(resultado, msg.getHeaders());
	}

	@Splitter(inputChannel = "splitterChannel", outputChannel = "routerChannel")
	public List<HashMap<String, String>> splitRequest(Message<Map<String, Object>> msg) throws MicroserviceException {
		AperturaTcRequest requestOriginal = (AperturaTcRequest) msg.getPayload().get(ConfigConstantes.MESSAGE_TYPE_REQUEST);
		String responseCrearCliente = null;
		Object crearClienteObject = msg.getPayload().get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE));
		if (crearClienteObject != null) {
			responseCrearCliente = (String) crearClienteObject;
		}
		messageId = requestOriginal.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		String className = this.getClass().getName();
		List<HashMap<String, String>> listaMessages = new ArrayList<>();
		AperturarTCType aperturaRequest = requestOriginal.getMessageRequest().getBody().getAperturarTC();
		OperacionMedioContacto operacionMedioContacto = aperturaRequest.getOperacionMedioContacto();
		HeaderRequest headerRequest = requestOriginal.getMessageRequest().getHeader();
		DatosCliente datosCliente = aperturaRequest.getDatosCliente();
		Cliente cliente = null;
		AltaTCType altaTCType = requestOriginal.getMessageRequest().getBody().getAperturarTC().getAltaTC();
		if (datosCliente != null) {
			cliente = datosCliente.getCliente();
		}
		if (altaTCType != null && cliente != null) {
			try {
				if (operacionMedioContacto != null) {
					listaMessages.addAll(obtenerRequestsMedioContacto(operacionMedioContacto, headerRequest, cliente.getIdCliente(), responseCrearCliente));
				}

				if (responseCrearCliente != null) {
					HashMap<String, String> crearClienteResponseMap = new HashMap<>();
					crearClienteResponseMap.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE), responseCrearCliente);
					listaMessages.add(crearClienteResponseMap);
				}
				AltaTarjetaRequest altaTarjetaRequest = RequestConversor.obtenerAltaTarjetaRequest(requestOriginal.getMessageRequest(), headerRequest);
				String msgAltaTarjeta = mapper.writeValueAsString(altaTarjetaRequest);
				HashMap<String, String> mapAltaTarjeta = new HashMap<>();
				mapAltaTarjeta.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA), msgAltaTarjeta);
				listaMessages.add(mapAltaTarjeta);

			} catch (IOException e) {
				String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
				logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
				throw new MicroserviceException(mensaje, e);
			}
		} else {
			if (altaTCType == null) {
				throw new MicroserviceException(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + ConfigConstantes.SERVICE_ID + " - " + className + ": splitRequest - " + "No existe tarjeta a procesar" + Constantes.MESSAGE_ID_LOG + messageId);
			} else {
				throw new MicroserviceException(PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + ConfigConstantes.SERVICE_ID + " - " + className + ": splitRequest - " + "No existe cliente a procesar");
			}
		}

		return listaMessages;
	}

	@Router(inputChannel = "routerChannel")
	public String routeMessage(Message<HashMap<String, String>> message) {
		HashMap<String, String> msgToRoute = message.getPayload();
		String channel = "";
		if (msgToRoute.containsKey(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL))) {
			channel = "crearMedioContactoGeneralChannel";
		}
		if (msgToRoute.containsKey(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL))) {
			channel = "actualizarMedioContactoGeneralChannel";
		}
		if (msgToRoute.containsKey(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO))) {
			channel = "crearMedioContactoEspecificoChannel";
		}

		if (msgToRoute.containsKey(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE))) {
			channel = "aggregateChannel";
		}

		if (msgToRoute.containsKey(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA))) {
			channel = "aggregateChannel";
		}
		return channel;
	}

	@ServiceActivator(inputChannel = "crearMedioContactoGeneralChannel", outputChannel = "aggregateChannel")
	public Map<String, String> wsCrearMedioContactoGeneralGateway(Message<HashMap<String, String>> message) throws MicroserviceException {
		HashMap<String, String> msgToRoute = message.getPayload();
		String medioContactoGeneralRequest = msgToRoute.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL));
		String response = "";
		try {
			response = aperturaService.crearMedioContactoGeneral(medioContactoGeneralRequest, messageId);
		} catch (LegacyException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL), response);
		return map;
	}

	@ServiceActivator(inputChannel = "actualizarMedioContactoGeneralChannel", outputChannel = "aggregateChannel")
	public Map<String, String> wsActualizarMedioContactoGeneralGateway(Message<HashMap<String, String>> message) throws MicroserviceException {
		HashMap<String, String> msgToRoute = message.getPayload();
		String medioContactoGeneralRequest = msgToRoute.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL));
		String response = "";
		try {
			response = aperturaService.actualizarMedioContactoGeneral(medioContactoGeneralRequest, messageId);
		} catch (LegacyException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL), response);
		return map;
	}

	@ServiceActivator(inputChannel = "crearMedioContactoEspecificoChannel", outputChannel = "aggregateChannel")
	public Map<String, String> wsCrearMedioContactoEspecificoGateway(Message<HashMap<String, String>> message) throws MicroserviceException {
		String className = this.getClass().getName();
		HashMap<String, String> msgToRoute = message.getPayload();
		String medioContactoEspecificoRequest = msgToRoute.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO));
		String response = "";
		try {
			response = aperturaService.crearMedioContactoEspecializado(medioContactoEspecificoRequest, messageId);
		} catch (LegacyException e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName() + " en " + className + ":" + e.getStackTrace()[0].getMethodName() + Constantes.MESSAGE_ID_LOG + messageId + " - " + e);
			throw new MicroserviceException(PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + ConfigConstantes.SERVICE_ID + " - " + className + ":" + e.getStackTrace()[0].getMethodName() + " - " + e.getMessage(), e);
		}
		HashMap<String, String> map = new HashMap<>();
		map.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO), response);
		return map;
	}

	@Aggregator(inputChannel = "aggregateChannel", outputChannel = "altaTarjetaChannel")
	public Message<String> buildResponse(List<HashMap<String, String>> responses) throws MicroserviceException {//NOSONAR
		Iterator<HashMap<String, String>> iterator = responses.iterator();
		HashMap<String, String> actual;
		HashMap<String, String> altaTarjeta = null;
		HashMap<String, String> crearDireccion = null;
		HashMap<String, String> crearCliente = null;
		StringBuilder codigoCorrespondenciaMedioContactoGeneral = new StringBuilder();
		codigoCorrespondenciaMedioContactoGeneral.append("");
		while (iterator.hasNext()) {
			actual = iterator.next();
			if (actual.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA)) != null) {
				// si es el request de alta tarjeta se separa el elemento para
				// su envio posterior al microservicio
				altaTarjeta = actual;
			} else {
				aperturaService.validarRespuesta(actual);
				if (actual.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO)) != null) {
					crearDireccion = actual;
				} else if (actual.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE)) != null) {
					crearCliente = actual;
				} else {// CREAR O ACTUALIZAR MEDIO CONTACTO GENERAL ENTRA AQUI
						// SOLO 1 VEZ nunca vendra crear y actualizar junto
					codigoCorrespondenciaMedioContactoGeneral.append(RequestConversor.extraerCodigoCorrespondencia(actual));
				}
			}

		}
		if (altaTarjeta != null) {
			String altaTarjetaRequest = altaTarjeta.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA));

			altaTarjetaRequest = RequestConversor.actualizarRequestAltaTarjeta((crearDireccion != null) ? crearDireccion.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO)) : "", altaTarjetaRequest,
					codigoCorrespondenciaMedioContactoGeneral.toString(), (crearCliente != null) ? crearCliente.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE)) : "");
			Map<String, Object> responseHeaderMap = new HashMap<>();
			responseHeaderMap.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA), altaTarjetaRequest);
			if (crearCliente != null) {
				responseHeaderMap.putAll(crearCliente);
			}
			return new GenericMessage<>(altaTarjetaRequest, responseHeaderMap);
		} else {
			throw new MicroserviceException(PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + "no se recibio request de alta tarjeta");
		}
	}

	@ServiceActivator(inputChannel = "altaTarjetaChannel", outputChannel = "finalChannel")
	public Message<String> wsAltaTarjetaChannelGateway(Message<String> message) throws MicroserviceException {
		try {
			String response = aperturaService.altaTarjeta(message.getPayload(), messageId);
			return new GenericMessage<>(response, message.getHeaders());
		} catch (LegacyException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	@ServiceActivator(inputChannel = "finalChannel")
	public Message<AperturaTcResponse> prepareResponse(Message<String> msg) throws MicroserviceException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		String cadenaJson = msg.getPayload();
		Map<String, String> responseAltaTC = new HashMap<>();
		responseAltaTC.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE), cadenaJson);
		String altaTarjetaRequest = (String) msg.getHeaders().get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA));
		responseAltaTC.put(ConfigConstantes.MESSAGE_TYPE_REQUEST, altaTarjetaRequest);
		aperturaService.validarRespuesta(responseAltaTC);
		try {
			JsonNode root = mapper.readTree(cadenaJson);
			JsonNode bodyJson = JsonUtil.getNodeReferenceFixCapitalized(Constantes.ALTA_TARJETA_RESPONSE_BODY, root);
			String bodyJsonString = mapper.writeValueAsString(bodyJson);
			AperturarTCResponseType regularResponse = mapper.readValue(bodyJsonString, AperturarTCResponseType.class);
			BodyResponse body = new BodyResponse(regularResponse);
			JsonNode responseJson = JsonUtil.getNodeReferenceFixCapitalized("response", root);
			String responseJsonString = mapper.writeValueAsString(responseJson);
			Response response = mapper.readValue(responseJsonString, Response.class);
			Status status = new Status(AperturaTcUtils.obtenerValorCadenaSeguro(root, "responseType"), AperturaTcUtils.obtenerValorCadenaSeguro(root, "busResponseCode"), AperturaTcUtils.obtenerValorCadenaSeguro(root, "busResponseMessage"),
					AperturaTcUtils.obtenerValorCadenaSeguro(root, "srvResponseCode"), AperturaTcUtils.obtenerValorCadenaSeguro(root, "srvResponseMessage"), "", "", AperturaTcUtils.obtenerValorCadenaSeguro(root, "messageIdResBus"),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.OK_CODE_MS), PropertiesCache.getInstance().getProperty(ConfigConstantes.OK_RESPONSE_MS), AperturaTcUtils.obtenerValorCadenaSeguro(root, "msResponseCode"),
					AperturaTcUtils.obtenerValorCadenaSeguro(root, "msResponseMessage"));
			response.setTimestamp(timestamp);
			HeaderResponse headerResponse = new HeaderResponse(response, status);
			HeaderResponseType header = new HeaderResponseType(headerResponse);
			MessageResponse messageResponse = new MessageResponse(header, body);
			AperturaTcResponse aperturaTcResponse = new AperturaTcResponse(messageResponse);
			Map<String, Object> responseHeaderMap = new HashMap<>();
			responseHeaderMap.putAll(msg.getHeaders());
			responseHeaderMap.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE), cadenaJson);
			return new GenericMessage<>(aperturaTcResponse, responseHeaderMap);
		} catch (IOException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	protected List<HashMap<String, String>> obtenerRequestsMedioContacto(OperacionMedioContacto operacionMedioContacto, HeaderRequest headerRequest, String codigoUnico, String crearClienteResponse)
			throws JsonProcessingException, MicroserviceException {
		List<HashMap<String, String>> listaMessages = new ArrayList<>();
		if (operacionMedioContacto.getCrearMedioContactoGeneral() != null) {
			CrearMedioContactoGeneralRequest creaEmail = RequestConversor.obtenerCrearMedioContactoRequest(operacionMedioContacto, headerRequest, codigoUnico);
			String msgMedioContactoGeneral = mapper.writeValueAsString(creaEmail);
			HashMap<String, String> mapMedioContactoGeneral = new HashMap<>();
			mapMedioContactoGeneral.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_GENERAL), msgMedioContactoGeneral);
			listaMessages.add(mapMedioContactoGeneral);
		}

		if (operacionMedioContacto.getActualizarMedioContactoGeneral() != null) {
			ActualizarMedioContactoGeneralRequest actualizaEmail = RequestConversor.obtenerActualizarMedioContactoRequest(operacionMedioContacto, headerRequest, codigoUnico);
			String msgMedioContactoGeneral = mapper.writeValueAsString(actualizaEmail);
			HashMap<String, String> mapMedioContactoGeneral = new HashMap<>();
			mapMedioContactoGeneral.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_MEDIO_CONTACTO_GENERAL), msgMedioContactoGeneral);
			listaMessages.add(mapMedioContactoGeneral);
		}

		if (operacionMedioContacto.getCrearMedioContactoEspecializado() != null) {
			CrearMedioContactoEspecializadoRequest creaDireccion = RequestConversor.obtenerCrearMedioContactoEspecializadoRequest(operacionMedioContacto, headerRequest, codigoUnico, (crearClienteResponse != null) ? crearClienteResponse : "");
			String msgMedioContactoGeneral = mapper.writeValueAsString(creaDireccion);
			HashMap<String, String> mapMedioContactoGeneral = new HashMap<>();
			mapMedioContactoGeneral.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_MEDIO_CONTACTO_ESPECIFICO), msgMedioContactoGeneral);
			listaMessages.add(mapMedioContactoGeneral);
		}
		return listaMessages;
	}

	protected HashMap<String, String> obtenerRequestCrearCliente(Cliente cliente, HeaderRequest headerRequest) throws MicroserviceException, JsonProcessingException {
		HashMap<String, String> request = new HashMap<>();
		CrearClienteRequest clienteRequest = RequestConversor.obtenerCrearClienteRequest(cliente, headerRequest);
		String msgClienteRequest = mapper.writeValueAsString(clienteRequest);
		request.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE), msgClienteRequest);
		return request;
	}

	public void setJoltRequestConversor(JoltRequestConversor joltRequestConversor) {
		this.joltRequestConversor = joltRequestConversor;
	}

}
