package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiarioType {

	private String beneficiarioCodigoUnico;
	private String beneficiarioTipoCliente;
	private String beneficiarioNombreTarjeta;
	private String beneficiarioPorcentaje;
	private String beneficiarioMontoLinea;
	private String importeDisponibleEfectivoAdicional;
	private String porcentajeDisponibleEfectivoAdicional;
	private String beneficiarioCol;

	public BeneficiarioType(@JsonProperty("beneficiarioCodigoUnico") String beneficiarioCodigoUnico,//NOSONAR
			@JsonProperty("beneficiarioTipoCliente") String beneficiarioTipoCliente,
			@JsonProperty("beneficiarioNombreTarjeta") String beneficiarioNombreTarjeta,
			@JsonProperty("beneficiarioPorcentaje") String beneficiarioPorcentaje,
			@JsonProperty("beneficiarioMontoLinea") String beneficiarioMontoLinea,
			@JsonProperty("importeDisponibleEfectivoAdicional") String importeDisponibleEfectivoAdicional,
			@JsonProperty("porcentajeDisponibleEfectivoAdicional") String porcentajeDisponibleEfectivoAdicional,
			@JsonProperty("beneficiarioCol") String beneficiarioCol) {
		this.beneficiarioCodigoUnico = beneficiarioCodigoUnico;
		this.beneficiarioTipoCliente = beneficiarioTipoCliente;
		this.beneficiarioNombreTarjeta = beneficiarioNombreTarjeta;
		this.beneficiarioPorcentaje = beneficiarioPorcentaje;
		this.beneficiarioMontoLinea = beneficiarioMontoLinea;
		this.importeDisponibleEfectivoAdicional = importeDisponibleEfectivoAdicional;
		this.porcentajeDisponibleEfectivoAdicional = porcentajeDisponibleEfectivoAdicional;
		this.beneficiarioCol = beneficiarioCol;
	}

	public String getBeneficiarioCodigoUnico() {
		return beneficiarioCodigoUnico;
	}

	public void setBeneficiarioCodigoUnico(String beneficiarioCodigoUnico) {
		this.beneficiarioCodigoUnico = beneficiarioCodigoUnico;
	}

	public String getBeneficiarioTipoCliente() {
		return beneficiarioTipoCliente;
	}

	public void setBeneficiarioTipoCliente(String beneficiarioTipoCliente) {
		this.beneficiarioTipoCliente = beneficiarioTipoCliente;
	}

	public String getBeneficiarioNombreTarjeta() {
		return beneficiarioNombreTarjeta;
	}

	public void setBeneficiarioNombreTarjeta(String beneficiarioNombreTarjeta) {
		this.beneficiarioNombreTarjeta = beneficiarioNombreTarjeta;
	}

	public String getBeneficiarioPorcentaje() {
		return beneficiarioPorcentaje;
	}

	public void setBeneficiarioPorcentaje(String beneficiarioPorcentaje) {
		this.beneficiarioPorcentaje = beneficiarioPorcentaje;
	}

	public String getBeneficiarioMontoLinea() {
		return beneficiarioMontoLinea;
	}

	public void setBeneficiarioMontoLinea(String beneficiarioMontoLinea) {
		this.beneficiarioMontoLinea = beneficiarioMontoLinea;
	}

	public String getImporteDisponibleEfectivoAdicional() {
		return importeDisponibleEfectivoAdicional;
	}

	public void setImporteDisponibleEfectivoAdicional(String importeDisponibleEfectivoAdicional) {
		this.importeDisponibleEfectivoAdicional = importeDisponibleEfectivoAdicional;
	}

	public String getPorcentajeDisponibleEfectivoAdicional() {
		return porcentajeDisponibleEfectivoAdicional;
	}

	public void setPorcentajeDisponibleEfectivoAdicional(String porcentajeDisponibleEfectivoAdicional) {
		this.porcentajeDisponibleEfectivoAdicional = porcentajeDisponibleEfectivoAdicional;
	}

	public String getBeneficiarioCol() {
		return beneficiarioCol;
	}

	public void setBeneficiarioCol(String beneficiarioCol) {
		this.beneficiarioCol = beneficiarioCol;
	}

}
