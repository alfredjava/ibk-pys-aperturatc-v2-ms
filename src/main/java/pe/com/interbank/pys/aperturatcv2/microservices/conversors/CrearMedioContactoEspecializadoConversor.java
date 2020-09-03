package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.OperacionMedioContacto;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.CrearMedioContactoEspecializado;
import pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.CrearMedioContactoEspecializadoRequest;
import pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.MedioContactoEspecializado;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class CrearMedioContactoEspecializadoConversor {

	private static final Logger logger = LoggerFactory.getLogger(CrearMedioContactoEspecializadoConversor.class);

	protected CrearMedioContactoEspecializadoConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static CrearMedioContactoEspecializadoRequest obtenerCrearMedioContactoEspecializadoRequest(
			OperacionMedioContacto crearMedioContactoDireccionType, HeaderRequest headerRequest, String codigoUnico,
			String crearClienteResponse) throws MicroserviceException {
		String messageId = headerRequest.getHeaderRequest().getRequest().getMessageId();
		CrearMedioContactoEspecializadoRequest contactoEspecializadoRequest = null;
		try {
			pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.HeaderRequest header = RequestConversor
					.obtenerHeaderRequest(headerRequest,
							new TypeReference<pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.HeaderRequest>() {
							});
			CrearMedioContactoEspecializado crearMedioContactoEspecializado = CrearMedioContactoEspecializadoConversor
					.obtenerCrearMedioContactoEspecializado(crearMedioContactoDireccionType, codigoUnico);

			pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.BodyRequest body = new pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.BodyRequest(
					crearMedioContactoEspecializado);
			pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.MessageRequest messageRequest = new pe.com.interbank.pys.crearmediocontactoespecializado.client.domain.MessageRequest(
					header, body);
			contactoEspecializadoRequest = new CrearMedioContactoEspecializadoRequest(messageRequest);

			if (codigoUnico == null || codigoUnico.isEmpty()) {
				contactoEspecializadoRequest.getMessageRequest().getBody().getCrearMedioContactoEspecializado()
						.setIdRolIndividuoPrincipal(
								AperturaTcUtils.obtenerCodigoUnicoCrearClienteResponse(crearClienteResponse));
			}

		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		logger.debug("Fin de obtenerCrearMedioContactoEspecializadoRequest");
		return contactoEspecializadoRequest;
	}

	private static CrearMedioContactoEspecializado obtenerCrearMedioContactoEspecializado(
			OperacionMedioContacto crearMedioContactoDireccionType, String codigoUnico) {
		logger.debug("Inicio de obtenerCrearMedioContactoEspecializado");
		CrearMedioContactoEspecializado contactoEspecializado = null;
		if (crearMedioContactoDireccionType != null) {
			MedioContactoEspecializado medioContactoEspecializado = obtenerMedioContactoEspecializado(
					crearMedioContactoDireccionType.getCrearMedioContactoEspecializado());
			contactoEspecializado = new CrearMedioContactoEspecializado(codigoUnico, "",
					PropertiesCache.getInstance().getProperty(ConfigConstantes.TIPO_ROL_MEDIO_CONTACTO),
					medioContactoEspecializado);
		}
		logger.debug("Fin de obtenerCrearMedioContactoEspecializado");
		return contactoEspecializado;
	}

	private static MedioContactoEspecializado obtenerMedioContactoEspecializado(
			pe.com.interbank.pys.aperturatcv2.microservices.domain.MedioContactoEspecializado datosCDireccionType) {
		logger.debug("Inicio de obtenerMedioContactoEspecializado");
		MedioContactoEspecializado medioContactoEspecializado = null;
		if (datosCDireccionType != null) {
			medioContactoEspecializado = new MedioContactoEspecializado(datosCDireccionType.getCodigoUso(),
					datosCDireccionType.getEstado(), datosCDireccionType.getTipoDireccion(),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getTipoVia()),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getNombreVia()),
					datosCDireccionType.getNumero(), AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getManzana()),
					datosCDireccionType.getPisoLote(),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getInterior()),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getUrbanizacion()),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getReferencia()),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getDepartamento()),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getProvincia()),
					AperturaTcUtils.toSafeUpperCase(datosCDireccionType.getDistrito()), datosCDireccionType.getPais(),
					datosCDireccionType.getUbigeo(), datosCDireccionType.getCodigoPostal());
		}
		logger.debug("Fin de obtenerMedioContactoEspecializado");
		return medioContactoEspecializado;
	}

}
