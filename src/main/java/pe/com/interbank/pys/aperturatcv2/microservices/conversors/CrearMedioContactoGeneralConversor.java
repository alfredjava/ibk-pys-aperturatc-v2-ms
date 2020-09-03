package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.OperacionMedioContacto;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.BodyRequest;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.CrearMedioContactoGeneral;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.CrearMedioContactoGeneralRequest;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.MedioContactoGeneral;
import pe.com.interbank.pys.crearmediocontactogeneral.client.domain.MessageRequest;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class CrearMedioContactoGeneralConversor {

	private static final Logger logger = LoggerFactory.getLogger(CrearMedioContactoGeneralConversor.class);

	protected CrearMedioContactoGeneralConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static CrearMedioContactoGeneralRequest obtenerCrearMedioContactoGeneralRequest(
			OperacionMedioContacto crearMedioContactoGeneralType, HeaderRequest headerRequest, String codigoUnico)
			throws MicroserviceException {
		String messageId = headerRequest.getHeaderRequest().getRequest().getMessageId();
		CrearMedioContactoGeneralRequest contactoGeneralRequest = null;
		try {
			pe.com.interbank.pys.crearmediocontactogeneral.client.domain.HeaderRequest header;
			header = RequestConversor.obtenerHeaderRequest(headerRequest,
					new TypeReference<pe.com.interbank.pys.crearmediocontactogeneral.client.domain.HeaderRequest>() {
					});
			CrearMedioContactoGeneral crearMedioContactoGeneral = CrearMedioContactoGeneralConversor
					.obtenerCrearMedioContactoGeneral(crearMedioContactoGeneralType, codigoUnico);
			BodyRequest body = new BodyRequest(crearMedioContactoGeneral);
			MessageRequest messageRequest = new MessageRequest(header, body);
			contactoGeneralRequest = new CrearMedioContactoGeneralRequest(messageRequest);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		logger.debug("Fin de obtenerCrearMedioContactoGeneralRequest");
		return contactoGeneralRequest;

	}

	private static CrearMedioContactoGeneral obtenerCrearMedioContactoGeneral(
			OperacionMedioContacto crearMedioContactoGeneralType, String codigoUnico) {
		logger.debug("Inicio de obtenerCrearMedioContactoGeneral");
		CrearMedioContactoGeneral crearMedioContactoGeneral = null;
		if (crearMedioContactoGeneralType != null) {
			logger.debug("Existe tipo de medio de contacto general");
			MedioContactoGeneral medioContactoGeneral = obtenerMedioContactoGeneral(
					crearMedioContactoGeneralType.getCrearMedioContactoGeneral());

			crearMedioContactoGeneral = new CrearMedioContactoGeneral(codigoUnico, "",
					PropertiesCache.getInstance().getProperty(ConfigConstantes.TIPO_ROL_MEDIO_CONTACTO),
					medioContactoGeneral);
		}
		logger.debug("Fin de obtenerCrearMedioContactoGeneral");
		return crearMedioContactoGeneral;
	}

	private static MedioContactoGeneral obtenerMedioContactoGeneral(
			pe.com.interbank.pys.aperturatcv2.microservices.domain.MedioContactoGeneral crearMedioContactoGeneralType) {
		logger.debug("Inicio de obtenerMedioContactoGeneral");
		MedioContactoGeneral medioContactoGeneral = null;
		if (crearMedioContactoGeneralType != null) {
			medioContactoGeneral = new MedioContactoGeneral(crearMedioContactoGeneralType.getTipoMedioContacto(),
					crearMedioContactoGeneralType.getEstado(), crearMedioContactoGeneralType.getCodigoUso(),
					crearMedioContactoGeneralType.getDescripcion(), crearMedioContactoGeneralType.getDepartamento(),
					crearMedioContactoGeneralType.getPrefijo(), crearMedioContactoGeneralType.getNumeroAnexo(),
					crearMedioContactoGeneralType.getObservaciones());
		}
		logger.debug("Fin de obtenerMedioContactoGeneral");
		return medioContactoGeneral;
	}

}
