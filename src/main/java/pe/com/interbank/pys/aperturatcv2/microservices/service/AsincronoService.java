package pe.com.interbank.pys.aperturatcv2.microservices.service;

import org.jvnet.hk2.annotations.Service;

import pe.com.interbank.pys.trace.microservices.exceptions.LegacyException;
import pe.com.interbank.pys.trace.microservices.exceptions.MicroserviceException;


@FunctionalInterface
@Service
public interface AsincronoService {

	public void escribirTopicoAsincronoTemplate(String serviceId, String request, String topico)
			throws LegacyException, MicroserviceException;
}
