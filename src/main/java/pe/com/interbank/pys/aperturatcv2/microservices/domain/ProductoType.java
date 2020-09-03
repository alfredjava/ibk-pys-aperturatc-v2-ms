package pe.com.interbank.pys.aperturatcv2.microservices.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoType {

	private String codigoProducto;
	private String core;
	private String disponible;
	private String entregaEsperada;
	private String estadoSolicitud;
	private String fechaAlta;
	private String fechaAprobacion;
	private String identificador;
	private String monto;
	private String nombreProducto;
	private String numeroSolicitud;
	private String paquete;
	private String productoVendido;
	private String productoVenta;
	private String tamanio;
	private String tipoTramite;
	private String unidades;

	public ProductoType(@JsonProperty("codigoProducto") String codigoProducto, @JsonProperty("core") String core,//NOSONAR
			@JsonProperty("disponible") String disponible, @JsonProperty("entregaEsperada") String entregaEsperada,
			@JsonProperty("estadoSolicitud") String estadoSolicitud, @JsonProperty("fechaAlta") String fechaAlta,
			@JsonProperty("fechaAprobacion") String fechaAprobacion,
			@JsonProperty("identificador") String identificador, @JsonProperty("monto") String monto,
			@JsonProperty("nombreProducto") String nombreProducto,
			@JsonProperty("numeroSolicitud") String numeroSolicitud, @JsonProperty("paquete") String paquete,
			@JsonProperty("productoVendido") String productoVendido,
			@JsonProperty("productoVenta") String productoVenta, @JsonProperty("tamanio") String tamanio,
			@JsonProperty("tipoTramite") String tipoTramite, @JsonProperty("unidades") String unidades) {
		this.codigoProducto = codigoProducto;
		this.core = core;
		this.disponible = disponible;
		this.entregaEsperada = entregaEsperada;
		this.estadoSolicitud = estadoSolicitud;
		this.fechaAlta = fechaAlta;
		this.fechaAprobacion = fechaAprobacion;
		this.identificador = identificador;
		this.monto = monto;
		this.nombreProducto = nombreProducto;
		this.numeroSolicitud = numeroSolicitud;
		this.paquete = paquete;
		this.productoVendido = productoVendido;
		this.productoVenta = productoVenta;
		this.tamanio = tamanio;
		this.tipoTramite = tipoTramite;
		this.unidades = unidades;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getDisponible() {
		return disponible;
	}

	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}

	public String getEntregaEsperada() {
		return entregaEsperada;
	}

	public void setEntregaEsperada(String entregaEsperada) {
		this.entregaEsperada = entregaEsperada;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(String fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}

	public String getProductoVendido() {
		return productoVendido;
	}

	public void setProductoVendido(String productoVendido) {
		this.productoVendido = productoVendido;
	}

	public String getProductoVenta() {
		return productoVenta;
	}

	public void setProductoVenta(String productoVenta) {
		this.productoVenta = productoVenta;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

}
