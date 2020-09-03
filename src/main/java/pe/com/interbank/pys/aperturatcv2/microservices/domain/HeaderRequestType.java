package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeaderRequestType {

	private Request request;
	private Identity identity;

	@JsonCreator
	public HeaderRequestType(@JsonProperty("request") Request request,
			@JsonProperty("identity") Identity identity) {
		this.request = request;
		this.identity = identity;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
}
