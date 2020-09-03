package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeaderRequest {

	private HeaderRequestType headerRequest;//NOSONAR

	@JsonCreator
	public HeaderRequest(@JsonProperty("HeaderRequest") HeaderRequestType headerRequest) {
		this.headerRequest = headerRequest;
	}

	@JsonProperty("HeaderRequest")
	public HeaderRequestType getHeaderRequest() {
		return headerRequest;
	}

	public void setHeaderRequest(HeaderRequestType headerRequest) {
		this.headerRequest = headerRequest;
	}
}
