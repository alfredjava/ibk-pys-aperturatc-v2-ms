package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequest {

	private HeaderRequest header;
	private BodyRequest body;

	@JsonCreator
	public MessageRequest(@JsonProperty("Header") HeaderRequest header, @JsonProperty("Body") BodyRequest body) {
		this.header = header;
		this.body = body;
	}

	public HeaderRequest getHeader() {
		return header;
	}

	public void setHeader(HeaderRequest header) {
		this.header = header;
	}

	public BodyRequest getBody() {
		return body;
	}

	public void setBody(BodyRequest body) {
		this.body = body;
	}
}
