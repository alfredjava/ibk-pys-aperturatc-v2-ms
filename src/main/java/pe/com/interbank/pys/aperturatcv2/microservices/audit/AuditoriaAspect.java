package pe.com.interbank.pys.aperturatcv2.microservices.audit;

import javax.ws.rs.core.Response;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.Constantes;
import pe.com.interbank.pys.aperturatcv2.microservices.util.MicroserviceUtils;
import pe.com.interbank.pys.trace.microservices.service.AuditoriaService;
import pe.com.interbank.pys.trace.microservices.util.JsonUtil;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

@Aspect
@Component
public class AuditoriaAspect {
	private static final String ERROR_CODE = "-1";
	private static final String CLIENT = "client";
	public static final String ERROR_INVOCAR_BUS = "Error al invocar el Servicio Bus ";
	public static final String MESSAGEID = "messageId";
	
	@Autowired
	private AuditoriaService auditoriaService;

	@Before("execution(* pe.com.interbank.pys.*.microservices.service.SecurityRestClient.invokeExternalService(..))")
	public void auditoriaRequest(JoinPoint joinPoint) {
		Object[] parametros = joinPoint.getArgs();
		String request = JsonUtil.getTrama(parametros[0]);
		String path = (String) parametros[2];
		long offset = (long) parametros[4];
		String destinationServiceId = null;
		if (parametros.length >= 6) {
			destinationServiceId = (String) parametros[5];
		}
		JsonNode requestBusNode = JsonUtil.getRootNode(request);
		String requestBus = JsonUtil.getJsonString(requestBusNode);
		String messageId = JsonUtil.getCampoTrama(MESSAGEID, request);
		auditoriaService.escribirAuditoria(messageId, requestBus, Constantes.MSG_TYPE_REQUEST,
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID), path, offset,
				destinationServiceId);

	}

	@AfterReturning(pointcut = "execution(* pe.com.interbank.pys.*.microservices.service.SecurityRestClient.invokeExternalService(..))", returning = "resultado")
	public void auditoriaResponse(JoinPoint joinPoint, Object resultado) {
		Response response = (Response) resultado;
		response.bufferEntity();
		Object[] parametros = joinPoint.getArgs();
		String request = JsonUtil.getTrama(parametros[0]);
		String responseService = response.readEntity(String.class);
		String path = (String) parametros[2];
		long offset = (long) parametros[4];
		String destinationServiceId = null;
		if (parametros.length >= 6) {
			destinationServiceId = (String) parametros[5];
		}
		String messageId = JsonUtil.getCampoTrama(MESSAGEID, request);
		auditoriaService.escribirAuditoria(messageId, responseService, Constantes.MSG_TYPE_REQUEST,
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID), path, offset,
				destinationServiceId);
	}

	@AfterThrowing(pointcut = "execution(* pe.com.interbank.pys.*.microservices.service.SecurityRestClient.invokeExternalService(..))", throwing = "error")
	public void auditoriaError(JoinPoint joinPoint, Throwable error) {
		Object[] parametros = joinPoint.getArgs();
		String request = JsonUtil.getTrama(parametros[0]);
		String path = (String) parametros[2];
		long offset = (long) parametros[4];
		String destinationServiceId = null;
		if (parametros.length >= 6) {
			destinationServiceId = (String) parametros[5];
		}
		String responseService = MicroserviceUtils.registrarTimeOut(request);
		String statusMsCode = ERROR_CODE;
		String statusMsMessage = ERROR_INVOCAR_BUS + path + " Error: " + error.getMessage();
		JsonNode responseNode = MicroserviceUtils.obtenerNode(responseService, statusMsCode, statusMsMessage);
		String messageId = JsonUtil.getCampoTrama(MESSAGEID, request);
		auditoriaService.escribirAuditoria(messageId, responseNode.toString(), Constantes.MSG_TYPE_RESPONSE,
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID), path, offset,
				destinationServiceId);
	}

	@Before("execution(* pe.com.interbank.pys.*.microservices.controller.*Controller.*Post(..))")
	public void auditoriaControllerRequest(JoinPoint joinPoint) {
		Object[] parametros = joinPoint.getArgs();
		String request = JsonUtil.getTrama(parametros[0]);
		String messageId = JsonUtil.getCampoTrama(MESSAGEID, request);
		auditoriaService.escribirAuditoria(messageId, request, Constantes.MSG_TYPE_REQUEST, CLIENT,
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID), -10L, null);

	}

	@AfterReturning(pointcut = "execution(* pe.com.interbank.pys.*.microservices.controller.*Controller.*Post(..))", returning = "resultado")
	public void auditoriaControllerResponse(JoinPoint joinPoint, Object resultado) {
		Object[] parametros = joinPoint.getArgs();
		Response response = (Response) resultado;
		String request = JsonUtil.getTrama(parametros[0]);
		String messageId = JsonUtil.getCampoTrama(MESSAGEID, request);
		String responseStr;
		response.bufferEntity();
		responseStr = JsonUtil.getTrama(response.getEntity());
		auditoriaService.escribirAuditoria(messageId, responseStr, Constantes.MSG_TYPE_RESPONSE,
				PropertiesCache.getInstance().getProperty(ConfigConstantes.SERVICE_ID), CLIENT, -10L, null);

	}

	public void setAuditoriaService(AuditoriaService auditoriaService) {
		this.auditoriaService = auditoriaService;
	}

}
