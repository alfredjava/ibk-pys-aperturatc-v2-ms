package pe.com.interbank.pys.aperturatcv2.microservices.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Provider
@Component
public class UnrecognizedPropertyExceptionMapper extends GenericExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {

	@Override
	public Response toResponse(UnrecognizedPropertyException exception) {
		return super.generarRespuesta(exception);
	}

}
