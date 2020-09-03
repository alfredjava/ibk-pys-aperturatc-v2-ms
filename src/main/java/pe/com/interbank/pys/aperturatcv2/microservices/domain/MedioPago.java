package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedioPago {

	private String codigo;
	private String tipo;
	private CuentaDeposito numeroCuenta;

	public MedioPago(@JsonProperty("codigo") String codigo, @JsonProperty("tipo") String tipo,
			@JsonProperty("numeroCuenta") CuentaDeposito numeroCuenta) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.numeroCuenta = numeroCuenta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CuentaDeposito getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(CuentaDeposito numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

}
