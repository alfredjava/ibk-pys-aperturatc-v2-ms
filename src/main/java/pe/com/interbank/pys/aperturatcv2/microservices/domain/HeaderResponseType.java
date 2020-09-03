package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeaderResponseType {

	private HeaderResponse headerResponse;

	@JsonCreator
	public HeaderResponseType(@JsonProperty("HeaderResponse") HeaderResponse headerResponse) {
		this.headerResponse = headerResponse;
	}

	@JsonProperty("HeaderResponse")
	public HeaderResponse getHeaderResponse() {
		return headerResponse;
	}

	public void setHeaderResponse(HeaderResponse headerResponse) {
		this.headerResponse = headerResponse;
	}
}
