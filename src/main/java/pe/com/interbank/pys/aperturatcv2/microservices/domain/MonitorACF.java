package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitorACF {
	
	private String tipoMensaje;
	private String tarjetaLogin;
	private String codigoTransaccion;
	private String reverso;
	private String numeroTransaccion;
	private String condicionPtoVenta;
	private String codigoRespuesta;
	private String razonRespuesta;
	private String codigoServicio;
	private String codigoEquipoCliente;
	private String codigoTienda;
	private String nombreComercio;
	private String localidadComercio;
	private String paisOrigen;
	private String direccionCorrespondencia;
	private String codigoProcedencia;

	public MonitorACF(@JsonProperty("tipoMensaje") String tipoMensaje,//NOSONAR
			@JsonProperty("tarjetaLogin") String tarjetaLogin,
			@JsonProperty("codigoTransaccion") String codigoTransaccion, @JsonProperty("reverso") String reverso,
			@JsonProperty("numeroTransaccion") String numeroTransaccion,
			@JsonProperty("condicionPtoVenta") String condicionPtoVenta,
			@JsonProperty("codigoRespuesta") String codigoRespuesta,
			@JsonProperty("razonRespuesta") String razonRespuesta,
			@JsonProperty("codigoServicio") String codigoServicio,
			@JsonProperty("codigoEquipoCliente") String codigoEquipoCliente,
			@JsonProperty("codigoTienda") String codigoTienda, @JsonProperty("nombreComercio") String nombreComercio,
			@JsonProperty("localidadComercio") String localidadComercio, @JsonProperty("paisOrigen") String paisOrigen,
			@JsonProperty("direccionCorrespondencia") String direccionCorrespondencia,
			@JsonProperty("codigoProcedencia") String codigoProcedencia) {
		this.tipoMensaje = tipoMensaje;
		this.tarjetaLogin = tarjetaLogin;
		this.codigoTransaccion = codigoTransaccion;
		this.reverso = reverso;
		this.numeroTransaccion = numeroTransaccion;
		this.condicionPtoVenta = condicionPtoVenta;
		this.codigoRespuesta = codigoRespuesta;
		this.razonRespuesta = razonRespuesta;
		this.codigoServicio = codigoServicio;
		this.codigoEquipoCliente = codigoEquipoCliente;
		this.codigoTienda = codigoTienda;
		this.nombreComercio = nombreComercio;
		this.localidadComercio = localidadComercio;
		this.paisOrigen = paisOrigen;
		this.direccionCorrespondencia = direccionCorrespondencia;
		this.codigoProcedencia = codigoProcedencia;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getTarjetaLogin() {
		return tarjetaLogin;
	}

	public void setTarjetaLogin(String tarjetaLogin) {
		this.tarjetaLogin = tarjetaLogin;
	}

	public String getCodigoTransaccion() {
		return codigoTransaccion;
	}

	public void setCodigoTransaccion(String codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}

	public String getReverso() {
		return reverso;
	}

	public void setReverso(String reverso) {
		this.reverso = reverso;
	}

	public String getNumeroTransaccion() {
		return numeroTransaccion;
	}

	public void setNumeroTransaccion(String numeroTransaccion) {
		this.numeroTransaccion = numeroTransaccion;
	}

	public String getCondicionPtoVenta() {
		return condicionPtoVenta;
	}

	public void setCondicionPtoVenta(String condicionPtoVenta) {
		this.condicionPtoVenta = condicionPtoVenta;
	}

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public String getRazonRespuesta() {
		return razonRespuesta;
	}

	public void setRazonRespuesta(String razonRespuesta) {
		this.razonRespuesta = razonRespuesta;
	}

	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public String getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public String getCodigoEquipoCliente() {
		return codigoEquipoCliente;
	}

	public void setCodigoEquipoCliente(String codigoEquipoCliente) {
		this.codigoEquipoCliente = codigoEquipoCliente;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	public String getNombreComercio() {
		return nombreComercio;
	}

	public void setNombreComercio(String nombreComercio) {
		this.nombreComercio = nombreComercio;
	}

	public String getLocalidadComercio() {
		return localidadComercio;
	}

	public void setLocalidadComercio(String localidadComercio) {
		this.localidadComercio = localidadComercio;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public String getDireccionCorrespondencia() {
		return direccionCorrespondencia;
	}

	public void setDireccionCorrespondencia(String direccionCorrespondencia) {
		this.direccionCorrespondencia = direccionCorrespondencia;
	}

	public String getCodigoProcedencia() {
		return codigoProcedencia;
	}

	public void setCodigoProcedencia(String codigoProcedencia) {
		this.codigoProcedencia = codigoProcedencia;
	}

}
