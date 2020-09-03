package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpedienteADQ {

	private String codigoCanal;
	private String codigoPuntoVenta;
	private String codigoTienda;
	private String codigoPromotor;
	private String codigoTipoGestion;
	private String codigoEstadoProceso;
	private String codigoProceso;
	private String codigoSubProceso;
	private String codigoTipoTramite;

	public ExpedienteADQ(@JsonProperty("codigoCanal") String codigoCanal,//NOSONAR
			@JsonProperty("codigoPuntoVenta") String codigoPuntoVenta,
			@JsonProperty("codigoTienda") String codigoTienda, @JsonProperty("codigoPromotor") String codigoPromotor,
			@JsonProperty("codigoTipoGestion") String codigoTipoGestion,
			@JsonProperty("codigoEstadoProceso") String codigoEstadoProceso,
			@JsonProperty("codigoProceso") String codigoProceso,
			@JsonProperty("codigoSubProceso") String codigoSubProceso,
			@JsonProperty("codigoTipoTramite") String codigoTipoTramite) {
		this.codigoCanal = codigoCanal;
		this.codigoPuntoVenta = codigoPuntoVenta;
		this.codigoTienda = codigoTienda;
		this.codigoPromotor = codigoPromotor;
		this.codigoTipoGestion = codigoTipoGestion;
		this.codigoEstadoProceso = codigoEstadoProceso;
		this.codigoProceso = codigoProceso;
		this.codigoSubProceso = codigoSubProceso;
		this.codigoTipoTramite = codigoTipoTramite;
	}

	public String getCodigoCanal() {
		return codigoCanal;
	}

	public void setCodigoCanal(String codigoCanal) {
		this.codigoCanal = codigoCanal;
	}

	public String getCodigoPuntoVenta() {
		return codigoPuntoVenta;
	}

	public void setCodigoPuntoVenta(String codigoPuntoVenta) {
		this.codigoPuntoVenta = codigoPuntoVenta;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	public String getCodigoPromotor() {
		return codigoPromotor;
	}

	public void setCodigoPromotor(String codigoPromotor) {
		this.codigoPromotor = codigoPromotor;
	}

	public String getCodigoTipoGestion() {
		return codigoTipoGestion;
	}

	public void setCodigoTipoGestion(String codigoTipoGestion) {
		this.codigoTipoGestion = codigoTipoGestion;
	}

	public String getCodigoEstadoProceso() {
		return codigoEstadoProceso;
	}

	public void setCodigoEstadoProceso(String codigoEstadoProceso) {
		this.codigoEstadoProceso = codigoEstadoProceso;
	}

	public String getCodigoProceso() {
		return codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public String getCodigoSubProceso() {
		return codigoSubProceso;
	}

	public void setCodigoSubProceso(String codigoSubProceso) {
		this.codigoSubProceso = codigoSubProceso;
	}

	public String getCodigoTipoTramite() {
		return codigoTipoTramite;
	}

	public void setCodigoTipoTramite(String codigoTipoTramite) {
		this.codigoTipoTramite = codigoTipoTramite;
	}

}
