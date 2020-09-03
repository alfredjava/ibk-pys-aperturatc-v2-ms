package pe.com.interbank.pys.aperturatcv2.microservices.util;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.ActualizarMedioContactoGeneralRequest;
import pe.com.interbank.pys.altatarjeta.client.domain.AltaTarjetaRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.ActualizarMedioContactoGeneralConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.AltaTarjetaConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.CrearClienteConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.CrearMedioContactoEspecializadoConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.CrearMedioContactoGeneralConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Cliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.OperacionMedioContacto;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Request;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Response;
import pe.com.interbank.pys.crearcliente.client.domain.CrearClienteRequest;
import pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.CrearMedioContactoEspecializadoRequest;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.CrearMedioContactoGeneralRequest;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class RequestConversor {

	private static final Logger logger = LoggerFactory.getLogger(RequestConversor.class);

	protected RequestConversor() throws IllegalAccessError {
		throw new IllegalAccessError("Static Conversor Class");
	}

	public static CrearMedioContactoGeneralRequest obtenerCrearMedioContactoRequest(
			OperacionMedioContacto crearMedioContactoGeneralType, HeaderRequest headerRequest, String codigoUnico)
			throws MicroserviceException {
		return CrearMedioContactoGeneralConversor.obtenerCrearMedioContactoGeneralRequest(crearMedioContactoGeneralType,
				headerRequest, codigoUnico);
	}

	public static ActualizarMedioContactoGeneralRequest obtenerActualizarMedioContactoRequest(
			OperacionMedioContacto actualizarMedioContactoGeneralType, HeaderRequest headerRequest, String codigoUnico)
			throws MicroserviceException {
		return ActualizarMedioContactoGeneralConversor.obtenerActualizarMedioContactoGeneralRequest(
				actualizarMedioContactoGeneralType, headerRequest, codigoUnico);
	}

	public static CrearMedioContactoEspecializadoRequest obtenerCrearMedioContactoEspecializadoRequest(
			OperacionMedioContacto crearMedioContactoDireccionType, HeaderRequest headerRequest, String codigoUnico,
			String crearClienteResponse) throws MicroserviceException {
		return CrearMedioContactoEspecializadoConversor.obtenerCrearMedioContactoEspecializadoRequest(
				crearMedioContactoDireccionType, headerRequest, codigoUnico, crearClienteResponse);
	}

	public static AltaTarjetaRequest obtenerAltaTarjetaRequest(
			pe.com.interbank.pys.aperturatcv2.microservices.domain.MessageRequest requestAperturaTC,
			HeaderRequest headerRequest) throws MicroserviceException {
		return AltaTarjetaConversor.obtenerAltaTarjetaRequest(requestAperturaTC, headerRequest);

	}

	public static CrearClienteRequest obtenerCrearClienteRequest(Cliente cliente, HeaderRequest headerRequest)
			throws MicroserviceException {
		return CrearClienteConversor.obtenerCrearClienteRequest(cliente, headerRequest);
	}

	public static Response convertirAperturaTcRequestHeaderToResponseHeader(Request request) {
		return new Response(request.getServiceId(), request.getConsumerId(), request.getModuleId(),
				request.getChannelCode(), request.getMessageId(), request.getTimestamp(), request.getCountryCode(),
				request.getGroupMember(), request.getReferenceNumber());
	}

	public static String actualizarRequestAltaTarjeta(String crearDireccionResponse, String altaTarjetaRequest,
			String codigoCorrespondenciaMedioContactoGeneral, String crearClienteResponse)
			throws MicroserviceException {
		String codigoCorrespondenciaCrearDireccion = "";
		try {
			AltaTarjetaRequest tarjetaRequest = new ObjectMapper().readValue(altaTarjetaRequest,
					AltaTarjetaRequest.class);
			String codigoUnicoCliente = tarjetaRequest.getMessageRequest().getBody().getCrearTarjetaCredito()
					.getCodigoUnico();

			String codigoCorrespondenciaAlta = tarjetaRequest.getMessageRequest().getBody().getCrearTarjetaCredito()
					.getCodigoCorrespondencia();

			if (!crearDireccionResponse.isEmpty()) {
				JsonNode root = new ObjectMapper().readTree(crearDireccionResponse);
				codigoCorrespondenciaCrearDireccion = JsonUtil.getCampo("codigoUso", root);
			}

			/**
			 * Cambio 08/11/2017 se quito el reemplazo de nulo por vacio y se
			 * implemento logica para BPI CASO 1 nulo por vacio solo si no hubo
			 * ningun medioContacto peticion de Jazmin, Eduardo y Brelis
			 */
			boolean artificioSkip = esArtificioMedioContacto(codigoCorrespondenciaAlta,
					codigoCorrespondenciaCrearDireccion, codigoCorrespondenciaMedioContactoGeneral);
			if (artificioSkip) {
				logger.info("artificio");
				codigoCorrespondenciaAlta = "";
			}

			String codigoCorrespondenciaUnificado = obtenerCodigoCorrespondenciaConsolidado(codigoCorrespondenciaAlta,
					codigoCorrespondenciaCrearDireccion, codigoCorrespondenciaMedioContactoGeneral);

			if (codigoUnicoCliente == null || codigoUnicoCliente.isEmpty()) {
				tarjetaRequest.getMessageRequest().getBody().getCrearTarjetaCredito()
						.setCodigoUnico(AperturaTcUtils.obtenerCodigoUnicoCrearClienteResponse(crearClienteResponse));
			}

			tarjetaRequest.getMessageRequest().getBody().getCrearTarjetaCredito()
					.setCodigoCorrespondencia(codigoCorrespondenciaUnificado);

			return new ObjectMapper().writeValueAsString(tarjetaRequest);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	public static String obtenerCodigoCorrespondenciaConsolidado(String codigoCorrespondenciaAlta,
			String codigoCorrespondenciaCrearDireccion, String codigoCorrespondenciaMedioContactoGeneral)
			throws MicroserviceException {
		logger.info(
				"obtenerCodigoCorrespondenciaConsolidado inicio recibio - codigoCorrespondenciaAlta: {} "
						+ "codigoCorrespondenciaCrearDireccion: {} codigoCorrespondenciaMedioContactoGeneral: {}",
				codigoCorrespondenciaAlta, codigoCorrespondenciaCrearDireccion,
				codigoCorrespondenciaMedioContactoGeneral);
		try {
			String codigoCorrespondenciaUnificado = codigoCorrespondenciaMedioContactoGeneral;
			if (!codigoCorrespondenciaCrearDireccion.isEmpty()) {
				codigoCorrespondenciaUnificado = unificarConDireccion(codigoCorrespondenciaCrearDireccion,
						codigoCorrespondenciaMedioContactoGeneral);
			}
			if (!codigoCorrespondenciaUnificado.isEmpty() && codigoCorrespondenciaAlta != null) {
				// si vino Codigocorrespondencia en alta se junta con el
				// unificado
				codigoCorrespondenciaUnificado = unificarCodigoCorresAlta(codigoCorrespondenciaAlta,
						codigoCorrespondenciaUnificado);
			}

			if (codigoCorrespondenciaUnificado.isEmpty() && codigoCorrespondenciaAlta != null
					&& !codigoCorrespondenciaAlta.isEmpty()) {
				codigoCorrespondenciaUnificado = codigoCorrespondenciaAlta;
			}

			logger.info("obtenerCodigoCorrespondenciaConsolidado devolvio codigoCorrespondenciaUnificado: {} ",
					codigoCorrespondenciaUnificado);
			return codigoCorrespondenciaUnificado;

		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	public static String extraerCodigoCorrespondencia(Map<String, String> crearActualizarMedioContacto)
			throws MicroserviceException {
		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.putAll(crearActualizarMedioContacto);
		String respuestaMs = treeMap.get(treeMap.firstKey());
		String codigoCorrespondencia = "";
		try {
			JsonNode root = new ObjectMapper().readTree(respuestaMs);
			JsonNode idSecundario = root
					.findValue(PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_ID_SECUNDARIO));
			if (idSecundario != null && idSecundario.isTextual()) {
				codigoCorrespondencia = idSecundario.textValue();
				// desde el tercer caracter
				if (codigoCorrespondencia.contains(
						PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_ID_SECUNDARIO_VALOR_EMAIL))) {
					// ID SECUNDARIO REPRESENTA AL CODIGO DEVUELTO POR MEDIO
					// CONTACTO GENERAL SE ELIMINA LOS 2 PRIMEROS CARACTERES Y
					codigoCorrespondencia = codigoCorrespondencia.substring(3);
				} else {
					codigoCorrespondencia = "";
				}
			}
			return codigoCorrespondencia;
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

	protected static String unificarCodigoCorrespondencia(String codigoUso, String idSecundario) {
		// CODIGO USO RETORNA DE DEL MEDIO CONTACTO ESPECIFICO Y REPRESENTA LA
		// DIRECCION
		// SE TOMAN LOS 2 ULTIMOS DIGITOS DE ESTE CODIGO
		StringBuilder codigoCorrespondenciaBuilder = new StringBuilder();
		if (codigoUso != null && idSecundario != null) {
			if (!idSecundario.trim().isEmpty()) {
				codigoCorrespondenciaBuilder.append(unificarConCodigoUso(codigoUso, idSecundario));
			} else {
				// SI IDSECUNDARIO esta vacio se devuelve solo el codigoUso
				codigoCorrespondenciaBuilder.append(codigoUso);
			}

		}
		return codigoCorrespondenciaBuilder.toString();
	}

	private static String unificarConDireccion(String codigoCorrespondenciaCrearDireccion,
			String codigoCorrespondenciaMedioContactoGeneral) throws MicroserviceException {
		String codigoCorrespondenciaUnificado = codigoCorrespondenciaMedioContactoGeneral;
		try {
			if (!codigoCorrespondenciaCrearDireccion.isEmpty()) {
				codigoCorrespondenciaUnificado = codigoCorrespondenciaCrearDireccion;
				if (!codigoCorrespondenciaMedioContactoGeneral.isEmpty()
						&& !esConstruidoFisico(codigoCorrespondenciaMedioContactoGeneral)) {
					codigoCorrespondenciaUnificado = unificarCodigoCorrespondencia(codigoCorrespondenciaCrearDireccion,
							codigoCorrespondenciaMedioContactoGeneral);
				}
			}
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + e.getMessage();
			logger.error(mensaje, e);
			throw new MicroserviceException(mensaje, e);
		}
		return codigoCorrespondenciaUnificado;
	}

	private static String unificarCodigoFisico(String codigoCorrespondenciaAlta,
			String codigoCorrespondenciaUnificado) {
		if (esConstruidoFisico(codigoCorrespondenciaUnificado)) {
			// si el codigo unificado es una direccion fisica o un EMPE O EMJO
			// reemplaza al del alta
			return codigoCorrespondenciaUnificado;
		} else {
			// si es un email se unifica con el de alta
			return unificarCodigoCorrespondencia(codigoCorrespondenciaAlta, codigoCorrespondenciaUnificado);
		}
	}

	private static String unificarCodigoEmail(String codigoCorrespondenciaAlta, String codigoCorrespondenciaUnificado) {
		if (codigoCorrespondenciaUnificado.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_FISICA))) {
			// si el unificado es una direccion fisica
			// se unifican codigos pero de manera inversa
			return unificarCodigoCorrespondencia(codigoCorrespondenciaUnificado, codigoCorrespondenciaAlta);
		} else {
			// si el unificado es Email tambien entonces
			// reemplaza
			return codigoCorrespondenciaUnificado;
		}
	}

	protected static String unificarCodigoCorresAlta(String codigoCorrespondenciaAlta,
			String codigoCorrespondenciaUnificado) {
		if (codigoCorrespondenciaAlta.isEmpty() || codigoCorrespondenciaAlta.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_PRINCI))) {
			return unificarCodigoFisico(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_FISICA)
							+ "00",
					codigoCorrespondenciaUnificado);
		} else if (codigoCorrespondenciaAlta.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_FISICA))) {
			// si el codigo es un codigo de direccion
			return unificarCodigoFisico(codigoCorrespondenciaAlta, codigoCorrespondenciaUnificado);

		} else {// si es un codigo Email en entrada alta tc
			return unificarCodigoEmail(codigoCorrespondenciaAlta, codigoCorrespondenciaUnificado);
		}
	}

	public static <T> T obtenerHeaderRequest(HeaderRequest headerAperturaTc, final TypeReference<T> type)
			throws IOException {
		String headerOrigen = new ObjectMapper().writeValueAsString(headerAperturaTc);
		return new ObjectMapper().readValue(headerOrigen, type);
	}

	protected static String unificarConCodigoUso(String codigoUso, String idSecundario) {
		if (codigoUso != null && !codigoUso.isEmpty()) {
			StringBuilder codigoCorrespondenciaBuilder = new StringBuilder();
			codigoCorrespondenciaBuilder.append(codigoUso.substring(codigoUso.length() - 2));
			// IDSECUNDARIO ES EL CODIGO DEVUELTO POR MEDIO CONTACTO GENERAL
			// SE ELIMINA LA LETRA A DE EN MEDIO DEL CODIGO
			String sinA = idSecundario.replace("A", "");
			codigoCorrespondenciaBuilder.append(sinA.substring(0, sinA.length() - 1));
			return codigoCorrespondenciaBuilder.toString();
		} else {
			return idSecundario;
		}
	}

	private static boolean esConstruidoFisico(String codigoUnificado) {
		if (codigoUnificado.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_FISICA))) {
			return true;
		}

		if (codigoUnificado.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_PRINCI))) {
			return true;
		}

		if (codigoUnificado.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_EMPE))) {
			return true;
		}

		if (codigoUnificado.contains(
				PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPO_CODIGO_CORRESPONDENCIA_EMJO))) {
			return true;
		}
		return false;
	}

	public static boolean esArtificioMedioContacto(String codigoCorrespondenciaAlta,
			String codigoCorrespondenciaDireccion, String codigoCorrespondenciaEmail) {
		String info="Alta: " + codigoCorrespondenciaAlta + " MCG: " + codigoCorrespondenciaDireccion + " MCE: "
				+ codigoCorrespondenciaEmail;
		logger.info(info);
		if (codigoCorrespondenciaDireccion.isEmpty() && codigoCorrespondenciaEmail.isEmpty()
				&& codigoCorrespondenciaAlta == null) {
			return true;
		}
		return false;

	}

}
