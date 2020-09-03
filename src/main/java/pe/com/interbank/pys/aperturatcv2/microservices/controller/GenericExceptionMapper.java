package pe.com.interbank.pys.aperturatcv2.microservices.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturaTcResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.AperturarTCResponseType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.BodyResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.HeaderResponseType;
import pe.com.interbank.pys.aperturatcv2.microservices.domain.MessageResponse;
import pe.com.interbank.pys.aperturatcv2.microservices.util.ConfigConstantes;
import pe.com.interbank.pys.trace.microservices.util.PropertiesCache;

public class GenericExceptionMapper {
	
	public Response generarRespuesta(Exception exception) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		pe.com.interbank.pys.aperturatcv2.microservices.domain.Response response = new pe.com.interbank.pys.aperturatcv2.microservices.domain.Response(
				"5", "", "", "", "", timestamp, "", "", "");
		// Respuesta por defecto casos de errores no manejados como Nullpointer
		// exception
		AperturarTCResponseType aperturarTcResponse = null;
		BodyResponse body = new BodyResponse(aperturarTcResponse);
		pe.com.interbank.pys.aperturatcv2.microservices.domain.Status status = new pe.com.interbank.pys.aperturatcv2.microservices.domain.Status(
				"", "", "", "", "", "", "", "", "1",
				PropertiesCache.getInstance().getProperty(ConfigConstantes.MICROSERVICE_ERROR) + exception.getMessage(),
				"", "");
		response.setTimestamp(timestamp);
		HeaderResponse headerResponse = new HeaderResponse(response, status);
		HeaderResponseType header = new HeaderResponseType(headerResponse);
		MessageResponse messageResponse = new MessageResponse(header, body);
		AperturaTcResponse aperturaTcResponse = new AperturaTcResponse(messageResponse);
		return Response.status(Status.BAD_REQUEST).entity(aperturaTcResponse).build();
	}

}
