package pe.com.interbank.pys.aperturatcv2.microservices.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.interbank.pys.actualizarcampania.microservices.client.ActualizarCampaniaRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.ActualizarCampaniaConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.conversors.GrabarLDPDConversor;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.ActualizarCampania;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.grabarldpd.client.domain.GrabarLDPDRequest;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;

public class RequestConversorAsincrono {

	private static final Logger logger = LoggerFactory.getLogger(RequestConversorAsincrono.class);

	protected RequestConversorAsincrono() throws IllegalAccessError {
		throw new IllegalAccessError("Static Conversor Class");
	}

	public static <T> T obtenerHeaderRequest(HeaderRequest headerAperturaTc, final TypeReference<T> type)
			throws IOException {
		logger.debug(" obtenerHeaderRequest :" + type.getType().getTypeName());
		String headerOrigen = new ObjectMapper().writeValueAsString(headerAperturaTc);
		return new ObjectMapper().readValue(headerOrigen, type);
	}

	public static GrabarLDPDRequest obtenerGrabarLDPDRequest(AperturaTcRequest aperturaTcRequest,
			String crearClienteResponse) throws MicroserviceException {
		logger.debug(" obtenerGrabarLDPDRequest");
		return GrabarLDPDConversor.obtenerGrabarLDPDRequest(aperturaTcRequest, crearClienteResponse);
	}

	public static ActualizarCampaniaRequest obtenerActualizarCampaniaRequest(AperturaTcRequest aperturaTcRequest,
			ActualizarCampania actualizaCampaniaType, String crearClienteResponse) throws MicroserviceException {
		logger.debug(" obtenerActualizarCampaniaRequest");
		return ActualizarCampaniaConversor.obtenerActualizarCampaniaRequest(aperturaTcRequest, actualizaCampaniaType,
				crearClienteResponse);
	}

}
