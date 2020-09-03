package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedioContactoEspecializado {

	private String codigoUso;
	private String estado;
	private String tipoDireccion;
	private String tipoVia;
	private String nombreVia;
	private String numero;
	private String manzana;
	private String pisoLote;
	private String interior;
	private String urbanizacion;
	private String referencia;
	private String departamento;
	private String provincia;
	private String distrito;
	private String pais;
	private String ubigeo;
	private String codigoPostal;

	public MedioContactoEspecializado(@JsonProperty("codigoUso") String codigoUso,//NOSONAR
			@JsonProperty("estado") String estado, @JsonProperty("tipoDireccion") String tipoDireccion,
			@JsonProperty("tipoVia") String tipoVia, @JsonProperty("nombreVia") String nombreVia,
			@JsonProperty("numero") String numero, @JsonProperty("manzana") String manzana,
			@JsonProperty("pisoLote") String pisoLote, @JsonProperty("interior") String interior,
			@JsonProperty("urbanizacion") String urbanizacion, @JsonProperty("referencia") String referencia,
			@JsonProperty("departamento") String departamento, @JsonProperty("provincia") String provincia,
			@JsonProperty("distrito") String distrito, @JsonProperty("pais") String pais,
			@JsonProperty("ubigeo") String ubigeo, @JsonProperty("codigoPostal") String codigoPostal) {
		this.codigoUso = codigoUso;
		this.estado = estado;
		this.tipoDireccion = tipoDireccion;
		this.tipoVia = tipoVia;
		this.nombreVia = nombreVia;
		this.numero = numero;
		this.manzana = manzana;
		this.pisoLote = pisoLote;
		this.interior = interior;
		this.urbanizacion = urbanizacion;
		this.referencia = referencia;
		this.departamento = departamento;
		this.provincia = provincia;
		this.distrito = distrito;
		this.pais = pais;
		this.ubigeo = ubigeo;
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoUso() {
		return codigoUso;
	}

	public void setCodigoUso(String codigoUso) {
		this.codigoUso = codigoUso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getManzana() {
		return manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	public String getPisoLote() {
		return pisoLote;
	}

	public void setPisoLote(String pisoLote) {
		this.pisoLote = pisoLote;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
}
