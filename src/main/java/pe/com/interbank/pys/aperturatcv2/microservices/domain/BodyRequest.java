package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyRequest {

	private AperturarTCType aperturarTC;

	public BodyRequest(@JsonProperty("aperturarTC") AperturarTCType aperturarTC) {
		this.aperturarTC = aperturarTC;
	}

	public AperturarTCType getAperturarTC() {
		return aperturarTC;
	}

	public void setAperturarTC(AperturarTCType aperturarTC) {
		this.aperturarTC = aperturarTC;
	}

	

}
