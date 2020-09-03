package pe.com.interbank.pys.aperturatcv2.microservices.modifiers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AltaTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Cliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DatosCliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DireccionType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequestType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.InformacionEntrega;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.InformacionLaboralType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.MedioContactoEspecializado;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.OperacionMedioContacto;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class RequestModifier {//NOSONAR

	private static final Logger logger = LoggerFactory.getLogger(RequestModifier.class);
	private static final String ERROR_MODIFICAR = "Error al modificar request para el Conversor de ";
	private static final String FECHA_NACIMIENTO_MONITOR = "fechaNacimientoMonitor";
	private static final String FECHA_ALTA = "fechaAlta";
	private static final String FECHA_TRANSMISION = "fechaTransmision";
	private static final String FECHA_TRANSACCION = "fechaTransaccion";
	private static final String HORA_TRANSACCION = "horaTransaccion";
	private static final String FECHA_HORA_TRANSACCION = "fechaHoraTrx";
	private static final String FECHA_ENTREGA = "fechaEntrega";
	private static final String HORA_ENTREGA = "horaEntrega";
	private static final String CODIGO_UNICO_CORE = "codigoUnicoCore";
	private static final int CODIGO_UNICO_CORE_LONGITUD = 12;

	public static String actualizarRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,//NOSONAR
			String crearClienteResponse, String codigoCorrespondencia, String serviceId) throws MicroserviceException {
		String messageId = aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest().getRequest()
				.getMessageId();
		String info="Inicio Request Modifier para " + serviceId + Constantes.MESSAGE_ID_LOG + messageId;
		logger.info(info);
		ObjectMapper mapper = new ObjectMapper();
		String requestModificado;
		String descripcionDireccion = "";
		try {
			String aperturaTcRequestString = actualizarCamposEspecificos(mapper.writeValueAsString(aperturaTcRequest),
					serviceId, messageId);
			AperturaTcRequest aperturaTcRequestUpdated = mapper.readValue(aperturaTcRequestString,
					AperturaTcRequest.class);
			AperturarTCType aperturarTCType = aperturaTcRequestUpdated.getMessageRequest().getBody().getAperturarTC();
			DatosCliente clienteType = aperturarTCType.getDatosCliente();
			clienteType.setCliente(AperturaTcUtils.actualizarDatosClientesNuevos(clienteType.getCliente(),
					 crearClienteResponse));

			AltaTCType altaTCType = aperturarTCType.getAltaTC();
			info="Modifier CodigoCorrespondencia recibido: " + codigoCorrespondencia + " para servicio :"
					+ serviceId + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			if (!codigoCorrespondencia.isEmpty()) {
				Map<String, String> camposDireccion;
				String json = PropertiesCache.getInstance()
						.getProperty(ConfigConstantes.ENVIO_CORREO_DIRECCION_MAPPING);
				camposDireccion = mapper.readValue(json, new TypeReference<Map<String, String>>() {
				});
				descripcionDireccion = AperturaTcUtils.obtenerValorFromJsonMap(camposDireccion, codigoCorrespondencia);
				altaTCType.setCodigoCorrespondencia(codigoCorrespondencia);
			} else {
				descripcionDireccion = PropertiesCache.getInstance()
						.getProperty(ConfigConstantes.ENVIO_CORREO_DIRECCION_FISICO);
			}
			if (serviceId
					.contains(PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ))) {
				altaTCType.setCodigoCorrespondencia(codigoCorrespondencia);
			}
			info="Modifier CodigoCorrespondencia alta final: " + altaTCType.getCodigoCorrespondencia()
			+ " para servicio :" + serviceId + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);

			JsonNode requestConCliente = mapper.valueToTree(aperturaTcRequestUpdated);
			JsonNode requestBodyNode = JsonUtil.getNodeReference(Constantes.PATH_MSG_BODY, requestConCliente);
			String fechaAlta = "";
			if (altaTarjetaResponse != null) {
				String altaTarjetaResponseUpdate = formatearRespuestaAltaTarjeta(altaTarjetaResponse, serviceId,
						messageId);
				JsonNode altaTarjetaResponseNode = mapper.readTree(altaTarjetaResponseUpdate);
				JsonNode crearTarjetaCreditoResponseNode = JsonUtil
						.getNodeReferenceFixCapitalized(Constantes.ALTA_TARJETA_RESPONSE_BODY, altaTarjetaResponseNode);
				((ObjectNode) requestBodyNode).putObject(Constantes.ALTA_TARJETA_RESPONSE_BODY);
				((ObjectNode) requestBodyNode).replace(Constantes.ALTA_TARJETA_RESPONSE_BODY,
						crearTarjetaCreditoResponseNode);
				fechaAlta = JsonUtil.getCampo(FECHA_ALTA, altaTarjetaResponseNode);
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.ENVIO_CORREO_FORMATO_FECHA_LARGA));
			String fechaLargaCorreo = dateFormat
					.format(Calendar.getInstance(TimeZone.getTimeZone("America/Lima")).getTime());
			((ObjectNode) requestBodyNode).put("fechaLargaCorreo", fechaLargaCorreo);

			((ObjectNode) requestBodyNode).put("descripcionDireccion", descripcionDireccion);

			boolean flagNuevoCliente = false;
			if (crearClienteResponse != null) {
				flagNuevoCliente = true;
			}
			((ObjectNode) requestBodyNode).put("flagNuevoCliente", flagNuevoCliente);

			String marca = aperturarTCType.getAltaTC().getCodigoMarca();
			String tipo = aperturarTCType.getAltaTC().getIndicadorTipo();
			String marcaTipo = marca.concat(tipo);
			String constMarcatipo = PropertiesCache.getInstance()
					.getProperty(ConfigConstantes.ENVIO_CORREO_MARCATIPO + marcaTipo);
			((ObjectNode) requestBodyNode).put("nombreProducto", constMarcatipo);

			String lineaEmail = darFormatoNumerico(aperturarTCType.getAltaTC().getImporteLinea());
			((ObjectNode) requestBodyNode).put("lineaEmail", lineaEmail);

			if (clienteType.getCliente().getListaDireccion() != null
					&& !clienteType.getCliente().getListaDireccion().getDireccion().isEmpty()) {
				((ObjectNode) requestBodyNode).put("concatenarDireccion",
						obtenerDireccionLarga(clienteType.getCliente().getListaDireccion().getDireccion().get(0)));
			}

			if (aperturarTCType.getAfiliarAon() != null) {
				((ObjectNode) requestBodyNode).put("templateSeguro",
						PropertiesCache.getInstance().getProperty(ConfigConstantes.ENVIO_CORREO_TEMPLATENAME_SEGURO));
			} else {
				((ObjectNode) requestBodyNode).put("templateSeguro", PropertiesCache.getInstance()
						.getProperty(ConfigConstantes.ENVIO_CORREO_TEMPLATENAME_SIN_SEGURO));
			}

			if (serviceId.contains(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_FECHA_HORA_ENTREGA))
					|| serviceId.contains(
							PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD))) {
				InformacionEntrega informacionEntrega = aperturaTcRequestUpdated.getMessageRequest().getBody()
						.getAperturarTC().getInformacionEntrega();
				Map<String, String> fechasfechaHoraEntrega = obtenerFechaHoraEntrega(informacionEntrega, fechaAlta,
						serviceId, messageId);

				((ObjectNode) requestBodyNode).put(FECHA_ENTREGA, fechasfechaHoraEntrega.get(FECHA_ENTREGA));
				((ObjectNode) requestBodyNode).put(HORA_ENTREGA, fechasfechaHoraEntrega.get(HORA_ENTREGA));
				((ObjectNode) requestBodyNode).put(FECHA_ALTA, fechasfechaHoraEntrega.get(FECHA_ALTA));
			}

			if (serviceId.contains(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_REGISTRAR_MONITOR))) {
				if (clienteType.getCliente().getInformacionLaboral().getListaDireccion() != null && !clienteType
						.getCliente().getInformacionLaboral().getListaDireccion().getDireccion().isEmpty()) {
					((ObjectNode) requestBodyNode).put("concatenarDireccionLaboral", obtenerDireccionLarga(clienteType
							.getCliente().getInformacionLaboral().getListaDireccion().getDireccion().get(0)));
				}
				Map<String, String> fechasMonitor = obtenerFechasMonitor(aperturaTcRequest);
				((ObjectNode) requestBodyNode).put(FECHA_NACIMIENTO_MONITOR,
						fechasMonitor.get(FECHA_NACIMIENTO_MONITOR));
				((ObjectNode) requestBodyNode).put(FECHA_TRANSMISION, fechasMonitor.get(FECHA_TRANSMISION));
				((ObjectNode) requestBodyNode).put(FECHA_TRANSACCION, fechasMonitor.get(FECHA_TRANSACCION));
				((ObjectNode) requestBodyNode).put(HORA_TRANSACCION, fechasMonitor.get(HORA_TRANSACCION));
				((ObjectNode) requestBodyNode).put(FECHA_HORA_TRANSACCION, fechasMonitor.get(FECHA_HORA_TRANSACCION));
				((ObjectNode) requestBodyNode).put(CODIGO_UNICO_CORE,
						AperturaTcUtils.recortarCadena(AperturaTcUtils
								.completarCeros(clienteType.getCliente().getIdCliente(), CODIGO_UNICO_CORE_LONGITUD),
								CODIGO_UNICO_CORE_LONGITUD, true));
			}

			requestModificado = mapper.writeValueAsString(requestConCliente);
			info="Fin Request Modifier para " + serviceId + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
		} catch (Exception e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + e.getStackTrace()[0].getClassName() + ":" + e.getStackTrace()[0].getMethodName() + " - "
					+ e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(ERROR_MODIFICAR + serviceId + Constantes.MESSAGE_ID_LOG + messageId);
		}
		return requestModificado;
	}

	protected static String formatearRespuestaAltaTarjeta(String altaTarjetaResponse, String serviceId,
			String messageId) throws MicroserviceException {
		try {
			if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ)
					.equals(serviceId)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode altaTarjetaResponseNode = mapper.readTree(altaTarjetaResponse);
				JsonNode crearTarjetaCreditoResponseNode = JsonUtil
						.getNodeReferenceFixCapitalized(Constantes.ALTA_TARJETA_RESPONSE_BODY, altaTarjetaResponseNode);
				JsonNode fechaAltaTitularNode = JsonUtil.getNodeReference(FECHA_ALTA, altaTarjetaResponseNode);
				SimpleDateFormat sdf = new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TC_FECHA_FORMATO));
				Date fechaAltaTcResponse = sdf.parse(fechaAltaTitularNode.textValue());
				SimpleDateFormat sdfadq = new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_ADQ_FECHA_FORMATO));
				((ObjectNode) crearTarjetaCreditoResponseNode).remove(FECHA_ALTA);
				((ObjectNode) crearTarjetaCreditoResponseNode).put(FECHA_ALTA, sdfadq.format(fechaAltaTcResponse));
				return mapper.writeValueAsString(altaTarjetaResponseNode);
			}

			return altaTarjetaResponse;
		} catch (Exception e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + e.getStackTrace()[0].getClassName() + ":" + e.getStackTrace()[0].getMethodName() + " - "
					+ e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(ERROR_MODIFICAR + serviceId);
		}
	}

	protected static String actualizarCamposEspecificos(String aperturaTcRequestString, String serviceId,
			String messageId) throws MicroserviceException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			AperturaTcRequest aperturaTcRequest = mapper.readValue(aperturaTcRequestString, AperturaTcRequest.class);
			AperturarTCType aperturarTCType = aperturaTcRequest.getMessageRequest().getBody().getAperturarTC();
			aperturarTCType = AperturaTcUtils.aplicarMayusculas(aperturarTCType);
			if (PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ)
					.equals(serviceId)) {
				aperturarTCType.setDatosCliente(actualizarDatosClienteAdq(aperturarTCType, messageId));

				if (aperturarTCType.getAltaTC().getIndicadorDevolucionCashback() == null) {
					aperturarTCType.getAltaTC().setIndicadorDevolucionCashback("0");
				}
			}
			return mapper.writeValueAsString(aperturaTcRequest);
		} catch (Exception e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + e.getStackTrace()[0].getClassName() + ":" + e.getStackTrace()[0].getMethodName() + " - "
					+ e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(ERROR_MODIFICAR + serviceId);
		}
	}

	protected static DatosCliente actualizarDatosClienteAdq(AperturarTCType aperturarTCType, String messageId)
			throws MicroserviceException {
		DatosCliente clienteType = aperturarTCType.getDatosCliente();
		InformacionLaboralType informacionLaboral = clienteType.getCliente().getInformacionLaboral();
		try {
			if (informacionLaboral.getFechaInicioContrato() != null
					&& !informacionLaboral.getFechaInicioContrato().isEmpty()) {

				SimpleDateFormat sdf = new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_FECHAS_FORMATO_INPUT));
				Date fechaInicioContratoDate = sdf.parse(informacionLaboral.getFechaInicioContrato());
				Date fechaFinContratoDate = sdf.parse(informacionLaboral.getFechaFinContrato());
				SimpleDateFormat crearclientesdf = new SimpleDateFormat(PropertiesCache.getInstance()
						.getProperty(ConfigConstantes.REGISTRAR_ADQ_FECHA_CONTRATO_FORMATO));
				informacionLaboral.setFechaInicioContrato(crearclientesdf.format(fechaInicioContratoDate));
				informacionLaboral.setFechaFinContrato(crearclientesdf.format(fechaFinContratoDate));

			}
			Cliente cliente = actualizarFechaNacimientoAdq(clienteType.getCliente(), messageId);
			cliente = actualizarDireccionCorrespondenciaCliente(aperturarTCType.getOperacionMedioContacto(), cliente,
					messageId);
			clienteType.setCliente(cliente);
			if (clienteType.getIntegrantes() != null && !clienteType.getIntegrantes().getIntegrante().isEmpty()) {
				List<Cliente> listaIntegrantes = clienteType.getIntegrantes().getIntegrante();
				Iterator<Cliente> iterator = listaIntegrantes.iterator();
				while (iterator.hasNext()) {
					actualizarFechaNacimientoAdq(iterator.next(), messageId);

				}
				List<Cliente> integrantesActualizado = new ArrayList<>();
				integrantesActualizado.add(null);
				integrantesActualizado.addAll(listaIntegrantes);
				clienteType.getIntegrantes().setIntegrante(integrantesActualizado);
			}

		} catch (Exception e) {
			logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getClass().getName()
					+ " en " + e.getStackTrace()[0].getClassName() + ":" + e.getStackTrace()[0].getMethodName() + " - "
					+ e.getMessage() + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(ERROR_MODIFICAR
					+ PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ));

		}

		return clienteType;
	}

	protected static Cliente actualizarFechaNacimientoAdq(Cliente cliente, String messageId)
			throws MicroserviceException {
		if (cliente.getFechaNacimiento() != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_FECHAS_FORMATO_INPUT));
				Date fechaNacOrig = sdf.parse(cliente.getFechaNacimiento());
				SimpleDateFormat adqsdf = new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_ADQ_FECHA_FORMATO));
				cliente.setFechaNacimiento(adqsdf.format(fechaNacOrig));
			} catch (Exception e) {
				logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
						+ e.getClass().getName() + " en " + e.getStackTrace()[0].getClassName() + ":"
						+ e.getStackTrace()[0].getMethodName() + " - " + e.getMessage() + Constantes.MESSAGE_ID_LOG
						+ messageId, e);
				throw new MicroserviceException(ERROR_MODIFICAR
						+ PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ));

			}
		}
		return cliente;
	}

	protected static String obtenerDireccionLarga(DireccionType direccion) {
		StringBuilder resultado = new StringBuilder();
		if (direccion.getCodigoTipoVia() != null && !direccion.getCodigoTipoVia().isEmpty()) {
			resultado.append(direccion.getCodigoTipoVia());
		}
		if (direccion.getNombreVia() != null && !direccion.getNombreVia().isEmpty()) {
			resultado.append(" " + direccion.getNombreVia());
		}
		if (direccion.getNumeroCalle() != null && !direccion.getNumeroCalle().isEmpty()) {
			resultado.append(" " + direccion.getNumeroCalle());
		}

		if (direccion.getNumeroManzana() != null && !direccion.getNumeroManzana().isEmpty()) {
			resultado.append(" MZ " + direccion.getNumeroManzana());
		}
		if (direccion.getNumeroInterior() != null && !direccion.getNumeroInterior().isEmpty()) {
			resultado.append(" INT " + direccion.getNumeroInterior());
		}
		if (direccion.getLote() != null && !direccion.getLote().isEmpty()) {
			resultado.append(" LT " + direccion.getLote());
		}
		return resultado.toString();
	}

	protected static Map<String, String> obtenerFechasMonitor(AperturaTcRequest aperturaTcRequest)
			throws MicroserviceException {
		String messageId = aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest().getRequest()
				.getMessageId();
		Map<String, String> fechas = new HashMap<>();

		try {
			Cliente cliente = aperturaTcRequest.getMessageRequest().getBody().getAperturarTC().getDatosCliente()
					.getCliente();
			String fechaNacimiento;
			String fechaNacimientoCliente = cliente.getFechaNacimiento();
			if (fechaNacimientoCliente != null && !fechaNacimientoCliente.isEmpty()) {
				SimpleDateFormat dateFormatFN = new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.CREAR_CLIENTE_FECHAS_FORMATO_INPUT));
				Date fechaNacimientoDate = dateFormatFN.parse(fechaNacimientoCliente);
				fechaNacimiento = (new SimpleDateFormat(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_MONITOR_FECHA_NACIMIENTO)))
								.format(fechaNacimientoDate);
				fechas.put(FECHA_NACIMIENTO_MONITOR, fechaNacimiento);
			}

			HeaderRequestType headerRequestType = aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest();
			String fechaHeaderStr = headerRequestType.getRequest().getTimestamp();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.HEADER_RQ_TIMESTAMP_FORMATO));
			Date fechaHeader = dateFormat.parse(fechaHeaderStr);
			String fechaTransmision = (new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_MONITOR_FECHA_TRANSMISION)))
							.format(fechaHeader);
			String fechaTransaccion = (new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_MONITOR_FECHA_TRANSACCION)))
							.format(fechaHeader);
			String horaTransaccion = (new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_MONITOR_HORA_TRANSACCION)))
							.format(fechaHeader);
			String fechaHoraTransaccion = (new SimpleDateFormat(PropertiesCache.getInstance()
					.getProperty(ConfigConstantes.REGISTRAR_MONITOR_FECHAHORA_TRANSACCION))).format(fechaHeader);
			fechas.put(FECHA_TRANSMISION, fechaTransmision);
			fechas.put(FECHA_TRANSACCION, fechaTransaccion);
			fechas.put(HORA_TRANSACCION, horaTransaccion);
			fechas.put(FECHA_HORA_TRANSACCION, fechaHoraTransaccion);

		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		return fechas;

	}

	public static String darFormatoNumerico(String numero) {
		DecimalFormat formatoDecimal = new DecimalFormat(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.ENVIO_CORREO_FORMATO_NUMERICO),
				DecimalFormatSymbols.getInstance(Locale.US));
		return formatoDecimal.format(Double.parseDouble(numero));
	}

	protected static Cliente actualizarDireccionCorrespondenciaCliente(OperacionMedioContacto medioContacto,
			Cliente cliente, String messageId) throws MicroserviceException {
		if (medioContacto != null && medioContacto.getCrearMedioContactoEspecializado() != null) {
			try {
				MedioContactoEspecializado direccion = medioContacto.getCrearMedioContactoEspecializado();
				DireccionType actual = cliente.getListaDireccion().getDireccion().get(0);
				actual.setCodigoTipoVia(direccion.getTipoVia());
				actual.setNombreVia(direccion.getNombreVia());
				actual.setNumeroCalle(direccion.getNumero());
				actual.setNumeroManzana(direccion.getManzana());
				actual.setNumeroInterior(direccion.getInterior());
				actual.setLote(direccion.getPisoLote());
				actual.setCodigoUbigeo(direccion.getUbigeo());
				return cliente;
			} catch (Exception e) {
				logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
						+ e.getClass().getName() + " en " + e.getStackTrace()[0].getClassName() + ":"
						+ e.getStackTrace()[0].getMethodName() + " - " + e.getMessage() + Constantes.MESSAGE_ID_LOG
						+ messageId, e);
				throw new MicroserviceException(ERROR_MODIFICAR
						+ PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ));

			}
		}
		return cliente;
	}

	public static Map<String, String> obtenerFechaHoraEntrega(InformacionEntrega informacionEntrega, String fechaAlta,
			String serviceId, String messageId) throws MicroserviceException {
		Map<String, String> fechaHoraEntregaMap = new HashMap<>();
		try {
			String fechaEntrega = "";
			String horaEntrega = "";
			if (informacionEntrega != null) {
				fechaEntrega = informacionEntrega.getFechaEntrega();
				horaEntrega = informacionEntrega.getHoraInicio();
			}
			fechaHoraEntregaMap.put(HORA_ENTREGA, horaEntrega);
			if (serviceId
					.contains(PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD))) {

				fechaHoraEntregaRegistrarBD(fechaHoraEntregaMap,fechaAlta,fechaEntrega);

			} else {
				fechaHoraEntregaNoRegBD(fechaHoraEntregaMap,fechaAlta,fechaEntrega);
			}

		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		return fechaHoraEntregaMap;
	}
	
	private static void fechaHoraEntregaNoRegBD(Map<String, String> fechaHoraEntregaMap,String fechaAlta,String fechaEntrega) throws ParseException{
		fechaHoraEntregaMap.put(FECHA_ALTA, fechaAlta);

		Date fechaEntregaDate = null;
		if (fechaEntrega != null && !fechaEntrega.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(PropertiesCache.getInstance()
					.getProperty(ConfigConstantes.INFORMACION_ENTREGA_FECHA_ENTREGA_IN));
			fechaEntregaDate = dateFormat.parse(fechaEntrega);
		}

		if (fechaEntregaDate != null) {
			Calendar fechaEntregaCal = Calendar.getInstance();
			fechaEntregaCal.setTime(fechaEntregaDate);
			Date date = fechaEntregaCal.getTime();
			SimpleDateFormat formatoFecha = new SimpleDateFormat(PropertiesCache.getInstance()
					.getProperty(ConfigConstantes.INFORMACION_ENTREGA_FECHA_ENTREGA_OUT));
			String fechaEntregaOut = formatoFecha.format(date);
			fechaHoraEntregaMap.put(FECHA_ENTREGA, fechaEntregaOut);
		}
	}
	
	private static void fechaHoraEntregaRegistrarBD(Map<String, String> fechaHoraEntregaMap,String fechaAlta,String fechaEntrega) throws ParseException{
		Date fechaAltaDate = null;
		if (fechaAlta != null && !fechaAlta.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.ALTA_TC_FECHA_FORMATO));
			fechaAltaDate = dateFormat.parse(fechaAlta);
		}

		if (fechaAltaDate != null) {
			Calendar fechaAltaCal = Calendar.getInstance();
			fechaAltaCal.setTime(fechaAltaDate);
			Date date = fechaAltaCal.getTime();
			SimpleDateFormat formatoFecha = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_BD_FECHA_FORMATO));
			String fechaAltaOut = formatoFecha.format(date);
			fechaHoraEntregaMap.put(FECHA_ALTA, fechaAltaOut);
		}

		Date fechaEntregaDate = null;
		if (fechaEntrega != null && !fechaEntrega.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(PropertiesCache.getInstance()
					.getProperty(ConfigConstantes.INFORMACION_ENTREGA_FECHA_ENTREGA_IN));
			fechaEntregaDate = dateFormat.parse(fechaEntrega);
		}

		if (fechaEntregaDate != null) {
			Calendar fechaEntregaCal = Calendar.getInstance();
			fechaEntregaCal.setTime(fechaEntregaDate);
			Date date = fechaEntregaCal.getTime();
			SimpleDateFormat formatoFecha = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.REGISTRAR_BD_FECHA_FORMATO));
			String fechaEntregaOut = formatoFecha.format(date);
			fechaHoraEntregaMap.put(FECHA_ENTREGA, fechaEntregaOut);
		}
	}

}
