package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageResponse {

	private HeaderResponseType header;
	private BodyResponse body;

	@JsonCreator
	public MessageResponse(@JsonProperty("Header") HeaderResponseType header, @JsonProperty("Body") BodyResponse body) {
		this.header = header;
		this.body = body;
	}
	
	@JsonProperty("Header")
	public HeaderResponseType getHeader() {
		return header;
	}

	public void setHeader(HeaderResponseType header) {
		this.header = header;
	}
	
	@JsonProperty("Body")
	public BodyResponse getBody() {
		return body;
	}

	public void setBody(BodyResponse body) {
		this.body = body;
	}
}
