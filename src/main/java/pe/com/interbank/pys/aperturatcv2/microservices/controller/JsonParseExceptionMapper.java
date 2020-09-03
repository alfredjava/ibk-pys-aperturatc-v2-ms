package pe.com.interbank.pys.aperturatcv2.microservices.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;

@Provider
@Component
public class JsonParseExceptionMapper extends GenericExceptionMapper implements ExceptionMapper<JsonParseException> {

	@Override
	public Response toResponse(JsonParseException exception) {
		return super.generarRespuesta(exception);
	}

}
