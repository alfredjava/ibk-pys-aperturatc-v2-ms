package pe.com.interbank.pys.aperturatcv2.microservices.conversors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.Cliente;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.DireccionType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.InformacionLaboralType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.TelefonoType;
import pe.com.interbank.pys.aperturatcv2.microservices.util.AperturaTcUtils;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.RequestConversor;
import pe.com.interbank.pys.crearcliente.client.domain.Body;
import pe.com.interbank.pys.crearcliente.client.domain.CrearCliente;
import pe.com.interbank.pys.crearcliente.client.domain.CrearClienteRequest;
import pe.com.interbank.pys.crearcliente.client.domain.MessageRequest;
import pe.com.interbank.pys.crearcliente.client.domain.Telefono;
import pe.com.interbank.pys.crearcliente.client.domain.TelefonoCliente;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class CrearClienteConversor {

	private static final Logger logger = LoggerFactory.getLogger(CrearClienteConversor.class);

	protected CrearClienteConversor() throws IllegalAccessException {
		throw new IllegalAccessException("Static Conversor Class");
	}

	public static CrearClienteRequest obtenerCrearClienteRequest(Cliente cliente, HeaderRequest headerRequest)
			throws MicroserviceException {
		String messageId = headerRequest.getHeaderRequest().getRequest().getMessageId();
		CrearClienteRequest clienteRequest = null;
		try {
			pe.com.interbank.pys.crearcliente.client.domain.HeaderRequestType header = RequestConversor
					.obtenerHeaderRequest(headerRequest,
							new TypeReference<pe.com.interbank.pys.crearcliente.client.domain.HeaderRequestType>() {
							});

			CrearCliente crearCliente = obtenerCrearCliente(AperturaTcUtils.mayusculasCliente(cliente));
			Body body = new Body(crearCliente);
			MessageRequest messageRequest = new MessageRequest(header, body);
			clienteRequest = new CrearClienteRequest(messageRequest);
		} catch (MicroserviceException mse) {
			throw mse;
		} catch (Exception e) {
			String mensaje = PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO) + e.getMessage();
			logger.error(mensaje + Constantes.MESSAGE_ID_LOG + messageId, e);
			throw new MicroserviceException(mensaje, e);
		}
		logger.debug("Fin de obtenerActualizarMedioContactoGeneralRequest");
		return clienteRequest;
	}

	public static CrearCliente obtenerCrearCliente(Cliente cliente) throws MicroserviceException {
		String methodName = " - obtenerCrearClienteRequest";
		logger.debug("Inicio de obtenerCrearCliente");
		CrearCliente crearCliente = null;
		TelefonoType telefono = null;
		if (cliente.getListaTelefono() != null && cliente.getListaTelefono().getTelefono() != null
				&& !cliente.getListaTelefono().getTelefono().isEmpty()) {
			telefono = cliente.getListaTelefono().getTelefono().get(0);
		}

		if (telefono != null) {
			String numerotelefono;
			if ("C".equals(telefono.getTipoTelefono())) {
				numerotelefono = telefono.getNumeroCelular();
			} else {
				numerotelefono = telefono.getNumeroTelefono();
			}
			Telefono item = new Telefono(telefono.getTipoTelefono(), telefono.getCodigoCiudad(), numerotelefono,
					telefono.getNumeroAnexo());
			TelefonoCliente telefonoCliente = new TelefonoCliente(item);
			List<TelefonoCliente> listaTelefonos = new ArrayList<>();
			listaTelefonos.add(telefonoCliente);
			InformacionLaboralType informacionLaboral = cliente.getInformacionLaboral();
			crearCliente = construirCrearCliente(cliente, informacionLaboral, listaTelefonos);

		} else {
			errorCrearCliente(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID) + methodName
					+ " se requiere la informacion de crearMedioContacto y al menos un telefono para registrar un nuevo cliente");
		}
		return crearCliente;
	}

	public static CrearCliente construirCrearCliente(Cliente cliente, InformacionLaboralType informacionLaboral,
			List<TelefonoCliente> listaTelefonos) throws MicroserviceException {
		String methodName = " - construirCrearCliente";
		CrearCliente crearCliente = null;
		if (informacionLaboral != null) {
			DireccionType direccion = cliente.getListaDireccion().getDireccion().get(0);
			crearCliente = new CrearCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento(),
					cliente.getNumeroPasaporte(), cliente.getFechaNacimiento(), cliente.getApellidoPaterno(),
					cliente.getApellidoMaterno(), cliente.getPrimerNombre(), cliente.getSegundoNombre(),
					cliente.getSexo(), cliente.getEstadoCivil(), cliente.getCodigoNacionalidad(),
					cliente.getCodigoPaisNacimiento(), cliente.getCodigoPaisResidencia(),
					cliente.getCodigoPaisNacionalidad(), direccion.getCodigoTipoVia(), direccion.getNombreVia(),
					direccion.getNumeroCalle(), direccion.getNumeroManzana(), direccion.getLote(),
					direccion.getNumeroInterior(), direccion.getUrbanizacion(), direccion.getReferencia(),
					direccion.getDepartamento(), direccion.getProvincia(), direccion.getDistrito(), listaTelefonos,
					PropertiesCache.getInstance().getProperty(ConfigConstantes.TIPO_EMAIL_CREAR_CLIENTE),
					cliente.getEmail(),
					PropertiesCache.getInstance().getProperty(ConfigConstantes.CODIGO_CIIU_CREAR_CLIENTE),
					informacionLaboral.getFechaInicioContrato(), informacionLaboral.getRazonSocial(),
					informacionLaboral.getCodigoOcupacion());
		} else {
			errorCrearCliente(PropertiesCache.getInstance().getProperty(ConfigConstantes.ERROR_TIPO)
					+ PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID) + methodName
					+ " se requiere la informacion laboral para registrar un nuevo cliente");
		}
		return crearCliente;
	}

	public static void errorCrearCliente(String mensaje) throws MicroserviceException {
		logger.error(mensaje);
		throw new MicroserviceException(mensaje);
	}

}
