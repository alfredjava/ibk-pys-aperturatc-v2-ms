package pe.com.interbank.pys.aperturatcv2.microservices.integration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.actualizarcampania.microservices.client.ActualizarCampaniaRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.ActualizarCampania;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.ListaActualizarCampaniaType;
import pe.com.interbank.pys.aperturatcv2.microservices.jolt.JoltRequestConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.service.AsincronoServiceImpl;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ApplicationContextProvider;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversorAsincrono;
import pe.com.interbank.pys.grabarldpd.client.domain.GrabarLDPDRequest;
import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class AperturaAsincronoEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(AperturaAsincronoEndpoint.class);

	@Autowired
	private AsincronoServiceImpl asincronoServiceImpl;

	@Autowired
	private JoltRequestConversor joltRequestConversor;

	private ObjectMapper mapper = new ObjectMapper();

	private String messageId;

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void setAsincronoServiceImpl(AsincronoServiceImpl asincronoServiceImpl) {
		this.asincronoServiceImpl = asincronoServiceImpl;
	}

	@ServiceActivator(inputChannel = "asincronoChannel", poller = {})
	public void prepareKafka(Message<Map<String, Object>> msg) throws MicroserviceException {
		AperturaTcRequest requestOriginal = (AperturaTcRequest) msg.getPayload()
				.get(ConfigConstantes.MESSAGE_TYPE_REQUEST);
		messageId = requestOriginal.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		MessagingTemplate template = new MessagingTemplate();
		String strOrqList = PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID_LIST);
		HashMap<String, Object> parametros = (HashMap<String, Object>) msg.getPayload();
		try (Scanner scanner = new Scanner(strOrqList)) {
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				String serviceId = scanner.next();
				Map<String, Object> aperturaProcess = new HashMap<>();
				aperturaProcess.putAll(parametros);
				aperturaProcess.put(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID),
						serviceId);
				send(template, aperturaProcess);
			}
			scanner.close();
		} catch (Exception e) {
			logger.error("Error general" + Constantes.MESSAGE_ID_LOG + messageId + e.getMessage(), e);
			throw new MicroserviceException("Error general" + Constantes.MESSAGE_ID_LOG + messageId + e.getMessage(),
					e);
		}
	}

	@ServiceActivator(inputChannel = "prepareRequestChannel")
	public void sendKafka(Message<Map<String, Object>> msg) throws MicroserviceException {
		AperturaTcRequest requestOriginal = (AperturaTcRequest) msg.getPayload()
				.get(ConfigConstantes.MESSAGE_TYPE_REQUEST);
		messageId = requestOriginal.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		String altaTarjetaRequestJson = (String) msg.getPayload()
				.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA));
		String altaTarjetaResponseJson = (String) msg.getPayload()
				.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TARJETA_RESPONSE));
		String crearClienteResponse = (msg.getPayload()
				.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE)) != null)
						? ((String) msg.getPayload().get(
								PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_RESPONSE)))
						: null;
		String serviceId = (String) msg.getPayload()
				.get(PropertiesCache.getInstance().getProperty(ConfigConstantes.ORQ_SERVICE_ID));
		try {
			String codigoCorrespondenciaAlta = AperturaTcUtils.obtenerCodigoCorrespondenciaAlta(altaTarjetaRequestJson);
			String requestAsincrono = "";
			if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_GRABAR_LDPD).equals(serviceId)) {
				GrabarLDPDRequest grabarLDPDRequest = RequestConversorAsincrono
						.obtenerGrabarLDPDRequest(requestOriginal, crearClienteResponse);
				requestAsincrono = obtenerRequestAsincrono(grabarLDPDRequest, serviceId);
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_ACTUALIZAR_CAMPANIA)
					.equals(serviceId)) {
				procesarActualizarCampania(requestOriginal, serviceId, crearClienteResponse);
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD)
					.equals(serviceId)) {
				String registrarBdRequest = joltRequestConversor.obtenerRegistrarBdRequest(requestOriginal,
						altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta);
				requestAsincrono = registrarBdRequest;
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ)
					.equals(serviceId)) {
				requestAsincrono = joltRequestConversor.obtenerRegistrarAdqRequest(requestOriginal,
						altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta);
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_AFILIAR_SEGURO_JLT)
					.equals(serviceId)) {
				String afiliarJltRequest = joltRequestConversor.obtenerAfiliarSeguroJLTRequest(requestOriginal,
						altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta);
				requestAsincrono = afiliarJltRequest;
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_NOTIFICACION)
					.equals(serviceId)) {
				procesarNotificaciones(requestOriginal, serviceId, altaTarjetaResponseJson, crearClienteResponse,
						codigoCorrespondenciaAlta);
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_REGISTRAR_MONITOR)
					.equals(serviceId)) {
				procesarRegistroMonitor(requestOriginal, serviceId, altaTarjetaResponseJson, crearClienteResponse,
						codigoCorrespondenciaAlta);
			} else if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_FECHA_HORA_ENTREGA)
					.equals(serviceId)) {
				String fechaHoraEntregaRequest = joltRequestConversor.obtenerFechaHoraEntregaRequest(requestOriginal,
						altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta);
				requestAsincrono = fechaHoraEntregaRequest;
			}
			if (!requestAsincrono.isEmpty()) {
				MessagingTemplate template = new MessagingTemplate();
				Map<String, String> datosRequest = new HashMap<>();
				datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincrono);
				datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
				sendKafka(template, datosRequest);
			}

		} catch (IOException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage()
					+ " - al ejecutar extraccion de codigo de correspondencia: " + serviceId + Constantes.MESSAGE_ID_LOG
					+ messageId;
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}

	}

	protected void procesarNotificaciones(AperturaTcRequest aperturaTcRequest, String serviceId,
			String altaTarjetaResponseJson, String crearClienteResponse, String codigoCorrespondenciaAlta)
			throws MicroserviceException {
		String requestAsincronoPrincipal;
		String requestAsincronoSeguro;
		AperturarTCType aperturarTCType = aperturaTcRequest.getMessageRequest().getBody().getAperturarTC();
		requestAsincronoPrincipal = joltRequestConversor.obtenerNotificacionRequest(aperturaTcRequest,
				altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta,
				Constantes.NOTIFICACION_TIPO_PRINCIPAL);
		String info="";
		if (requestAsincronoPrincipal != null && !requestAsincronoPrincipal.isEmpty()) {
			info="Se enviara mensaje de Notificacion Principal" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			MessagingTemplate template = new MessagingTemplate();
			Map<String, String> datosRequest = new HashMap<>();
			datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincronoPrincipal);
			datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
			sendKafka(template, datosRequest);
		}
		requestAsincronoSeguro = joltRequestConversor.obtenerNotificacionRequest(aperturaTcRequest,
				altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta,
				Constantes.NOTIFICACION_TIPO_SEGURO_CONSTANCIA);
		if (requestAsincronoSeguro != null && !requestAsincronoSeguro.isEmpty()) {
			info="Se enviara mensaje de  Notificacion Seguro/Constancia" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			MessagingTemplate template = new MessagingTemplate();
			Map<String, String> datosRequest = new HashMap<>();
			datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincronoSeguro);
			datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
			sendKafka(template, datosRequest);
		}

		if (aperturarTCType.getOperacionMedioContacto() != null
				&& aperturarTCType.getOperacionMedioContacto().getActualizarMedioContactoGeneral() != null) {
			String requestAsincronoEmail = joltRequestConversor.obtenerNotificacionRequest(aperturaTcRequest,
					altaTarjetaResponseJson, crearClienteResponse, codigoCorrespondenciaAlta,
					Constantes.NOTIFICACION_TIPO_EMAIL);
			if (requestAsincronoEmail != null && !requestAsincronoEmail.isEmpty()) {
				info="Se enviara mensaje de  Notificacion Email" + Constantes.MESSAGE_ID_LOG + messageId;
				logger.info(info);
				MessagingTemplate template = new MessagingTemplate();
				Map<String, String> datosRequest = new HashMap<>();
				datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincronoEmail);
				datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
				sendKafka(template, datosRequest);
			}
		}

	}

	protected void procesarRegistroMonitor(AperturaTcRequest aperturaTcRequest, String serviceId,
			String altaTarjetaResponseJson, String crearClienteResponse, String codigoCorrespondenciaAlta)
			throws MicroserviceException {
		String requestAsincrono;
		String requestAsincronoCore;
		requestAsincrono = joltRequestConversor.obtenerMonitorRequest(aperturaTcRequest, altaTarjetaResponseJson,
				crearClienteResponse, codigoCorrespondenciaAlta, Constantes.MONITOR_TIPO_ACF);
		String info="";
		if (requestAsincrono != null && !requestAsincrono.isEmpty()) {
			info="Existe request monitorACF" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			MessagingTemplate template = new MessagingTemplate();
			Map<String, String> datosRequest = new HashMap<>();
			datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincrono);
			datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
			sendKafka(template, datosRequest);
		}
		requestAsincronoCore = joltRequestConversor.obtenerMonitorRequest(aperturaTcRequest, altaTarjetaResponseJson,
				crearClienteResponse, codigoCorrespondenciaAlta, Constantes.MONITOR_TIPO_CORE);
		if (requestAsincronoCore != null && !requestAsincronoCore.isEmpty()) {
			info="Existe request monitorCore" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			MessagingTemplate template = new MessagingTemplate();
			Map<String, String> datosRequest = new HashMap<>();
			datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincronoCore);
			datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
			sendKafka(template, datosRequest);
		}

	}

	public void procesarActualizarCampania(AperturaTcRequest aperturaTcRequest, String serviceId,
			String crearClienteResponse) throws MicroserviceException {
		String requestAsincrono;
		messageId = aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
		AperturarTCType aperturarTCType = aperturaTcRequest.getMessageRequest().getBody().getAperturarTC();
		ListaActualizarCampaniaType listaActualizarCampaniaType = aperturarTCType.getListaActualizarCampania();
		if (listaActualizarCampaniaType != null && !listaActualizarCampaniaType.getActualizaCampania().isEmpty()) {
			Iterator<ActualizarCampania> iterator = listaActualizarCampaniaType.getActualizaCampania().iterator();
			while (iterator.hasNext()) {
				ActualizarCampania actualizaCampaniaType = iterator.next();
				ActualizarCampaniaRequest actualizarCampaniaRequest = RequestConversorAsincrono
						.obtenerActualizarCampaniaRequest(aperturaTcRequest, actualizaCampaniaType,
								crearClienteResponse);
				requestAsincrono = obtenerRequestAsincrono(actualizarCampaniaRequest, serviceId);
				if (requestAsincrono != null && !requestAsincrono.isEmpty()) {
					MessagingTemplate template = new MessagingTemplate();
					Map<String, String> datosRequest = new HashMap<>();
					datosRequest.put(Constantes.MSG_TYPE_REQUEST, requestAsincrono);
					datosRequest.put(Constantes.PATH_MSG_SERVICE_ID, serviceId);
					sendKafka(template, datosRequest);
				}
			}
		}
	}

	public String obtenerRequestAsincrono(Object request, String serviceId) throws MicroserviceException {
		String className = this.getClass().getName();
		String requestAsincrono = "";
		try {
			if (request != null) {
				requestAsincrono = mapper.writeValueAsString(request);
			}
		} catch (JsonProcessingException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ e.getClass().getName() + " en " + className + ":" + e.getStackTrace()[0].getMethodName()
					+ " - obteniendo request de: " + serviceId + " error:" + e.getMessage() + Constantes.MESSAGE_ID_LOG
					+ messageId;
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
		return requestAsincrono;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void send(MessagingTemplate template, Map<String, Object> aperturaProcess) {
		template.send((MessageChannel) ApplicationContextProvider.getChannel("prepareRequestChannel"),
				new GenericMessage(aperturaProcess));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sendKafka(MessagingTemplate template, Map<String, String> datosAsincrono) {
		template.send((MessageChannel) ApplicationContextProvider.getChannel("kafkaChannel"),
				new GenericMessage(datosAsincrono));
	}

	@ServiceActivator(inputChannel = "kafkaChannel")
	public void sendAsincrono(Message<Map<String, String>> msg) throws MicroserviceException {
		String requestAsincrono = msg.getPayload().get(Constantes.MSG_TYPE_REQUEST);
		String serviceId = msg.getPayload().get(Constantes.PATH_MSG_SERVICE_ID);
		String className = this.getClass().getName();
		try {
			if (requestAsincrono != null && !requestAsincrono.isEmpty()) {
				asincronoServiceImpl.escribirTopicoAsincronoTemplate(serviceId, requestAsincrono,
						PropertiesCache.getInstance().getProperty(ConfigConstantes.TOPICO_ASINCRONO));
			}
		} catch (LegacyException e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ e.getClass().getName() + " en " + className + ":" + e.getStackTrace()[0].getMethodName()
					+ " - invocando a: " + serviceId + Constantes.MESSAGE_ID_LOG + messageId + " error:"
					+ e.getMessage();
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}

	}

	public void setJoltRequestConversor(JoltRequestConversor joltRequestConversor) {
		this.joltRequestConversor = joltRequestConversor;
	}

}
