package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyResponse {

	private AperturarTCResponseType aperturarTcResponse;

	public BodyResponse(@JsonProperty("aperturarTCResponse") AperturarTCResponseType aperturarTcResponse) {
		this.aperturarTcResponse = aperturarTcResponse;
	}

	@JsonProperty("aperturarTCResponse")
	public AperturarTCResponseType getAperturarTcResponse() {
		return aperturarTcResponse;
	}

	public void setAperturarTcResponse(AperturarTCResponseType aperturarTcResponse) {
		this.aperturarTcResponse = aperturarTcResponse;
	}

}
