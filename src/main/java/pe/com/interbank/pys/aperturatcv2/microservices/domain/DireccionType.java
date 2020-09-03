package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DireccionType {

	private String tipoDireccion;
	private String codigoTipoVivienda;
	private String codigoTipoVia;
	private String nombreVia;
	private String numeroCalle;
	private String numeroManzana;
	private String numeroInterior;
	private String lote;
	private String codigoUbigeo;
	private String sector;
	private String urbanizacion;
	private String referencia;
	private String tiempoResidenciaDomicilio;
	private String departamento;
	private String provincia;
	private String distrito;

	public DireccionType(@JsonProperty("tipoDireccion") String tipoDireccion,//NOSONAR
			@JsonProperty("codigoTipoVivienda") String codigoTipoVivienda,
			@JsonProperty("codigoTipoVia") String codigoTipoVia, @JsonProperty("nombreVia") String nombreVia,
			@JsonProperty("numeroCalle") String numeroCalle, @JsonProperty("numeroManzana") String numeroManzana,
			@JsonProperty("numeroInterior") String numeroInterior, @JsonProperty("lote") String lote,
			@JsonProperty("codigoUbigeo") String codigoUbigeo, @JsonProperty("sector") String sector,
			@JsonProperty("urbanizacion") String urbanizacion, @JsonProperty("referencia") String referencia,
			@JsonProperty("tiempoResidenciaDomicilio") String tiempoResidenciaDomicilio,
			@JsonProperty("departamento") String departamento, @JsonProperty("provincia") String provincia,
			@JsonProperty("distrito") String distrito) {
		this.tipoDireccion = tipoDireccion;
		this.codigoTipoVivienda = codigoTipoVivienda;
		this.codigoTipoVia = codigoTipoVia;
		this.nombreVia = nombreVia;
		this.numeroCalle = numeroCalle;
		this.numeroManzana = numeroManzana;
		this.numeroInterior = numeroInterior;
		this.lote = lote;
		this.codigoUbigeo = codigoUbigeo;
		this.sector = sector;
		this.urbanizacion = urbanizacion;
		this.referencia = referencia;
		this.tiempoResidenciaDomicilio = tiempoResidenciaDomicilio;
		this.departamento = departamento;
		this.provincia = provincia;
		this.distrito = distrito;
	}

	public String getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public String getCodigoTipoVivienda() {
		return codigoTipoVivienda;
	}

	public void setCodigoTipoVivienda(String codigoTipoVivienda) {
		this.codigoTipoVivienda = codigoTipoVivienda;
	}

	public String getCodigoTipoVia() {
		return codigoTipoVia;
	}

	public void setCodigoTipoVia(String codigoTipoVia) {
		this.codigoTipoVia = codigoTipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNumeroCalle() {
		return numeroCalle;
	}

	public void setNumeroCalle(String numeroCalle) {
		this.numeroCalle = numeroCalle;
	}

	public String getNumeroManzana() {
		return numeroManzana;
	}

	public void setNumeroManzana(String numeroManzana) {
		this.numeroManzana = numeroManzana;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getCodigoUbigeo() {
		return codigoUbigeo;
	}

	public void setCodigoUbigeo(String codigoUbigeo) {
		this.codigoUbigeo = codigoUbigeo;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getUrbanizacion() {
		return urbanizacion;
	}

	public void setUrbanizacion(String urbanizacion) {
		this.urbanizacion = urbanizacion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTiempoResidenciaDomicilio() {
		return tiempoResidenciaDomicilio;
	}

	public void setTiempoResidenciaDomicilio(String tiempoResidenciaDomicilio) {
		this.tiempoResidenciaDomicilio = tiempoResidenciaDomicilio;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
}
