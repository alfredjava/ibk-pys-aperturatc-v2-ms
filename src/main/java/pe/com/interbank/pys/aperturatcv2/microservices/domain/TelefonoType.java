package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelefonoType {

	private String tipoTelefono;
	private String codigoCiudad;
	private String numeroTelefono;
	private String numeroCelular;
	private String numeroAnexo;

	public TelefonoType(@JsonProperty("tipoTelefono") String tipoTelefono,
			@JsonProperty("codigoCiudad") String codigoCiudad, @JsonProperty("numeroTelefono") String numeroTelefono,
			@JsonProperty("numeroCelular") String numeroCelular, @JsonProperty("numeroAnexo") String numeroAnexo) {
		this.tipoTelefono = tipoTelefono;
		this.codigoCiudad = codigoCiudad;
		this.numeroTelefono = numeroTelefono;
		this.numeroCelular = numeroCelular;
		this.numeroAnexo = numeroAnexo;
	}

	public String getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(String tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public String getCodigoCiudad() {
		return codigoCiudad;
	}

	public void setCodigoCiudad(String codigoCiudad) {
		this.codigoCiudad = codigoCiudad;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getNumeroAnexo() {
		return numeroAnexo;
	}

	public void setNumeroAnexo(String numeroAnexo) {
		this.numeroAnexo = numeroAnexo;
	}

}
