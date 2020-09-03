package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrabarLPDP {

	private String flagCliente;
	private String flagLPD;
	private String tipoConsentimiento;
	private String idEmpresa;

	public GrabarLPDP(@JsonProperty("flagCliente") String flagCliente, @JsonProperty("flagLPD") String flagLPD,
			@JsonProperty("tipoConsentimiento") String tipoConsentimiento, @JsonProperty("idEmpresa") String idEmpresa) {
		this.flagCliente = flagCliente;
		this.flagLPD = flagLPD;
		this.tipoConsentimiento = tipoConsentimiento;
		this.idEmpresa = idEmpresa;
	}

	public String getFlagCliente() {
		return flagCliente;
	}

	public void setFlagCliente(String flagCliente) {
		this.flagCliente = flagCliente;
	}

	public String getFlagLPD() {
		return flagLPD;
	}

	public void setFlagLPD(String flagLPD) {
		this.flagLPD = flagLPD;
	}

	public String getTipoConsentimiento() {
		return tipoConsentimiento;
	}

	public void setTipoConsentimiento(String tipoConsentimiento) {
		this.tipoConsentimiento = tipoConsentimiento;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
