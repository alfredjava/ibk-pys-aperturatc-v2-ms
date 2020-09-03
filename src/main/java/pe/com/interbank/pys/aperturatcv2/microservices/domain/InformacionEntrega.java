package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InformacionEntrega {

	private String indicador;
	private String direccion;
	private String referencia;
	private String codigoDistrito;
	private String codigoProvincia;
	private String codigoDepartamento;
	private String codigoTiendaEntrega;
	private String tipoDireccion;
	private String numeroTelefono;
	private String mail;
	private String fechaEntrega;
	private String horaInicio;
	private String horaFin;

	public InformacionEntrega(@JsonProperty("indicador") String indicador, @JsonProperty("direccion") String direccion,//NOSONAR
			@JsonProperty("referencia") String referencia, @JsonProperty("codigoDistrito") String codigoDistrito,
			@JsonProperty("codigoProvincia") String codigoProvincia,
			@JsonProperty("codigoDepartamento") String codigoDepartamento,
			@JsonProperty("codigoTiendaEntrega") String codigoTiendaEntrega,
			@JsonProperty("tipoDireccion") String tipoDireccion, @JsonProperty("numeroTelefono") String numeroTelefono,
			@JsonProperty("mail") String mail, @JsonProperty("fechaEntrega") String fechaEntrega,
			@JsonProperty("horaInicio") String horaInicio, @JsonProperty("horaFin") String horaFin) {
		this.indicador = indicador;
		this.direccion = direccion;
		this.referencia = referencia;
		this.codigoDistrito = codigoDistrito;
		this.codigoProvincia = codigoProvincia;
		this.codigoDepartamento = codigoDepartamento;
		this.codigoTiendaEntrega = codigoTiendaEntrega;
		this.tipoDireccion = tipoDireccion;
		this.numeroTelefono = numeroTelefono;
		this.mail = mail;
		this.fechaEntrega = fechaEntrega;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getCodigoTiendaEntrega() {
		return codigoTiendaEntrega;
	}

	public void setCodigoTiendaEntrega(String codigoTiendaEntrega) {
		this.codigoTiendaEntrega = codigoTiendaEntrega;
	}

	public String getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
}
