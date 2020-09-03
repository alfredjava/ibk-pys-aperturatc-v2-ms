package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.BodyRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.Cliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DatosCliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.GrabarLPDP;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.TelefonoType;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversorAsincrono;
import pe.com.interbank.pys.grabarldpd.client.domain.Body;
import pe.com.interbank.pys.grabarldpd.client.domain.GrabarLDPDRequest;
import pe.com.interbank.pys.grabarldpd.client.domain.GrabarLPD;
import pe.com.interbank.pys.grabarldpd.client.domain.HeaderRequestType;
import pe.com.interbank.pys.grabarldpd.client.domain.MessageRequest;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class GrabarLDPDConversor {

	private static final Logger logger = LoggerFactory.getLogger(GrabarLDPDConversor.class);

	protected GrabarLDPDConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static GrabarLDPDRequest obtenerGrabarLDPDRequest(AperturaTcRequest aperturaTcRequest,
			String crearClienteResponse) throws MicroserviceException {
		String messageId = aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest().getRequest()
				.getMessageId();
		GrabarLDPDRequest grabarLDPDRequest = null;
		BodyRequest bodyRequest = aperturaTcRequest.getMessageRequest().getBody();
		GrabarLPDP grabarLPDType = bodyRequest.getAperturarTC().getGrabarLPDP();
		HeaderRequest headerRequest = aperturaTcRequest.getMessageRequest().getHeader();
		DatosCliente cliente = bodyRequest.getAperturarTC().getDatosCliente();
		if (grabarLPDType != null && cliente != null && cliente.getCliente() != null) {
			logger.debug("El cliente cuenta con informacion personal");
			try {
				cliente.setCliente(AperturaTcUtils.actualizarDatosClientesNuevos(cliente.getCliente(),
						 crearClienteResponse));
				logger.debug("Execucion de grabarLDPDRequest");
				pe.com.interbank.pys.grabarldpd.client.domain.HeaderRequestType header = RequestConversorAsincrono
						.obtenerHeaderRequest(headerRequest,
								new TypeReference<pe.com.interbank.pys.grabarldpd.client.domain.HeaderRequestType>() {
								});
				GrabarLPD grabarLPD = obtenerGrabarLPD(cliente.getCliente(), header, grabarLPDType);
				Body body = new Body(grabarLPD);
				MessageRequest messageRequest = new MessageRequest(header, body);
				grabarLDPDRequest = new GrabarLDPDRequest(messageRequest);
			} catch (Exception e) {
				String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
						+ e.getMessage();
				logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
				throw new MicroserviceException(mensaje, e);
			}
		} else {
			String info="obtenerGrabarLDPDRequest objetos - grabarLPDType: " + grabarLPDType + " cliente " + cliente
					+ " en mensaje con " + Constantes.MESSAGE_ID_LOG + messageId;
			logger.info(info);
		}
		return grabarLDPDRequest;
	}

	private static GrabarLPD obtenerGrabarLPD(Cliente cliente, HeaderRequestType header, GrabarLPDP grabarLPDType)
			throws MicroserviceException {
		String messageId = header.getHeaderRequest().getRequest().getMessageId();
		try {
			String telefonoCasa = null;
			String telefonoCelular = null;
			if (cliente.getListaTelefono() != null && !cliente.getListaTelefono().getTelefono().isEmpty()) {
				TelefonoType telefonoType = cliente.getListaTelefono().getTelefono().get(0);
				telefonoCasa = telefonoType.getNumeroTelefono();
				telefonoCelular = telefonoType.getNumeroCelular();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.HEADER_RQ_TIMESTAMP_FORMATO));
			String fechaProceso = header.getHeaderRequest().getRequest().getTimestamp();
			Date fechaDate = simpleDateFormat.parse(fechaProceso);
			SimpleDateFormat sdfFecha = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.GRABAR_LDPD_FECHA_FORMATO));
			SimpleDateFormat sdfHora = new SimpleDateFormat(
					PropertiesCache.getInstance().getProperty(ConfigConstantes.GRABAR_LDPD_HORA_FORMATO));
			fechaProceso = sdfFecha.format(fechaDate);
			String horaProceso = sdfHora.format(fechaDate);
			String flagCliente = grabarLPDType.getFlagCliente();
			if (flagCliente == null) {
				throw new MicroserviceException("El campo Flag Cliente es necesario para Grabar LDPD no puede ser nulo "
						+ Constantes.MESSAGE_ID_LOG + messageId);
			}
			String emailLaboral = null;
			if (cliente.getInformacionLaboral() != null) {
				emailLaboral = cliente.getEmail();
			}
			return new GrabarLPD(Integer.parseInt(flagCliente), grabarLPDType.getFlagLPD(), cliente.getTipoDocumento(),
					cliente.getNumeroDocumento(), cliente.getIdCliente(), cliente.getApellidoPaterno(),
					cliente.getApellidoMaterno(), cliente.getPrimerNombre(), cliente.getSegundoNombre(),
					grabarLPDType.getTipoConsentimiento(), grabarLPDType.getIdEmpresa(), telefonoCasa, telefonoCelular,
					cliente.getEmail(), emailLaboral, "", fechaProceso, horaProceso);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
	}

}
