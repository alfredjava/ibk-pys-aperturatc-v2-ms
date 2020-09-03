package pe.com.interbank.pys.aperturatcv2.microservices.jolt;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcRequest;
import pe.com.interbank.pys.aperturatcv2.microservices.modifiers.RequestModifier;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Component
public class JoltRequestConversor {

	private static final Logger logger = LoggerFactory.getLogger(JoltRequestConversor.class);

	@Autowired
	@Qualifier("conversorList")
	private Map<String, List<Object>> conversorList;

	@Autowired
	private JoltRequestSorter sorter;

	public String obtenerConsultaAdicionalesRequest(String request){
		String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.LISTA_ADICIONALES);
		return obtenerRequest(request, serviceId);
	}
	
	public String obtenerAfiliarSeguroJLTRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,
			String crearClienteResponse, String codigoCorrespondencia) throws MicroserviceException {
		if (aperturaTcRequest.getMessageRequest().getBody().getAperturarTC().getAfiliarAon() != null) {
			String serviceId = PropertiesCache.getInstance()
					.getProperty(ConfigConstantes.SERVICE_ID_AFILIAR_SEGURO_JLT);
			String request = RequestModifier.actualizarRequest(aperturaTcRequest, altaTarjetaResponse,
					crearClienteResponse, codigoCorrespondencia, serviceId);
			return sorter.ordenarRequest(obtenerRequest(request, serviceId), serviceId);
		} else {
			String info="No se encuentra informacion de afiliacion seguro, omitiendo orquestacion de servicio para"
					+ Constantes.MESSAGE_ID_LOG
					+ aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
			logger.info(info);
			return StringUtils.EMPTY;
		}

	}

	public String obtenerRegistrarAdqRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,
			String crearClienteResponse, String codigoCorrespondenciaAlta) throws MicroserviceException {
		if (aperturaTcRequest.getMessageRequest().getBody().getAperturarTC().getRegistrarADQ() != null) {
			String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_ADQ);
			String request = RequestModifier.actualizarRequest(aperturaTcRequest, altaTarjetaResponse,
					crearClienteResponse, codigoCorrespondenciaAlta, serviceId);
			return sorter.ordenarRequest(obtenerRequest(request, serviceId), serviceId);
		} else {
			String info="No se encuentra informacion de ADQ, omitiendo orquestacion de servicio para"
					+ Constantes.MESSAGE_ID_LOG
					+ aperturaTcRequest.getMessageRequest().getHeader().getHeaderRequest().getRequest().getMessageId();
			logger.info(info);
			return StringUtils.EMPTY;
		}
	}

	public String obtenerMonitorRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,
			String crearClienteResponse, String codigoCorrespondenciaAlta, String tipoMonitor)
			throws MicroserviceException {
		String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_REGISTRAR_MONITOR)
				+ tipoMonitor;
		String request = RequestModifier.actualizarRequest(aperturaTcRequest, altaTarjetaResponse, crearClienteResponse,
				codigoCorrespondenciaAlta, serviceId);
		return obtenerRequest(request, serviceId);
	}

	public String obtenerNotificacionRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,
			String crearClienteResponse, String codigoCorrespondenciaAlta, String tipoNotificacion)
			throws MicroserviceException {
		String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_NOTIFICACION)
				+ tipoNotificacion;
		String request = RequestModifier.actualizarRequest(aperturaTcRequest, altaTarjetaResponse, crearClienteResponse,
				codigoCorrespondenciaAlta, serviceId);
		return sorter.ordenarRequest(obtenerRequest(request, serviceId),
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_NOTIFICACION));
	}

	public String obtenerFechaHoraEntregaRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,
			String crearClienteResponse, String codigoCorrespondencia) throws MicroserviceException {
		String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_MS_FECHA_HORA_ENTREGA);
		String request = RequestModifier.actualizarRequest(aperturaTcRequest, altaTarjetaResponse, crearClienteResponse,
				codigoCorrespondencia, serviceId);
		return obtenerRequest(request, serviceId);
	}

	public String obtenerRegistrarBdRequest(AperturaTcRequest aperturaTcRequest, String altaTarjetaResponse,
			String crearClienteResponse, String codigoCorrespondencia) throws MicroserviceException {
		String serviceId = PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID_REGISTRAR_BD);
		String request = RequestModifier.actualizarRequest(aperturaTcRequest, altaTarjetaResponse, crearClienteResponse,
				codigoCorrespondencia, serviceId);
		return obtenerRequest(request, serviceId);
	}

	protected String obtenerRequest(String request, String serviceId) {
		List<Object> specs = obtenerSpecs(serviceId);
		Chainr chainr = Chainr.fromSpec(specs);
		Object transformedOutput = chainr.transform(JsonUtils.jsonToObject(request));
		return JsonUtils.toJsonString(transformedOutput);
	}

	protected List<Object> obtenerSpecs(String serviceId) {
		return conversorList.get(serviceId);
	}

}
