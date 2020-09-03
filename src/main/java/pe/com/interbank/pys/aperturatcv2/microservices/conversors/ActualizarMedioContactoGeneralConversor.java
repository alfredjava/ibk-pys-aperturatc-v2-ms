package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.ActualizarMedioContactoGeneral;
import pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.ActualizarMedioContactoGeneralRequest;
import pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.MedioContactoGeneral;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.OperacionMedioContacto;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class ActualizarMedioContactoGeneralConversor {

	private static final Logger logger = LoggerFactory.getLogger(ActualizarMedioContactoGeneralConversor.class);

	protected ActualizarMedioContactoGeneralConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static ActualizarMedioContactoGeneralRequest obtenerActualizarMedioContactoGeneralRequest(
			OperacionMedioContacto actualizarMedioContactoGeneralType, HeaderRequest headerRequest, String codigoUnico)
			throws MicroserviceException {
		ActualizarMedioContactoGeneralRequest contactoGeneralRequest = null;
		String messageId = headerRequest.getHeaderRequest().getRequest().getMessageId();
		try {
			pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.HeaderRequest header = RequestConversor
					.obtenerHeaderRequest(headerRequest,
							new TypeReference<pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.HeaderRequest>() {
							});
			ActualizarMedioContactoGeneral crearMedioContactoGeneral = ActualizarMedioContactoGeneralConversor
					.obtenerActualizarMedioContactoGeneral(actualizarMedioContactoGeneralType, codigoUnico);
			pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.BodyRequest body = new pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.BodyRequest(
					crearMedioContactoGeneral);
			pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.MessageRequest messageRequest = new pe.com.interbank.pys.actualizarmediocontactogeneral.client.domain.MessageRequest(
					header, body);
			contactoGeneralRequest = new ActualizarMedioContactoGeneralRequest(messageRequest);
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		return contactoGeneralRequest;
	}

	private static ActualizarMedioContactoGeneral obtenerActualizarMedioContactoGeneral(
			OperacionMedioContacto actualizarMedioContactoGeneralType, String codigoUnico) {
		ActualizarMedioContactoGeneral actualizarMedioContactoGeneral = null;
		if (actualizarMedioContactoGeneralType != null) {
			MedioContactoGeneral medioContactoGeneral = obtenerMedioContactoGeneral(
					actualizarMedioContactoGeneralType.getActualizarMedioContactoGeneral());

			actualizarMedioContactoGeneral = new ActualizarMedioContactoGeneral(codigoUnico, "",
					PropertiesCache.getInstance().getProperty(ConfigConstantes.TIPO_ROL_MEDIO_CONTACTO),
					medioContactoGeneral);

		}
		return actualizarMedioContactoGeneral;
	}

	private static MedioContactoGeneral obtenerMedioContactoGeneral(
			pe.com.interbank.pys.aperturatcv2.microservices.domain.MedioContactoGeneral aMedioContactoGeneralType) {
		if (aMedioContactoGeneralType != null) {
			return new MedioContactoGeneral(aMedioContactoGeneralType.getTipoMedioContacto(),
					aMedioContactoGeneralType.getFlagModificacion(), aMedioContactoGeneralType.getEstado(),
					aMedioContactoGeneralType.getCodigoUso(), aMedioContactoGeneralType.getDescripcion(),
					aMedioContactoGeneralType.getDepartamento(), aMedioContactoGeneralType.getPrefijo(),
					aMedioContactoGeneralType.getNumeroAnexo(), aMedioContactoGeneralType.getObservaciones(),
					aMedioContactoGeneralType.getIdMedioContacto());
		}
		return null;
	}

}
