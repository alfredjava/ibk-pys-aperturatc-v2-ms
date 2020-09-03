package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.actualizarcampania.microservices.client.ActualizarCampaniaBody;
import pe.com.interbank.pys.actualizarcampania.microservices.client.ActualizarCampaniaRequest;
import pe.com.interbank.pys.actualizarcampania.microservices.client.ProductosType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.ActualizarCampania;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DatosCliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.ProductoType;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversorAsincrono;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class ActualizarCampaniaConversor {

	private static final Logger logger = LoggerFactory.getLogger(ActualizarCampaniaConversor.class);
	private static final String MESSAGEID=" - messageId: ";

	protected ActualizarCampaniaConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static ActualizarCampaniaRequest obtenerActualizarCampaniaRequest(AperturaTcRequest aperturaTcRequest,
			ActualizarCampania actualizarCampania, String crearClienteResponse) throws MicroserviceException {
		logger.debug("Inicio de obtenerActualizarCampaniaRequest");
		ActualizarCampaniaRequest actualizarCampaniaRequest = null;
		AperturarTCType aperturarTCType = aperturaTcRequest.getMessageRequest().getBody().getAperturarTC();
		HeaderRequest headerRequest = aperturaTcRequest.getMessageRequest().getHeader();
		String messageId = headerRequest.getHeaderRequest().getRequest().getMessageId();
		DatosCliente cliente = aperturarTCType.getDatosCliente();
		String info="";
		if (actualizarCampania != null && cliente != null) {
			try {
				cliente.setCliente(AperturaTcUtils.actualizarDatosClientesNuevos(cliente.getCliente(),crearClienteResponse));
				pe.com.interbank.pys.actualizarcampania.microservices.client.HeaderRequest header = RequestConversorAsincrono
						.obtenerHeaderRequest(headerRequest,
								new TypeReference<pe.com.interbank.pys.actualizarcampania.microservices.client.HeaderRequest>() {
								});
				logger.debug("Actualizando campo consumerID");
				header.getHeaderRequest().getRequest().setConsumerId(obtenerConsumerIdActualizarCampania(headerRequest.getHeaderRequest().getRequest().getChannelCode()));
				ActualizarCampaniaBody actualizarCampaniaBody = obtenerActualizarCampaniaBody(aperturarTCType, header,
						actualizarCampania, crearClienteResponse);
				pe.com.interbank.pys.actualizarcampania.microservices.client.BodyRequest body = new pe.com.interbank.pys.actualizarcampania.microservices.client.BodyRequest(
						actualizarCampaniaBody);
				pe.com.interbank.pys.actualizarcampania.microservices.client.MessageRequest messageRequest = new pe.com.interbank.pys.actualizarcampania.microservices.client.MessageRequest(
						header, body);
				actualizarCampaniaRequest = new ActualizarCampaniaRequest(messageRequest);
				info="Exito en obtenerActualizarCampaniaRequest : " + MESSAGEID + messageId;
				logger.debug(info);
			} catch (Exception e) {
				String mensaje = 
						PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
				info=mensaje + Constantes.MESSAGE_ID_LOG + messageId;
				logger.error(info,e);
				throw new MicroserviceException(mensaje, e);
			}
		} else {
			info="obtenerActualizarCampaniaRequest objetos - actualizarCampanaType: " + actualizarCampania
					+ " cliente " + cliente + MESSAGEID + messageId;
			logger.debug(info);
		}
		info="Fin de obtenerActualizarCampaniaRequest" + MESSAGEID + messageId;
		logger.debug(info);
		return actualizarCampaniaRequest;
	}

	public static ActualizarCampaniaBody obtenerActualizarCampaniaBody(AperturarTCType aperturarTCType,
			pe.com.interbank.pys.actualizarcampania.microservices.client.HeaderRequest header,
			ActualizarCampania actualizarCampaniaType, String crearClienteResponse) throws ParseException {
		logger.debug("Inicio de obtenerRegistrarBdRequest");
		ActualizarCampaniaBody body = null;
		if (actualizarCampaniaType != null) {
			String tipoDocumento = aperturarTCType.getDatosCliente().getCliente().getTipoDocumento();
			String numeroDocumento = aperturarTCType.getDatosCliente().getCliente().getNumeroDocumento();
			String codigoUnico = aperturarTCType.getDatosCliente().getCliente().getIdCliente();
			if(crearClienteResponse!=null){
				codigoUnico=null;
			}
			String creadoPor = null;
			String modificadoPor = null;
			String codigoCanal = null;
			if (header.getHeaderRequest() != null && header.getHeaderRequest().getRequest() != null) {
				codigoCanal = header.getHeaderRequest().getRequest().getConsumerId();
			}
			if (header.getHeaderRequest() != null && header.getHeaderRequest().getIdentity() != null) {
				creadoPor = header.getHeaderRequest().getIdentity().getUserId();
				modificadoPor = header.getHeaderRequest().getIdentity().getUserId();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.HEADER_RQ_TIMESTAMP_FORMATO));
			String fechaCreacion;
			String fechaActualizacion;
			Date fechaDate = simpleDateFormat.parse(header.getHeaderRequest().getRequest().getTimestamp());
			SimpleDateFormat sdf = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.ACTUALIZAR_CAMPANIA_FECHA_FORMATO));
			fechaCreacion = sdf.format(fechaDate);
			fechaActualizacion = fechaCreacion;
			List<ProductosType> productosRespuesta = obtenerListaProductos(actualizarCampaniaType);
			body = new ActualizarCampaniaBody(actualizarCampaniaType.getTipoRespuesta(),
					actualizarCampaniaType.getDescripcion(), actualizarCampaniaType.getEstadoRespuesta(),
					actualizarCampaniaType.getAplicacion(), actualizarCampaniaType.getCodigoCampania(),
					actualizarCampaniaType.getNombreCampania(), codigoCanal,
					actualizarCampaniaType.getCodigoEstadoLead(), actualizarCampaniaType.getCodigoFuente(),
					actualizarCampaniaType.getCodigoOferta(), actualizarCampaniaType.getNombreOferta(),
					actualizarCampaniaType.getCodigoTratamiento(), actualizarCampaniaType.getNombreTratamiento(),
					actualizarCampaniaType.getCore(), actualizarCampaniaType.getFlagNuevoBus(),
					actualizarCampaniaType.getFlagOperacion(), actualizarCampaniaType.getIdMensaje(),
					actualizarCampaniaType.getMotivo(), tipoDocumento, numeroDocumento, codigoUnico, productosRespuesta,
					actualizarCampaniaType.getPuntuacion(), actualizarCampaniaType.getResultado(),
					actualizarCampaniaType.getResumen(), creadoPor, modificadoPor, fechaCreacion, fechaActualizacion);
			logger.debug("Exito obtenerActualizarCampaniaBody");
		}
		logger.debug("Fin de obtenerActualizarCampaniaBody");
		return body;
	}

	public static List<ProductosType> obtenerListaProductos(ActualizarCampania actualizarCampanaType) {
		logger.debug("Inicio de obtenerRegistrarBdRequest");
		List<ProductosType> productosRespuesta = new ArrayList<>();
		if (actualizarCampanaType.getListaProductos() != null) {
			logger.debug("Cuenta con lista de productos");
			List<ProductoType> productoTypes = actualizarCampanaType.getListaProductos().getProducto();
			Iterator<ProductoType> iterator = productoTypes.iterator();
			List<pe.com.interbank.pys.actualizarcampania.microservices.client.ProductoType> productos = new ArrayList<>();
			while (iterator.hasNext()) {
				ProductoType actual = iterator.next();
				pe.com.interbank.pys.actualizarcampania.microservices.client.ProductoType item = new pe.com.interbank.pys.actualizarcampania.microservices.client.ProductoType(
						actual.getCodigoProducto(), actual.getCore(), actual.getDisponible(),
						actual.getEntregaEsperada(), actual.getEstadoSolicitud(), actual.getFechaAlta(),
						actual.getFechaAprobacion(), actual.getIdentificador(), actual.getMonto(),
						actual.getNombreProducto(), actual.getNumeroSolicitud(), actual.getPaquete(),
						actual.getProductoVendido(), actual.getProductoVenta(), actual.getTamanio(),
						actual.getTipoTramite(), actual.getUnidades());
				productos.add(item);
			}
			ProductosType producto = new ProductosType(productos);
			productosRespuesta.add(producto);
		}
		logger.debug("Fin de obtenerListaProductos");
		return productosRespuesta;
	}
	
	private static String obtenerConsumerIdActualizarCampania(String channelCode) throws IOException {
		String json = PropertiesCache.getInstance().getProperty(ConfigConstantes.CAMPOS_MAPEO_CONSUMERID_CAMPANIA);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> mappingConsumerId = mapper.readValue(json, new TypeReference<Map<String, String>>() {
		});
		return mappingConsumerId.get(channelCode);
	}

}
