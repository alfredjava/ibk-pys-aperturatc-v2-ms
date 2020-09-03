package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedioContactoGeneral {

	private String tipoMedioContacto;
	private String flagModificacion;
	private String estado;
	private String codigoUso;//campo1
	private String descripcion;//campo2
	private String departamento;//campo3
	private String prefijo;//campo4
	private String numeroAnexo;//campo5
	private String idMedioContacto;
	private String observaciones;
	
	
	public MedioContactoGeneral(@JsonProperty("tipoMedioContacto") String tipoMedioContacto,//NOSONAR
	@JsonProperty("flagModificacion") String flagModificacion,
	@JsonProperty("estado") String estado,
	@JsonProperty("codigoUso") String codigoUso,//campo1
	@JsonProperty("descripcion") String descripcion,//campo2
	@JsonProperty("departamento") String departamento,//campo3
	@JsonProperty("prefijo") String prefijo,//campo4
	@JsonProperty("numeroAnexo") String numeroAnexo,//campo5
	@JsonProperty("idMedioContacto") String idMedioContacto,
	@JsonProperty("observaciones") String observaciones) {
		this.tipoMedioContacto = tipoMedioContacto;
		this.flagModificacion = flagModificacion;
		this.estado = estado;
		this.codigoUso = codigoUso;
		this.descripcion = descripcion;
		this.departamento = departamento;
		this.prefijo = prefijo;
		this.numeroAnexo = numeroAnexo;
		this.idMedioContacto = idMedioContacto;
		this.observaciones = observaciones;
	}

	public String getTipoMedioContacto() {
		return tipoMedioContacto;
	}


	public void setTipoMedioContacto(String tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}


	public String getFlagModificacion() {
		return flagModificacion;
	}


	public void setFlagModificacion(String flagModificacion) {
		this.flagModificacion = flagModificacion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCodigoUso() {
		return codigoUso;
	}


	public void setCodigoUso(String codigoUso) {
		this.codigoUso = codigoUso;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getDepartamento() {
		return departamento;
	}


	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}


	public String getPrefijo() {
		return prefijo;
	}


	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}


	public String getNumeroAnexo() {
		return numeroAnexo;
	}


	public void setNumeroAnexo(String numeroAnexo) {
		this.numeroAnexo = numeroAnexo;
	}


	public String getIdMedioContacto() {
		return idMedioContacto;
	}


	public void setIdMedioContacto(String idMedioContacto) {
		this.idMedioContacto = idMedioContacto;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}
