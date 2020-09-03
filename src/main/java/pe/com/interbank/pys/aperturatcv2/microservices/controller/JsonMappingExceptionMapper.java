package pe.com.interbank.pys.aperturatcv2.microservices.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException;

@Provider
@Component
public class JsonMappingExceptionMapper extends GenericExceptionMapper implements ExceptionMapper<JsonMappingException> {

	@Override
	public Response toResponse(JsonMappingException exception) {
		return super.generarRespuesta(exception);
	}

}
