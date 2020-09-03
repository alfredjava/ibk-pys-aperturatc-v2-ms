package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitorCore {

	private String cuentaOrigen;
	private String sucursalAperturaDestino;
	private String direccionDomicilio;
	private String telefonoResidencia;
	private String statusCuentaProducto;

	public MonitorCore(@JsonProperty("cuentaOrigen") String cuentaOrigen,
			@JsonProperty("sucursalAperturaDestino") String sucursalAperturaDestino,
			@JsonProperty("direccionDomicilio") String direccionDomicilio,
			@JsonProperty("telefonoResidencia") String telefonoResidencia,
			@JsonProperty("statusCuentaProducto") String statusCuentaProducto) {
		this.cuentaOrigen = cuentaOrigen;
		this.sucursalAperturaDestino = sucursalAperturaDestino;
		this.direccionDomicilio = direccionDomicilio;
		this.telefonoResidencia = telefonoResidencia;
		this.statusCuentaProducto = statusCuentaProducto;
	}

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public String getSucursalAperturaDestino() {
		return sucursalAperturaDestino;
	}

	public void setSucursalAperturaDestino(String sucursalAperturaDestino) {
		this.sucursalAperturaDestino = sucursalAperturaDestino;
	}

	public String getDireccionDomicilio() {
		return direccionDomicilio;
	}

	public void setDireccionDomicilio(String direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}

	public String getTelefonoResidencia() {
		return telefonoResidencia;
	}

	public void setTelefonoResidencia(String telefonoResidencia) {
		this.telefonoResidencia = telefonoResidencia;
	}

	public String getStatusCuentaProducto() {
		return statusCuentaProducto;
	}

	public void setStatusCuentaProducto(String statusCuentaProducto) {
		this.statusCuentaProducto = statusCuentaProducto;
	}
}
