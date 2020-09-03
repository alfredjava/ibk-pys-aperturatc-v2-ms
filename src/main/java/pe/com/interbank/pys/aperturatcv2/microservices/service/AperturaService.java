package pe.com.interbank.pys.aperturatcv2.microservices.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;


@Service
public interface AperturaService {

	public void validarRespuesta(Map<String, String> map) throws MicroserviceException;

	public String validarAltaTc(String request, String messageId) throws LegacyException , MicroserviceException;
	
	public String altaTarjeta(String request, String messageId) throws LegacyException, MicroserviceException;

	public String crearMedioContactoGeneral(String request, String messageId)
			throws LegacyException, MicroserviceException;

	public String actualizarMedioContactoGeneral(String request, String messageId)
			throws LegacyException, MicroserviceException;

	public String crearMedioContactoEspecializado(String request, String messageId)
			throws LegacyException, MicroserviceException;

	public String crearCliente(String request, String messageId) throws LegacyException, MicroserviceException;

}
