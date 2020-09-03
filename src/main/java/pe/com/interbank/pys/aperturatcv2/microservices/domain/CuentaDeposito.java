package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CuentaDeposito {

	private String codigoBanco;
	private String codigoMoneda;
	private String codigoCategoria;
	private String codigoTienda;
	private String numeroCuenta;

	public CuentaDeposito(@JsonProperty("codigoBanco") String codigoBanco,
			@JsonProperty("codigoMoneda") String codigoMoneda, @JsonProperty("codigoCategoria") String codigoCategoria,
			@JsonProperty("codigoTienda") String codigoTienda, @JsonProperty("numeroCuenta") String numeroCuenta) {
		this.codigoBanco = codigoBanco;
		this.codigoMoneda = codigoMoneda;
		this.codigoCategoria = codigoCategoria;
		this.codigoTienda = codigoTienda;
		this.numeroCuenta = numeroCuenta;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getCodigoMoneda() {
		return codigoMoneda;
	}

	public void setCodigoMoneda(String codigoMoneda) {
		this.codigoMoneda = codigoMoneda;
	}

	public String getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(String codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

}
