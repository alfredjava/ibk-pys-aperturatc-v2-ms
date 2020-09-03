package pe.com.interbank.pys.aperturatcv2.microservices.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Cliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DireccionType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.ListaDireccionType;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class AperturaTcUtils {
	private static final Logger logger = LoggerFactory.getLogger(AperturaTcUtils.class);

	protected AperturaTcUtils() {
		throw new IllegalAccessError("Clase Utils");
	}

	public static String obtenerValorCadenaSeguro(JsonNode jsonNode, String campo) {
		JsonNode nodoCampo = jsonNode.findValue(campo);
		if (nodoCampo != null && !(nodoCampo instanceof MissingNode) && !(nodoCampo instanceof NullNode)) {
			return nodoCampo.textValue();
		} else {
			return "";
		}

	}

	public static Cliente actualizarDatosClientesNuevos(Cliente cliente,
			String crearClienteResponse) {
		if (crearClienteResponse != null) {
			try {
				if (cliente != null && (cliente.getIdCliente() == null || cliente.getIdCliente().isEmpty())) {
					cliente.setIdCliente(obtenerCodigoUnicoCrearClienteResponse(crearClienteResponse));
				}
			} catch (Exception e) {
				logger.error(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
						+ e.getClass().getName() + " en " + e.getStackTrace().getClass().getName() + ":"
						+ e.getStackTrace()[0].getMethodName() + " error:" + e);
			}
		}
		return cliente;
	}

	public static String obtenerCodigoUnicoCrearClienteResponse(String crearClienteResponse)
			throws MicroserviceException {
		String resultado = "";
		String messageId = JsonUtil.getCampoTrama(Constantes.MESSAGE_ID_MAP, crearClienteResponse);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			JsonNode root = mapper.readTree(crearClienteResponse);
			JsonNode codigoUnicoClienteNode = root.findValue("codigoUnicoCliente");
			if (codigoUnicoClienteNode != null && !(codigoUnicoClienteNode instanceof MissingNode)
					&& codigoUnicoClienteNode.isTextual()) {
				resultado = codigoUnicoClienteNode.textValue();
			}
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ e.getClass().getName() + " en " + "RequestConvertor" + ":" + e.getStackTrace()[0].getMethodName()
					+ " - Al obtener el codigo unico de la respuesta de crear cliente " + e;
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		return resultado;
	}

	public static String obtenerDescripcionDireccion(String altaTarjetaRequest) throws IOException {
		String direccion;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(altaTarjetaRequest);
		String codigoCorrespondencia = AperturaTcUtils.obtenerValorCadenaSeguro(root, "codigoCorrespondencia");
		if (!codigoCorrespondencia.isEmpty()) {
			Map<String, String> camposDireccion;
			String json = PropertiesCache.getInstance().getProperty(ConfigConstantes.ENVIO_CORREO_DIRECCION_MAPPING);
			camposDireccion = mapper.readValue(json, new TypeReference<Map<String, String>>() {
			});
			direccion = obtenerValorFromJsonMap(camposDireccion, codigoCorrespondencia);
		} else {
			// es un cliente nuevo y no registro direccion
			direccion = PropertiesCache.getInstance().getProperty(ConfigConstantes.ENVIO_CORREO_DIRECCION_FISICO);
		}
		return direccion;
	}

	public static String obtenerCodigoCorrespondenciaAlta(String altaTarjetaRequest) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(altaTarjetaRequest);
		return obtenerValorCadenaSeguro(root, "codigoCorrespondencia");
	}

	public static String obtenerValorFromJsonMap(Map<String, String> camposDireccion, String codigoCorrespondencia) {
		Iterator<Entry<String, String>> iterator = camposDireccion.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> actual = iterator.next();
			if (codigoCorrespondencia.contains(actual.getKey())) {
				return actual.getValue();
			}
		}
		return "";
	}

	public static String obtenerValorExactoFromJsonMap(Map<String, String> camposDireccion,
			String codigoCorrespondencia) {
		Iterator<Entry<String, String>> iterator = camposDireccion.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> actual = iterator.next();
			if (codigoCorrespondencia.equals(actual.getKey())) {
				return actual.getValue();
			}
		}
		return "";
	}

	public static AperturarTCType aplicarMayusculas(AperturarTCType aperturarTCType) {

		aperturarTCType.getAltaTC().setNombrePlastico(toSafeUpperCase(aperturarTCType.getAltaTC().getNombrePlastico()));
		if (aperturarTCType.getDatosCliente().getCliente() != null) {
			aperturarTCType.getDatosCliente()
					.setCliente(mayusculasCliente(aperturarTCType.getDatosCliente().getCliente()));
		}

		if (aperturarTCType.getInformacionEntrega() != null) {
			aperturarTCType.getInformacionEntrega()
					.setDireccion(toSafeUpperCase(aperturarTCType.getInformacionEntrega().getDireccion()));
			aperturarTCType.getInformacionEntrega()
					.setCodigoDistrito(toSafeUpperCase(aperturarTCType.getInformacionEntrega().getCodigoDistrito()));
			aperturarTCType.getInformacionEntrega()
					.setCodigoProvincia(toSafeUpperCase(aperturarTCType.getInformacionEntrega().getCodigoProvincia()));
			aperturarTCType.getInformacionEntrega().setCodigoDepartamento(
					toSafeUpperCase(aperturarTCType.getInformacionEntrega().getCodigoDepartamento()));
			aperturarTCType.getInformacionEntrega()
					.setMail(toSafeUpperCase(aperturarTCType.getInformacionEntrega().getMail()));
		}
		if (aperturarTCType.getRegistroMonitor() != null) {
			aperturarTCType.getRegistroMonitor().getMonitorACF().setDireccionCorrespondencia(toSafeUpperCase(
					aperturarTCType.getRegistroMonitor().getMonitorACF().getDireccionCorrespondencia()));
			aperturarTCType.getRegistroMonitor().getMonitorCore().setDireccionDomicilio(
					toSafeUpperCase(aperturarTCType.getRegistroMonitor().getMonitorCore().getDireccionDomicilio()));
		}

		return aperturarTCType;
	}

	public static Cliente mayusculasCliente(Cliente cliente) {
		cliente.setApellidoMaterno(toSafeUpperCase(cliente.getApellidoMaterno()));
		cliente.setApellidoPaterno(toSafeUpperCase(cliente.getApellidoPaterno()));
		cliente.setPrimerNombre(toSafeUpperCase(cliente.getPrimerNombre()));
		cliente.setSegundoNombre(toSafeUpperCase(cliente.getSegundoNombre()));
		cliente.setEmail(toSafeUpperCase(cliente.getEmail()));
		ListaDireccionType listaDireccionType = cliente.getListaDireccion();
		if (listaDireccionType != null) {
			List<DireccionType> listaDirecciones = listaDireccionType.getDireccion();
			List<DireccionType> listaDirecciones2 = direccionesMayuscula(listaDirecciones);
			cliente.setListaDireccion(new ListaDireccionType(listaDirecciones2));
		}

		ListaDireccionType listaDireccionLaboralType = cliente.getInformacionLaboral().getListaDireccion();
		if (listaDireccionLaboralType != null) {
			List<DireccionType> listaDirecciones = listaDireccionLaboralType.getDireccion();
			List<DireccionType> listaDirecciones2 = direccionesMayuscula(listaDirecciones);
			cliente.getInformacionLaboral().setListaDireccion(new ListaDireccionType(listaDirecciones2));
		}

		if (cliente.getInformacionLaboral() != null) {
			cliente.getInformacionLaboral().setEmail(toSafeUpperCase(cliente.getInformacionLaboral().getEmail()));
			cliente.getInformacionLaboral()
					.setRazonSocial(toSafeUpperCase(cliente.getInformacionLaboral().getRazonSocial()));
		}
		return cliente;

	}

	private static List<DireccionType> direccionesMayuscula(List<DireccionType> listaDirecciones) {
		if (listaDirecciones != null && !listaDirecciones.isEmpty()) {
			Iterator<DireccionType> direcciones = listaDirecciones.iterator();
			while (direcciones.hasNext()) {
				DireccionType actual = direcciones.next();
				actual.setNombreVia(toSafeUpperCase(actual.getNombreVia()));
				actual.setDepartamento(toSafeUpperCase(actual.getDepartamento()));
				actual.setProvincia(toSafeUpperCase(actual.getProvincia()));
				actual.setDistrito(toSafeUpperCase(actual.getDistrito()));
				actual.setReferencia(toSafeUpperCase(actual.getReferencia()));
			}
		}
		return listaDirecciones;

	}

	public static String toSafeUpperCase(String cadena) {
		if (cadena != null && !cadena.isEmpty()) {
			return cadena.toUpperCase();
		}
		return cadena;

	}

	public static String completarCeros(String cadena, int longitudMaxima) {
		if (cadena.length() >= longitudMaxima) {
			return cadena;
		} else {
			return StringUtils.leftPad(cadena, longitudMaxima, '0');
		}
	}

	public static String recortarCadena(String cadena, int longitudMaxima, boolean recortarInicio) {
		if (cadena.length() >= longitudMaxima) {
			if (recortarInicio) {
				return cadena.substring(cadena.length() - longitudMaxima);
			} else {
				return cadena.substring(0, longitudMaxima);
			}
		} else {
			return cadena;
		}
	}

	public static void validateFechaAlta(String listaAdicionalesResponse, String messageId) throws MicroserviceException {//NOSONAR
		String info="validate Fecha Alta iniciando" + Constantes.MESSAGE_ID_LOG + messageId;
		logger.info(info);
		String strSrvResponseCode  = JsonUtil.getCampoTrama("srvResponseCode", listaAdicionalesResponse);
		info="Codigo de respuesta del core:" + strSrvResponseCode;
		logger.info(info);		
		if( !PropertiesCache.getInstance().getProperty(ConfigConstantes.SRVRESPONSECODE_OK).equals(strSrvResponseCode) ) {
			JsonNode detalleTarjetaNode = null;
			String fechaAltaTC = null;
			String tipoBeneficiario = null;
			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.DATE_YYYY_MM_DD_FORMAT);
			String fechaHoy = localDate.format(formatter);
			JsonNode listaAdicionalesResponseNode = JsonUtil.getRootNode(listaAdicionalesResponse);
			JsonNode detalleCuentaNode = JsonUtil.getNodeReference(Constantes.DETALLECUENTA, listaAdicionalesResponseNode);
			info="Validando fechas de lista de tcs " + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
			for (JsonNode cuentas : detalleCuentaNode) {
				detalleTarjetaNode = JsonUtil.getNodeReference(Constantes.DETALLETARJETA, cuentas);
				for (JsonNode tarjetas : detalleTarjetaNode) {
					fechaAltaTC = JsonUtil.getCampo(Constantes.FECHAALTA, tarjetas);
					tipoBeneficiario = JsonUtil.getCampo(Constantes.TIPOBENEFICIARIO, tarjetas);
					if (Constantes.TIPOBENEFICIARIO_TI.equals(tipoBeneficiario) && fechaHoy.equals(fechaAltaTC)) {											
							info=PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
									+ ": Existe un tc con la fecha de hoy" + Constantes.MESSAGE_ID_LOG + messageId;
							logger.error(info);
							throw new MicroserviceException(
									PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
											+ ": Existe un tc con la fecha de hoy");
					}
				}
			}
			info="validate Fecha Alta terminado" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
		}else {
			info="Cliente no cuenta con trarjetas" + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
		}				
	}

}
