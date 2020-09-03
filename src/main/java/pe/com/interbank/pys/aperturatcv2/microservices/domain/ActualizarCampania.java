package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActualizarCampania {

	private String tipoRespuesta;
	private String descripcion;
	private String estadoRespuesta;
	private String aplicacion;
	private String codigoEstadoLead;
	private String codigoFuente;
	private String codigoOferta;
	private String nombreOferta;
	private String codigoTratamiento;
	private String nombreTratamiento;
	private String core;
	private String flagNuevoBus;
	private String flagOperacion;
	private String idMensaje;
	private String motivo;
	private ListaProductosType listaProductos;
	private String puntuacion;
	private String resultado;
	private String resumen;
	private String codigoCampania;
	private String nombreCampania;

	public ActualizarCampania(@JsonProperty("tipoRespuesta") String tipoRespuesta,//NOSONAR
			@JsonProperty("descripcion") String descripcion, @JsonProperty("estadoRespuesta") String estadoRespuesta,
			@JsonProperty("aplicacion") String aplicacion, @JsonProperty("codigoEstadoLead") String codigoEstadoLead,
			@JsonProperty("codigoFuente") String codigoFuente, @JsonProperty("codigoOferta") String codigoOferta,
			@JsonProperty("nombreOferta") String nombreOferta,
			@JsonProperty("codigoTratamiento") String codigoTratamiento,
			@JsonProperty("nombreTratamiento") String nombreTratamiento, @JsonProperty("core") String core,
			@JsonProperty("flagNuevoBus") String flagNuevoBus, @JsonProperty("flagOperacion") String flagOperacion,
			@JsonProperty("idMensaje") String idMensaje, @JsonProperty("motivo") String motivo,
			@JsonProperty("listaProductos") ListaProductosType listaProductos,
			@JsonProperty("puntuacion") String puntuacion, @JsonProperty("resultado") String resultado,
			@JsonProperty("resumen") String resumen, @JsonProperty("codigoCampania") String codigoCampania,
			@JsonProperty("nombreCampania") String nombreCampania) {
		this.tipoRespuesta = tipoRespuesta;
		this.descripcion = descripcion;
		this.estadoRespuesta = estadoRespuesta;
		this.aplicacion = aplicacion;
		this.codigoEstadoLead = codigoEstadoLead;
		this.codigoFuente = codigoFuente;
		this.codigoOferta = codigoOferta;
		this.nombreOferta = nombreOferta;
		this.codigoTratamiento = codigoTratamiento;
		this.nombreTratamiento = nombreTratamiento;
		this.core = core;
		this.flagNuevoBus = flagNuevoBus;
		this.flagOperacion = flagOperacion;
		this.idMensaje = idMensaje;
		this.motivo = motivo;
		this.listaProductos = listaProductos;
		this.puntuacion = puntuacion;
		this.resultado = resultado;
		this.resumen = resumen;
		this.codigoCampania = codigoCampania;
		this.nombreCampania = nombreCampania;
	}

	public String getTipoRespuesta() {
		return tipoRespuesta;
	}

	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoRespuesta() {
		return estadoRespuesta;
	}

	public void setEstadoRespuesta(String estadoRespuesta) {
		this.estadoRespuesta = estadoRespuesta;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getCodigoEstadoLead() {
		return codigoEstadoLead;
	}

	public void setCodigoEstadoLead(String codigoEstadoLead) {
		this.codigoEstadoLead = codigoEstadoLead;
	}

	public String getCodigoFuente() {
		return codigoFuente;
	}

	public void setCodigoFuente(String codigoFuente) {
		this.codigoFuente = codigoFuente;
	}

	public String getCodigoOferta() {
		return codigoOferta;
	}

	public void setCodigoOferta(String codigoOferta) {
		this.codigoOferta = codigoOferta;
	}

	public String getNombreOferta() {
		return nombreOferta;
	}

	public void setNombreOferta(String nombreOferta) {
		this.nombreOferta = nombreOferta;
	}

	public String getCodigoTratamiento() {
		return codigoTratamiento;
	}

	public void setCodigoTratamiento(String codigoTratamiento) {
		this.codigoTratamiento = codigoTratamiento;
	}

	public String getNombreTratamiento() {
		return nombreTratamiento;
	}

	public void setNombreTratamiento(String nombreTratamiento) {
		this.nombreTratamiento = nombreTratamiento;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getFlagNuevoBus() {
		return flagNuevoBus;
	}

	public void setFlagNuevoBus(String flagNuevoBus) {
		this.flagNuevoBus = flagNuevoBus;
	}

	public String getFlagOperacion() {
		return flagOperacion;
	}

	public void setFlagOperacion(String flagOperacion) {
		this.flagOperacion = flagOperacion;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public ListaProductosType getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ListaProductosType listaProductos) {
		this.listaProductos = listaProductos;
	}

	public String getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getCodigoCampania() {
		return codigoCampania;
	}

	public void setCodigoCampania(String codigoCampania) {
		this.codigoCampania = codigoCampania;
	}

	public String getNombreCampania() {
		return nombreCampania;
	}

	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
}
