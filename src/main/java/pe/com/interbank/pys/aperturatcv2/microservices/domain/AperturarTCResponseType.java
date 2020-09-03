package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import pe.com.interbank.pys.altatarjeta.client.domain.BeneficiarioRespuesta;

public class AperturarTCResponseType {
	
	private String codigoUnico;
	private String numeroTarjeta;
	private String numeroCuenta;
	private String fechaAlta;
	private String fechaVencimiento;
	private List<BeneficiarioRespuesta> beneficiariosRespuesta;

	@JsonCreator
	public AperturarTCResponseType(@JsonProperty("codigoUnico") String codigoUnico, @JsonProperty("numeroTarjeta") String numeroTarjeta,
			@JsonProperty("numeroCuenta") String numeroCuenta, @JsonProperty("fechaAlta") String fechaAlta,
			@JsonProperty("fechaVencimiento") String fechaVencimiento,
			@JsonProperty("beneficiariosRespuesta") List<BeneficiarioRespuesta> beneficiariosRespuesta) {
		this.codigoUnico = codigoUnico;
		this.numeroTarjeta = numeroTarjeta;
		this.numeroCuenta = numeroCuenta;
		this.fechaAlta = fechaAlta;
		this.fechaVencimiento = fechaVencimiento;
		this.beneficiariosRespuesta = beneficiariosRespuesta;
	}

	public String getCodigoUnico() {
		return codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<BeneficiarioRespuesta> getBeneficiariosRespuesta() {
		return beneficiariosRespuesta;
	}

	public void setBeneficiariosRespuesta(List<BeneficiarioRespuesta> beneficiariosRespuesta) {
		this.beneficiariosRespuesta = beneficiariosRespuesta;
	}
}
